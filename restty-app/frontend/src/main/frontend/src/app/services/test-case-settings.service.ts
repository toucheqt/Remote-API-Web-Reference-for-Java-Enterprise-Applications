import { IdNameMethod } from '../model/id-name-method';
import { TestCaseSettings } from '../model/test-case-settings';
import { TestCaseService } from './test-case.service';
import { HttpHeaders } from '@angular/common/http';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

const httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class TestCaseSettingsService {

  public static get TEST_CASE_SETTINGS_PATH(): string { return '/settings'; }
  public static get TEST_CASE_STEPS_PATH(): string { return TestCaseSettingsService.TEST_CASE_SETTINGS_PATH + '/steps'; }

  constructor(private http: HttpClient) {}

  /**
   * Finds all test case settings for given test case.
   *
   * @param testCaseId ID of test case to search by
   * @return list of test case settings
   */
  findAllByTestCaseId(testCaseId: number) {
    return this.http.get<TestCaseSettings[]>(
      TestCaseService.TEST_CASE_PATH + `/${testCaseId}` + TestCaseSettingsService.TEST_CASE_SETTINGS_PATH);
  }

  /**
   * Finds test case's settings by given id
   *
   * @param testCaseSettingsId ID of test case settings to search by
   * @return TestCaseSettings
   */
  findById(testCaseSettingsId: number) {
    return this.http.get<TestCaseSettings>('/api/test-cases/settings' + `/${testCaseSettingsId}`);
  }

  /**
   * Finds all steps for given test case.
   *
   * @param testCaseId
   *            ID of test case to search by
   * @return list of step's ids, names and methods
   */
  findAllSteps(testCaseId: number) {
    return this.http.get<IdNameMethod[]>(
      TestCaseService.TEST_CASE_PATH + `/${testCaseId}` + TestCaseSettingsService.TEST_CASE_STEPS_PATH);
  }

  /**
   * Adds endpoint to the test case.
   *
   * @param testCaseId ID of test case
   * @param endpointId ID of endpoint
   * @param usePrevious if the endpoint should be connected to previous step
   * @return testCaseSettings
   */
  addEndpointToTestCase(testCaseId: number, endpointId: number, usePrevious: boolean) {
    return this.http.post<TestCaseSettings>(TestCaseService.TEST_CASE_PATH + `/${testCaseId}`
      + TestCaseSettingsService.TEST_CASE_SETTINGS_PATH + `/endpoints/${endpointId}`, JSON.stringify(usePrevious), httpOptions);
  }

  /**
   * Adds test case to the test case.
   *
   * @param testCaseId ID of test case
   * @param stepTestCaseId ID of step test case
   * @param usePrevious if the endpoint should be connected to previous step
   * @return testCaseSettings
   */
  addTestStepToTestCase(testCaseId: number, testStepId: number, usePrevious: boolean) {
    return this.http.post<TestCaseSettings>(TestCaseService.TEST_CASE_PATH + `/${testCaseId}`
      + TestCaseSettingsService.TEST_CASE_SETTINGS_PATH + `/test-cases/${testStepId}`, JSON.stringify(usePrevious), httpOptions);
  }

  /**
   * Deletes given and following steps
   *
   * @param testCaseId ID of test case
   * @param stepOrder step to delete
   * @return http status
   */
  deleteSteps(testCaseId: number, stepOrder: number) {
    return this.http.request('delete',
      TestCaseService.TEST_CASE_PATH + `/${testCaseId}` + TestCaseSettingsService.TEST_CASE_SETTINGS_PATH, {
        headers: httpOptions.headers,
        body: stepOrder
      });
  }

}
