import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { RouterModule } from '@angular/router';


import { PageHeaderComponent } from './components/page-header/page-header.component';
import { PageFooterComponent } from './components/page-footer/page-footer.component';
import { PageSidebarComponent } from './components/page-sidebar/page-sidebar.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { BucketObjectsComponent } from './components/bucket-objects/bucket-objects.component';

import { MapIteratorPipe } from './utility/map-iterator.pipe';

import { Sbs3appService } from './services/sbs3app.service';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { AuthModule } from './modules/auth/auth.module';

@NgModule({
  declarations: [
    AppComponent,
    PageHeaderComponent,
    PageFooterComponent,
    PageSidebarComponent,
    DashboardComponent,
    MapIteratorPipe,
    BucketObjectsComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    RouterModule,
    AppRoutingModule,
    AuthModule
  ],
  providers: [Sbs3appService],
  bootstrap: [AppComponent]
})
export class AppModule { }
