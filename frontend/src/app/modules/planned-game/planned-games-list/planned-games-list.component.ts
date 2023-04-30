import {Component, Input, OnInit} from '@angular/core';
import {BggApiService} from "../../core/services/bgg-api.service";
import {SecurityService} from "../../core/services/security.service";
import {PlanGameService} from "../../core/services/plan-game.service";

@Component({
  selector: 'app-planned-games-list',
  templateUrl: './planned-games-list.component.html',
  styleUrls: ['./planned-games-list.component.css']
})
export class PlannedGamesListComponent implements OnInit {
  @Input() plannedGames: Array<any> = [];
  gamesInfo: Array<any> | undefined;

  constructor(private bggApiService: BggApiService,
              private planGameService: PlanGameService,
              private securityService: SecurityService) {
  }

  ngOnInit(): void {
    let userId = this.securityService.getUserId()
    for (let plannedGame of this.plannedGames) {
      plannedGame.userNames = (plannedGame.users as Array<any>).map(user => user.login)
      plannedGame.participate = (plannedGame.users as Array<any>)
        .filter(user => user.id == userId)
        .length != 0
      plannedGame.canParticipate = !plannedGame.participate
      if (plannedGame.currentCount >= plannedGame.countPlayers) {
        plannedGame.canParticipate = false
      }
    }

    this.bggApiService.getGameByIds(this.plannedGames.map(planned => planned.gameId).join(','))
      .subscribe(
        data => {
          this.gamesInfo = data
          console.log(data)
        }
      )
  }

  participate(plannedGameId: string, plannedGame: any) {
    this.planGameService.participateGame({plannedGameId})
      .subscribe(
        data => {
          plannedGame.canParticipate = false;
          plannedGame.currentCount++
          plannedGame.userNames += ', You'
        }
      )
  }
}
