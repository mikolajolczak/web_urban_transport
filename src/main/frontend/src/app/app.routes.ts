import {Routes} from '@angular/router';
import {ErrorComponent} from '../error-component/error-component';
import {HomeComponent} from '../home-component/home-component';
import {AboutComponent} from '../about-component/about-component';
import {VehicleComponent} from '../vehicle-component/vehicle-component';
import {OfficeComponent} from '../office-component/office-component';

export const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'error/:code', component: ErrorComponent},
  {path: 'about', component: AboutComponent},
  {path: 'offices', component: OfficeComponent},
  {path: 'vehicles', component: VehicleComponent},
  {path: '**', redirectTo: 'error/404'}
];
