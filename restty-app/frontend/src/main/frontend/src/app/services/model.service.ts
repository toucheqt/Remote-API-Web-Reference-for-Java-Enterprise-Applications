import { Model } from '../model/model';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class ModelService {

  public static get MODELS_PATH(): string { return '/api/models'; }

  constructor(private http: HttpClient) {}

  /**
   * Updates model values.
   *
   * @param modelId ID of model to update
   * @param content updated model's content
   * @return updated model
   */
  updateModelValues(modelId: number, content: string) {
    return this.http.put<Model>(ModelService.MODELS_PATH + `/${modelId}`, content, httpOptions);
  }

}
