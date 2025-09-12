import {Routes} from '@angular/router';
import {ErrorComponent} from '../error-component/error-component';
import {HomeComponent} from '../home-component/home-component';
import {AboutComponent} from '../about-component/about-component';

export const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'error/:code', component: ErrorComponent},
  {path: 'about', component: AboutComponent},
  {path: '**', redirectTo: 'error/404'}
];
