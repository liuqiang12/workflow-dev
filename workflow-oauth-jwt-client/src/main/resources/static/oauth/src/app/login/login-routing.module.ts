import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
/**
 * app路由
 */
const appRoutes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  {
    path: 'login',
    loadChildren: 'app/login/login.module#LoginModule'
  },
  {
    path: 'app',
    loadChildren: 'app/main/main.module#MainModule'
  }
];

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: []
})
export class LoginRoutingModule { }
