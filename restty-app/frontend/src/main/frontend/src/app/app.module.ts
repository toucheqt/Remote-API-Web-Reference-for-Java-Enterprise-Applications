import { AppRoutingModule } from './app-routing.module';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NotificationModule, PatternFlyNgModule, ChartModule } from 'patternfly-ng';
import { HttpClientModule } from '@angular/common/http';
import { ProjectService } from './services/project.service';
import { NgxPopperModule } from 'ngx-popper';


import { AppComponent } from './app.component';
import { MastheadComponent } from './components/masthead/masthead.component';
import { ProjectExplorerComponent } from './pages/project-explorer/project-explorer.component';
import { EmptyProjectComponent } from './pages/empty-project/empty-project.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CreateProjectButtonComponent } from './components/create-project-button/create-project-button.component';
import { RouterModule } from '@angular/router';
import { ProjectContainerComponent } from './pages/project-container/project-container.component';
import { ApiComponent } from './page-details/api/api.component';
import { TestCasesComponent } from './page-details/test-cases/test-cases.component';
import { SettingsComponent } from './page-details/settings/settings.component';
import { ProjectDetailsComponent } from './page-details/project-details/project-details.component';
import { EditProjectComponent } from './components/modals/edit-project/edit-project.component';
import { DeleteProjectComponent } from './components/modals/delete-project/delete-project.component';
import { EndpointService } from './services/endpoint.service';
import { TestCaseService } from './services/test-case.service';
import { NoTestCasesComponent } from './page-details/test-cases/no-test-cases/no-test-cases.component';

@NgModule({
  declarations: [
    AppComponent,
    MastheadComponent,
    ProjectExplorerComponent,
    EmptyProjectComponent,
    CreateProjectButtonComponent,
    ProjectContainerComponent,
    ApiComponent,
    TestCasesComponent,
    SettingsComponent,
    ProjectDetailsComponent,
    EditProjectComponent,
    DeleteProjectComponent,
    NoTestCasesComponent
  ],
  imports: [
    BrowserModule,
    NotificationModule,
    HttpClientModule,
    NgxPopperModule,
    FormsModule,
    ReactiveFormsModule,
    PatternFlyNgModule,
    AppRoutingModule,
    ChartModule
  ],
  providers: [
    ProjectService,
    EndpointService,
    TestCaseService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
