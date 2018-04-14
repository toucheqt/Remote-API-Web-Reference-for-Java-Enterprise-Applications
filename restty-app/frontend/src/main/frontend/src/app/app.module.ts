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
import { EditProjectComponent } from './pages/project-explorer/edit-project/edit-project.component';
import { DeleteProjectComponent } from './pages/project-explorer/delete-project/delete-project.component';
import { EndpointService } from './services/endpoint.service';
import { TestCaseService } from './services/test-case.service';
import { SettingsService } from './services/settings.service';
import { HeadersTableComponent } from './page-details/settings/headers-table/headers-table.component';
import { AddHeaderComponent } from './page-details/settings/headers-table/add-header/add-header.component';
import { FailuresTableComponent } from './page-details/project-details/failures-table/failures-table.component';
import { ApiDetailComponent } from './page-details/api-detail/api-detail.component';
import { RecentTableComponent } from './page-details/project-details/recent-table/recent-table.component';
import { TestCaseDetailComponent } from './page-details/test-case-detail/test-case-detail.component';
import { TimeAgoPipe } from 'time-ago-pipe';
import { TestCaseTableComponent } from './page-details/test-cases/test-case-table/test-case-table.component';
import { AddTestCaseComponent } from './page-details/test-cases/add-test-case/add-test-case.component';

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
    HeadersTableComponent,
    AddHeaderComponent,
    FailuresTableComponent,
    ApiDetailComponent,
    RecentTableComponent,
    TestCaseDetailComponent,
    TimeAgoPipe,
    TestCaseTableComponent,
    AddTestCaseComponent
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
    TestCaseService,
    SettingsService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
