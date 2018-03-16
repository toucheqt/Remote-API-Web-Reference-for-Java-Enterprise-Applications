import { AppRoutingModule } from './app-routing.module';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NotificationModule } from 'patternfly-ng';
import { HttpClientModule } from '@angular/common/http';
import { ProjectService } from './services/project.service';
import { NgxPopperModule } from 'ngx-popper';


import { AppComponent } from './app.component';
import { MastheadComponent } from './components/navigation/masthead/masthead.component';
import { ProjectExplorerComponent } from './components/content/project-explorer/project-explorer.component';
import { EmptyProjectComponent } from './components/content/empty-project/empty-project.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CreateProjectButtonComponent } from './components/create-project-button/create-project-button.component';
import { RouterModule } from '@angular/router';
import { DashboardComponent } from './components/content/dashboard/dashboard.component';
import { NotFoundComponent } from './components/content/not-found/not-found.component';


@NgModule({
  declarations: [
    AppComponent,
    MastheadComponent,
    ProjectExplorerComponent,
    EmptyProjectComponent,
    CreateProjectButtonComponent,
    DashboardComponent,
    NotFoundComponent
  ],
  imports: [
    BrowserModule,
    NotificationModule,
    HttpClientModule,
    NgxPopperModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule
  ],
  providers: [
    ProjectService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
