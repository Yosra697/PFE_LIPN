import { Doctorant } from './doctorant.model';
import { User } from './user.model';
import {ValidationStatus} from '../enums/validationStatus.enum';

export interface PropositionThese {

  id?: number;

  encadrant?: User;

  coEncadrantNom?: string;
  coEncadrantPrenom?: string;

  doctorant?: Doctorant | null;

  sujet: string;
  description: string;

  dateDebut: string;

  financement: string;
  ecoleDoctorale: string;

  commentaire: string;

  pdfPath?: string;

  validationChef?: ValidationStatus;
  validationDirection?: ValidationStatus;

  statut?: string;
  dateSoumission?: string;
}
