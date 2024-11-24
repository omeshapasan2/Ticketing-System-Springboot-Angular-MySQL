import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignupClientComponent } from './basic/components/signup-client/signup-client.component';
import { SignupVendorComponent } from './basic/components/signup-vendor/signup-vendor.component';
import { LoginComponent } from './basic/components/login/login.component';

const routes: Routes = [
  { path: 'register_customer', component: SignupClientComponent},
  { path: 'register_vendor', component: SignupVendorComponent},
  { path: 'login', component: LoginComponent},
  { path: 'client', loadChildren: () => import('./client/client.module').then(m => m.ClientModule) },
  { path: 'vendor', loadChildren: () => import('./vendor/vendor.module').then(m => m.VendorModule) },

  { path: 'register', redirectTo: 'register_customer', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
