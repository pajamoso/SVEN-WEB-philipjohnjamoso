import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ApiService {
  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  getPets(): Observable<any> {
    return this.http.get(`${this.baseUrl}/pets`);
  }

  getSampleData(): Observable<any> {
    return this.http.get('http://localhost:8080/api/test'); // Test
  }

  submitAppointment(data: any): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Authorization': 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VyQGVtYWlsLmNvbSIsImlhdCI6MTY5NTg0ODAwMCwiZXhwIjoxNzAwMDAwMDAwfQ.dummy-signature',
    });
    return this.http.post(`${this.baseUrl}/form/submit`, data, { headers });
  }
  
}
