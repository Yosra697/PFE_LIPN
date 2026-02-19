package com.lipn.app.repository;

import com.lipn.app.model.Doctorant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorantRepository extends JpaRepository<Doctorant, Long> {
}
