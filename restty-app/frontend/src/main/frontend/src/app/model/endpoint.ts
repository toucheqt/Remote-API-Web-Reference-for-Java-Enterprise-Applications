/**
 * Entity that contains information about endpoints.
 * @author Ondrej Krpec
 */
export class Endpoint {
  id: number;
  path: string;
  method: string;
  description: string;

  lastRun: string;
  lastRunSuccess: boolean;
}
