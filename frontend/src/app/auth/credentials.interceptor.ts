import { HttpInterceptorFn } from '@angular/common/http';

// CRUXTRACK: "WITHCREDENTIALS" TELLS THE BROWSER TO INCLUDE COOKIES (SESSION + CSRF) ON API CALLS
// CRUXTRACK: WITHOUT THIS, SPRING WOULD NOT SEE YOUR LOGIN SESSION AFTER LOGGING IN
export const credentialsInterceptor: HttpInterceptorFn = (req, next) => {
  if (req.url.includes('/api')) {
    return next(req.clone({ withCredentials: true }));
  }
  return next(req);
};
