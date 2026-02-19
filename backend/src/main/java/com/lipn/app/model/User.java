package com.lipn.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lipn.app.enums.EquipeEnum;
import com.lipn.app.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @Enumerated(EnumType.STRING)
    private EquipeEnum equipe;
}
