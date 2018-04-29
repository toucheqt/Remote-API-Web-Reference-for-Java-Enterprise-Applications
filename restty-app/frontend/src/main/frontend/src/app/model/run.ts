/**
 * Entity that contains information about endpoint's / test case's run.
 *
 * @author Ondrej Krpec
 */
export class Run {
  id: number;

  name: string;
  method: string;
  testType: string;

  run: string;
  success: boolean;
}
