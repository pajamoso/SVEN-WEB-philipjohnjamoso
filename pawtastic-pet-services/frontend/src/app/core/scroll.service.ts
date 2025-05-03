import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })

export class ScrollService {
  scrollToElement(id: string): void {
    const el = document.getElementById(id);
    if (el) {
      el.scrollIntoView({ behavior: 'smooth' });
    }
  }

  scrollToTop() {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }
}