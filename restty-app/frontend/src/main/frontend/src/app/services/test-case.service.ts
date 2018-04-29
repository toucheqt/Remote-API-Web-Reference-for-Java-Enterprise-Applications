import { Stats } from '../model/stats';
import { TestCase } from '../model/test-case';
import { ProjectService } from './project.service';
import { HttpParams } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class TestCaseService {

  public static get TEST_CASES_PATH(): string { return '/test-cases'; }
  public static get TEST_CASE_VALIDATION_PATH(): string { return TestCaseService.TEST_CASES_PATH + '/validate'; }

  constructor(private http: HttpClient) {}

  /**
   * Finds all test cases for given project.
   *
   * @param projectId ID of project to search by
   * @return list of test cases
   */
  findAll(projectId: number) {
    return this.http.get<TestCase[]>(ProjectService.PROJECTS_PATH + `/${projectId}` + TestCaseService.TEST_CASES_PATH);
  }

  /**
   * Finds test case by given name.
   *
   * @param projectId ID of project to search by
   * @param name Name to search test case by
   * @return TestCase
   */
  findByName(projectId: number, name: string) {
    let params = new HttpParams();
    params = params.append('name', name);
    return this.http.get(ProjectService.PROJECTS_PATH + `/${projectId}` + TestCaseService.TEST_CASE_VALIDATION_PATH, {params: params});
  }

  /**
   * Creates new test case from the information in the input parameter.
   *
   * @param projectId ID of project to create test case for
   * @param testCase Information about new TestCase
   * @return TestCase
   */
  createTestCase(projectId: number, testCase: TestCase) {
    return this.http.post<TestCase>(ProjectService.PROJECTS_PATH + `/${projectId}`
       + TestCaseService.TEST_CASES_PATH, JSON.stringify(testCase), httpOptions);
  }

  /**
   * Deletes test cases with given IDs from the project.
   *
   * @param projectId ID of the project
   * @param testCasesIds IDs of the test cases to delete
   */
  deleteTestCases(projectId: number, testCasesIds: number[]) {
    return this.http.request('delete', ProjectService.PROJECTS_PATH + `/${projectId}` + TestCaseService.TEST_CASES_PATH, {
      headers: httpOptions.headers,
      body: testCasesIds
    });
  }

}
