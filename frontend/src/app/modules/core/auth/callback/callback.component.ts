import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {SecurityService} from "../../services/security.service";

@Component({
  selector: 'app-callback',
  templateUrl: './callback.component.html',
  styleUrls: ['./callback.component.css']
})
export class CallbackComponent implements OnInit {


  constructor(private route: ActivatedRoute,
              private router: Router,
              private securityService: SecurityService) {
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(p => {
      if (p.code != null)
        this.securityService.fetchToken(p.code, p.state).subscribe(data => {
            this.securityService.updateToken(data);
            this.router.navigate(['/profile']);
          },
          error => {
            console.log(error)
          })
    })
  }

}
