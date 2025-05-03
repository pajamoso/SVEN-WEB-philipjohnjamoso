import { Component, Inject } from '@angular/core';
import { DOCUMENT } from '@angular/common';

@Component({
  selector: 'app-about-us',
  standalone: true,
  imports: [],
  templateUrl: './about-us.component.html',
  styleUrls: ['./about-us.component.scss']
})
export class AboutUsComponent {
  constructor(@Inject(DOCUMENT) private document: Document) {}

  scrollToAppointment(): void {
    const element = this.document.getElementById('appointment');
    element?.scrollIntoView({ behavior: 'smooth' });
  }

scrollTo(section: string): void {
  document.getElementById(section)?.scrollIntoView({ behavior: 'smooth' });
}

}
