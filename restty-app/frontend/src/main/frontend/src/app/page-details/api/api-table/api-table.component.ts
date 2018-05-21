import { EndpointService } from '../../../services/endpoint.service';
import { Component, OnInit, ViewEncapsulation, Input, ChangeDetectorRef, NgZone } from '@angular/core';
import { Router } from '@angular/router';
import { ActionConfig, ListConfig, Action, ListEvent, PaginationConfig, PaginationEvent,
  FilterConfig, FilterType, FilterField, Filter, FilterEvent, NotificationType, NotificationService, Notification } from 'patternfly-ng';
import { TimeAgoPipe } from 'time-ago-pipe';
import { Observable } from 'rxjs/Observable';

@Component({
  selector: 'app-api-table',
  templateUrl: './api-table.component.html',
  styleUrls: ['./api-table.component.css'],
  encapsulation: ViewEncapsulation.None,
})
export class ApiTableComponent implements OnInit {

  @Input() projectId: number;

  // Table config
  filterConfig: FilterConfig;
  actionConfig: ActionConfig;
  listConfig: ListConfig;
  paginationConfig: PaginationConfig;

  methodQueries: any[];

  loading = true;

  allItems: any[];
  items: any[];
  filteredItems: any[];

  actionsText: '';
  filtersText: '';

  notifications: Observable<Notification[]>;

  constructor(
    private endpointService: EndpointService,
    private ref: ChangeDetectorRef,
    private ngZone: NgZone,
    private router: Router,
    private notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    this.methodQueries = [{
      id: 'GET',
      value: 'GET'
    }, {
      id: 'HEAD',
      value: 'HEAD'
    }, {
      id: 'POST',
      value: 'POST'
    }, {
      id: 'PUT',
      value: 'PUT'
    }, {
      id: 'PATCH',
      value: 'PATCH'
    }, {
      id: 'DELETE',
      value: 'DELETE'
    }];

    this.endpointService.findAll(this.projectId).subscribe(endpoints => {
      this.allItems = endpoints.map(endpoint => {
        return {
          id: endpoint.id,
          path: endpoint.path,
          method: endpoint.method,
          description: endpoint.description,
          lastRun: endpoint.lastRun === null ? null : new TimeAgoPipe(this.ref, this.ngZone).transform(endpoint.lastRun),
          lastRunVal: endpoint.lastRun === null ? '--' : endpoint.lastRun,
          lastRunSuccess: endpoint.lastRunSuccess === null ? null : endpoint.lastRunSuccess ? 't' : 'f'
        };
      });

      this.filteredItems = this.allItems;
      this.initConfigurations();
      this.updateItems();
      this.loading = false;
    });

    this.notifications = this.notificationService.getNotificationsObserver;
    this.notificationService.setDelay(3000);
  }

  initConfigurations() {
    this.filterConfig = {
      fields: [{
        id: 'path',
        title: 'Path',
        placeholder: 'Filter by path',
        type: FilterType.TEXT
      }, {
        id: 'method',
        title: 'Method',
        placeholder: 'Filter by method',
        type: FilterType.TYPEAHEAD,
        queries: [
          ...this.methodQueries,
        ]
      }] as FilterField[],
      appliedFilters: []
    } as FilterConfig;

    this.paginationConfig = {
      pageNumber: 1,
      pageSize: 5,
      pageSizeIncrements: [5, 10, 25],
      totalItems: this.filteredItems.length
    } as PaginationConfig;

    this.listConfig = {
      dblClick: false,
      multiSelect: false,
      selectItems: false,
      selectionMatchProp: 'path',
      showCheckbox: false,
      useExpandItems: false // TODO pridat expandItems
    } as ListConfig;

    this.actionConfig = {
      moreActions: [{
        id: 'run',
        title: 'Run',
        tooltip: 'Run API'
        // TODO pridat upravu endpointu
//      }, {
//        id: 'edit',
//        title: 'Edit',
//        tooltip: 'Edit API'
//      }, {
//        id: 'remove',
//        title: 'Remove',
//        tooltip: 'Remove API'
      }],
    } as ActionConfig;
  }

  // Filter functions
  applyFilters(filters: Filter[]): void {
    this.filteredItems = [];
    if (filters && filters.length > 0) {
      this.allItems.forEach((item) => {
        if (this.matchesFilters(item, filters)) {
          this.filteredItems.push(item);
        }
      });
    } else {
      this.filteredItems = this.allItems;
    }

    this.filterConfig.resultsCount = this.filteredItems.length;
    this.updateItems();
  }

  filterChanged($event: FilterEvent): void {
    this.filtersText = '';
    $event.appliedFilters.forEach((filter) => {
      this.filtersText += filter.field.title + ' : ' + filter.value + '\n';
    });
    this.applyFilters($event.appliedFilters);
  }

  matchesFilter(item: any, filter: Filter): boolean {
    let match = true;
    if (filter.field.id === 'path') {
      match = item.path.match(filter.value) !== null;
    } else if (filter.field.id === 'method') {
      match = item.method.match(filter.value) !== null;
    }

    return match;
  }

  matchesFilters(item: any, filters: Filter[]): boolean {
    let matches = true;
    filters.forEach((filter) => {
      if (!this.matchesFilter(item, filter)) {
        matches = false;
        return matches;
      }
    });
    return matches;
  }

  // Filter queries for type ahead
  filterQueries($event: FilterEvent) {
    const index = (this.filterConfig.fields as any).findIndex((i: any) => i.id === $event.field.id);
    const val = $event.value.trim();

    if (this.filterConfig.fields[index].id = 'method') {
      this.filterConfig.fields[index].queries = [
        ...this.methodQueries.filter((item: any) => {
          if (item.value) {
            return (item.value.toLowerCase().indexOf(val.toLowerCase()) > -1);
          } else {
            return true;
          }
        })
      ];
    }
  }

  // Actions
  handleAction($event: Action, item: any): void {
    if ($event.id === 'run') {
      this.endpointService.run(item.id).subscribe(
        response => {
          this.notificationService.message(
            NotificationType.SUCCESS,
            'Success',
            'The request was completed successfully',
            false,
            null,
            null
          );
        },
        error => {
          this.notificationService.message(
            NotificationType.DANGER,
            'Failure',
            'The request failed, see logs for details',
            false,
            null,
            null
          );
        }
      );
    }
  }

  handleClick($event: ListEvent): void {
    this.router.navigate(['projects', this.projectId, 'api', $event.item.id]);
  }

  handlePageSize($event: PaginationEvent) {
    this.updateItems();
  }

  handlePageNumber($event: PaginationEvent) {
    this.updateItems();
  }

  // Pagination
  updateItems() {
    this.paginationConfig.totalItems = this.filteredItems.length;
    this.items = this.filteredItems.slice((this.paginationConfig.pageNumber - 1) * this.paginationConfig.pageSize,
    this.paginationConfig.totalItems).slice(0, this.paginationConfig.pageSize);
  }

}
