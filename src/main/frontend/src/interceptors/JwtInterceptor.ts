import {
  HttpRequest,
  HttpInterceptorFn,
  HttpHandlerFn
} from '@angular/common/http';

export const jwtInterceptor: HttpInterceptorFn = (req: HttpRequest<any>, next: HttpHandlerFn) => {
  let token: string | null = null;

  if (typeof window !== 'undefined' && window.localStorage) {
    const userString = localStorage.getItem('user');
    if (userString) {
      const user = JSON.parse(userString);
      token = user.token;
    }
  }

  if (token) {
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
  }

  return next(req);
};
