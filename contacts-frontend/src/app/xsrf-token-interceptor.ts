
import { Injectable } from '@angular/core';
import {
  HttpEvent, HttpRequest, HttpHandler,
  HttpInterceptor, HttpErrorResponse, HttpXsrfTokenExtractor
} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class XSRFTokenInterceptor implements HttpInterceptor {

  constructor(private tokenExtractor: HttpXsrfTokenExtractor) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

       /*  let requestMethod: string = req.method;
         requestMethod = requestMethod.toLowerCase();

         if (requestMethod && (requestMethod === 'post' || requestMethod === 'delete' || requestMethod === 'put' )) {
             const headerName = 'X-XSRF-TOKEN';
             const token = this.tokenExtractor.getToken() as string;
             console.log(token);
             if (token !== null && !req.headers.has(headerName)) {
               req = req.clone({ headers: req.headers.set(headerName, token) });
             }
          }
*/
         return next.handle(req);
       }
}
