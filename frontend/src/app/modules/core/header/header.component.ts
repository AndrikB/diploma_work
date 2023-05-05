import {Component, OnInit} from '@angular/core';
import {SecurityService} from "../services/security.service";
import {BggApiService} from "../services/bgg-api.service";
import {FormControl} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  isWaiting: boolean = false;
  data: Array<any> | undefined
  myControl = new FormControl();
  private keyUpTimeout: any;

  constructor(public securityService: SecurityService,
              public router: Router,
              public bggApiService: BggApiService) {
  }

  ngOnInit(): void {
    this.myControl.valueChanges.subscribe(next =>
      this.onKey(next));
  }

  onKey(value: string) {
    console.log(value)
    clearTimeout(this.keyUpTimeout);
    this.keyUpTimeout = setTimeout(() => {
      this.getNew(value);
    }, 450);
  }

  select(id: string) {
    this.data = []
    this.router.navigate([`game/${id}`])
    this.myControl.reset();
  }

  private getNew(value: string) {
    console.log("getNew " + value)
    if (this.isWaiting) {
      return;
    }
    this.isWaiting = true;
    this.bggApiService.searchGame(value).subscribe(
      data => {
        this.data = data;
        console.log(data)
      }
    )
    this.isWaiting = false
  }
}
