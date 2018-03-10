import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs/Observable';

import { ProjectService } from './services/project.service';
import { Project } from './model/project';

// TODO vychazime z https://github.com/kdechant/angular5-httpclient-demo/tree/master/src/app

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';
  projects: Project[];

  constructor(private projectService: ProjectService) {}

  OnInit() {
    this.projectService
      .findProjects()
      .subscribe(result => this.projects = result);
  }

}
