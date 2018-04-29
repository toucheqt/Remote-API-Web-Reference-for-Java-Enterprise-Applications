import { Endpoint } from '../../../model/endpoint';
import { EndpointService } from '../../../services/endpoint.service';
import { LogService } from '../../../services/log.service';
import { Component, OnInit, Input, OnChanges, SimpleChanges } from '@angular/core';

@Component({
  selector: 'app-api-detail-tab',
  templateUrl: './api-detail-tab.component.html',
  styleUrls: ['./api-detail-tab.component.css']
})
export class ApiDetailTabComponent implements OnChanges {

  @Input() endpoint: Endpoint;
  @Input() update: number;

  constructor(private logService: LogService) {}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.update.currentValue && this.endpoint !== undefined) {
      this.logService.findRecentLogByEndpoint(this.endpoint.id).subscribe(log => {
        this.endpoint.lastRun = log.run;
        this.endpoint.lastRunSuccess = log.success;
        this.endpoint.lastRunMessage = log.responseMessage;
      });
    }
  }

}
