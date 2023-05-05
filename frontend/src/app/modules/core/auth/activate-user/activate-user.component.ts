import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {SecurityService} from "../../services/security.service";

@Component({
  selector: 'app-activate-user',
  templateUrl: './activate-user.component.html',
  styleUrls: ['./activate-user.component.css']
})
export class ActivateUserComponent implements OnInit {

  constructor(private route: ActivatedRoute,
              private router: Router,
              private securityService: SecurityService) { }

  ngOnInit(): void {
    this.securityService.activateUser(this.route.snapshot.params.token).subscribe(
      _ => this.router.navigate(["login"])
    )
  }
}
