/**
 * Entity that contains information about last run.
 *
 * @author Ondrej Krpec
 */
export class LastRun {
  id: number;
  name: string;
  method: string;
  testType: string;
  lastRun: string;
  lastRunSuccess: boolean;
}
