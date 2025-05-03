import { inject } from '@angular/core';
import {
  HttpInterceptorFn,
  HttpRequest,
  HttpHandlerFn,
  HttpEvent,
} from '@angular/common/http';
import { Observable } from 'rxjs';

export const authInterceptor: HttpInterceptorFn = (
  req: HttpRequest<any>,
  next: HttpHandlerFn
): Observable<HttpEvent<any>> => {

  // Hardcoded JWT since there's no login for now
  const token = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VyQGVtYWlsLmNvbSIsImlhdCI6MTY5NTg0ODAwMCwiZXhwIjoxNzAwMDAwMDAwfQ.dummy-signature';
              
  const authToken = `Bearer ${token}`;

  const clonedRequest = req.clone({
    setHeaders: {
      Authorization: authToken,
      
    },
  });

  return next(clonedRequest);
};