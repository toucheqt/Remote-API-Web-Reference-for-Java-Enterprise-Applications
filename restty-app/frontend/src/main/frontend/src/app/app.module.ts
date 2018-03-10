import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NotificationModule } from 'patternfly-ng';
import { HttpClientModule } from '@angular/common/http';
import { ProjectService } from './services/project.service';


import { AppComponent } from './app.component';
import { MastheadComponent } from './components/navigation/masthead/masthead.component';
import { ProjectExplorerComponent } from './components/content/project-explorer/project-explorer.component';
import { EmptyStateComponent } from './components/content/empty-state/empty-state.component';


@NgModule({
  declarations: [
    AppComponent,
    MastheadComponent,
    ProjectExplorerComponent,
    EmptyStateComponent
  ],
  imports: [
    BrowserModule,
    NotificationModule,
    HttpClientModule
  ],
  providers: [
    ProjectService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
