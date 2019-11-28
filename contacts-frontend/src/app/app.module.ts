
import { Routes } from '@angular/router';
import { AuthInterceptor } from './auth.interceptor';
import { ContactService } from './contact.service';
import { MaterialModule } from './material.module';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ContactListComponent } from './contact-list/contact-list.component';
import { AddContactComponent } from './add-contact/add-contact.component';
import { ContactFormComponent } from './contact-form/contact-form.component';
import { ReactiveFormsModule } from '@angular/forms';
import { EditContactComponent } from './edit-contact/edit-contact.component';
import { HeaderComponent } from './header/header.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { XSRFTokenInterceptor } from './xsrf-token-interceptor';
import { OktaAuthModule, OktaCallbackComponent } from '@okta/okta-angular';
import {
  OKTA_CONFIG
} from '@okta/okta-angular';
import { OktaAuthGuard } from './app.guard';


const oktaConfig = {
  issuer: 'https://dev-490248.okta.com/oauth2/default',
  redirectUri: window.location.origin + '/implicit/callback',
  clientId: '0oa1y9e5cl9X2xeBq357',
  pkce: true
};
@NgModule({
  declarations: [
    AppComponent,

    ContactListComponent,
    AddContactComponent,
    ContactFormComponent,
    EditContactComponent,
    HeaderComponent
  ],
  imports: [
    BrowserModule,
    OktaAuthModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    ReactiveFormsModule,
    HttpClientModule,

  ],
  providers: [
    OktaAuthGuard,
    {provide: HTTP_INTERCEPTORS, useClass : XSRFTokenInterceptor, multi: true},
    { provide: OKTA_CONFIG, useValue: oktaConfig },
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true},
  ],
  entryComponents: [AddContactComponent],

  bootstrap: [AppComponent]
})
export class AppModule { }
