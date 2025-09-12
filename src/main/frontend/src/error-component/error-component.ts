import {Component} from '@angular/core';
import {faXmark} from '@fortawesome/free-solid-svg-icons';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {ActivatedRoute} from '@angular/router';

const errorDescriptions: Record<string, string> = {
  "404": "The page you are looking for does not exist or has been moved.",
  "403": "You do not have permission to access this page.",
  "500": "An internal server error occurred. Please try again later."

}

@Component({
  selector: 'app-error-component',
  imports: [FontAwesomeModule],
  templateUrl: './error-component.html',
  styleUrl: './error-component.scss'
})
export class ErrorComponent {
  faXmark = faXmark;
  code: string = "500";
  protected readonly errorDescriptions = errorDescriptions;

  constructor(private route: ActivatedRoute) {
    const codeParam = this.route.snapshot.paramMap.get('code');
    if (codeParam && errorDescriptions.hasOwnProperty(codeParam)) {
      this.code = codeParam;
    }
  }
}
