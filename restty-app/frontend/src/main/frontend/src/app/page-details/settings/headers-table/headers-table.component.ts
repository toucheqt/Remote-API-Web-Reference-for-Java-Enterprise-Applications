import { SettingsService } from '../../../services/headers.service';
import { Component, OnInit, ViewChild, ViewEncapsulation, TemplateRef, Input } from '@angular/core';
import { SortField, FilterConfig, PaginationConfig, SortConfig, TableConfig,
  ToolbarConfig, FilterType, FilterField, Action, FilterEvent, Filter,
  PaginationEvent, SortEvent, ToolbarView, TableEvent } from 'patternfly-ng';

@Component({
  selector: 'app-headers-table',
  templateUrl: './headers-table.component.html',
  styleUrls: ['./headers-table.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class HeadersTableComponent implements OnInit {

  @Input() projectId: number;
  @ViewChild('headerTemplate') headerTemplate: TemplateRef<any>;
  @ViewChild('valueTemplate') valueTemplate: TemplateRef<any>;

  // Toolbar Configuration
  filterConfig: FilterConfig;
  sortConfig: SortConfig;
  toolbarConfig: ToolbarConfig;

  // Table Configuration
  currentSortField: SortField;
  paginationConfig: PaginationConfig;
  tableConfig: TableConfig;

  loading = false; // FIXME

  allRows: any[];
  rows: any[];
  rowsAvailable = true;
  columns: any[];
  filteredRows: any[];
  filtersText = '';
  isAscendingSort = true;

  constructor(private settingsService: SettingsService) {}

  ngOnInit(): void {
    this.initTable();
    this.allRows = [{
      header: 'application/json',
      value: 'muj json',
    }, {
      header: 'application/xml',
      value: 'moje xml',
    }, {
      header: 'Authorization',
      value: 'Bearer faskjasugvpnbsdg4s5g4sd23gs489dg74sdg4sd56g4s654g56ds4g56sdg',
    }, {
      header: 'Content-Type',
      value: 'application/json',
    }, {
      header: 'Content-Length',
      value: '42',
    }];

    this.filteredRows = this.allRows;
    this.initConfigurations();
    this.updateRows(); // Need to initialize for results/total counts
  }

  initTable() {
    this.columns = [{
      cellTemplate: this.headerTemplate,
      draggable: false,
      prop: 'header',
      name: 'Header',
      resizeable: false,
      sortable: false
    }, {
      cellTemplate: this.valueTemplate,
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
      pageSize: 5,
      pageSizeIncrements: [5, 10, 25],
      totalItems: this.filteredRows.length
    } as PaginationConfig;

    this.tableConfig = {
      paginationConfig: this.paginationConfig,
      showCheckbox: true,
    } as TableConfig;
  }

   // Actions
  addHeader(): void {
  }

  handleAction(action: Action): void {
  }

  optionSelected(option: number): void {
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

  // Filter queries for type ahead
  filterQueries($event: FilterEvent) {
    const index = (this.filterConfig.fields as any).findIndex((i: any) => i.id === $event.field.id);
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
    this.toolbarConfig.filterConfig.selectedCount = $event.selectedRows.length;
  }

  updateItemsAvailable(): void {
    if (this.rowsAvailable) {
      this.toolbarConfig.disabled = false;
      this.toolbarConfig.filterConfig.totalCount = this.allRows.length;
      this.filteredRows = this.allRows;
      this.updateRows();
    } else {
      // Clear previously applied properties to simulate no rows available
      this.toolbarConfig.disabled = true;
      this.toolbarConfig.filterConfig.totalCount = 0;
      this.filterConfig.appliedFilters = [];
      this.rows = [];
    }
  }

}
