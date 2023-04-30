import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {SecurityService} from "../../core/services/security.service";
import {UserService} from "../../core/services/user.service";

@Component({
  selector: 'app-view-profile',
  templateUrl: './view-profile.component.html',
  styleUrls: ['./view-profile.component.css']
})
export class ViewProfileComponent implements OnInit {

  myProfile: boolean = true;
  userData: any;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private userService: UserService,
              private securityService: SecurityService) {
  }

  ngOnInit(): void {
    if (this.route.snapshot.paramMap.has("userId")) {
      this.myProfile = false
    }
    this.getUser().subscribe(data => {
      this.userData = data;
      console.log(data)
    });
  }

  private getUser() {
    if(this.myProfile) {
      return this.userService.getMyUser()
    }
    else {
      return this.userService.getUser(String(this.route.snapshot.paramMap.get('userId')))
    }
  }
}
