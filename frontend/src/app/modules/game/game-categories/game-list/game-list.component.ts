import {Component, OnInit} from '@angular/core';
import {BggApiService} from "../../../core/services/bgg-api.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-game-list',
  templateUrl: './game-list.component.html',
  styleUrls: ['./game-list.component.css']
})
export class GameListComponent implements OnInit {

  games: Array<any> | undefined

  constructor(private bggApiService: BggApiService,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(queryParams => {
      console.log(queryParams)
      this.getGames(this.route.snapshot.params.type, queryParams.name);
    });
  }

  private getGames(type: string, name: string) {
    this.bggApiService.getGameByTypeName(type, name).subscribe(
      data => {
        this.games = data.items
        console.log(this.games)
      },
      error => this.games = []
    )
  }
}
