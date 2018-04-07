import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-no-test-cases',
  templateUrl: './no-test-cases.component.html',
  styleUrls: ['./no-test-cases.component.css']
})
export class NoTestCasesComponent implements OnInit {

  projectId: number;

  constructor(
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(pathVariable => {
      this.projectId = pathVariable.id;
    });
  }

  toTestCaseDetail() {
    this.router.navigate(['projects', this.projectId, 'test-cases']);
  }

}
