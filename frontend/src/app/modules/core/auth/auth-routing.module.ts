import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SignUpComponent} from "./sign-up/sign-up.component";
import {CallbackComponent} from "./callback/callback.component";
import {SignInComponent} from "./sign-in/sign-in.component";
import {ActivateUserComponent} from "./activate-user/activate-user.component";

const authenticationRoutes: Routes = [
  {path: 'join', component: SignUpComponent},
  {path: 'login', component: SignInComponent},
  {path: 'callback', component: CallbackComponent},
  {path: 'activate/:token', component: ActivateUserComponent},
];

@NgModule({
  imports: [
    RouterModule.forChild(authenticationRoutes)
  ],
  exports: [
    RouterModule
  ]
})
export class AuthRoutingModule {
}
