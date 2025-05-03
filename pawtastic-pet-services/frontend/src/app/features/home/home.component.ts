import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { JsonPipe } from '@angular/common';
import { ApiService } from '../../core/api.service';
import { HeroSectionComponent } from '../../components/hero-section/hero-section.component';
import { AboutUsComponent } from '../../components/about-us/about-us.component';
import { AppointmentFormComponent } from '../../components/appointment-form/appointment-form.component';
import { ToastContainerComponent } from '../../core/toast-container.component';


@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    CommonModule,
    HeroSectionComponent,
    AboutUsComponent,
    AppointmentFormComponent,
    ToastContainerComponent
  ],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  data: any;

  constructor(private api: ApiService) {}

  ngOnInit(): void {
  }
}
