import { Header } from '../model/header';
import { EndpointService } from './endpoint.service';
import { ProjectService } from './project.service';
import { HttpParams } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RequestOptions } from '@angular/http';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class HeaderService {

  public static get GLOBAL_HEADERS_PATH(): string { return '/global-headers'; }
  public static get GLOBAL_HEADER_VALIDATION_PATH(): string { return HeaderService.GLOBAL_HEADERS_PATH + '/validate'; }

  public static get HEADERS_PATH(): string { return '/headers'; }
  public static get HEADER_VALIDATION_PATH(): string { return HeaderService.HEADERS_PATH + '/validate'; }

  constructor(private http: HttpClient) {}

  /**
   * Finds all global headers by project.
   *
   * @param projectId ID of project to search by
   * @return list of global headers
   */
  findAllGlobalHeadersByProject(projectId: number) {
    return this.http.get<Header[]>(ProjectService.PROJECTS_PATH + `/${projectId}` + HeaderService.GLOBAL_HEADERS_PATH);
  }

  /**
   * Finds global header by given name and project.
   *
   * @param projectId ID of project to search by
   * @param header Name of the header to search by
   * @return global header
   */
  findGlobalByHeader(projectId: number, header: string) {
    let params = new HttpParams();
    params = params.append('header', header);
    return this.http.get<Header>(ProjectService.PROJECTS_PATH + `/${projectId}` + HeaderService.GLOBAL_HEADER_VALIDATION_PATH,
      {params: params});
  }

  /**
   * Finds endpoint specific header by given name.
   *
   * @param endpointId ID of endpoint to search by
   * @param header Name of the header to search by
   * @return header
   */
  findByHeader(endpointId: number, header: string) {
    let params = new HttpParams();
    params = params.append('header', header);
    return this.http.get<Header>(EndpointService.ENDPOINT_PATH + `/${endpointId}` + HeaderService.HEADER_VALIDATION_PATH,
      {params: params});
  }

  /**
   * Finds all headers by given endpoint.
   *
   * @param endpointId ID of endpoint to search by
   * @return list of headers
   */
  findAllByEndpoint(endpointId: number) {
    return this.http.get<Header[]>(EndpointService.ENDPOINT_PATH + `/${endpointId}` + HeaderService.HEADERS_PATH);
  }

  /**
   * Create new global header for given project.
   *
   * @param projectId Id of project to create header for
   * @param header Information about new header
   * @return Header
   */
  createGlobalHeader(projectId: number, header: Header) {
    return this.http.post<Header>(ProjectService.PROJECTS_PATH + `/${projectId}` + HeaderService.GLOBAL_HEADERS_PATH,
      JSON.stringify(header), httpOptions);
  }

  /**
   * Creates new header for given endpoint.
   *
   * @param endpointId ID of endpoint to create header for
   * @param header Information about new header
   * @return header
   */
  createHeader(endpointId: number, header: Header) {
    return this.http.post<Header>(EndpointService.ENDPOINT_PATH + `/${endpointId}` + HeaderService.HEADERS_PATH,
      JSON.stringify(header), httpOptions);
  }

  /**
   * Updates status of global header for given endpoint.
   *
   * @param headerId ID of header to update
   * @param endpointId ID of endpoint to update
   * @return response status
   */
  updateGlobalHeaderStatus(headerId: number, endpointId: number) {
    return this.http.put(EndpointService.ENDPOINT_PATH + `/${endpointId}` + HeaderService.HEADERS_PATH + `/${headerId}`, null);
  }

  /**
   * Updates given header.
   *
   * @param header Header with new name and value
   */
  updateHeader(header: Header) {
    return this.http.put('/api' + HeaderService.HEADERS_PATH + `/${header.id}`, JSON.stringify(header), httpOptions);
  }

  /**
   * Deletes headers with given ids.
   *
   * @param headerIds IDs of headers to delete
   * @return http status
   */
  deleteHeaders(headerIds: number[]) {
    return this.http.request('delete', '/api' + HeaderService.HEADERS_PATH, {
      headers: httpOptions.headers,
      body: headerIds
    });
  }

}
