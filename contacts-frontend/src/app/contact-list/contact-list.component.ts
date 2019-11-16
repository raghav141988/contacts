import { ContactService } from './../contact.service';
import { AddContactComponent } from './../add-contact/add-contact.component';
import { Component, OnInit, Inject, ViewChild } from '@angular/core';

import { Router } from '@angular/router';

import { environment } from 'src/environments/environment';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { slideInAnimation } from '../animation';
import { MatSnackBar } from '@angular/material/snack-bar';
export interface Contact {
  id: string;
  name: string;
  phone: string;
  email: string;
  address: Address;

}
export interface Address {
  line1: string;
  line2: string;

}
@Component({
  selector: 'app-contact-list',
  templateUrl: './contact-list.component.html',
  styleUrls: ['./contact-list.component.scss'],
  animations:  [ slideInAnimation ]
})
export class ContactListComponent implements OnInit {


  public location: any;
  public contactList = [];
  public option = '';
  public selected: any;
  public showProgressBar = false;

  constructor(public dialog: MatDialog, private service: ContactService, private router: Router,
              // tslint:disable-next-line:variable-name
              private _snackBar: MatSnackBar
    ) { }
    openSnackBar(message: string, className) {
      this._snackBar.open(message, null,  {
        duration: 2 * 1000,
        panelClass: [className]
      });
    }
    openDialog()  {
      const dialogRef = this.dialog.open(AddContactComponent, {
        width: '550px',
        height: '650px'
      });
      dialogRef.afterClosed().subscribe(result => {
        console.log('The dialog was closed', result);
        if (result !== null && result !== undefined) {
       this.addContact(result);
     }
      });
    }
addContact(result: Contact) {

  this.service.addContact(result).subscribe(
    contact => {
       this.openSnackBar('Contact added successfully', 'success-snackbar') ;
       console.log(contact);
       this.contactList = this.contactList.concat(contact);
      }
       ,
    error => this.openSnackBar(error, 'error-snackbar' )
    );
}
  ngOnInit() {
   this.service.getContacts().subscribe(contacts => this.contactList = contacts,
    error =>   this.openSnackBar(error, 'error-snackbar')
    );
  }



  getCompleteAddress(address) {
    let completeAddress = '';
    let count = 0;
    // tslint:disable-next-line:forin
    for (const i in address) {
      if (count !== 0) {
        completeAddress += ', ';
      }
      completeAddress += address[i];
      count++;
    }
    return completeAddress;
  }


edit(contact: Contact) {
  this.service.editedContact = contact;
  this.router.navigate(['/contact']);
}
delete(contact: Contact) {
  // tslint:disable-next-line:max-line-length
  this.service.deleteContact(contact).subscribe((success) => {
    this.contactList = this.contactList.filter(eachcontact => eachcontact.id !== contact.id);
    this.openSnackBar('Contact deleted successfully',  'error-success');
  } ,
  (error) =>  this.openSnackBar(error, 'error-snackbar')
  );
}
}
