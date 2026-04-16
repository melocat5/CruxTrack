// CRUXTRACK: ROOT APP SETTINGS — WE ADDED HTTP CLIENT + XSRF HERE SO LOGIN CAN TALK TO SPRING BOOT SAFELY
import { ApplicationConfig, provideBrowserGlobalErrorListeners } from '@angular/core';
import { provideHttpClient, withFetch, withInterceptors, withXsrfConfiguration } from '@angular/common/http';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideClientHydration, withEventReplay } from '@angular/platform-browser';

import { credentialsInterceptor } from './auth/credentials.interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideHttpClient(
      // CRUXTRACK: USES THE BROWSER FETCH API (GOOD FOR SERVER-SIDE RENDERING IN ANGULAR)
      withFetch(),
      // CRUXTRACK: ADDS SESSION COOKIES TO /API REQUESTS (SEE CREDENTIALS.INTERCEPTOR.TS)
      withInterceptors([credentialsInterceptor]),
      // CRUXTRACK: MATCHES SPRING SECURITY CSRF COOKIE/HEADER NAMES SO POST LOGIN SENDS THE TOKEN
      withXsrfConfiguration({
        cookieName: 'XSRF-TOKEN',
        headerName: 'X-XSRF-TOKEN',
      }),
    ),
    provideRouter(routes),
    provideClientHydration(withEventReplay()),
  ],
};
