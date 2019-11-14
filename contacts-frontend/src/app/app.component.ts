import { slideInAnimation } from './animation';
import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  animations:  [ slideInAnimation ]
})
export class AppComponent {
  title = 'contactsApp';
}
