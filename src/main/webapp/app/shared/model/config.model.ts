export interface IConfig {
  id?: number;
  code?: string;
  value?: string;
  description?: string | null;
}

export class Config implements IConfig {
  constructor(public id?: number, public code?: string, public value?: string, public description?: string | null) {}
}
