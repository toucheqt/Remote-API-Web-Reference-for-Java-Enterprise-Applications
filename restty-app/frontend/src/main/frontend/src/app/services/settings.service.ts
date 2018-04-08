import { Header } from '../model/header';
import { HttpHeaders } from '@angular/common/http';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RequestOptions } from '@angular/http';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class SettingsService {

  constructor(private http: HttpClient) {}

  /**
   * Finds all global headers for given project.
   * @param projectId ID of project to search by
   * @return list of global headers
   */
  findGlobalHeaders(projectId) {
    return this.http.get<Header[]>(`/api/projects/${projectId}/global-headers`);
  }

  /**
   * Finds global header by given name for given project.
   * @param projectId ID of project to search by
   * @param header Header name to search by
   * @return Header
   */
  findByHeader(projectId: number, header: string) {
    return this.http.get<Header>(`/api/projects/${projectId}/headers/${header}`);
  }

  /**
   * Creates global header.
   * @param projectId ID of project
   * @param header Header entity with information about new header
   * @return Header
   */
  createHeader(projectId: number, header: Header) {
    return this.http.post<Header>(`/api/projects/${projectId}/headers`, JSON.stringify(header), httpOptions);
  }

  /**
   * Deletes headers with given ids from given project.
   * @param projectId ID of project
   * @param headerIds IDs of headers to delete
   */
  deleteHeaders(projectId: number, headerIds: number[]) {
    return this.http.request('delete', `/api/projects/${projectId}/headers`, {
      headers: httpOptions.headers,
      body: headerIds
    });
  }

}
