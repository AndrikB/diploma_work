import {Component, OnInit} from '@angular/core';
import {SecurityService} from "../../services/security.service";
import {Router} from "@angular/router";
import {FormControl, Validators} from "@angular/forms";

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent implements OnInit {
  loginFormControl = new FormControl('', [Validators.required]);
  passwordFormControl = new FormControl('', [Validators.required]);

  constructor(private router: Router,
              private securityService: SecurityService) {
  }

  ngOnInit(): void {
    if (this.securityService.isLoggedIn())
      this.router.navigate(['profile'])
  }

  login() {
    if (!this.passwordFormControl.valid || !this.loginFormControl.valid)
      return

    this.securityService.login(this.loginFormControl.value, this.passwordFormControl.value)
      .subscribe(
        data => {
          this.securityService.updateToken(data);
          this.router.navigate(['profile']).then();
        },
        error => {
          console.log(error)
        });
    return false //https://stackoverflow.com/a/50129358/11653398
  }

  loginViaGitHub() {
    this.securityService.loginViaGitHub();
  }
}
