import { EditContactComponent } from './edit-contact/edit-contact.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ContactListComponent } from './contact-list/contact-list.component';


const routes: Routes = [
  { path: 'contact-list', component: ContactListComponent, data: { animation: 'contacts' } },
  { path: 'contact',      component: EditContactComponent, data: { animation: 'contact' } },

  { path: '',
    redirectTo: '/contact-list',
    pathMatch: 'full'
  }

];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
