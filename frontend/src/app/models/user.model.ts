import { Role } from '../enums/role.enum';
import { Equipe } from '../enums/equipe.enum';

export interface User {
  id?: number;
  nom: string;
  email: string;
  password: string;
  role: Role;
  equipe: Equipe;
}
