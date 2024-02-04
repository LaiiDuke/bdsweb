export interface ICategory {
  id?: number;
  name?: string;
  icon?: string | null;
  description?: string | null;
}

export class Category implements ICategory {
  constructor(public id?: number, public name?: string, public icon?: string | null, public description?: string | null) {}
}
