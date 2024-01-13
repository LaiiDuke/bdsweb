import { IUser } from '@/shared/model/user.model';

export interface IUserInfo {
  id?: number;
  name?: string | null;
  phone?: string;
  user?: IUser | null;
}

export class UserInfo implements IUserInfo {
  constructor(public id?: number, public name?: string | null, public phone?: string, public user?: IUser | null) {}
}
