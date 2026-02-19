package com.lipn.app.service;

import com.lipn.app.model.Doctorant;
import com.lipn.app.model.User;
import com.lipn.app.repository.DoctorantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorantService {

    private final DoctorantRepository repository;
    private final CustomUserDetailsService userDetailsService;

    // ================= CREATE =================
    public Doctorant create(Doctorant doctorant) {
        User currentUser = userDetailsService.getCurrentUser();

        doctorant.setEncadrant(currentUser);
        return repository.save(doctorant);
    }

    // ================= UPDATE =================
    public Doctorant update(Long id, Doctorant updated) {

        Doctorant existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctorant introuvable"));

        existing.setNom(updated.getNom());
        existing.setPrenom(updated.getPrenom());
        existing.setEmail(updated.getEmail());

        return repository.save(existing);
    }

    // ================= GET BY ID =================
    public Doctorant getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctorant introuvable"));
    }

    // ================= GET ALL =================
    public List<Doctorant> getAll() {
        return repository.findAll();
    }

    // ================= DELETE =================
    public void delete(Long id) {

        Doctorant doctorant = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctorant introuvable"));

        if (doctorant.getDossierPath() != null) {
            Path filePath = Paths.get("uploads/doctorants/" + doctorant.getDossierPath());
            try {
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                throw new RuntimeException("Erreur suppression fichier");
            }
        }

        repository.delete(doctorant);
    }

    // ================= UPLOAD DOSSIER =================
    public Doctorant uploadDossier(Long id, MultipartFile file)
            throws IOException {

        Doctorant doctorant = getById(id);

        String uploadDir = "uploads/doctorants/";
        new File(uploadDir).mkdirs();

        String fileName = System.currentTimeMillis()
                + "_" + file.getOriginalFilename();

        Path filePath = Paths.get(uploadDir + fileName);

        Files.copy(file.getInputStream(), filePath,
                StandardCopyOption.REPLACE_EXISTING);

        doctorant.setDossierPath(fileName);

        return repository.save(doctorant);
    }
}
