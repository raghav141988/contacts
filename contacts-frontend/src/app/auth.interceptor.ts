import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable, from } from 'rxjs';
import { MsalService } from '@azure/msal-angular';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private authService: MsalService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return from(this.handleAccess(request, next));
  }

  private async handleAccess(request: HttpRequest<any>, next: HttpHandler): Promise<HttpEvent<any>> {

        const requestObj = {
          scopes: [ 'User.Read',
          'openid',
          'profile', 'api://6a3f3c8e-0fc4-48ab-aa5e-b47b66e82421/access_as_user']
      };

        this.authService.acquireTokenSilent(requestObj).then((tokenResponse) => {
          // Callback code here
          console.log(tokenResponse.accessToken);
          if ( tokenResponse.accessToken) {
            request = request.clone({
              setHeaders: {
                Authorization: 'Bearer ' + tokenResponse.accessToken
              }
            });
         }

      }).catch((error) => {
          console.log(error);
      });

        return next.handle(request).toPromise();

   }
}
