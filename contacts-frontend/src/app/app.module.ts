import { MaterialModule } from './material.module';
import { HeaderComponent } from './header/header.component';
import { EditContactComponent } from './edit-contact/edit-contact.component';
import { ContactFormComponent } from './contact-form/contact-form.component';
import { AddContactComponent } from './add-contact/add-contact.component';
import { ContactListComponent } from './contact-list/contact-list.component';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';

import { MatToolbarModule, MatButtonModule, MatListModule } from '@angular/material';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { MsalModule, MsalInterceptor } from '@azure/msal-angular';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { ProfileComponent } from './profile/profile.component';
import { HomeComponent } from './home/home.component';
import { AuthInterceptor } from './auth.interceptor';
import { environment } from 'src/environments/environment';
import { ReactiveFormsModule } from '@angular/forms';
const isIE = window.navigator.userAgent.indexOf('MSIE ') > -1 || window.navigator.userAgent.indexOf('Trident/') > -1;

@NgModule({
  declarations: [
    AppComponent,
    ProfileComponent,
    HomeComponent,
   
    ContactListComponent,
    AddContactComponent,
    ProfileComponent,
    ContactFormComponent,
    EditContactComponent,
    HeaderComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MaterialModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatToolbarModule,
    MatButtonModule,
    MatListModule,
    AppRoutingModule,
    MsalModule.forRoot({
      auth: {
        clientId: '05fa74fe-77ae-459e-abf1-aac9f1ea5b64', // This is your client ID
        authority: 'https://login.microsoftonline.com/0eb87d70-e377-4dd9-9140-ca124f14d99a', // This is your tenant ID
        redirectUri: 'http://localhost:4200'// This is your redirect URI
      },
      cache: {
        cacheLocation: 'localStorage',
        storeAuthStateInCookie: isIE, // set to true for IE 11
      },
    },
    {
      popUp: !isIE,
      consentScopes: [
       'User.Read',
       'openid',
       'profile',
        'api://6a3f3c8e-0fc4-48ab-aa5e-b47b66e82421/access_as_user'
      ],
      unprotectedResources: [],
      protectedResourceMap: [
        ['https://graph.microsoft.com/v1.0/me', ['User.Read',
        'openid',
        'profile', 'api://6a3f3c8e-0fc4-48ab-aa5e-b47b66e82421/access_as_user']],
        [environment.url, [
         'api://6a3f3c8e-0fc4-48ab-aa5e-b47b66e82421/access_as_user','openid',
         'profile']]
      ],
      extraQueryParameters: {}
    })
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: MsalInterceptor,
      multi: true
    },
   // {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true},
  ],
  entryComponents: [AddContactComponent],

  bootstrap: [AppComponent]
})
export class AppModule { }
