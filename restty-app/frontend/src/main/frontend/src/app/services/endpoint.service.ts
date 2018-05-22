import { Endpoint } from '../model/endpoint';
import { Stats } from '../model/stats';
import { ProjectService } from './project.service';
import { HttpHeaders } from '@angular/common/http';
import { HttpDownloadProgressEvent } from '@angular/common/http';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class EndpointService {

  public static get ENDPOINTS_PATH(): string { return '/endpoints'; }
  public static get ENDPOINT_PATH(): string { return '/api/endpoints'; }

  public static get RUN_PATH(): string { return '/run'; }

  constructor(private http: HttpClient) {}

  /**
   * Finds all endpoints for given project.
   *
   * @param projectId ID of project to search by
   * @return list of endpoints
   */
  findAll(projectId: number) {
    return this.http.get<Endpoint[]>(ProjectService.PROJECTS_PATH + `/${projectId}` + EndpointService.ENDPOINTS_PATH);
  }

  /**
   * Finds endpoint by given ID.
   *
   * @param endpointId ID of endpoint to search by
   * @return Endpoint
   */
  findById(endpointId: number) {
    return this.http.get<Endpoint>(EndpointService.ENDPOINT_PATH + `/${endpointId}`);
  }

  /**
   * Search endpoint by given ID and runs it.
   *
   * @param endpointId ID of endpoint to run.
   * @return response status
   */
  run(endpointId: number) {
    return this.http.post(EndpointService.ENDPOINT_PATH + `/${endpointId}` + EndpointService.RUN_PATH, null);
  }

  /**
   * Runs all endpoints against test server.
   *
   * @param endpointIds IDs of endpoints to run
   * @return response status
   */
  runAll(endpointIds: number[]) {
    return this.http.post(`/api/endpoints/run`, endpointIds, httpOptions);
  }

}
