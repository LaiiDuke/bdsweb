import { IImage } from '@/shared/model/image.model';
import { IPostType } from '@/shared/model/post-type.model';
import { ICategory } from '@/shared/model/category.model';
import { IUser } from '@/shared/model/user.model';

import { PostStatus } from '@/shared/model/enumerations/post-status.model';
export interface IPost {
  id?: number;
  title?: string;
  content?: string | null;
  price?: number;
  square?: number | null;
  status?: PostStatus | null;
  hash?: string | null;
  images?: IImage[] | null;
  type?: IPostType | null;
  category?: ICategory | null;
  user?: IUser | null;
}

export class Post implements IPost {
  constructor(
    public id?: number,
    public title?: string,
    public content?: string | null,
    public price?: number,
    public square?: number | null,
    public status?: PostStatus | null,
    public hash?: string | null,
    public images?: IImage[] | null,
    public type?: IPostType | null,
    public category?: ICategory | null,
    public user?: IUser | null
  ) {}
}
