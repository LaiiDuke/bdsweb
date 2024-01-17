import { IImage } from '@/shared/model/image.model';
import { IPostType } from '@/shared/model/post-type.model';
import { ICategory } from '@/shared/model/category.model';
import { IUser } from '@/shared/model/user.model';
import { IProvince } from '@/shared/model/province.model';
import { IDistrict } from '@/shared/model/district.model';
import { IWard } from '@/shared/model/ward.model';
import { IStreet } from '@/shared/model/street.model';

import { PostStatus } from '@/shared/model/enumerations/post-status.model';
export interface IPost {
  id?: number;
  title?: string;
  content?: string | null;
  price?: number;
  square?: number | null;
  address?: string | null;
  googleMapsLocation?: string | null;
  width?: number | null;
  length?: number | null;
  direction?: string | null;
  distance?: string | null;
  legal?: string | null;
  numberOfFloors?: number | null;
  numberOfBedroom?: number | null;
  hasKitchen?: boolean | null;
  hasDinningRoom?: boolean | null;
  hasRooftop?: boolean | null;
  hasGarage?: boolean | null;
  isVip?: boolean | null;
  postingTime?: Date | null;
  expiredTime?: Date | null;
  brokerageFees?: number | null;
  status?: PostStatus | null;
  star?: number | null;
  hash?: string | null;
  images?: IImage[] | null;
  type?: IPostType | null;
  category?: ICategory | null;
  user?: IUser | null;
  province?: IProvince | null;
  district?: IDistrict | null;
  ward?: IWard | null;
  street?: IStreet | null;
}

export class Post implements IPost {
  constructor(
    public id?: number,
    public title?: string,
    public content?: string | null,
    public price?: number,
    public square?: number | null,
    public address?: string | null,
    public googleMapsLocation?: string | null,
    public width?: number | null,
    public length?: number | null,
    public direction?: string | null,
    public distance?: string | null,
    public legal?: string | null,
    public numberOfFloors?: number | null,
    public numberOfBedroom?: number | null,
    public hasKitchen?: boolean | null,
    public hasDinningRoom?: boolean | null,
    public hasRooftop?: boolean | null,
    public hasGarage?: boolean | null,
    public isVip?: boolean | null,
    public postingTime?: Date | null,
    public expiredTime?: Date | null,
    public brokerageFees?: number | null,
    public status?: PostStatus | null,
    public star?: number | null,
    public hash?: string | null,
    public images?: IImage[] | null,
    public type?: IPostType | null,
    public category?: ICategory | null,
    public user?: IUser | null,
    public province?: IProvince | null,
    public district?: IDistrict | null,
    public ward?: IWard | null,
    public street?: IStreet | null
  ) {
    this.hasKitchen = this.hasKitchen ?? false;
    this.hasDinningRoom = this.hasDinningRoom ?? false;
    this.hasRooftop = this.hasRooftop ?? false;
    this.hasGarage = this.hasGarage ?? false;
    this.isVip = this.isVip ?? false;
  }
}
