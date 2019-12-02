import { OktaAuthService } from '@okta/okta-angular';
// app.guard.ts

import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {
  oktaAuth;
  user;
  constructor(private okta: OktaAuthService, private router: Router) {

    this.oktaAuth = okta;
  }

  async canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {

    this.user = await this.okta.getUser();

    const result = this.user.groups.find((group) => group === 'Admin');
console.log(result);

    if (result) {
        return true;
    } else {
        alert('User is not authorized to perform this operation');
        return false; }

    // Redirect to login flow.

  }
}
