import { slideInAnimation } from './animation';
import { Component } from '@angular/core';
import { OktaAuthService } from '@okta/okta-angular';
import { AppService } from './app.service';
const  config = {
  client_id: '0oa1y9e5cl9X2xeBq357',
  redirect_uri: 'http://localhost:4200/',
  authorization_endpoint: 'https://dev-490248.okta.com/oauth2/default/v1/authorize',
  token_endpoint: 'https://dev-490248.okta.com/oauth2/default/v1/token',
  requested_scopes: 'openid'
};

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  animations:  [ slideInAnimation ]
})
export class AppComponent {
  title = 'contactsApp';
  isAuthenticated = false;
  private accessToken;
  private error;
  constructor(public oktaAuth: OktaAuthService, public appService: AppService) {
  }
  // tslint:disable-next-line:use-lifecycle-interface
  async ngOnInit() {
    // this.isAuthenticated = await this.oktaAuth.isAuthenticated();
    // // Subscribe to authentication state changes
    // console.log('authenticate', this.isAuthenticated );
    // this.oktaAuth.$authenticationState.subscribe(
    //   (isAuthenticated: boolean)  => {
    //     console.log(isAuthenticated);
    //     this.isAuthenticated = isAuthenticated;
    //   }
    // );


    // Handle the redirect back from the authorization server and
// get an access token from the token endpoint

const q: any = this.parseQueryString(window.location.search.substring(1));

// Check if the server returned an error string
if (q.error) {
    alert('Error returned from authorization server: ' + q.error);
    this.error = q.error + '\n\n' + q.error_description;
   // document.getElementById('error').classList = '';
}

// If the server returned an authorization code, attempt to exchange it for an access token
if (q.code) {

    // Verify state matches what we set at the beginning
    if (localStorage.getItem('pkce_state') !== q.state) {
        alert('Invalid state');
    } else {
const data = {
  grant_type: 'authorization_code',
  code: q.code,
  client_id: config.client_id,
  redirect_uri: config.redirect_uri,
  code_verifier: localStorage.getItem('pkce_code_verifier')};

        // Exchange the authorization code for an access token
        console.log(data);
this.appService.getToken (config.token_endpoint, data ).subscribe (token => {
            console.log(token);
          });

    }

    // Clean these up since we don't need them anymore
    localStorage.removeItem('pkce_state');
    localStorage.removeItem('pkce_code_verifier');
}

  }

  async login() {
    // Create and store a random "state" value
    const state = this.generateRandomString();
    localStorage.setItem('pkce_state', state);

    // Create and store a new PKCE code_verifier (the plaintext random secret)
    // tslint:disable-next-line:variable-name
    const code_verifier = this.generateRandomString();
    localStorage.setItem('pkce_code_verifier', code_verifier);
    // Hash and base64-urlencode the secret to use as the challenge
    // tslint:disable-next-line:variable-name
    const code_challenge = await this.pkceChallengeFromVerifier(code_verifier);

     // Build the authorization URL
    const url = config.authorization_endpoint
     + '?response_type=code'
     + '&client_id=' + encodeURIComponent(config.client_id)
     + '&state=' + encodeURIComponent(state)
     + '&scope=' + encodeURIComponent(config.requested_scopes)
     + '&redirect_uri=' + encodeURIComponent(config.redirect_uri)
     + '&code_challenge=' + encodeURIComponent(code_challenge)
     + '&code_challenge_method=S256'
     ;

 // Redirect to the authorization server
 
    window.location.replace (url);
  }
  parseQueryString(queryString: string) {
    if (queryString === '') { return {}; }
    const segments = queryString.split('&').map(s => s.split('=') );
    const queryStringParam = {};
    segments.forEach(s => queryStringParam[s[0]] = s[1]);
    return queryStringParam;
}

  // PKCE HELPER FUNCTIONS
  // Generate a secure random string using the browser crypto functions
  generateRandomString() {
    const array = new Uint32Array(28);
    window.crypto.getRandomValues(array);
    return Array.from(array, dec => ('0' + dec.toString(16)).substr(-2)).join('');
}
// Calculate the SHA256 hash of the input text.
// Returns a promise that resolves to an ArrayBuffer
sha256(plain) {
  const encoder = new TextEncoder();
  const data = encoder.encode(plain);
  return window.crypto.subtle.digest('SHA-256', data);
}
// Base64-urlencodes the input string
 base64urlencode(str) {
  // Convert the ArrayBuffer to string using Uint8 array to convert to what btoa accepts.
  // btoa accepts chars only within ascii 0-255 and base64 encodes them.
  // Then convert the base64 encoded to base64url encoded
  //   (replace + with -, replace / with _, trim trailing =)
  return btoa(String.fromCharCode.apply(null, new Uint8Array(str)))
      .replace(/\+/g, '-').replace(/\//g, '_').replace(/=+$/, '');
}
// Return the base64-urlencoded sha256 hash for the PKCE challenge
  pkceChallengeFromVerifier = async (v) => {
  const hashed = await this.sha256(v);
  return this.base64urlencode(hashed);
}

}
