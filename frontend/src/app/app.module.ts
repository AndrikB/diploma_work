import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';


import {AppComponent} from './app.component';
import {CoreModule} from "./modules/core/core.module";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {AuthHeaderInterceptor} from "./modules/core/services/auth-header.interceptor";
import {RouterModule} from "@angular/router";
import {AppRoutingModule} from "./app-routing.module";
import {AuthModule} from "./modules/core/auth/auth.module";
import {SecurityService} from "./modules/core/services/security.service";
import {UserPageModule} from "./modules/core/user-page/user-page.module";
import {ProfileModule} from "./modules/profile/profile.module";
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {MDBBootstrapModule} from "angular-bootstrap-md";
import {JWT_OPTIONS, JwtHelperService} from "@auth0/angular-jwt";
import {MatTabsModule} from "@angular/material/tabs";
import {MatDatepickerModule} from '@angular/material/datepicker';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {GameModule} from "./modules/game/game.module";
import {PlannedGameModule} from "./modules/planned-game/planned-game.module";
import {MatNativeDateModule} from "@angular/material/core";
import {ChatModule} from "./modules/chat/chat.module";
import {ChatRoutingModule} from "./modules/chat/chat-routing.module";
import { HomeComponent } from './modules/home/home.component';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    RouterModule,
    HttpClientModule,
    CoreModule,
    AuthModule,
    UserPageModule,
    AppRoutingModule,
    ProfileModule,
    PlannedGameModule,
    GameModule,
    NgbModule,
    MDBBootstrapModule.forRoot(),
    MatTabsModule,
    BrowserAnimationsModule,
    MatDatepickerModule,        // <----- import(must)
    MatNativeDateModule,        // <----- import for date formating(optional)
    MatAutocompleteModule,
    ChatModule,
    ChatRoutingModule,
  ],
  providers: [
    SecurityService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthHeaderInterceptor,
      multi: true
    },
    JwtHelperService, {provide: JWT_OPTIONS, useValue: JWT_OPTIONS},
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
