import { OktaAuthService } from '@okta/okta-angular';
// app.guard.ts

import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class OktaAuthGuard implements CanActivate {
  oktaAuth;
  authenticated;
  constructor(private okta: OktaAuthService, private router: Router) {

    this.oktaAuth = okta;
  }

  async canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {

    this.authenticated = await this.okta.isAuthenticated();
    console.log('can activate?', this.authenticated);
    if (this.authenticated) { return true; }

    // Redirect to login flow.
    this.oktaAuth.loginRedirect();
    return false;
  }
}
