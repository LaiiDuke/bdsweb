import { IStreet } from '@/shared/model/street.model';
import { IDistrict } from '@/shared/model/district.model';

export interface IWard {
  id?: number;
  name?: string;
  streets?: IStreet[] | null;
  district?: IDistrict | null;
}

export class Ward implements IWard {
  constructor(public id?: number, public name?: string, public streets?: IStreet[] | null, public district?: IDistrict | null) {}
}
