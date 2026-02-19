import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { PropositionService } from '../../services/proposition.service';
import { PropositionThese } from '../../models/propositionThese.model';
import { Doctorant } from '../../models/doctorant.model';
import { Financement } from '../../enums/financement.enum';
import {DoctorantService} from '../../services/doctorant.service';

@Component({
  selector: 'app-proposition-form',
  standalone: false,
  templateUrl: './proposition-form.component.html',
  styleUrls: ['./proposition-form.component.css']
})
export class PropositionFormComponent {

  selectedPdf?: File;
  selectedDoctorantFile?: File;

  Financement = Financement;
  financements = Object.values(Financement);

  hasDirection = false;
  hasDoctorant = false;

  doctorant: Doctorant = {
    nom: '',
    prenom: '',
    email: ''
  };

  proposition: PropositionThese = {
    coEncadrantNom: '',
    coEncadrantPrenom: '',
    doctorant: null,
    sujet: '',
    description: '',
    dateDebut: '',
    financement: Financement.CONTRAT_DOCTORAL,
    ecoleDoctorale: '',
    commentaire: ''
  };

  constructor(
    private service: PropositionService,
    private doctorantService: DoctorantService,
    private router: Router
  ) {}

  onPdfSelected(event: any): void {
    if (event.target.files?.length > 0) {
      this.selectedPdf = event.target.files[0];
    }
  }

  onDoctorantFileSelected(event: any): void {
    if (event.target.files?.length > 0) {
      this.selectedDoctorantFile = event.target.files[0];
    }
  }

  save(): void {

    if (this.hasDirection) {
      if (!this.proposition.coEncadrantNom ||
        !this.proposition.coEncadrantPrenom) {
        alert("Veuillez remplir les informations du co-encadrant.");
        return;
      }
    } else {
      this.proposition.coEncadrantNom = '';
      this.proposition.coEncadrantPrenom = '';
    }

    if (this.hasDoctorant) {

      if (!this.doctorant.nom ||
        !this.doctorant.prenom ||
        !this.doctorant.email) {
        alert("Veuillez remplir les informations du doctorant.");
        return;
      }

      this.proposition.doctorant = { ...this.doctorant };

    } else {
      this.proposition.doctorant = null;
    }

    this.service.create(this.proposition).subscribe({

      next: (created) => {

        if (!created.id) {
          this.router.navigate(['/propositions']);
          return;
        }

        // Upload dossier doctorant
        if (this.selectedDoctorantFile && created.doctorant?.id) {

          this.doctorantService.uploadDossier(
            created.doctorant.id,
            this.selectedDoctorantFile
          ).subscribe();
        }

        // Upload PDF proposition
        if (this.selectedPdf) {

          this.service.uploadFile(created.id, this.selectedPdf)
            .subscribe({
              next: () => this.router.navigate(['/propositions']),
              error: () => this.router.navigate(['/propositions'])
            });

        } else {
          this.router.navigate(['/propositions']);
        }

      },

      error: (err) => {
        console.error("Erreur création", err);
      }
    });
  }
}
