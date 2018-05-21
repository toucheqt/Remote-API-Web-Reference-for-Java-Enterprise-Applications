import { Endpoint } from '../../../model/endpoint';
import { Header } from '../../../model/header';
import { Parameter } from '../../../model/parameter';
import { HeaderService } from '../../../services/header.service';
import { ModelService } from '../../../services/model.service';
import { Component, OnInit, Input } from '@angular/core';

declare var $: any;

@Component({
  selector: 'app-api-config-tab',
  templateUrl: './api-config-tab.component.html',
  styleUrls: ['./api-config-tab.component.css']
})
export class ApiConfigTabComponent implements OnInit {

  @Input() endpoint: Endpoint;

  headers: Header[];
  loading = true;

  bodyEditable = false;
  bodyString: string;

  constructor(
    private headerService: HeaderService,
    private modelService: ModelService
  ) {}

  ngOnInit(): void {
    this.headerService.findAllByEndpoint(this.endpoint.id).subscribe(headers => {
      this.headers = headers;
      this.loading = false;
    });

    this.endpoint.parameters.forEach(parameter => {
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
  }

  updateHeaderStatus(headerId: number) {
    this.headers.forEach(header => {
      if (header.id === headerId) {
        header.enabled = !header.enabled;
      }
    });

    this.headerService.updateGlobalHeaderStatus(headerId, this.endpoint.id).subscribe();
  }

  createModal(modalId: string, itemId: number) {
    if (modalId === '#editParamModal') {
      modalId = modalId + itemId;
    }

    $(modalId).modal('show');
  }

  refreshOnCreate(header: Header): void {
    this.headers.push(header);
  }

  refreshOnEdit(header: Header): void {
    if (header.enabled) {
      this.headers.forEach(headerItem => {
        if (headerItem.id === header.id) {
          headerItem = header;
        }
      });
    } else {
      this.headers = this.headers.filter(headerItem => headerItem.id !== header.id);
    }
  }

  refreshOnParamEdit(parameter: Parameter): void {
    this.endpoint.parameters.forEach(item => {
      if (item.id === parameter.id) {
        item = parameter;
      }
    });
  }

  makeBody(): void {
    this.bodyEditable = !this.bodyEditable;
    if (!this.bodyEditable) {
      this.bodyString = $('#area-content').val();
      this.endpoint.parameters.forEach(param => {
        if (param.type === 'body') {
          this.modelService.updateModelValues(param.model.id, this.bodyString).subscribe(model => {
            param.model = model;
          });
        }
      });
    }
  }

}
