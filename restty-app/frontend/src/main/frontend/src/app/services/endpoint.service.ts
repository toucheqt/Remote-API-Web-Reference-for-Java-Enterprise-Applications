import { Stats } from '../model/stats';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable()
export class EndpointService {

  constructor(private http: HttpClient) {}

  findStatsByProject(projectId: number) {
    return this.http.get<Stats>(`/api/projects/${projectId}/endpoints/stats`);
  }

}
