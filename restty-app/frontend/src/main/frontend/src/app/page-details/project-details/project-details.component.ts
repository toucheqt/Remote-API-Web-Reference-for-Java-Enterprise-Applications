import { Project } from '../../model/project';
import { Stats } from '../../model/stats';
import { EndpointService } from '../../services/endpoint.service';
import { TestCaseService } from '../../services/test-case.service';
import { Component, OnInit, TemplateRef, ViewChild, Input, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { cloneDeep } from 'lodash';
import { DonutConfig, TableConfig, TableEvent } from 'patternfly-ng';

@Component({
  selector: 'app-project-details',
  templateUrl: './project-details.component.html',
  styleUrls: ['./project-details.component.css'],
  encapsulation: ViewEncapsulation.None,
})
export class ProjectDetailsComponent implements OnInit {

  projectId: number;

  endpointsData: any[];
  testCasesData: any[];

  endpointsConfig: DonutConfig;
  testCasesConfig: DonutConfig;

  loadingCharts = true;

  constructor(
    private route: ActivatedRoute,
    private endpointsService: EndpointService,
    private testCasesService: TestCaseService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(pathVariable => {
      this.endpointsService.findStatsByProject(pathVariable.id).subscribe(stats => {
        this.endpointsData = [
          ['New', stats.untested],
          ['Successful', stats.successes],
          ['Failed', stats.failures]
        ];
        this.endpointsConfig = {
          chartId: 'endpointsDonut',
          colors: {
            New: '#d1d1d1', // gray
            Successful: '#39a5dc', // blue
            Failed: '#cc0000' // red
          },
          donut: {
            title: 'Endpoints'
          },
          legend: {
            show: true
          },
          chartHeight: 200
        };

        if (this.endpointsData !== undefined && this.testCasesData !== undefined) {
          this.loadingCharts = false;
        }
      });
    });

    this.route.params.subscribe(pathVariable => {
      this.testCasesService.findStatsByProject(pathVariable.id).subscribe(stats => {
        this.testCasesData = [
          ['New', stats.untested],
          ['Successful', stats.successes],
          ['Failed', stats.failures]
        ];
        this.testCasesConfig = {
          chartId: 'testCasesDonut',
          colors: {
            New: '#d1d1d1', // gray
            Successful: '#39a5dc', // blue
            Failed: '#cc0000' // red
          },
          donut: {
            title: 'Test Cases'
          },
          legend: {
            show: true
          },
          chartHeight: 200
        };

        if (this.endpointsData !== undefined && this.testCasesData !== undefined) {
          this.loadingCharts = false;
        }
      });
    });

  }

}
