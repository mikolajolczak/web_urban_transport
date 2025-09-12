import { Routes } from '@angular/router';
import {ErrorComponent} from '../error-component/error-component';
import {HomeComponent} from '../home-component/home-component';

export const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'error/:code', component: ErrorComponent},
  {path: '**', redirectTo: 'error/404'}
];
