import {Component} from '@angular/core';
import {faCopyright} from '@fortawesome/free-regular-svg-icons';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';

const currentYear = new Date().getFullYear();

@Component({
  selector: 'app-footer',
  imports: [FontAwesomeModule],
  templateUrl: './footer.html',
  styleUrl: './footer.scss'
})

export class Footer {
  faCopyright = faCopyright;
  currentYear = currentYear;

}
