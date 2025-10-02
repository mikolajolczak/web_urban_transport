import {
  HttpRequest,
  HttpInterceptorFn,
  HttpHandlerFn
} from '@angular/common/http';

export const jwtInterceptor: HttpInterceptorFn = (req: HttpRequest<unknown>, next: HttpHandlerFn) => {
  req = req.clone({
    withCredentials: true
  });

  return next(req);
};
