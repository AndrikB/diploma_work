import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../../core/services/user.service";
import {SecurityService} from "../../core/services/security.service";
import {FormControl, Validators} from "@angular/forms";

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css']
})
export class EditProfileComponent implements OnInit {
  firstNameFormControl = new FormControl('', [Validators.pattern("^[a-zA-Zа-яА-Яієї']{0,25}$")]);
  lastNameFormControl = new FormControl('', [Validators.pattern("^[a-zA-Zа-яА-Яієї']{0,25}$")]);
  descriptionFormControl = new FormControl('', []);

  currentPasswordFormControl = new FormControl('', [Validators.required, Validators.minLength(8)]);
  newPasswordFormControl = new FormControl('', [Validators.required, Validators.minLength(8)]);
  confirmPasswordFormControl = new FormControl('', [Validators.required]);

  userData: any;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private userService: UserService,
              private securityService: SecurityService) {
  }

  ngOnInit(): void {
    this.userService.getMyUser().subscribe(data => {
      this.userData = data;
      this.firstNameFormControl.setValue(data.firstName)
      this.lastNameFormControl.setValue(data.secondName)
      this.descriptionFormControl.setValue(data.profile)
    });
  }

  changePassword() {
    if (!this.currentPasswordFormControl.valid || !this.newPasswordFormControl.valid || !this.confirmPasswordFormControl.valid)
      return

    let oldPassword = this.currentPasswordFormControl.value
    let newPassword = this.newPasswordFormControl.value

    this.userService.updatePassword({oldPassword, newPassword}).subscribe(
      data => this.router.navigate(["profile"])
    )
  }

  save() {
    if (!this.firstNameFormControl.valid || !this.lastNameFormControl.valid || !this.descriptionFormControl.valid)
      return
    this.userData.firstName = this.firstNameFormControl.value
    this.userData.secondName = this.lastNameFormControl.value
    this.userData.profile = this.descriptionFormControl.value

    this.userService.updateProfile(this.userData).subscribe(
      data => this.router.navigate(["profile"])
    )
  }

  public matchEquality() {
    console.log(this.newPasswordFormControl.errors)
    if (this.newPasswordFormControl.value === this.confirmPasswordFormControl.value) {
      return this.confirmPasswordFormControl.setErrors(null);
    } else {
      return this.confirmPasswordFormControl.setErrors({notequal: true});
    }
  }
}
