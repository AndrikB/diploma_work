import {Component, OnInit} from '@angular/core';
import {BggApiService} from "../../core/services/bgg-api.service";
import {ActivatedRoute} from "@angular/router";
import {SecurityService} from "../../core/services/security.service";

@Component({
  selector: 'app-view-game',
  templateUrl: './view-game.component.html',
  styleUrls: ['./view-game.component.css']
})
export class ViewGameComponent implements OnInit {

  game: any = null;
  planGame: boolean = false;

  constructor(public bggApiService: BggApiService,
              public securityService: SecurityService,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(routeParams => {
      this.getGame(routeParams.gameId);
    });
  }

  private getGame(gameId: string) {
    console.log(this.route.snapshot.params)
    this.bggApiService.getGameById(gameId).subscribe(
      data => {
        this.game = data
        this.planGame = false
        console.log(this.game)
        this.fixHtmlTag()
      }
    )
  }

  private fixHtmlTag() {
    this.game.description = (this.game.description + "").replace(/&#\d+;/gm,
      function (s) {
        // @ts-ignore
        return String.fromCharCode(s.match(/\d+/gm)[0]);
      })
  }

  convertToLink(category: string, clas: string) {
    let convertedCaegory = category.replace("/", "")
      .replace("  ", " ")
      .replace(' ', '-')
      .toLowerCase()
    return `/games/${clas}/${convertedCaegory}`;
  }
}
