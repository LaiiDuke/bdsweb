import { IWard } from '@/shared/model/ward.model';
import { IDistrict } from '@/shared/model/district.model';

import { PostStatus } from '@/shared/model/enumerations/post-status.model';
export interface IStreet {
  id?: number;
  name?: string;
  status?: PostStatus | null;
  ward?: IWard | null;
  district?: IDistrict;
}

export class Street implements IStreet {
  constructor(
    public id?: number,
    public name?: string,
    public status?: PostStatus | null,
    public ward?: IWard | null,
    public district?: IDistrict
  ) {}
}
