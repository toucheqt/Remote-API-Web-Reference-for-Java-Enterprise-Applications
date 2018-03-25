import { Project } from '../../../model/project';
import { ProjectService } from '../../../services/project.service';
import { Component, OnInit, ViewEncapsulation, ChangeDetectorRef, Input } from '@angular/core';
import { NavigationItemConfig } from 'patternfly-ng';

@Component({
  selector: 'app-vertical-navigation',
  templateUrl: './vertical-navigation.component.html',
  styleUrls: ['./vertical-navigation.component.css'],
  encapsulation: ViewEncapsulation.None,
})
export class VerticalNavigationComponent implements OnInit {

  @Input() activeProject: Project;

  navigationItems: NavigationItemConfig[];
  projects: Project[];

  defaultName = '';

  constructor(
    private chRef: ChangeDetectorRef,
    private projectService: ProjectService
  ) {}

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

    this.projectService.findProjects()
      .subscribe(results => {
        this.projects = results;
      });
  }

  AfterContentInit(): void {
    this.defaultName = this.activeProject.name;
  }


  // TODO
  onItemClicked($event: NavigationItemConfig): void {
    this.navigationItems.forEach(item => {
      item.trackActiveState = false;
    });
    $event.trackActiveState = true;
    console.log('Item Clicked: ' + $event.title + '\n');
  }

  onNavigation($event: NavigationItemConfig): void {
    console.log('Navigation event fired: ' + $event.title + '\n');
  }

}
