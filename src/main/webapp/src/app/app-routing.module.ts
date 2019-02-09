import { NgModule } from '@angular/core';

import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { BucketObjectsComponent } from './components/bucket-objects/bucket-objects.component';
import { AuthGuard } from './guards';
import { AuthenticationService, AlertService } from './services';
import { Sbs3appService } from './services/sbs3app.service';

const routes: Routes = [
    { path: '', redirectTo: 'index', pathMatch: 'full' },
    { path: 'index', component: DashboardComponent, canActivate: [AuthGuard]},
    { path: 'bucketobjects', component: BucketObjectsComponent, canActivate: [AuthGuard]}];

@NgModule({
    imports: [RouterModule.forRoot(routes, {useHash: true})],
    exports: [RouterModule],
    providers: [AuthGuard, AuthenticationService, AlertService, Sbs3appService]
})
export class AppRoutingModule { }