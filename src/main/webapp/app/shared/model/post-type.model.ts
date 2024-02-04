export interface IPostType {
  id?: number;
  name?: string;
  icon?: string | null;
  description?: string | null;
}

export class PostType implements IPostType {
  constructor(public id?: number, public name?: string, public icon?: string | null, public description?: string | null) {}
}
