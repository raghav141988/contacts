import { Contact } from './../contact-list/contact-list.component';
import { Component, Output, EventEmitter, ViewChild, OnInit, Input } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, Validators, FormGroupDirective } from '@angular/forms';
import { Observable } from 'rxjs';

@Component({
    selector: 'app-contact-form',
    templateUrl: './contact-form.component.html',
    styleUrls: ['./contact-form.component.scss']
  })
  export class ContactFormComponent implements OnInit {

    @Output() formSubmitEvent =  new EventEmitter();
    // tslint:disable-next-line:no-input-rename
    @Input('contact') contact: Contact;

    formGroup: FormGroup;
    titleAlert = 'This field is required';
    post: any = '';

    constructor(private formBuilder: FormBuilder) { }

    ngOnInit() {
      this.createForm();
      this.setChangeValidate();
    }

    get addressLine1() {
        return this.formGroup.get('address.line1');
      }

    createForm() {
      // tslint:disable-next-line:max-line-length
      const emailregex: RegExp = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      this.formGroup = this.formBuilder.group({
        email: [this.contact ? this.contact.email : null, [Validators.required, Validators.pattern(emailregex)]],
        name: [this.contact ? this.contact.name : null, Validators.required],
       phone: [this.contact ? this.contact.phone : null, Validators.required],
       address:  this.formBuilder.group({
        line1: [this.contact ? this.contact.address.line1 : null, [Validators.required]],
        line2: [this.contact ? this.contact.address.line2 : null, [Validators.required]],
       }),

        validate: ''
      });

    }

    setChangeValidate() {
      this.formGroup.get('validate').valueChanges.subscribe(
        (validate) => {
          if (validate === '1') {
            this.formGroup.get('name').setValidators([Validators.required, Validators.minLength(3)]);
            this.titleAlert = 'You need to specify at least 3 characters';
          } else {
            this.formGroup.get('name').setValidators(Validators.required);
          }
          this.formGroup.get('name').updateValueAndValidity();
        }
      );
    }

    get name() {
      return this.formGroup.get('name') as FormControl;
    }

    checkPassword(control) {
      const enteredPassword = control.value;
      const passwordCheck = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.{8,})/;
      return (!passwordCheck.test(enteredPassword) && enteredPassword) ? { requirements: true } : null;
    }



    getErrorEmail() {
      return this.formGroup.get('email').hasError('required') ? 'Field is required' :
        this.formGroup.get('email').hasError('pattern') ? 'Not a valid emailaddress' :
          this.formGroup.get('email').hasError('alreadyInUse') ? 'This emailaddress is already in use' : '';
    }

    getErrorPassword() {
      // tslint:disable-next-line:max-line-length
      return this.formGroup.get('password').hasError('required') ? 'Field is required (at least eight characters, one uppercase letter and one number)' :
        // tslint:disable-next-line:max-line-length
        this.formGroup.get('password').hasError('requirements') ? 'Password needs to be at least eight characters, one uppercase letter and one number' : '';
    }
    onNoClicked() {

        this.formSubmitEvent.emit(null);

    }
    onSubmit(post) {
      console.log('submit clicked', post);

      const data = {...post,
                 id: this.contact ? this.contact.id : null
    };
      this.formSubmitEvent.emit(data);
  }



}
