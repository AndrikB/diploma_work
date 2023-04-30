import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {PlannedGameRoutingModule} from "./planned-game-routing.module";
import {PlanGameComponent} from './plan-game/plan-game.component';
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatInputModule} from "@angular/material/input";
import {MatSelectModule} from "@angular/material/select";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatButtonModule} from "@angular/material/button";
import {PlannedGamesListComponent} from './planned-games-list/planned-games-list.component';
import {AllPlannedGamesComponent} from './all-planned-games/all-planned-games.component';


@NgModule({
  declarations: [
    PlanGameComponent,
    PlannedGamesListComponent,
    AllPlannedGamesComponent
  ],
  exports: [
    PlanGameComponent
  ],
  imports: [
    CommonModule,
    PlannedGameRoutingModule,
    MatDatepickerModule,
    MatInputModule,
    MatSelectModule,
    FormsModule,
    MatButtonModule,
    ReactiveFormsModule,
  ]
})
export class PlannedGameModule {
}
