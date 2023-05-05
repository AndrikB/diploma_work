import {Component, OnInit} from '@angular/core';
import {BggApiService} from "../../core/services/bgg-api.service";

@Component({
  selector: 'app-game-classes',
  templateUrl: './game-types.component.html',
  styleUrls: ['./game-types.component.css']
})
export class GameTypesComponent implements OnInit {

  constructor(private bggApiService: BggApiService) {
  }

  ngOnInit(): void {

  }

}
