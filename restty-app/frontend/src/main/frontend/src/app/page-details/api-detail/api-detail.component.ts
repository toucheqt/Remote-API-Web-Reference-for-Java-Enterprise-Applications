import { Endpoint } from '../../model/endpoint';
import { EndpointService } from '../../services/endpoint.service';
import { Component, OnInit, ViewEncapsulation, ChangeDetectionStrategy, Output, EventEmitter } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NotificationService, NotificationType, Notification } from 'patternfly-ng';
import { Observable } from 'rxjs/Observable';

@Component({
  selector: 'app-api-detail',
  templateUrl: './api-detail.component.html',
  styleUrls: ['./api-detail.component.css'],
  encapsulation: ViewEncapsulation.None,
})
export class ApiDetailComponent implements OnInit {


  projectId: number;
  apiId: number;

  endpoint: Endpoint;

  notifications: Observable<Notification[]>;

  loading = true;
  updateLogs = 0;

  activeTab = 'Details';
  detailsActive = true;
  configActive = false;
  historyActive = false;

  constructor(
    private route: ActivatedRoute,
    private endpointService: EndpointService,
    private notificationService: NotificationService
  ) {
    this.route.parent.params.subscribe(pathVariable => this.projectId = pathVariable.id);
    this.route.params.subscribe(pathVariable => this.apiId = pathVariable.apiId);
  }

  ngOnInit(): void {
    this.endpointService.findById(this.apiId).subscribe(endpoint => {
      this.endpoint = endpoint;
      this.loading = false;
    });

    this.notifications = this.notificationService.getNotificationsObserver;
    this.notificationService.setDelay(3000);
  }

  handleTabChange(view: string) {
    this.activeTab = view;
    this.detailsActive = false;
    this.configActive = false;
    this.historyActive = false;

    if (view === 'Details') {
      this.detailsActive = true;
    } else if (view === 'Configuration') {
      this.configActive = true;
    } else if (view === 'History') {
      this.historyActive = true;
    }
  }

  run(): void {
    this.endpointService.run(this.endpoint.id).subscribe(
      response => {
        this.notificationService.message(
          NotificationType.SUCCESS,
          'Success',
          'The request was completed successfully',
          false,
          null,
          null
        );
        this.updateLogs++;
      },
      error => {
        this.notificationService.message(
          NotificationType.DANGER,
          'Failure',
          'The request failed, see logs for details',
          false,
          null,
          null
        );
        this.updateLogs++;
      },
    );
  }

}
