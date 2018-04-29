import { Log } from '../model/log';
import { EndpointService } from './endpoint.service';
import { TestCaseService } from './test-case.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable()
export class LogService {

  public static get LOGS_PATH(): string { return '/logs'; }
  public static get RECENT_LOG_PATH(): string { return LogService.LOGS_PATH + '/recent'; }

  constructor(private http: HttpClient) {}

  /**
   * Finds all logs for given endpoint.
   *
   * @param endpointId ID of endpoint to search by
   * @return list of logs
   */
  findAllByEndpoint(endpointId: number) {
    return this.http.get<Log[]>('/api' + EndpointService.ENDPOINTS_PATH + `/${endpointId}` + LogService.LOGS_PATH);
  }

  /**
   * Finds all logs for given test case.
   *
   * @param testCaseId ID of test case to search by
   * @return list of logs
   */
  findAllByTestCase(testCaseId: number) {
    return this.http.get<Log[]>('/api' + TestCaseService.TEST_CASES_PATH + `/${testCaseId}` + LogService.LOGS_PATH);
  }

  /**
   * Finds most recent log for given endpoint.
   *
   * @param endpointId ID of endpoint to search by
   * @return recent log
   */
  findRecentLogByEndpoint(endpointId: number) {
    return this.http.get<Log>('/api' + EndpointService.ENDPOINTS_PATH + `/${endpointId}` + LogService.RECENT_LOG_PATH);
  }

  /**
   * Finds most recent log for given test case.
   *
   * @param testCaseId ID of test case to search by
   * @return list of logs
   */
  findRecentLogByTestCase(testCaseId: number) {
    return this.http.get<Log>('/api' + TestCaseService.TEST_CASES_PATH + `/${testCaseId}` + LogService.RECENT_LOG_PATH);
  }

}
