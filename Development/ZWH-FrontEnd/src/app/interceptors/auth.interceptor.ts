import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  // Recupera il token dal localStorage
  const token = localStorage.getItem('token');

  // Se il token esiste, clona la richiesta e aggiunge l'intestazione Authorization
  if (token) {
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });
  }
  // Passa la richiesta modificata (o non modificata) al prossimo handler
  return next(req);
};
