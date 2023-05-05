import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ViewProfileComponent} from "./view-profile/view-profile.component";
import {AuthGuardService} from "../core/services/auth-guard.service";
import {EditProfileComponent} from "./edit-profile/edit-profile.component";


const routes: Routes = [
  {path: 'profile', component: ViewProfileComponent, canActivate: [AuthGuardService]},
  {path: 'users/:userId', component: ViewProfileComponent, canActivate: [AuthGuardService]},
  {path: 'edit-profile', component: EditProfileComponent, canActivate: [AuthGuardService]},

];


@NgModule({
  declarations: [],
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class ProfileRoutingModule {
}
