import { Header } from '../../../../../model/header';
import { IdNameMethod } from '../../../../../model/id-name-method';
import { TestCase } from '../../../../../model/test-case';
import { TestCaseSettings } from '../../../../../model/test-case-settings';
import { HeaderService } from '../../../../../services/header.service';
import { TestCaseSettingsService } from '../../../../../services/test-case-settings.service';
import { TestCaseService } from '../../../../../services/test-case.service';
import { Component, OnInit, EventEmitter, Output, ViewEncapsulation, Input, Compiler } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { debounceTime } from 'rxjs/operators/debounceTime';

declare var $: any;

@Component({
  selector: 'app-add-test-step',
  templateUrl: './add-test-step.component.html',
  styleUrls: ['./add-test-step.component.css'],
})
export class AddTestStepComponent implements OnInit {

  @Output() testStepCreateEvent = new EventEmitter<TestCaseSettings>();
  @Input() allSteps: IdNameMethod[];
  @Input() usePreviousEnabled;

  testCaseId: number;
  testCase: TestCase;

  stepAttr: any;
  usePreviousAttr = false;
  settingsForm: FormGroup;

  showDefault = true;
  loading = true;

  constructor(
    private route: ActivatedRoute,
    private testCaseService: TestCaseService,
    private testCaseSettingsService: TestCaseSettingsService,
  ) {
    this.route.params.subscribe(pathVariable => this.testCaseId = pathVariable.testCaseId);
  }

  ngOnInit() {
    this.testCaseService.findById(this.testCaseId).subscribe(testCase => {
      this.testCase = testCase;
      this.usePreviousEnabled = testCase.hasSteps;
      this.settingsForm = new FormGroup({
        'step': new FormControl(this.stepAttr, [Validators.required]),
        'usePrevious': new FormControl({value: this.usePreviousAttr, disabled: !this.usePreviousEnabled }, [])
      });

      this.loading = false;

      $(document).ready(function() {
        $('.combobox').combobox();
      });
    });
  }

  onCancel(): void {
    this.settingsForm.reset();
    $('.combobox').clear();
  }

  onSubmit(): void {
    let activeItem = $('ul.typeahead').find('li.active').attr('data-value');
    if (activeItem !== undefined) {
      this.usePreviousAttr = this.settingsForm.get('usePrevious').value;
      if (activeItem.indexOf(' ') === 0) { // test case
        activeItem = activeItem.replace(/\s/g, '');
        const stepTestId = this.allSteps.find(step => step.method === null && step.name === activeItem).id;

        this.testCaseSettingsService.addTestStepToTestCase(this.testCaseId, stepTestId, this.usePreviousAttr).subscribe(settings => {
          this.testStepCreateEvent.emit(settings);
        });
      } else { // endpoint
        activeItem = activeItem.split(' ');
        const endpoint = this.allSteps.find(step => step.method === activeItem[0] && step.name === activeItem[1]);

        this.testCaseSettingsService.addEndpointToTestCase(this.testCaseId, endpoint.id, this.usePreviousAttr).subscribe(settings => {
          this.testStepCreateEvent.emit(settings);
        });
      }

      this.onCancel();
      $('#addTestStepModal').modal('hide');
    }
  }

  get step() {
    return this.settingsForm.get('step');
  }

  get usePrevious() {
    return this.settingsForm.get('usePrevious');
  }

}
