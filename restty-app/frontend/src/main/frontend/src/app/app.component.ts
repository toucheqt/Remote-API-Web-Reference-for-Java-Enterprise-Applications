import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs/Observable';

import { ProjectService } from './services/project.service';
import { Project } from './model/project';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  title = 'app';
  projects: Project[];

  constructor(
    private router: Router,
    private projectService: ProjectService) {}

  ngOnInit(): void {
    this.projectService
      .findProjects()
      .subscribe(result => {
        if (result.length > 0) {
          this.router.navigate(['projects']);
        } else {
          this.router.navigate(['new-project']);
        }
      });
  }

}
