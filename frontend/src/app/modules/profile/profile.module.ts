import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ViewProfileComponent} from './view-profile/view-profile.component';
import {ProfileRoutingModule} from "./profile-routing.module";
import {EditProfileComponent} from './edit-profile/edit-profile.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatTabsModule} from "@angular/material/tabs";
import {MatInputModule} from "@angular/material/input";


@NgModule({
  declarations: [
    ViewProfileComponent,
    EditProfileComponent
  ],
  imports: [
    ProfileRoutingModule,
    CommonModule,
    FormsModule,
    MatTabsModule,
    MatInputModule,
    ReactiveFormsModule
  ]
})
export class ProfileModule {
}
