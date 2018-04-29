import { Project } from '../../model/project';
import { ProjectService } from '../../services/project.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { SortEvent, SortConfig, SortField, ActionConfig, Action } from 'patternfly-ng';

declare var $: any;

@Component({
  selector: 'app-project-explorer',
  templateUrl: './project-explorer.component.html',
  styleUrls: ['./project-explorer.component.css']
})
export class ProjectExplorerComponent implements OnInit {

  allProjects: Project[];
  projects: Project[];
  project: Project;

  isAscendingSort = true;

  sortConfig: SortConfig;
  sortField: SortField;

  actionConfig: ActionConfig;

  loading = true;

  constructor(
    private router: Router,
    private projectService: ProjectService) {}

  ngOnInit(): void {
    this.projectService.findAll()
      .subscribe(projects => {
        this.allProjects = projects;
        this.projects = projects;
        this.loading = false;
      });

    this.sortConfig = {
      fields: [{
        id: 'name',
        title: 'Name',
        sortType: 'alpha'
      }, {
        id: 'endpoints',
        title: 'Endpoints',
        sortType: 'numeric'
      }, {
        id: 'tests',
        title: 'Test cases',
        sortType: 'numeric'
      }],
      isAscending: this.isAscendingSort
    } as SortConfig;

    this.actionConfig = {
      moreActions: [{
        id: 'editProject',
        title: 'Edit Project'
      }, {
        id: 'deleteProject',
        title: 'Delete Project'
      }]
    } as ActionConfig;
  }

  /**
   * Implementation of the sort function.
   *
   * @param project1 first item to compare
   * @param project2 second item to compare
   * @return item order
   */
  compare(project1: Project, project2: Project): number {
    let order = 0;
    if (this.sortField.id === 'name') {
      order = project1.name.localeCompare(project2.name);
    } else if (this.sortField.id === 'endpoints') {
      if (project1.endpoints > project2.endpoints) {
        order = 1;
      } else if (project1.endpoints < project2.endpoints) {
        order = -1;
      } else {
        order = 0;
      }
    } else if (this.sortField.id === 'tests') {
      if (project1.tests > project2.tests) {
        order = 1;
      } else if (project1.tests < project2.tests) {
        order = -1;
      } else {
        order = 0;
      }
    }

    if (!this.isAscendingSort) {
      order = order * -1;
    }

    return order;
  }

  sortChange($event: SortEvent): void {
    this.sortField = $event.field;
    this.isAscendingSort = $event.isAscending;
    this.projects.sort((project1, project2) => this.compare(project1, project2));
  }

  filter($event): void {
    if ($event === undefined) {
      this.projects = this.allProjects;
    } else {
      this.projects = this.allProjects.filter(project => project.name.includes($event.target.value));
    }
  }

  refreshProjects(projectId): void {
    this.allProjects = this.allProjects.filter(project => project.id !== projectId);
    this.projects = this.projects.filter(project => project.id !== projectId);
    if (this.allProjects.length <= 0) {
      this.router.navigate(['new-project']);
    }
  }

  onKebabClick(projectId): void {
    this.project = this.projects.find(project => project.id === projectId);
  }

  handleAction(action: Action): void {
    if (action.id === 'deleteProject') {
      $('#deleteModal').modal('show');
    } else if (action.id === 'editProject') {
      $('#editModal').modal('show');
    }
  }

  showDetail(event, projectId) {
    if (event.target.id !== 'source' && !event.target.classList.contains('fa-ellipsis-v') &&
      !event.target.classList.contains('dropdown-toggle') && !event.target.classList.contains('secondary-action')) {
      this.router.navigate(['projects', projectId]);
    }
  }

}
