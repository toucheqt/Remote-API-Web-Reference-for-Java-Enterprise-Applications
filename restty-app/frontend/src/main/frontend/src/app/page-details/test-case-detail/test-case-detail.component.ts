import { TestCase } from '../../model/test-case';
import { TestCaseService } from '../../services/test-case.service';
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NotificationService, Notification, NotificationType } from 'patternfly-ng';
import { Observable } from 'rxjs/Observable';

@Component({
  selector: 'app-test-case-detail',
  templateUrl: './test-case-detail.component.html',
  styleUrls: ['./test-case-detail.component.css'],
  encapsulation: ViewEncapsulation.None,
})
export class TestCaseDetailComponent implements OnInit {

  testCaseId: number;
  testCase: TestCase;

  notifications: Observable<Notification[]>;

  loading = true;
  updateLogs = 0;

  activeTab = 'Details';
  detailsActive = true;
  historyActive = false;

  constructor(
    private route: ActivatedRoute,
    private testCaseService: TestCaseService,
    private notificationService: NotificationService
  ) {
    this.route.params.subscribe(pathVariable => this.testCaseId = pathVariable.testCaseId);
  }

  ngOnInit(): void {
    this.testCaseService.findById(this.testCaseId).subscribe(testCase => {
      this.testCase = testCase;
      this.loading = false;
    });

    this.notifications = this.notificationService.getNotificationsObserver;
    this.notificationService.setDelay(3000);
  }

  handleTabChange(view: string) {
    this.activeTab = view;
    this.detailsActive = false;
    this.historyActive = false;

    if (view === 'Details') {
      this.detailsActive = true;
    } else if (view === 'History') {
      this.historyActive = true;
    }
  }

  run(): void {
    this.testCaseService.run(this.testCaseId).subscribe(
      response => {
        this.notificationService.message(
          NotificationType.SUCCESS,
          'Success',
          'The test case was completed successfully',
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
          'The test case failed, see logs for details',
          false,
          null,
          null
        );
        this.updateLogs++;
      }
    );
  }

}
