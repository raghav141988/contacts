import {Component, Inject} from '@angular/core';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { FormGroup } from '@angular/forms';
export interface DialogData {
    animal: string;
    name: string;
  }


@Component({
    selector: 'app-add-contact',
    templateUrl: 'add-contact.component.html',
    styleUrls: ['./add-contact.component.scss']
  })
  export class AddContactComponent {

    constructor(
      public dialogRef: MatDialogRef<AddContactComponent>,
      @Inject(MAT_DIALOG_DATA) public data: DialogData) {}

 

    onFormSave($event) {

this.dialogRef.close($event);
    }
  }
