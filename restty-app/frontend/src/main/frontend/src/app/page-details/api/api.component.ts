import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-api',
  templateUrl: './api.component.html'
})
export class ApiComponent {

  projectId: number;

  constructor(private route: ActivatedRoute) {
    this.route.parent.params.subscribe(pathVariable => this.projectId = pathVariable.id);
  }

}
