import { User } from './user.model';
import {ValidationStatus} from '../enums/validationStatus.enum';

export interface Doctorant {

  id?: number;

  nom: string;
  prenom: string;

  email: string;

  dossierPath?: string;

  encadrant?: User;

  equipe?: string;
  dateDebut?: string;

  validationChef?: ValidationStatus;
  validationDirection?: ValidationStatus;

  statut?: string;
}
