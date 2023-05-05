import {Component, OnInit} from '@angular/core';
import {PlanGameService} from "../../core/services/plan-game.service";

@Component({
  selector: 'app-all-planned-games',
  templateUrl: './all-planned-games.component.html',
  styleUrls: ['./all-planned-games.component.css']
})
export class AllPlannedGamesComponent implements OnInit {

  planGameList: Array<any> | undefined

  constructor(private planGameService: PlanGameService) {
  }

  ngOnInit(): void {
    this.planGameService.getAllPlannedGames().subscribe(
      date => {
        this.planGameList = date
        console.log(date)
      }
    )
  }

}
