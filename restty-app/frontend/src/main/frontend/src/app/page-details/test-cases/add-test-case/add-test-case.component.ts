import { Header } from '../../../model/header';
import { TestCase } from '../../../model/test-case';
import { TestCaseService } from '../../../services/test-case.service';
import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { debounceTime } from 'rxjs/operators/debounceTime';

declare var $: any;

@Component({
  selector: 'app-add-test-case',
  templateUrl: './add-test-case.component.html',
  styleUrls: ['./add-test-case.component.css']
})
export class AddTestCaseComponent implements OnInit {

  @Output() testCaseCreateEvent = new EventEmitter<TestCase>();

  projectId: number;
  testCase: TestCase = new TestCase();
  testCaseForm: FormGroup;

  unique = true;

  constructor(
    private route: ActivatedRoute,
    private testCaseService: TestCaseService
  ) {
    this.route.parent.params.subscribe(pathVariable => this.projectId = pathVariable.id);
  }

  ngOnInit() {
    this.testCaseForm = new FormGroup({
      'name': new FormControl(this.testCase.name, [
        Validators.required,
        Validators.minLength(2)
      ]),
      'description': new FormControl(this.testCase.description, [])
    });

    this.onChanges();
  }

  onChanges(): void {
    this.testCaseForm.get('name').valueChanges
      .pipe(
        debounceTime(400)
      ).subscribe(name => {
        if (this.testCaseForm.get('name').valid) {
          this.testCaseService.findByName(this.projectId, name).subscribe(result => this.unique = result == null);
        }
      });
  }

  onCancel(): void {
    this.testCaseForm.reset();
  }

  onSubmit(): void {
    if (this.testCaseForm.valid) {
      this.testCase.name = this.testCaseForm.get('name').value;
      this.testCase.description = this.testCaseForm.get('description').value;
      this.testCaseService.createTestCase(this.projectId, this.testCase).subscribe(testCase => {
        this.testCase.id = testCase.id;
        this.testCaseCreateEvent.emit(this.testCase);
      });

      this.onCancel();
      $('#addTestCaseModal').modal('hide');
    }
  }

  get name() {
    return this.testCaseForm.get('name');
  }

  get description() {
    return this.testCaseForm.get('description');
  }

}
