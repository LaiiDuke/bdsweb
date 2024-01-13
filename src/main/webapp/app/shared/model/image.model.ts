import { IPost } from '@/shared/model/post.model';

export interface IImage {
  id?: number;
  dataContentType?: string | null;
  data?: string | null;
  url?: string | null;
  post?: IPost | null;
}

export class Image implements IImage {
  constructor(
    public id?: number,
    public dataContentType?: string | null,
    public data?: string | null,
    public url?: string | null,
    public post?: IPost | null
  ) {}
}
