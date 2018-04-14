import { ProjectService } from '../../../services/project.service';
import { Component, OnInit, Input, ViewEncapsulation, TemplateRef, ViewChild, ViewContainerRef, ChangeDetectorRef,
  NgZone } from '@angular/core';
import { Router } from '@angular/router';
import { EmptyStateConfig, TableConfig } from 'patternfly-ng';
import { TimeAgoPipe } from 'time-ago-pipe';

@Component({
  selector: 'app-recent-table',
  templateUrl: './recent-table.component.html',
  styleUrls: ['./recent-table.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class RecentTableComponent implements OnInit {

  @Input() projectId: number;

    // Table Configuration
  emptyStateConfig: EmptyStateConfig;
  tableConfig: TableConfig;

  loading = true;

  allRows: any[];
  columns: any[];

  constructor(
    private projectService: ProjectService,
    private router: Router,
    private ref: ChangeDetectorRef,
    private ngZone: NgZone
  ) {}

  ngOnInit() {
    this.initTable();
    this.projectService.findRecentRuns(this.projectId).subscribe(lastRuns => {
      this.allRows = lastRuns.map(lastRun => {
        return {
          id: lastRun.id,
          name: lastRun.name,
          method: lastRun.method,
          testType: lastRun.testType,
          lastRun: new TimeAgoPipe(this.ref, this.ngZone).transform(lastRun.lastRun),
          lastRunSuccess: lastRun.lastRunSuccess ? '<span class="fa fa-check"></span>' : '<span class="pf pficon-close"></span>'
        };
      });

      this.initConfigurations();
      this.loading = false;
    });
  }

  initTable() {
    this.columns = [{
      draggable: false,
      prop: 'name',
      name: 'Name',
      resizeable: false,
      sortable: false,
    }, {
      draggable: false,
      prop: 'method',
      name: 'Method',
      resizeable: false,
      sortable: false
    }, {
      draggable: false,
      prop: 'testType',
      name: 'Type',
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
  }

  initConfigurations() {
    this.emptyStateConfig = {
      iconStyleClass: 'pficon-warning-triangle-o',
      title: 'No API Endpoints or Test Cases has been run.',
      info: 'Start by running an API Endpoint or by creating a Test Case.'
    } as EmptyStateConfig;

    this.tableConfig = {
      emptyStateConfig: this.emptyStateConfig
    } as TableConfig;
  }

  handleOnActivate($event: any): void {
    if ($event.type === 'click' && $event.column.prop === 'name' && $event.event.target.localName === 'span') {
      if ($event.row.testType === 'Endpoint') {
        this.router.navigate(['projects', this.projectId, 'api', $event.row.id]);
      } else {
        this.router.navigate(['projects', this.projectId, 'test-cases', $event.row.id]);
      }
    }
  }

}
