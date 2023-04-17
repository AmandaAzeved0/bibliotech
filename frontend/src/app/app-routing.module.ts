import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {HomeComponent} from "./components/home/home.component";
import {FullComponent} from "./components/layouts/full/full.component";
import {RouteGaurdService} from "./services/routeGuard/route-gaurd.service";


const routes: Routes = [
  { path: '', component: HomeComponent },
  {
    path: 'bibliotech',
    component: FullComponent,
    children: [
      {
        path: '',
        redirectTo: '/dashboard',
        pathMatch: 'full',
      },
      {
        path: '',
        loadChildren:
          () => import('./components/material-component/material.module').then(m => m.MaterialComponentsModule),
        canActivate:[RouteGaurdService],
        data: {expectedRole: ['ADM', 'USR']}
      },
      {
        path: 'dashboard',
        loadChildren: () => import('./components/dashboard/dashboard.module').then(m => m.DashboardModule),
        canActivate:[RouteGaurdService],
        data: {expectedRole: ['ADM', 'USR']}
      }
    ]
  },
  { path: '**', component: HomeComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
