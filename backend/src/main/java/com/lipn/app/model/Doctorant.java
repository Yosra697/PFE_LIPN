package com.lipn.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lipn.app.enums.StatutPropositionEnum;
import com.lipn.app.enums.ValidationStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Doctorant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ===== Informations personnelles =====
    private String nom;
    private String prenom;
    private String email;
    private String dossierPath;

    // ===== Informations académiques =====
    private String equipe;

    private LocalDate dateDebut;

    // ===== VALIDATIONS =====
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ValidationStatusEnum validationChef = ValidationStatusEnum.EN_ATTENTE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ValidationStatusEnum validationDirection = ValidationStatusEnum.EN_ATTENTE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutPropositionEnum statut = StatutPropositionEnum.BROUILLON;

    // ===== Relation Encadrant =====
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "encadrant_id")
    private User encadrant;
}
