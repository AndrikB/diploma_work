import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {UserPageComponent} from './user-page/user-page.component';
import {UserPageRoutingModule} from "./user-page-routing.module";

@NgModule({
  imports: [
    CommonModule,
    UserPageRoutingModule
  ],
  declarations: [UserPageComponent]
})
export class UserPageModule {
}
