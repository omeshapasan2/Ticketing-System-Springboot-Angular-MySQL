import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignupClientComponent } from './basic/components/signup-client/signup-client.component';

const routes: Routes = [
  { path: 'register_customer', component: SignupClientComponent},
  { path: 'client', loadChildren: () => import('./client/client.module').then(m => m.ClientModule) },
  { path: 'vendor', loadChildren: () => import('./vendor/vendor.module').then(m => m.VendorModule) }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
