import { Log } from './log';
import { Parameter } from './parameter';
import { Response } from './response';

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
  lastRunMessage: string;

  parameters: Parameter[];
  responses: Response[];
  log: Log[];
}
