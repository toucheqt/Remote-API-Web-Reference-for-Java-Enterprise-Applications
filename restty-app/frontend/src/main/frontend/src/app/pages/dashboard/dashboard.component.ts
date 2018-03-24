import { Project } from '../../model/project';
import { ProjectService } from '../../services/project.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {

  project: Project;

  constructor(
    private route: ActivatedRoute,
    private projectService: ProjectService
  ) {
    this.route.params.subscribe(result => {
      this.projectService.findById(result.id).subscribe(project => this.project = project);
    });
  }

}
