import { ContactService } from './../contact.service';
import { Contact } from './../contact-list/contact-list.component';
import { Component, OnInit } from '@angular/core';
import { slideInAnimation } from '../animation';
import { Observable } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { ActivatedRoute, Router } from '@angular/router';

import { Location } from '@angular/common';
import { MatSnackBar } from '@angular/material/snack-bar';
@Component({
  selector: 'app-edit-contact',
  templateUrl: './edit-contact.component.html',
  styleUrls: ['./edit-contact.component.scss'],
  animations:  [ slideInAnimation ]
})
export class EditContactComponent implements OnInit {

  contact: Contact;
  constructor(private service: ContactService, private router: Router,
     // tslint:disable-next-line:variable-name
              private _snackBar: MatSnackBar
    ) {

  }

  ngOnInit() {
   this.contact = this.service.editedContact;

  }
  openSnackBar(message: string, className: string) {
    this._snackBar.open(message, null,  {
      duration: 2 * 1000,
      panelClass: [className]
    });
  }
  onFormSave(contact: Contact) {
    if (contact) {
    this.service.updateContact(contact).subscribe(() => {
      this.router.navigate(['/contact-list']);
      this.openSnackBar('Contact edit successful', 'success-snackbar');
    },
    (error) => this.openSnackBar(error, 'error-snackbar')
      );
  } else {
    this.router.navigate(['/contact-list']);
  }

}

}
