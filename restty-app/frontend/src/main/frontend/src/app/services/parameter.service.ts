import { Parameter } from '../model/parameter';
import { HttpHeaders } from '@angular/common/http';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};


@Injectable()
export class ParameterService {

  public static get PARAMETERS_PATH(): string { return '/api/parameters'; }

  constructor(private http: HttpClient) {}

  /**
   * Updates given parameter.
   *
   * @param parameter Parameter to update
   */
  updateParameter(parameter: Parameter) {
    return this.http.put<Parameter>(ParameterService.PARAMETERS_PATH + `/${parameter.id}`, JSON.stringify(parameter), httpOptions);
  }

}
