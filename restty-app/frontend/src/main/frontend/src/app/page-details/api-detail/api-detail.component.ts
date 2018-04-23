import { Endpoint } from '../../model/endpoint';
import { EndpointService } from '../../services/endpoint.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-api-detail',
  templateUrl: './api-detail.component.html',
  styleUrls: ['./api-detail.component.css']
})
export class ApiDetailComponent implements OnInit {

  projectId: number;
  apiId: number;

  endpoint: Endpoint;

  loading = true;

  activeTab = 'Details';

  constructor(
    private route: ActivatedRoute,
    private endpointService: EndpointService
  ) {
    this.route.parent.params.subscribe(pathVariable => this.projectId = pathVariable.id);
    this.route.params.subscribe(pathVariable => this.apiId = pathVariable.apiId);
  }

  ngOnInit(): void {
    this.endpointService.findById(this.projectId, this.apiId).subscribe(endpoint => {
      this.endpoint = endpoint;
      this.loading = false;
    });
  }

}
