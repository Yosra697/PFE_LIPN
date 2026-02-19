package com.lipn.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lipn.app.enums.StatutPropositionEnum;
import com.lipn.app.enums.ValidationStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PropositionThese {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ===== Encadrant =====
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "encadrant_id")
    private User encadrant;

    // ===== Co-encadrant =====
    private String coEncadrantNom;
    private String coEncadrantPrenom;

    // ===== Détails =====
    private String sujet;

    @Column(length = 2000)
    private String description;

    private LocalDate dateDebut;
    private String financement;
    private String ecoleDoctorale;
    private String commentaire;
    private String pdfPath;

    // ===== Relation Doctorant =====
    @ManyToOne
    @JoinColumn(name = "doctorant_id")
    private Doctorant doctorant;

    // ===== VALIDATIONS =====
    @Enumerated(EnumType.STRING)
    private ValidationStatusEnum validationChef = ValidationStatusEnum.EN_ATTENTE;

    @Enumerated(EnumType.STRING)
    private ValidationStatusEnum validationDirection = ValidationStatusEnum.EN_ATTENTE;

    // ===== Statut global =====
    @Enumerated(EnumType.STRING)
    private StatutPropositionEnum statut = StatutPropositionEnum.BROUILLON;

    private LocalDateTime dateSoumission;
}
