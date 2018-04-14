import { Stats } from '../model/stats';
import { TestCase } from '../model/test-case';
import { HttpHeaders } from '@angular/common/http';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class TestCaseService {

  constructor(private http: HttpClient) {}

  findAll(projectId: number) {
    return this.http.get<TestCase[]>(`/api/projects/${projectId}/test-cases`);
  }

  findByName(projectId: number, name: string) {
    return this.http.get<TestCase>(`/api/projects/${projectId}/test-cases/validate/${name}`);
  }

  findStatsByProject(projectId: number) {
    return this.http.get<Stats>(`/api/projects/${projectId}/test-cases/stats`);
  }

  createTestCase(projectId: number, testCase: TestCase) {
    return this.http.post<TestCase>(`/api/projects/${projectId}/test-cases`, JSON.stringify(testCase), httpOptions);
  }

  /**
   * Deletes test cases with given IDs from the project.
   * @param projectId ID of the project
   * @param testCasesIds IDs of the test cases to delete
   */
  deleteTestCases(projectId: number, testCasesIds: number[]) {
    return this.http.request('delete', `/api/projects/${projectId}/test-cases`, {
      headers: httpOptions.headers,
      body: testCasesIds
    });
  }

}
