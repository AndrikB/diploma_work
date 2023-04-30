import {Component, OnInit} from '@angular/core';
import {BggApiService} from "../../../core/services/bgg-api.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-game-types-list',
  templateUrl: './game-types-list.component.html',
  styleUrls: ['./game-types-list.component.css']
})
export class GameTypesListComponent implements OnInit {

  types: Array<any> | undefined
  type: string = '';

  constructor(private bggApiService: BggApiService,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(routeParams => {
      console.log(routeParams)
      console.log(this.type)
      if (this.type != routeParams.type){
        this.type = routeParams.type
        this.bggApiService.getGameType(routeParams.type).subscribe(
          data => this.types = data
        )
      }
    })
  }
}
