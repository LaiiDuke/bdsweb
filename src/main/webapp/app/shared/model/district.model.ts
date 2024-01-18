import { IWard } from '@/shared/model/ward.model';
import { IStreet } from '@/shared/model/street.model';
import { IProvince } from '@/shared/model/province.model';

export interface IDistrict {
  id?: number;
  name?: string;
  wards?: IWard[] | null;
  streets?: IStreet[] | null;
  province?: IProvince;
}

export class District implements IDistrict {
  constructor(
    public id?: number,
    public name?: string,
    public wards?: IWard[] | null,
    public streets?: IStreet[] | null,
    public province?: IProvince
  ) {}
}
