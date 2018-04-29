import { Project } from '../../model/project';
import { ProjectService } from '../../services/project.service';
import { Component, OnInit, ChangeDetectorRef, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NavigationItemConfig } from 'patternfly-ng';

@Component({
  selector: 'app-dashboard',
  templateUrl: './project-container.component.html',
  styleUrls: ['./project-container.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class ProjectContainerComponent implements OnInit {

  contentView = 'Dashboard';

  project: Project;
  loading = true;

  navigationItems: NavigationItemConfig[];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private projectService: ProjectService
  ) {
    this.route.params.subscribe(pathVariable => {
      this.projectService.findById(pathVariable.id).subscribe(project => {
        this.project = project;
        this.loading = false;
      });
    });
  }

  ngOnInit(): void {
    this.navigationItems = [
      {
        title: 'Dashboard',
        iconStyleClass: 'fa fa-dashboard',
        trackActiveState: true,
        url: '/navigation/dashboard'
      },
      {
        title: 'API',
        iconStyleClass: 'fa fa-cube',
        url: '/navigation/api'
      },
      {
        title: 'Test Cases',
        iconStyleClass: 'fa fa-cubes',
        url: '/navigation/test-cases'
      },
      {
        title: 'Settings',
        iconStyleClass: 'fa fa-paper-plane',
        url: '/navigation/settings'
      }
    ];
  }

  onItemClicked($event: NavigationItemConfig): void {
    this.contentView = $event.title;
    this.switchView($event.title);
    this.navigationItems.forEach(item => {
      item.trackActiveState = false;
    });

    $event.trackActiveState = true;
  }

  onBreadcrumbsClick($event): void {
    if ($event.target.innerText === this.project.name) {
      this.contentView = 'Dashboard';
    } else {
      this.contentView = $event.target.innerText;
    }

    this.navigationItems.forEach(item => item.trackActiveState = item.title === this.contentView);
    this.switchView(this.contentView);
  }

  switchView(viewName: string) {
    if (viewName === 'Dashboard') {
      this.router.navigate(['projects', this.project.id]);
    } else if (viewName === 'API') {
      this.router.navigate(['projects', this.project.id, 'api']);
    } else if (viewName === 'Test Cases') {
      this.router.navigate(['projects', this.project.id, 'test-cases']);
    } else if (viewName === 'Settings') {
      this.router.navigate(['projects', this.project.id, 'settings']);
    }
  }

}
