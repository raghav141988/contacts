import { environment } from '../environments/environment';
import { Observable , from } from 'rxjs';
import { Contact } from './contact-list/contact-list.component';
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { HttpErrorHandler, HandleError } from './http-error-handler.service';
import { catchError } from 'rxjs/operators';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json'
  })
};


@Injectable({
  providedIn: 'root'
})
export class AppService {



  private handleError: HandleError;
  // tslint:disable-next-line:variable-name


  constructor(private http: HttpClient,
              httpErrorHandler: HttpErrorHandler) {
      this.handleError = httpErrorHandler.createHandleError('HeroesService');

     }


  getToken(url: string, data: any): Observable<any> {
    const body = Object.keys(data).map(key => key + '=' + data[key]).join('&');
    httpOptions.headers = new HttpHeaders({
      'content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
      accept : 'application/json'
    });
    return this.http.post<any>(url, body, httpOptions)
    .pipe(
      catchError(this.handleError('addContact'))
    );
  }
 
  
}
