import { Header } from '../../../model/header';
import { HeaderService } from '../../../services/header.service';
import { Component, OnInit, ViewChild, ViewEncapsulation, TemplateRef, Input } from '@angular/core';
import { SortField, FilterConfig, PaginationConfig, SortConfig, TableConfig,
  ToolbarConfig, FilterType, FilterField, Action, FilterEvent, Filter,
  PaginationEvent, SortEvent, ToolbarView, TableEvent, EmptyStateConfig, ActionConfig } from 'patternfly-ng';

declare var $: any;

@Component({
  selector: 'app-headers-table',
  templateUrl: './headers-table.component.html',
  styleUrls: ['./headers-table.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class HeadersTableComponent implements OnInit {

  @Input() projectId: number;

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

  allRows: any[];
  rows: any[];
  selectedRows: any[];
  columns: any[];
  filteredRows: any[];
  filtersText = '';
  isAscendingSort = true;

  constructor(private headerService: HeaderService) {}

  ngOnInit(): void {
    this.initTable();
    this.headerService.findAllGlobalHeadersByProject(this.projectId).subscribe(headers => {
      this.allRows = headers.map(headerEntity => {
        return {
          header: headerEntity.header,
          value: headerEntity.value,
          id: headerEntity.id
        };
      });

      this.filteredRows = this.allRows;
      this.initConfigurations();
      this.updateRows(); // Need to initialize for results/total counts
      this.loading = false;
    });
  }

  initTable() {
    this.columns = [{
      draggable: false,
      prop: 'header',
      name: 'Header',
      resizeable: false,
      sortable: false
    }, {
      draggable: false,
      prop: 'value',
      name: 'Value',
      resizeable: false,
      sortable: false
    }];
  }

  initConfigurations() {
    this.filterConfig = {
      fields: [{
        id: 'header',
        title: 'Header',
        placeholder: 'Filter by header',
        type: FilterType.TEXT
      }, {
        id: 'value',
        title: 'Value',
        placeholder: 'Filter by value',
        type: FilterType.TEXT
      }] as FilterField[],
      appliedFilters: [],
    } as FilterConfig;

    this.sortConfig = {
      fields: [{
        id: 'header',
        title: 'Header',
        sortType: 'alpha'
      }, {
        id: 'value',
        title: 'Value',
        sortType: 'alpha'
      }],
      isAscending: this.isAscendingSort
    } as SortConfig;

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
      title: 'No Global Headers Available',
      info: 'Global headers are HTTP headers that are injected to every API and Test Case call. Start by creating a global header.'
    } as EmptyStateConfig;

    this.tableConfig = {
      emptyStateConfig: this.emptyStateConfig,
      paginationConfig: this.paginationConfig,
      showCheckbox: true,
    } as TableConfig;
  }

  // Actions
  createHeaderModal($event) {
    if ($event.screenX !== 0) {
      $('#addHeaderModal').modal('show');
    }
  }

  deleteHeaders() {
    if (this.selectedRows) {
      this.headerService.deleteHeaders(this.selectedRows.map(header => header.id)).subscribe(result => {
        this.headerService.findAllGlobalHeadersByProject(this.projectId).subscribe(headers => {
          this.allRows = headers.map(headerEntity => {
            return {
              header: headerEntity.header,
              value: headerEntity.value,
              id: headerEntity.id
            };
          });

          this.filteredRows = this.allRows;
          this.updateRows();
          this.applyFilters(this.filterConfig.appliedFilters || []);
          this.selectedRows = null;
        });
      });
    }
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

    if (filter.field.id === 'header') {
      match = item.header.match(filter.value) !== null;
    } else if (filter.field.id === 'value') {
      match = item.value.match(filter.value) !== null;
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
    if (this.currentSortField.id === 'header') {
      compValue = item1.header.localeCompare(item2.header);
    } else if (this.currentSortField.id === 'value') {
      compValue = item1.value.localeCompare(item2.value);
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

  refreshOnCreate(headerEntity: Header): void {
    this.allRows.push({
      header: headerEntity.header,
      value: headerEntity.value,
      id: headerEntity.id
    });

    this.updateRows();
    this.applyFilters(this.filterConfig.appliedFilters || []);
  }

}
