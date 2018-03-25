import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { EmptyProjectComponent } from './pages/empty-project/empty-project.component';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { ProjectExplorerComponent } from './pages/project-explorer/project-explorer.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const appRoutes: Routes = [
  { path: 'new-project', component: EmptyProjectComponent, pathMatch: 'full' },
  { path: 'projects', component: ProjectExplorerComponent , pathMatch: 'full' },
  { path: 'dashboard/:id', component: DashboardComponent, pathMatch: 'full' },
  { path: '**', component: NotFoundComponent, pathMatch: 'full' }
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