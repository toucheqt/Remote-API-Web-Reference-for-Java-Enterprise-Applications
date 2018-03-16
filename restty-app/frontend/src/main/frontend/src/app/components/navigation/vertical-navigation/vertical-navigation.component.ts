import { Component, OnInit, ViewEncapsulation, ChangeDetectorRef } from '@angular/core';
import { Router } from '@angular/router';
import { NavigationItemConfig } from 'patternfly-ng';

@Component({
  selector: 'app-vertical-navigation',
  templateUrl: './vertical-navigation.component.html',
  styleUrls: ['./vertical-navigation.component.css'],
  encapsulation: ViewEncapsulation.None,
})
export class VerticalNavigationComponent implements OnInit {

  navigationItems: NavigationItemConfig[];

  constructor(
    private chRef: ChangeDetectorRef,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.navigationItems = [
      {
        title: 'Dashboard',
        iconStyleClass: 'fa fa-dashboard',
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
    console.log('Item Clicked: ' + $event.title + '\n');
  }

  onNavigation($event: NavigationItemConfig): void {
    console.log('Navigation event fired: ' + $event.title + '\n');
  }

}
