import { Endpoint } from '../../../model/endpoint';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-api-detail-tab',
  templateUrl: './api-detail-tab.component.html',
  styleUrls: ['./api-detail-tab.component.css']
})
export class ApiDetailTabComponent {

  @Input() endpoint: Endpoint;

}
