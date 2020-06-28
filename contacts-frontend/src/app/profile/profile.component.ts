import { Component, OnInit } from '@angular/core';
import { MsalService } from '@azure/msal-angular';
import { HttpClient } from '@angular/common/http';
import { InteractionRequiredAuthError, AuthError } from 'msal';

const GRAPH_ENDPOINT = 'https://graph.microsoft.com/v1.0/me';
const BACKEND_ENDPOINT = 'http://localhost:8080/api/contacts';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  profile;
  serverResponse;

  constructor(private authService: MsalService, private http: HttpClient) { }

  ngOnInit() {
    this.getBackendResponse();
    this.getProfile();
    
  }
  getBackendResponse() {
    
 
    console.log('getting profile');
    this.http.get(BACKEND_ENDPOINT)
    .subscribe({
      next: (profile) => {
        this.profile = profile;
        console.log(profile);
      },
      error: (err: AuthError) => {
        // If there is an interaction required error,
        // call one of the interactive methods and then make the request again.
        if (InteractionRequiredAuthError.isInteractionRequiredError(err.errorCode)) {
          this.authService.acquireTokenPopup({
            scopes: this.authService.getScopesForEndpoint(GRAPH_ENDPOINT)
          })
          .then(() => {
           
          });
        }
      }
    });
  }
  getProfile() {
    console.log('getting profile');
    this.http.get(GRAPH_ENDPOINT)
    .subscribe({
      next: (profile) => {
        this.profile = profile;
       
      },
      error: (err: AuthError) => {
        // If there is an interaction required error,
        // call one of the interactive methods and then make the request again.
        if (InteractionRequiredAuthError.isInteractionRequiredError(err.errorCode)) {
          this.authService.acquireTokenPopup({
            scopes: this.authService.getScopesForEndpoint(GRAPH_ENDPOINT)
          })
          .then(() => {
            this.http.get(GRAPH_ENDPOINT)
              .toPromise()
              .then(profile => {
                this.profile = profile;
                this.getBackendResponse();
              });
          });
        }
      }
    });
  }
}