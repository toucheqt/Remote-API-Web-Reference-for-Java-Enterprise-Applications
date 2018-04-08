import { Header } from '../model/header';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable()
export class SettingsService {

  constructor(private http: HttpClient) {}

  /**
   * Finds all global headers for given project.
   * @param projectId ID of project to search by
   * @return list of global headers
   */
  findHeaders(projectId) {
    return this.http.get<Header[]>(`/api/projects/${projectId}/headers`);
  }

}
