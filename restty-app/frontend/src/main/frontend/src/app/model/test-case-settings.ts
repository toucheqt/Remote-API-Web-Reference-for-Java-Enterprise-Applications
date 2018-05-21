import { Endpoint } from './endpoint';
import { Parameter } from './parameter';
import { TestCase } from './test-case';
export class TestCaseSettings {
  id: number;
  endpointOrder: number;
  endpoint: Endpoint;
  failed: boolean;
  parent: TestCase;
  testCase: TestCase;
  parameters: Parameter[];
}
