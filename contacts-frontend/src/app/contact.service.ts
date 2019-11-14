import { environment } from './../environments/environment';
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
export class ContactService {
  counter = 5;
  contactsUrl = environment.contactAPI;  // URL to web api
  private handleError: HandleError;
  // tslint:disable-next-line:variable-name
  private _editedContact: Contact;

  updateContact(contact: Contact): Observable<Contact> {
    return this.http.post<Contact>(this.contactsUrl, contact, httpOptions)
    .pipe(
      catchError(this.handleError('updateContact', contact))
    );
  }
  getContacts(): Observable<Contact[]> {
   return this.http.get<Contact[]>(this.contactsUrl)
   .pipe(
     catchError(this.handleError('getContacts', []))
   );

  }

  constructor(private http: HttpClient,
              httpErrorHandler: HttpErrorHandler) {
      this.handleError = httpErrorHandler.createHandleError('HeroesService');

     }



  get editedContact() {
    return this._editedContact;
  }
  set editedContact(contact: Contact) {
    this._editedContact = contact;
  }
  addContact(result: Contact): Observable<any> {
    return this.http.post<Contact>(this.contactsUrl, result, httpOptions)
    .pipe(
      catchError(this.handleError('addContact'))
    );
  }
  deleteContact(result: Contact): Observable<{}> {
    const url = `${this.contactsUrl}/${result.id}`;
    return this.http.delete(url, httpOptions)
      .pipe(
        catchError(this.handleError('deleteContact'))
      );


  }
}
