import { TestCase } from '../../../../model/test-case';
import { TestCaseSettings } from '../../../../model/test-case-settings';
import { TestCaseSettingsService } from '../../../../services/test-case-settings.service';
import { TestCaseService } from '../../../../services/test-case.service';
import { Component, OnInit, Input, ChangeDetectorRef, NgZone, TemplateRef, ViewEncapsulation, OnChanges, SimpleChanges } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ActionConfig, ListConfig, Action, ListEvent, EmptyStateConfig } from 'patternfly-ng';

declare var $: any;

@Component({
  selector: 'app-test-case-settings-table',
  templateUrl: './test-case-settings-table.component.html',
  styleUrls: ['./test-case-settings-table.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class TestCaseSettingsTableComponent implements OnInit, OnChanges {
  [x: string]: any;

  @Input() testCaseId: number;
  @Input() update: number;

  projectId: number;
  testCase: TestCase;

  // Table config
  actionConfig: ActionConfig;
  listConfig: ListConfig;
  emptyStateConfig: EmptyStateConfig;

  loading = true;
  allSteps = [];

  items: any[];

  constructor(
    private route: ActivatedRoute,
    private testCaseService: TestCaseService,
    private testCaseSettingsService: TestCaseSettingsService,
    private ref: ChangeDetectorRef,
    private ngZone: NgZone,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.route.parent.params.subscribe(pathVariable => this.projectId = pathVariable.id);
    this.testCaseService.findById(this.testCaseId).subscribe(result => this.testCase = result);

    this.testCaseSettingsService.findAllSteps(this.testCaseId).subscribe(stepItems => {
      this.allSteps = stepItems.map(stepItem => {
        return {
          id: stepItem.id,
          name: stepItem.name,
          method: stepItem.method
        };
      });
    });

    this.testCaseSettingsService.findAllByTestCaseId(this.testCaseId).subscribe(settings => {
      this.items = settings.map(setting => {
        return {
          id: setting.id,
          endpointOrder: setting.endpointOrder,
          endpoint: setting.endpoint,
          failed: setting.failed,
          parameters: setting.parameters
        };
      });

      this.initConfigurations();
      this.loading = false;
    });
  }

  initConfigurations() {
    this.actionConfig = {
      moreActions: [{
        id: 'remove',
        title: 'Remove',
        tooltip: 'Remove step'
      }]
    } as ActionConfig;

    this.emptyStateConfig = {
      actions: {
        primaryActions: [{
          id: 'addTestStep',
          title: 'Add Test Step',
          tooltip: 'Adds step (an API endpoint or a test case) to this test case'
        }],
      } as ActionConfig,
      iconStyleClass: 'pficon-warning-triangle-o',
      title: 'No Configuration Available',
      info: 'No configuration is available. Start by adding a test step to this test case.'
    } as EmptyStateConfig;

    this.listConfig = {
      dblClick: false,
      multiSelect: false,
      selectItems: false,
      selectionMatchProp: 'path',
      showCheckbox: false,
      useExpandItems: false, // TODO pridat expand items
      emptyStateConfig: this.emptyStateConfig
    } as ListConfig;
  }

  // Actions
  handleAction($event: Action, item: any): void {
    if ($event.id === 'addTestStep') {
      $('#addTestStepModal').modal('show');
    } else if ($event.id === 'remove') {
      this.testCaseSettingsService.deleteSteps(this.testCaseId, item.endpointOrder).subscribe(result => {
        this.testCaseSettingsService.findAllByTestCaseId(this.testCaseId).subscribe(settings => {
          this.items = settings.map(setting => {
            return {
              id: setting.id,
              endpointOrder: setting.endpointOrder,
              endpoint: setting.endpoint,
              failed: setting.failed,
              parameters: setting.parameters
            };
          });
        });
      });
    }
  }

  handleClick($event: ListEvent): void {
    this.router.navigate(['projects', this.projectId, 'test-cases-settings', $event.item.id]);
  }

  openModal(template: TemplateRef<any>): void {
    this.modalRef = this.modalService.show(template, { class: 'modal-lg' });
  }

  refreshOnCreate(settings: TestCaseSettings): void {
    this.items.push({
      id: settings.id,
      endpointOrder: settings.endpointOrder,
      endpoint: settings.endpoint,
      parameters: settings.parameters
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.update.currentValue && this.testCase !== undefined) {
      this.testCaseSettingsService.findAllByTestCaseId(this.testCaseId).subscribe(settings => {
        this.items = settings.map(setting => {
          return {
            id: setting.id,
            endpointOrder: setting.endpointOrder,
            endpoint: setting.endpoint,
            failed: setting.failed,
            parameters: setting.parameters
          };
        });
      });
    }
  }

}
