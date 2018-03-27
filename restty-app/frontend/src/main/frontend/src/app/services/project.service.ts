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

  /**
   * Finds project by given name.
   * @param name Project name to search by
   */
  findByName(name: string) {
    return this.http.get<Project>(`/api/projects/validate/${name}`);
  }

  /**
   * Finds project by given id
   * @param projectId ID of project to search by
   */
  findById(projectId: number) {
    return this.http.get<Project>(`/api/projects/${projectId}`);
  }

  /**
   * Creates project.
   * @param project Project entity with information about project.
   */
  createProject(project: Project) {
    return this.http.post<Project>('/api/projects', JSON.stringify(project), httpOptions);
  }

}
