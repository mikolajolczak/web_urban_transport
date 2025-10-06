import {Routes} from '@angular/router';
import {ErrorComponent} from '../error-component/error-component';
import {HomeComponent} from '../home-component/home-component';
import {AboutComponent} from '../about-component/about-component';
import {VehicleComponent} from '../vehicle-component/vehicle-component';
import {OfficeComponent} from '../office-component/office-component';
import {EmployeeComponent} from '../employee-component/employee-component';
import {StopComponent} from '../stop-component/stop-component';
import {LoginComponent} from '../login-component/login-component';
import {AuthGuard} from '../services/auth-guard.service';
import {RoleGuard} from '../services/role-guard.service';

export const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'error/:code', component: ErrorComponent},
  {path: 'about', component: AboutComponent, canActivate: [AuthGuard]},
  {path: 'offices', component: OfficeComponent, canActivate: [AuthGuard, RoleGuard], data: {role: 'ADMIN'}},
  {path: 'vehicles', component: VehicleComponent, canActivate: [AuthGuard, RoleGuard], data: {role: 'ADMIN'}},
  {path: 'stops', component: StopComponent, canActivate: [AuthGuard, RoleGuard], data: {role: 'ADMIN'}},
  {path: 'login', component: LoginComponent},
  {path: 'employee', component: EmployeeComponent, canActivate: [AuthGuard, RoleGuard], data: {role: 'ADMIN'}},
  {path: '**', redirectTo: 'error/404'}
];
