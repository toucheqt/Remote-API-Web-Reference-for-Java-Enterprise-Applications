import { TestCase } from '../../../model/test-case';
import { TestCaseService } from '../../../services/test-case.service';
import { Component, OnInit, ViewEncapsulation, Input, ViewChild, TemplateRef, ChangeDetectorRef, NgZone } from '@angular/core';
import { Router } from '@angular/router';
import { FilterConfig, SortConfig, ToolbarConfig, SortField, PaginationConfig, EmptyStateConfig, TableConfig, FilterType,
  FilterField, Filter, FilterEvent, PaginationEvent, SortEvent, TableEvent } from 'patternfly-ng';
import { TimeAgoPipe } from 'time-ago-pipe';

declare var $: any;

@Component({
  selector: 'app-test-case-table',
  templateUrl: './test-case-table.component.html',
  styleUrls: ['./test-case-table.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class TestCaseTableComponent implements OnInit {

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

  lastRunSuccessQueries: any[];

  loading = true;

  allRows: any[];
  rows: any[];
  selectedRows: any[];
  columns: any[];
  filteredRows: any[];
  filtersText = '';
  isAscendingSort = true;

  constructor(
    private testCaseService: TestCaseService,
    private router: Router,
    private ref: ChangeDetectorRef,
    private ngZone: NgZone
  ) {}

  ngOnInit(): void {
    this.initTable();
    this.testCaseService.findAll(this.projectId).subscribe(testCases => {
      this.allRows = testCases.map(testCase => {
        return {
          id: testCase.id,
          name: testCase.name,
          lastRun: testCase.lastRun === null ? '' : new TimeAgoPipe(this.ref, this.ngZone).transform(testCase.lastRun),
          lastRunVal: testCase.lastRun === null ? '' : testCase.lastRun,
          lastRunSuccess: testCase.lastRunSuccess
            ? '<span class="fa fa-check padding-top-10"></span>'
            : '<span class="pf pficon-close unsuccessful padding-top-10"></span>',
          lastRunSuccessVal: testCase.lastRunSuccess ? 't' : 'f'
        };
      });

      this.filteredRows = this.allRows;
      this.initConfigurations();
      this.updateRows();
      this.loading = false;
    });
  }

  initTable() {
    this.columns = [{
      draggable: false,
      prop: 'name',
      name: 'Name',
      resizeable: false,
      sortable: false
    }, {
      draggable: false,
      prop: 'lastRun',
      name: 'Last run',
      resizeable: false,
      sortable: false
    }, {
      draggable: false,
      prop: 'lastRunSuccess',
      name: 'Success',
      resizeable: false,
      sortable: false
    }];

    this.lastRunSuccessQueries = [{
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
        id: 'name',
        title: 'Name',
        placeholder: 'Filter by name',
        type: FilterType.TEXT
      }, {
        id: 'lastRunSuccess',
        title: 'Successful',
        placeholder: 'Filter by success',
        type: FilterType.TYPEAHEAD,
        queries: [
          ...this.lastRunSuccessQueries,
        ]
      }] as FilterField[],
      appliedFilters: [],
    } as FilterConfig;

    this.sortConfig = {
      fields: [{
        id: 'name',
        title: 'Name',
        sortType: 'alpha'
      }, {
        id: 'lastRun',
        title: 'Last Run',
        sortType: 'alpha'
      }, {
        id: 'lastRunSuccess',
        title: 'Success',
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
      title: 'No Test Cases available',
      info: 'No test cases have been created. Start by creating a test case for your API.'
    } as EmptyStateConfig;

    this.tableConfig = {
      emptyStateConfig: this.emptyStateConfig,
      paginationConfig: this.paginationConfig,
      showCheckbox: true,
    } as TableConfig;
  }

  // Actions
  createAddTestCaseModal($event) {
    if ($event.screenX !== 0) {
      $('#addTestCaseModal').modal('show');
    }
  }

  deleteTestCases() {
    if (this.selectedRows) {
      this.testCaseService.deleteTestCases(this.projectId, this.selectedRows.map(testCase => testCase.id)).subscribe(result => {
        this.testCaseService.findAll(this.projectId).subscribe(testCases => {
          this.allRows = testCases.map(testCase => {
            return {
              id: testCase.id,
              name: testCase.name,
              lastRun: testCase.lastRun === null ? '' : new TimeAgoPipe(this.ref, this.ngZone).transform(testCase.lastRun),
              lastRunVal: testCase.lastRun === null ? '' : testCase.lastRun,
              lastRunSuccess: testCase.lastRunSuccess
                ? '<span class="fa fa-check padding-top-10"></span>'
                : '<span class="pf pficon-close unsuccessful padding-top-10"></span>',
              lastRunSuccessVal: testCase.lastRunSuccess ? 't' : 'f'
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

    if (filter.field.id === 'name') {
      match = item.name.match(filter.value) !== null;
    } else if (filter.field.id === 'lastRunSuccess') {
      match = item.lastRunSuccessVal.match(filter.value === 'True' ? 't' : 'f') !== null;
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

    if (this.filterConfig.fields[index].id = 'lastRunSuccessVal') {
      this.filterConfig.fields[index].queries = [
        ...this.lastRunSuccessQueries.filter((item: any) => {
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
    if (this.currentSortField.id === 'name') {
      compValue = item1.name.localeCompare(item2.name);
    } else if (this.currentSortField.id === 'lastRun') {
      compValue = item1.lastRunVal.localeCompare(item2.lastRunVal);
    } else if (this.currentSortField.id === 'lastRunSuccess') {
      compValue = item1.lastRunSuccessVal.localeCompare(item2.lastRunSuccessVal);
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

  refreshOnCreate(testCase: TestCase): void {
    this.allRows.push({
      id: testCase.id,
      name: testCase.name,
      lastRun: testCase.lastRun === null ? '' : new TimeAgoPipe(this.ref, this.ngZone).transform(testCase.lastRun),
      lastRunVal: testCase.lastRun === null ? '' : testCase.lastRun,
      lastRunSuccess: testCase.lastRunSuccess
        ? '<span class="fa fa-check padding-top-10"></span>'
        : '<span class="pf pficon-close unsuccessful padding-top-10"></span>',
      lastRunSuccessVal: testCase.lastRunSuccess ? 't' : 'f'
    });

    this.updateRows();
    this.applyFilters(this.filterConfig.appliedFilters || []);
  }

  handleOnActivate($event: any): void {
    if ($event.type === 'click' && $event.column.prop === 'name' && $event.event.target.localName === 'span') {
      this.router.navigate(['projects', this.projectId, 'test-cases', $event.row.id]);
    }
  }

}
