import { AppRoutingModule } from './app-routing.module';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NotificationModule, PatternFlyNgModule } from 'patternfly-ng';
import { HttpClientModule } from '@angular/common/http';
import { ProjectService } from './services/project.service';
import { NgxPopperModule } from 'ngx-popper';


import { AppComponent } from './app.component';
import { MastheadComponent } from './components/navigation/masthead/masthead.component';
import { ProjectExplorerComponent } from './pages/project-explorer/project-explorer.component';
import { EmptyProjectComponent } from './pages/empty-project/empty-project.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CreateProjectButtonComponent } from './components/create-project-button/create-project-button.component';
import { RouterModule } from '@angular/router';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { VerticalNavigationComponent } from './components/navigation/vertical-navigation/vertical-navigation.component';

@NgModule({
  declarations: [
    AppComponent,
    MastheadComponent,
    ProjectExplorerComponent,
    EmptyProjectComponent,
    CreateProjectButtonComponent,
    DashboardComponent,
    NotFoundComponent,
    VerticalNavigationComponent
  ],
  imports: [
    BrowserModule,
    NotificationModule,
    HttpClientModule,
    NgxPopperModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule,
    PatternFlyNgModule
  ],
  providers: [
    ProjectService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
