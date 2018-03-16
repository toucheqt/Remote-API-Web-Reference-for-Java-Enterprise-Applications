import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { Project } from '../model/project';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class ProjectService {

  constructor(private http: HttpClient) {}

  /**
   * Finds all projects.
   */
  findProjects() {
    return this.http.get<Project[]>('/api/projects');
  }

  findByName(name: string) {
    return this.http.get<Project>(`/api/projects/${name}`);
  }

  /**
   * Creates project.
   * @param project Project entity with information about project.
   */
  createProject(project: Project) {
    return this.http.post<Project>('/api/projects', JSON.stringify(project), httpOptions);
  }

}
