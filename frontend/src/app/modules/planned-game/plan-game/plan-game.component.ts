import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {PlanGameService} from "../../core/services/plan-game.service";

@Component({
  selector: 'app-plan-game',
  templateUrl: './plan-game.component.html',
  styleUrls: ['./plan-game.component.css']
})
export class PlanGameComponent implements OnInit {
  @Input() game: any;
  @Output() newItemEvent = new EventEmitter<string>();

  min = new Date();
  countPlayers: any;
  counts: number[] | undefined;
  public date = new Date();
  address = '';

  constructor(private planGameService: PlanGameService) {
  }

  ngOnInit(): void {
    this.counts = Array.from({length: this.game.maxplayers.value - this.game.minplayers.value + 1},
      (_, i) => i + this.game.minplayers.value)
  }

  planGame() {
    let date = this.date.toISOString()
    let countPlayers = this.countPlayers
    let address = this.address
    let gameId = this.game.id
    // @ts-ignore
    this.planGameService.planGame({date, countPlayers, address, gameId}).subscribe()
    this.newItemEvent.emit('')
  }
}
