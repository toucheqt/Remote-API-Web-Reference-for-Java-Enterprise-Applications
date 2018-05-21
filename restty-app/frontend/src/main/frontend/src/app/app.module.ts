import { AppRoutingModule } from './app-routing.module';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NotificationModule, PatternFlyNgModule, ChartModule, ListModule, WizardModule } from 'patternfly-ng';
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
import { HeaderService } from './services/header.service';
import { HeadersTableComponent } from './page-details/settings/headers-table/headers-table.component';
import { AddHeaderComponent } from './page-details/settings/headers-table/add-header/add-header.component';
import { FailuresTableComponent } from './page-details/project-details/failures-table/failures-table.component';
import { ApiDetailComponent } from './page-details/api-detail/api-detail.component';
import { RecentTableComponent } from './page-details/project-details/recent-table/recent-table.component';
import { TestCaseDetailComponent } from './page-details/test-case-detail/test-case-detail.component';
import { TimeAgoPipe } from 'time-ago-pipe';
import { TestCaseTableComponent } from './page-details/test-cases/test-case-table/test-case-table.component';
import { AddTestCaseComponent } from './page-details/test-cases/add-test-case/add-test-case.component';
import { ApiTableComponent } from './page-details/api/api-table/api-table.component';
import { BasicContentComponent } from './page-details/api/api-table/basic-content/basic-content.component';
import { ApiDetailTabComponent } from './page-details/api-detail/api-detail-tab/api-detail-tab.component';
import { ApiConfigTabComponent } from './page-details/api-detail/api-config-tab/api-config-tab.component';
import { ApiHistoryTableComponent } from './page-details/api-detail/api-history-table/api-history-table.component';
import { LogService } from './services/log.service';
import { AddEndpointHeaderComponent } from './page-details/api-detail/api-config-tab/add-endpoint-header/add-endpoint-header.component';
import { EditEndpointHeaderComponent } from './page-details/api-detail/api-config-tab/edit-endpoint-header/edit-endpoint-header.component';
import { EditParamComponent } from './page-details/api-detail/api-config-tab/edit-param/edit-param.component';
import { ModelService } from './services/model.service';
import { ParameterService } from './services/parameter.service';
import { TestCaseDetailTabComponent } from './page-details/test-case-detail/test-case-detail-tab/test-case-detail-tab.component';
import { TestCaseHistoryTabComponent } from './page-details/test-case-detail/test-case-history-tab/test-case-history-tab.component';
import { TestCaseSettingsTableComponent } from './page-details/test-case-detail/test-case-detail-tab/test-case-settings-table/test-case-settings-table.component';
import { TestCaseSettingsService } from './services/test-case-settings.service';
import { AddTestStepComponent } from './page-details/test-case-detail/test-case-detail-tab/test-case-settings-table/add-test-step/add-test-step.component';
import { NgSelectModule } from '@ng-select/ng-select';
import { TestCaseSettingsComponent } from './page-details/test-case-settings/test-case-settings.component';

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
    AddTestCaseComponent,
    ApiTableComponent,
    BasicContentComponent,
    ApiDetailTabComponent,
    ApiConfigTabComponent,
    ApiHistoryTableComponent,
    AddEndpointHeaderComponent,
    EditEndpointHeaderComponent,
    EditParamComponent,
    TestCaseDetailTabComponent,
    TestCaseHistoryTabComponent,
    TestCaseSettingsTableComponent,
    AddTestStepComponent,
    TestCaseSettingsComponent,
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
    ChartModule,
    ListModule,
    NgSelectModule
  ],
  providers: [
    ProjectService,
    EndpointService,
    TestCaseService,
    HeaderService,
    LogService,
    ParameterService,
    ModelService,
    TestCaseSettingsService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
