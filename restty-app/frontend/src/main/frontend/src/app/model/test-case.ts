/**
 * Entity that contains information about test cases.
 * @author Ondrej Krpec
 */
export class TestCase {
  id: number;
  name: string;
  description: string;
  lastRun: string;
  lastRunSuccess: boolean;
  lastRunMessage: string;
  hasSteps: boolean;
}
