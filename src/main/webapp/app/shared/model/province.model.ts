import { IDistrict } from '@/shared/model/district.model';

export interface IProvince {
  id?: number;
  name?: string;
  districts?: IDistrict[] | null;
}

export class Province implements IProvince {
  constructor(public id?: number, public name?: string, public districts?: IDistrict[] | null) {}
}
