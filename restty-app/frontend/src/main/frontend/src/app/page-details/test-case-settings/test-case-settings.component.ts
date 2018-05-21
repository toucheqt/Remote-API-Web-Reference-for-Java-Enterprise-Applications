import { Parameter } from '../../model/parameter';
import { TestCaseSettings } from '../../model/test-case-settings';
import { ModelService } from '../../services/model.service';
import { TestCaseSettingsService } from '../../services/test-case-settings.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

declare var $: any;

@Component({
  selector: 'app-test-case-settings',
  templateUrl: './test-case-settings.component.html',
  styleUrls: ['./test-case-settings.component.css']
})
export class TestCaseSettingsComponent implements OnInit {

  testCaseSettingsId: number;
  testCaseSettings: TestCaseSettings;

  loading = true;

  bodyEditable = false;
  bodyString: string;

  constructor(
    private route: ActivatedRoute,
    private modelService: ModelService,
    private testCaseSettingsService: TestCaseSettingsService
  ) {
    this.route.params.subscribe(pathVariable => this.testCaseSettingsId = pathVariable.settingsId);
  }

  ngOnInit() {
    this.testCaseSettingsService.findById(this.testCaseSettingsId).subscribe(settings => {
      this.testCaseSettings = settings;
      this.testCaseSettings.parameters.forEach(parameter => {
        if (parameter.type === 'body') {
          let content = '{\n';

          for (let i = 0; i < parameter.model.attributes.length; i++) {
            const paramType = parameter.model.attributes[i].type;
            const paramValue = parameter.model.attributes[i].value;

            content += '\t"' + parameter.model.attributes[i].name + '": ';
            if (paramValue === null) {
              content += '--';
            } else {
              if (paramType === 'string') {
                content += '"' + paramValue + '"';
              } else {
                content += paramValue;
              }
            }

            if (i < parameter.model.attributes.length - 1) {
              content += ',\n';
            } else {
              content += '\n';
            }
          }

          content += '}';
          this.bodyString = content;
        }
      });

      this.loading = false;
    });
  }

  createModal(modalId: string, itemId: number) {
    if (modalId === '#editParamModal') {
      modalId = modalId + itemId;
    }

    $(modalId).modal('show');
  }

  refreshOnParamEdit(parameter: Parameter): void {
    this.testCaseSettings.parameters.forEach(item => {
      if (item.id === parameter.id) {
        item = parameter;
      }
    });
  }

  makeBody(): void {
    this.bodyEditable = !this.bodyEditable;
    if (!this.bodyEditable) {
      this.bodyString = $('#area-content').val();
      this.testCaseSettings.parameters.forEach(param => {
        if (param.type === 'body') {
          this.modelService.updateModelValues(param.model.id, this.bodyString).subscribe(model => {
            param.model = model;
          });
        }
      });
    }
  }

}
