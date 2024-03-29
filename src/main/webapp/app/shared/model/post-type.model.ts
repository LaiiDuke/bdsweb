export interface IPostType {
  id?: number;
  name?: string;
  description?: string | null;
}

export class PostType implements IPostType {
  constructor(public id?: number, public name?: string, public description?: string | null) {}
}
