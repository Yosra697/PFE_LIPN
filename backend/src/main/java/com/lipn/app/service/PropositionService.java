package com.lipn.app.service;

import com.lipn.app.enums.StatutPropositionEnum;
import com.lipn.app.model.Doctorant;
import com.lipn.app.model.PropositionThese;
import com.lipn.app.model.User;
import com.lipn.app.repository.PropositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PropositionService {

    private final PropositionRepository repository;
    private final DoctorantService doctorantService;
    private final CustomUserDetailsService userDetailsService;

    // ================= CREATE =================
    public PropositionThese create(PropositionThese p) {

        if (p.getDoctorant() != null) {
            p.getDoctorant().setDateDebut(p.getDateDebut());
            Doctorant saved = doctorantService.create(p.getDoctorant());
            p.setDoctorant(saved);
        }

        User currentUser = userDetailsService.getCurrentUser();

        p.setEncadrant(currentUser);
        p.setStatut(StatutPropositionEnum.BROUILLON);
        p.setDateSoumission(null);

        return repository.save(p);
    }

    // ================= UPDATE =================
    public PropositionThese update(Long id, PropositionThese updated) {

        PropositionThese existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proposition introuvable"));

        if (existing.getStatut() != StatutPropositionEnum.BROUILLON) {
            throw new RuntimeException("Modification impossible après soumission");
        }

        existing.setSujet(updated.getSujet());
        existing.setDescription(updated.getDescription());
        existing.setDateDebut(updated.getDateDebut());
        existing.setFinancement(updated.getFinancement());
        existing.setEcoleDoctorale(updated.getEcoleDoctorale());
        existing.setCommentaire(updated.getCommentaire());
        existing.setCoEncadrantNom(updated.getCoEncadrantNom());
        existing.setCoEncadrantPrenom(updated.getCoEncadrantPrenom());

        if (updated.getDoctorant() != null) {

            if (updated.getDoctorant().getId() != null) {
                Doctorant updatedDoctorant =
                        doctorantService.update(
                                updated.getDoctorant().getId(),
                                updated.getDoctorant()
                        );
                existing.setDoctorant(updatedDoctorant);
            } else {
                Doctorant saved =
                        doctorantService.create(updated.getDoctorant());
                existing.setDoctorant(saved);
            }
        }

        return repository.save(existing);
    }

    // ================= GET BY ID =================
    public PropositionThese getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proposition introuvable"));
    }

    // ================= GET ALL =================
    public List<PropositionThese> getAll() {
        return repository.findAll();
    }

    // ================= DELETE =================
    public void delete(Long id) {

        PropositionThese p = getById(id);

        if (p.getDoctorant() != null) {
            doctorantService.delete(p.getDoctorant().getId());
        }

        repository.delete(p);
    }

    // ================= SUBMIT =================
    public PropositionThese submit(Long id) {

        PropositionThese p = getById(id);

        if (p.getStatut() != StatutPropositionEnum.BROUILLON) {
            throw new RuntimeException("Déjà soumise");
        }

        p.setStatut(StatutPropositionEnum.SOUMISE);
        p.setDateSoumission(LocalDateTime.now());

        return repository.save(p);
    }

    // ================= UPLOAD PDF =================
    public PropositionThese uploadFile(Long id, MultipartFile file)
            throws IOException {

        PropositionThese p = getById(id);

        String uploadDir = "uploads/propositions/";
        new File(uploadDir).mkdirs();

        String fileName = System.currentTimeMillis()
                + "_" + file.getOriginalFilename();

        Path filePath = Paths.get(uploadDir + fileName);

        Files.copy(file.getInputStream(), filePath,
                StandardCopyOption.REPLACE_EXISTING);

        p.setPdfPath(fileName);

        return repository.save(p);
    }
}
