import { Endpoint } from '../../../model/endpoint';
import { Header } from '../../../model/header';
import { HeaderService } from '../../../services/header.service';
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

  constructor(private headerService: HeaderService) {}

  ngOnInit(): void {
    this.headerService.findAllByEndpoint(this.endpoint.id).subscribe(headers => {
      this.headers = headers;
      this.loading = false;
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

  createHeaderModal($event) {
    if ($event.screenX !== 0) {
      $('#addHeaderModal').modal('show');
    }
  }

  editHeaderModal($event) {
    if ($event.screenX !== 0) {
      $('#editHeaderModal').modal('show');
    }
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

}
