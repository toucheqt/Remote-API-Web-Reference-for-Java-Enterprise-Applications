import { ApiComponent } from './page-details/api/api.component';
import { ProjectDetailsComponent } from './page-details/project-details/project-details.component';
import { ProjectContainerComponent } from './pages/project-container/project-container.component';
import { EmptyProjectComponent } from './pages/empty-project/empty-project.component';
import { ProjectExplorerComponent } from './pages/project-explorer/project-explorer.component';
import { SettingsComponent } from './page-details/settings/settings.component';
import { TestCasesComponent } from './page-details/test-cases/test-cases.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const appRoutes: Routes = [
  { path: 'new-project', component: EmptyProjectComponent },
  { path: 'projects', component: ProjectExplorerComponent },
  {
    path: 'projects/:id',
    component: ProjectContainerComponent,
    children: [
      {
        path: '',
        component: ProjectDetailsComponent
      },
      {
        path: 'api',
        component: ApiComponent
      },
      {
        path: 'test-cases',
        component: TestCasesComponent
      },
      {
        path: 'settings',
        component: SettingsComponent
      }
    ]
  },
  { path: '**', redirectTo: '/projects' }
];

@NgModule({
  imports: [
    RouterModule.forRoot(appRoutes)
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule { }
