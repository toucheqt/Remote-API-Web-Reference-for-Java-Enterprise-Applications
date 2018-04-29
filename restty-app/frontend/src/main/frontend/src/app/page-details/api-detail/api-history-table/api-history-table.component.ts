import { LogService } from '../../../services/log.service';
import { Component, OnInit, ChangeDetectorRef, NgZone, ViewEncapsulation, Input, SimpleChanges, OnChanges } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FilterConfig, SortConfig, ToolbarConfig, SortField, PaginationConfig, EmptyStateConfig, TableConfig, FilterType,
  FilterField, Filter, FilterEvent, PaginationEvent, SortEvent, TableEvent } from 'patternfly-ng';
import { TimeAgoPipe } from 'time-ago-pipe';

@Component({
  selector: 'app-api-history-table',
  templateUrl: './api-history-table.component.html',
  styleUrls: ['./api-history-table.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class ApiHistoryTableComponent implements OnInit, OnChanges {

  @Input() update: boolean;

  endpointId: number;

  // Toolbar Configuration
  filterConfig: FilterConfig;
  sortConfig: SortConfig;
  toolbarConfig: ToolbarConfig;

  // Table Configuration
  currentSortField: SortField;
  paginationConfig: PaginationConfig;
  emptyStateConfig: EmptyStateConfig;
  tableConfig: TableConfig;

  loading = true;

  runSuccessQueries: any[];

  allRows: any[];
  rows: any[];
  selectedRows: any[];
  columns: any[];
  filteredRows: any[];
  filtersText = '';
  isAscendingSort = false;

  constructor(
    private route: ActivatedRoute,
    private logService: LogService,
    private ref: ChangeDetectorRef,
    private ngZone: NgZone
  ) {
    this.route.params.subscribe(pathVariable => {
      this.endpointId = pathVariable.apiId;
    });
  }

  ngOnInit() {
    this.initTable();
    this.logService.findAllByEndpoint(this.endpointId).subscribe(logs => {
      this.allRows = logs.map(log => {
        return {
          run: new TimeAgoPipe(this.ref, this.ngZone).transform(log.run),
          runVal: log.run,
          responseStatus: log.responseStatus,
          responseMessage: log.responseMessage,
          success: log.success
            ? '<span class="fa fa-check padding-top-5"></span>'
            : '<span class="pf pficon-close unsuccessful padding-top-5"></span>',
          successVal: log.success ? 't' : 'f'
        };
      });

      this.filteredRows = this.allRows;
      this.initConfigurations();
      this.updateRows();
      this.loading = false;
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.update.currentValue) {
      this.logService.findRecentLogByEndpoint(this.endpointId).subscribe(log => {
        this.allRows.push({
          run: new TimeAgoPipe(this.ref, this.ngZone).transform(log.run),
          runVal: log.run,
          responseStatus: log.responseStatus,
          responseMessage: log.responseMessage,
          success: log.success
            ? '<span class="fa fa-check padding-top-5"></span>'
            : '<span class="pf pficon-close unsuccessful padding-top-5"></span>',
          successVal: log.success ? 't' : 'f'
        });

        this.allRows.sort((item1: any, item2: any) => this.compare(item1, item2));
        this.updateRows();
        this.applyFilters(this.filterConfig.appliedFilters || []);
        this.update = false;
      });
    }
  }

  initTable() {
    this.columns = [{
      draggable: false,
      prop: 'run',
      name: 'Tested',
      resizeable: false,
      sortable: false
    }, {
      draggable: false,
      prop: 'responseStatus',
      name: 'Response Status',
      resizeable: false,
      sortable: false
    }, {
      draggable: false,
      prop: 'responseMessage',
      name: 'Message',
      resizeable: false,
      sortable: false
    }, {
      draggable: false,
      prop: 'success',
      name: 'Success',
      resizeable: false,
      sortable: false
    }];

    this.runSuccessQueries = [{
      id: 't',
      value: 'True'
    }, {
      id: 'f',
      value: 'False'
    }];
  }

  initConfigurations() {
    this.filterConfig = {
      fields: [{
        id: 'responseStatus',
        title: 'Response Status',
        placeholder: 'Filter by response status',
        type: FilterType.TEXT
      }, {
        id: 'responseMessage',
        title: 'Message',
        placeholder: 'Filter by message',
        type: FilterType.TEXT
      }, {
        id: 'success',
        title: 'Success',
        placeholder: 'Filter by success',
        type: FilterType.TYPEAHEAD,
        queries: [
          ...this.runSuccessQueries,
        ]
      }] as FilterField[],
      appliedFilters: [],
    } as FilterConfig;

    this.sortConfig = {
      fields: [{
        id: 'run',
        title: 'Tested',
        sortType: 'alpha'
      }, {
        id: 'responseStatus',
        title: 'Response Status',
        sortType: 'alpha'
      }, {
        id: 'responseMessage',
        title: 'Message',
        sortType: 'alpha'
      }, {
        id: 'success',
        title: 'Success',
        sortType: 'alpha'
      }],
      isAscending: this.isAscendingSort
    } as SortConfig;
    this.currentSortField = this.sortConfig.fields[0];

    this.toolbarConfig = {
      filterConfig: this.filterConfig,
      sortConfig: this.sortConfig,
    } as ToolbarConfig;

    this.paginationConfig = {
      pageNumber: 1,
      pageSize: 10,
      pageSizeIncrements: [5, 10, 25],
      totalItems: this.filteredRows.length
    } as PaginationConfig;

    this.emptyStateConfig = {
      iconStyleClass: 'pficon-warning-triangle-o',
      title: 'No Logs available',
      info: 'No logs are available. Start by testing the endpoint.'
    } as EmptyStateConfig;

    this.tableConfig = {
      emptyStateConfig: this.emptyStateConfig,
      paginationConfig: this.paginationConfig,
    } as TableConfig;
  }

  // Filter
  applyFilters(filters: Filter[]): void {
    this.filteredRows = [];
    if (filters && filters.length > 0) {
      this.allRows.forEach((item) => {
        if (this.matchesFilters(item, filters)) {
          this.filteredRows.push(item);
        }
      });
    } else {
      this.filteredRows = this.allRows;
    }

    this.tableConfig.appliedFilters = filters; // Need to update appliedFilters to show filter empty state
    this.toolbarConfig.filterConfig.resultsCount = this.filteredRows.length;
    this.updateRows();
  }

  // Handle filter changes
  filterChanged($event: FilterEvent): void {
    this.filtersText = '';
    $event.appliedFilters.forEach((filter) => {
      this.filtersText += filter.field.title + ' : ' + filter.value + '\n';
    });

    this.applyFilters($event.appliedFilters);
  }

  matchesFilter(item: any, filter: Filter): boolean {
    let match = true;

    if (filter.field.id === 'responseStatus') {
      match = item.responseStatus.match(filter.value) !== null;
    } else if (filter.field.id === 'responseMessage') {
      match = item.responseMessage.match(filter.value) !== null;
    } else if (filter.field.id === 'success') {
      if (filter.value === null || item.successVal === null) {
        return false;
      }

      match = item.successVal.match(filter.value === 'True' ? 't' : 'f') !== null;
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

    if (this.filterConfig.fields[index].id = 'successVal') {
      this.filterConfig.fields[index].queries = [
        ...this.runSuccessQueries.filter((item: any) => {
          if (item.value) {
            return (item.value.toLowerCase().indexOf(val.toLowerCase()) > -1);
          } else {
            return true;
          }
        })
      ];
    }
  }

  // Pagination
  handlePageSize($event: PaginationEvent): void {
    this.updateRows();
  }

  handlePageNumber($event: PaginationEvent): void {
    this.updateRows();
  }

  updateRows(): void {
    this.paginationConfig.totalItems = this.filteredRows.length;
    this.rows = this.filteredRows.slice((this.paginationConfig.pageNumber - 1) * this.paginationConfig.pageSize,
    this.paginationConfig.totalItems).slice(0, this.paginationConfig.pageSize);
  }

  // Sort
  compare(item1: any, item2: any): number {
    let compValue = 0;
    if (this.currentSortField.id === 'run') {
      compValue = item1.runVal.localeCompare(item2.runVal);
    } else if (this.currentSortField.id === 'responseStatus') {
      compValue = item1.responseStatus.localeCompare(item2.responseStatus);
    } else if (this.currentSortField.id === 'responseMessage') {
      compValue = item1.responseMessage.localeCompare(item2.responseMessage);
    } else if (this.currentSortField.id === 'success') {
      compValue = item1.successVal.localeCompare(item2.successVal);
    }

    if (!this.isAscendingSort) {
      compValue = compValue * -1;
    }

    return compValue;
  }

  // Handle sort changes
  handleSortChanged($event: SortEvent): void {
    this.currentSortField = $event.field;
    this.isAscendingSort = $event.isAscending;
    this.allRows.sort((item1: any, item2: any) => this.compare(item1, item2));
    this.applyFilters(this.filterConfig.appliedFilters || []);
  }

  // Selection
  handleSelectionChange($event: TableEvent): void {
    this.selectedRows = $event.selectedRows;
  }

}
