import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ScrollService } from '../../core/scroll.service';

@Component({
  selector: 'app-hero-section',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './hero-section.component.html',
  styleUrls: ['./hero-section.component.scss'],
})
export class HeroSectionComponent {
  constructor(private scrollService: ScrollService) {}

  scrollTo(section: 'about' | 'form' | 'appointment'): void {
    // const targetId = section === 'about' ? 'about-us' : 'appointment-form';
    const targetId =
    section === 'about' ? 'about' :
    section === 'form' || section === 'appointment' ? 'appointment-form' :
    '';
    this.scrollService.scrollToElement(targetId);
  }

  scrollToTop() {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }
  
}
