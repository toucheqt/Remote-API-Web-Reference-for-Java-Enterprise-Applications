import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-test-cases',
  templateUrl: './test-cases.component.html'
})
export class TestCasesComponent {

  projectId: number;

  constructor(private route: ActivatedRoute) {
    this.route.parent.params.subscribe(pathVariable => this.projectId = pathVariable.id);
  }

}
