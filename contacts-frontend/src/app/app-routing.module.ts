import { AdminGuard } from './admin.guard';

import { OktaAuthGuard } from './app.guard';
import { EditContactComponent } from './edit-contact/edit-contact.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ContactListComponent } from './contact-list/contact-list.component';

import { OktaCallbackComponent } from '@okta/okta-angular';

const routes: Routes = [

  { path: 'contact-list',
  canActivate: [ OktaAuthGuard ],
  component: ContactListComponent, data: { animation: 'contacts' } },
  { path: 'contact', canActivate: [ OktaAuthGuard, AdminGuard ],     component: EditContactComponent, data: { animation: 'contact' } },

  {
    path: 'implicit/callback',
    component: OktaCallbackComponent
  },
  { path: '',
  redirectTo: 'contact-list',

  pathMatch: 'full'
},
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
