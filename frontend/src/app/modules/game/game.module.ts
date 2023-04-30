import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ViewGameComponent} from './view-game/view-game.component';
import {GameRoutingModule} from "./game-routing.module";
import {GameTypesComponent} from './game-categories/game-types.component';
import {GameListComponent} from "./game-categories/game-list/game-list.component";
import {MatChipsModule} from "@angular/material/chips";
import {GameTypesListComponent} from "./game-categories/game-types-list/game-types-list.component";
import {PlannedGameModule} from "../planned-game/planned-game.module";
import {MatButtonModule} from "@angular/material/button";
import {MatListModule} from "@angular/material/list";


@NgModule({
  declarations: [
    ViewGameComponent,
    GameTypesListComponent,
    GameTypesComponent,
    GameListComponent
  ],
  imports: [
    GameRoutingModule,
    CommonModule,
    MatChipsModule,
    PlannedGameModule,
    MatButtonModule,
    MatListModule
  ]
})
export class GameModule {
}
