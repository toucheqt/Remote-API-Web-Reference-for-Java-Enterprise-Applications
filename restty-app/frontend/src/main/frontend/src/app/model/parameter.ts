import { Model } from './model';

export class Parameter {
  id: number;
  type: string;
  name: string;
  required: boolean;
  parameter: string;
  parameterValue: string;
  model: Model;
}
