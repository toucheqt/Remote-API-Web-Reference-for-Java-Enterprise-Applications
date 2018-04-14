import { LastRun } from '../model/last-run';
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

  /*
   * Finds last five api or test cases that were unsucessful
   * @param projectId ID of project to search by
   * @return last five of LastRun
   */
  findLastRunFailures(projectId) {
    return this.http.get<LastRun[]>(`/api/projects/${projectId}/failures`);
  }

  /*
   * Finds five most recently called apis or test cases.
   * @param projectId ID of project to search by
   * @return last five of LastRun
   */
  findRecentRuns(projectId) {
    return this.http.get<LastRun[]>(`/api/projects/${projectId}/recent`);
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

  /**
   * Renames given project.
   * @param project Project to rename.
   */
  renameProject(project: Project) {
    return this.http.put<Project>(`/api/projects/${project.id}`, JSON.stringify(project), httpOptions).subscribe();
  }

  /**
   * Deletes project with given id.
   * @param projectId ID of project that should be deleted
   */
  deleteProject(projectId: number) {
      this.http.delete(`/api/projects/${projectId}`).subscribe();
  }

}
