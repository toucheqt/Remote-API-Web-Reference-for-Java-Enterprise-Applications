import { Run } from '../model/run';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { Project } from '../model/project';
import { Stats } from '../model/stats';
import { HttpParams } from '@angular/common/http';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class ProjectService {

  public static get PROJECTS_PATH(): string { return '/api/projects'; }
  public static get PROJECT_VALIDATION_PATH(): string { return ProjectService.PROJECTS_PATH + '/validate'; }

  public static get PROJECT_STATS_PATH(): string { return '/stats'; }
  public static get ENDPOINT_STATS_PATH(): string { return ProjectService.PROJECT_STATS_PATH + '/endpoints'; }
  public static get TEST_CASES_STATS_PATH(): string { return ProjectService.PROJECT_STATS_PATH + '/test-cases'; }

  public static get RECENT_RUNS_PATH(): string { return '/recent'; }
  public static get FAILED_RUNS_PATH(): string { return '/failed'; }

  constructor(private http: HttpClient) {}

  /**
   * Finds all projects.
   *
   * @return list of all projects in the application
   */
  findAll(): Observable<Project[]> {
    return this.http.get<Project[]>(ProjectService.PROJECTS_PATH);
  }

  /**
   * Finds project by given name.
   *
   * @param name Name to search project by.
   * @return project
   */
  findByName(name: string): Observable<Project> {
    let params = new HttpParams();
    params = params.append('name', name);
    return this.http.get<Project>(ProjectService.PROJECT_VALIDATION_PATH, {params: params});
  }

  /**
   * Finds project by given id.
   *
   * @param projectId ID of project to search by
   * @return project
   */
  findById(projectId: number) {
    return this.http.get<Project>(ProjectService.PROJECTS_PATH + `/${projectId}`);
  }

  /**
   * Finds five most recently called APIs or test cases.
   *
   * @param projectId ID of project to search by
   * @return List of five recent runs.
   */
  findRecentRuns(projectId: number) {
    return this.http.get<Run[]>(ProjectService.PROJECTS_PATH + `/${projectId}` + ProjectService.RECENT_RUNS_PATH);
  }

  /**
   * Finds five most recently failed API or test cases calls.
   *
   * @param projectId ID of project to search by
   * @return List of five recently failed runs.
   */
  findRecentFailedRuns(projectId: number) {
    return this.http.get<Run[]>(ProjectService.PROJECTS_PATH + `/${projectId}` + ProjectService.FAILED_RUNS_PATH);
  }

  /**
   * Finds endpoints statistics for given project.
   *
   * @param projectId ID of project to search by
   * @return Statistics of endpoints
   */
  findEndpointsStats(projectId: number) {
    return this.http.get<Stats>(ProjectService.PROJECTS_PATH + `/${projectId}` + ProjectService.ENDPOINT_STATS_PATH);
  }

  /**
   * Finds test cases statistics for given project.
   *
   * @param projectId ID of project to search by
   * @return Statistics of test cases
   */
  findTestCasesStats(projectId: number) {
    return this.http.get<Stats>(ProjectService.PROJECTS_PATH + `/${projectId}` + ProjectService.TEST_CASES_STATS_PATH);
  }

  /**
   * Creates new project from the information in the input parameter.
   *
   * @param project Information about new Project.
   * @return Project
   */
  createProject(project: Project) {
    return this.http.post<Project>(ProjectService.PROJECTS_PATH, JSON.stringify(project), httpOptions);
  }

  /**
   * Renames project.
   *
   * @param project Project to rename.
   */
  renameProject(project: Project) {
    this.http.put(ProjectService.PROJECTS_PATH + `/${project.id}`, JSON.stringify(project), httpOptions).subscribe();
  }

  /**
   * Deletes project with given id.
   *
   * @param projectId ID of project to delete.
   */
  deleteProject(projectId: number) {
    this.http.delete(ProjectService.PROJECTS_PATH + `/${projectId}`).subscribe();
  }

}
