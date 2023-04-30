import {Component, OnInit} from '@angular/core';
import {SecurityService} from "../../services/security.service";
import {Router} from "@angular/router";
import {FormControl, Validators} from "@angular/forms";

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {
  passwordFormControl = new FormControl('', [Validators.required, Validators.minLength(8)]);
  loginFormControl = new FormControl('', [Validators.required,Validators.minLength(5)]);
  emailFormControl = new FormControl('', [Validators.required, Validators.email]);


  constructor(
    private router: Router,
    private securityService: SecurityService) {
  }

  ngOnInit(): void {
    if (this.securityService.isLoggedIn())
      this.router.navigate(['profile'])
  }

  loginViaGitHub() {
    this.securityService.loginViaGitHub();
  }

  create() {
    if (!this.passwordFormControl.valid || !this.loginFormControl.valid || !this.emailFormControl.valid)
      return

    this.securityService.createAccount(this.loginFormControl.value, this.emailFormControl.value, this.passwordFormControl.value).subscribe(
      data => {
        console.log(data)
        this.router.navigate(['login'])
      },
      error => console.log(error)
    );
    return false
  }
}
