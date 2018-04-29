import { Parameter } from '../../../../model/parameter';
import { ParameterService } from '../../../../services/parameter.service';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

declare var $: any;

@Component({
  selector: 'app-edit-param',
  templateUrl: './edit-param.component.html',
  styleUrls: ['./edit-param.component.css']
})
export class EditParamComponent implements OnInit {

  @Input() parameter: Parameter;
  @Output() parameterEditEvent = new EventEmitter<Parameter>();

  endpointId: number;
  paramForm: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private parameterService: ParameterService
  ) {
    this.route.params.subscribe(pathVariable => this.endpointId = pathVariable.apiId);
  }

  ngOnInit() {
    this.paramForm = new FormGroup({
      'value': new FormControl(this.parameter.parameterValue, [])
    });
  }

  onCancel(): void {
    this.paramForm.reset();
  }

  onSubmit(): void {
    this.parameter.parameterValue = this.paramForm.get('value').value;
    this.parameterService.updateParameter(this.parameter).subscribe(parameter => {
      this.parameterEditEvent.emit(this.parameter);
    });

    this.onCancel();
    $('#editParamModal').modal('hide');
  }

  get value() {
    return this.paramForm.get('value');
  }

}
