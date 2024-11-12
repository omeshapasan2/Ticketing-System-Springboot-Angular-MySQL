import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { VendorRoutingModule } from './vendor-routing.module';
import { VendorComponent } from './vendor.component';
import { VendorDashboardComponent } from './pages/vendor-dashboard/vendor-dashboard.component';


@NgModule({
  declarations: [
    VendorComponent,
    VendorDashboardComponent
  ],
  imports: [
    CommonModule,
    VendorRoutingModule
  ]
})
export class VendorModule { }
