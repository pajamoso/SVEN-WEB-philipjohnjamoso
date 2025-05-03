import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl, ValidationErrors } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { ApiService } from '../../core/api.service';
import { ToastService } from '../../core/toast.service';
import { deriveKey, encryptAESGCM } from '../../core/crypto-utils';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-appointment-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './appointment-form.component.html',
  styleUrls: ['./appointment-form.component.scss']
})
export class AppointmentFormComponent implements OnInit {
  appointmentForm!: FormGroup;
  errors: string[] = [];
  today = new Date().toISOString().split('T')[0];

  // passphrase or key is hardcoded here for testing; should be in a config file
  private passphrase = 'super-secret-passphrase';
  private salt = crypto.getRandomValues(new Uint8Array(16));

  constructor(
    private fb: FormBuilder,
    private api: ApiService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.appointmentForm = this.fb.group({
      frequency: this.fb.control('One time', Validators.required),
      date: this.fb.control(this.today, [Validators.required, this.dateValidator]),
      time: this.fb.control('', Validators.required),
      days: this.fb.group({
        mon: this.fb.control(false),
        tue: this.fb.control(false),
        wed: this.fb.control(false),
        thu: this.fb.control(false),
        fri: this.fb.control(false),
        sat: this.fb.control(false),
        sun: this.fb.control(false),
      }, { validators: this.atLeastOneDay }),
      notes: this.fb.control('', Validators.maxLength(500))
    });
  }

  dateValidator(control: AbstractControl): ValidationErrors | null {
    const selected = new Date(control.value);
    selected.setHours(0, 0, 0, 0);
    const now = new Date(); now.setHours(0, 0, 0, 0);
    return selected < now ? { pastDate: true } : null;
  }

  atLeastOneDay(group: AbstractControl): ValidationErrors | null {
    const val = (group.value as Record<string, boolean>);
    const anySelected = Object.values(val).some(v => v);
    return anySelected ? null : { noDay: true };
  }

  async onSubmit(): Promise<void> {
    this.errors = [];
    this.toastService.clear();

    if (this.appointmentForm.invalid) {
      this.appointmentForm.markAllAsTouched();
      if (this.appointmentForm.get('frequency')?.invalid) {
        this.toastService.show('Please select a frequency.', { classname: 'bg-danger text-light', delay: 5000 });
      }
      if (this.appointmentForm.get('date')?.invalid) {
        this.toastService.show('Please choose a valid date (today or future).', { classname: 'bg-danger text-light', delay: 5000 });
      }
      if (this.appointmentForm.get('time')?.invalid) {
        this.toastService.show('Please select a time.', { classname: 'bg-danger text-light', delay: 5000 });
      }
      if (this.appointmentForm.get('days')?.hasError('noDay')) {
        this.toastService.show('Select at least one day.', { classname: 'bg-danger text-light', delay: 5000 });
      }
      if (this.appointmentForm.get('notes')?.hasError('maxlength')) {
        this.toastService.show('Notes cannot exceed 500 characters.', { classname: 'bg-danger text-light', delay: 5000 });
      }
      return;
    }

    try {
      const payload = JSON.stringify(this.appointmentForm.value);
      const key = await deriveKey(this.passphrase, this.salt);
      const encrypted = await encryptAESGCM(payload, key);

      this.api.submitAppointment({ salt: Array.from(this.salt), data: encrypted }).subscribe({
        next: () => {
          this.toastService.show('Your Appointment has been saved!', { classname: 'bg-success text-light', delay: 5000 });
          this.appointmentForm.reset({ frequency: 'One time', date: this.today });
        },
        error: () => {
          this.toastService.show('Send failed.', { classname: 'bg-danger text-light', delay: 5000 });
        }
      });
    } catch (err) {
      console.error('Encryption error', err);
      this.toastService.show('Encryption failed.', { classname: 'bg-danger text-light', delay: 5000 });
    }
  }

  scrollToTop() {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }
}
