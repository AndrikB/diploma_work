import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SignUpComponent} from './sign-up/sign-up.component';
import {AuthRoutingModule} from "./auth-routing.module";
import {CallbackComponent} from './callback/callback.component';
import {SignInComponent} from './sign-in/sign-in.component';
import {ActivateUserComponent} from './activate-user/activate-user.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";

@NgModule({
    imports: [
        CommonModule,
        AuthRoutingModule,
        FormsModule,
        MatInputModule,
        ReactiveFormsModule,
        MatButtonModule,
    ],
  declarations: [SignUpComponent, CallbackComponent, SignInComponent, ActivateUserComponent]
})
export class AuthModule {
}
