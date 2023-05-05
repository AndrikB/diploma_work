import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ViewGameComponent} from "./view-game/view-game.component";
import {GameTypesComponent} from "./game-categories/game-types.component";


const routes: Routes = [
  {path: 'game/:gameId', component: ViewGameComponent},
  {path: 'games/:type', component: GameTypesComponent},
];


@NgModule({
  declarations: [],
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class GameRoutingModule {
}
