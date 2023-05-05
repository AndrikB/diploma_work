import {Injectable} from '@angular/core';
import {CanActivate, Router} from "@angular/router";
import {SecurityService} from "./security.service";

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {

  constructor(public securityService: SecurityService, public router: Router) {
  }

  canActivate(): boolean {
    console.log(this.securityService.isLoggedIn())
    if (!this.securityService.isLoggedIn()) {
      this.router.navigate(['login']);
      return false;
    }
    return true;
  }
}
