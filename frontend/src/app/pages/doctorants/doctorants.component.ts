import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import { DoctorantService } from '../../services/doctorant.service';
import { Doctorant } from '../../models/doctorant.model';
import {ValidationStatus} from '../../enums/validationStatus.enum';

@Component({
  selector: 'app-doctorants',
  standalone: false,
  templateUrl: './doctorants.component.html',
  styleUrls: ['./doctorants.component.css']
})
export class DoctorantsComponent implements OnInit {

  doctorants: Doctorant[] = [];

  constructor(private service: DoctorantService,
              private cd: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.service.getAll().subscribe({
      next: (data) => {
        this.doctorants = data;
        this.cd.detectChanges();
      },
      error: (err) => {
        console.error("Erreur chargement doctorants", err);
      }
    });
  }

  delete(id: number): void {
    if (confirm("Supprimer ce doctorant ?")) {
      this.service.delete(id).subscribe(() => {
        this.load();
      });
    }
  }

  getStatusClass(status?: string): string {

    switch (status) {

      case 'VALIDEE':
        return 'badge badge-green';

      case 'REFUSEE':
        return 'badge badge-red';

      case 'EN_ATTENTE':
        return 'badge badge-blue';

      default:
        return 'badge badge-grey';
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
