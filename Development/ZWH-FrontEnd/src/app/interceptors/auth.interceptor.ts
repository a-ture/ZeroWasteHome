import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  // Recupera il token utente e il token admin dal localStorage
  const token = localStorage.getItem('token');
  const adminToken = localStorage.getItem('admin_token');

  // Se uno dei token esiste, clona la richiesta e aggiunge l'intestazione Authorization
  if (token) {
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });
  } else if (adminToken) {
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${adminToken}`,
      },
    });
  }
  // Passa la richiesta modificata (o non modificata) al prossimo handler
  return next(req);
};
