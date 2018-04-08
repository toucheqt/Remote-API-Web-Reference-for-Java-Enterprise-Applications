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

  contentView = null;

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
        title: 'Test cases',
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
    if ($event.title === 'Dashboard') {
      this.contentView = null;
      this.router.navigate(['projects', this.project.id]);
    } else if ($event.title === 'API') {
      this.router.navigate(['projects', this.project.id, 'api']);
    } else if ($event.title === 'Test cases') {
      this.router.navigate(['projects', this.project.id, 'test-cases']);
    } else if ($event.title === 'Settings') {
      this.router.navigate(['projects', this.project.id, 'settings']);
    }

    this.navigationItems.forEach(item => {
      item.trackActiveState = false;
    });

    $event.trackActiveState = true;
  }

}
