import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./modules/home/home.component";

const routes: Routes = [
  {path: '', component: HomeComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: false, initialNavigation: 'disabled'})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
