import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [{ path: 'client', loadChildren: () => import('./client/client.module').then(m => m.ClientModule) }, { path: 'vendor', loadChildren: () => import('./vendor/vendor.module').then(m => m.VendorModule) }];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
