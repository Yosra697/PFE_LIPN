import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { Router } from '@angular/router';
import { PropositionService } from '../../services/proposition.service';
import { PropositionThese } from '../../models/propositionThese.model';
import {ValidationStatus} from '../../enums/validationStatus.enum';

@Component({
  selector: 'app-propositions',
  standalone: false,
  templateUrl: './propositions.component.html',
  styleUrls: ['./propositions.component.css']
})
export class PropositionsComponent implements OnInit {

  propositions: PropositionThese[] = [];

  constructor(
    private service: PropositionService,
    private router: Router,
    private cd: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.service.getAll().subscribe({
      next: (data: PropositionThese[]) => {
        this.propositions = data;
        this.cd.detectChanges();
      },
      error: (err) => {
        console.error('Erreur lors du chargement des propositions', err);
      }
    });
  }

  goToNew(): void {
    this.router.navigate(['/propositions/nouvelle']);
  }

  // Gestion des badges
  getStatusClass(status?: string): string {

    if (!status) return 'badge badge-grey';

    switch (status) {
      case 'BROUILLON':
        return 'badge badge-grey';

      case 'SOUMISE':
        return 'badge badge-blue';

      case 'EN_ATTENTE_VALIDATION_EQUIPE':
        return 'badge badge-purple';

      case 'VALIDEE_EQUIPE':
      case 'VALIDEE_DIRECTION':
      case 'ACCEPTEE':
        return 'badge badge-green';

      case 'REFUSE_EQUIPE':
      case 'REFUSE_DIRECTION':
      case 'REFUSEE':
        return 'badge badge-red';

      default:
        return 'badge badge-purple';
    }
  }

  getValidationClass(status?: ValidationStatus): string {

    switch (status) {

      case ValidationStatus.VALIDEE:
        return 'badge badge-green';

      case ValidationStatus.REFUSEE:
        return 'badge badge-red';

      case ValidationStatus.EN_ATTENTE:
        return 'badge badge-blue';

      default:
        return 'badge badge-grey';
    }
  }

  formatValidation(status?: ValidationStatus): string {

    switch (status) {
      case ValidationStatus.VALIDEE:
        return 'Validée';

      case ValidationStatus.REFUSEE:
        return 'Refusée';

      case ValidationStatus.EN_ATTENTE:
        return 'En attente';

      default:
        return 'En attente';
    }
  }


}
