import { TestCase } from '../../../model/test-case';
import { LogService } from '../../../services/log.service';
import { TestCaseSettingsService } from '../../../services/test-case-settings.service';
import { Component, OnInit, Input, OnChanges, SimpleChanges, ViewEncapsulation } from '@angular/core';

declare var $: any;

@Component({
  selector: 'app-test-case-detail-tab',
  templateUrl: './test-case-detail-tab.component.html',
  styleUrls: ['./test-case-detail-tab.component.css'],
})
export class TestCaseDetailTabComponent implements OnInit, OnChanges {

  @Input() testCase: TestCase;
  @Input() update: number;

  allSteps = [];

  loading = true;

  constructor(
    private logService: LogService,
    private testCaseSettingsService: TestCaseSettingsService
  ) {}

   ngOnInit(): void {
    this.testCaseSettingsService.findAllSteps(this.testCase.id).subscribe(stepItems => {
      this.allSteps = stepItems.map(stepItem => {
        return {
          id: stepItem.id,
          name: stepItem.name,
          method: stepItem.method
        };
      });

      this.loading = false;
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.update.currentValue && this.testCase !== undefined) {
      this.logService.findRecentLogByTestCase(this.testCase.id).subscribe(log => {
        this.testCase.lastRun = log.run;
        this.testCase.lastRunSuccess = log.success;
        this.testCase.lastRunMessage = log.responseMessage;
      });
    }
  }

  addStep() {
    $('#addTestStepModal').modal('show');
  }

}
