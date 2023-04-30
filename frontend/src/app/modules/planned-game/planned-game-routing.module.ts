import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthGuardService} from "../core/services/auth-guard.service";
import {AllPlannedGamesComponent} from "./all-planned-games/all-planned-games.component";


const routes: Routes = [
  {path: 'plannedGames', component: AllPlannedGamesComponent, canActivate: [AuthGuardService]},
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
export class PlannedGameRoutingModule {
}
