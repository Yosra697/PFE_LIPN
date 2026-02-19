package com.lipn.app.controller;

import com.lipn.app.model.Doctorant;
import com.lipn.app.service.DoctorantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/doctorants")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class DoctorantController {

    private final DoctorantService service;

    @PostMapping
    public Doctorant create(@RequestBody Doctorant doctorant) {
        return service.create(doctorant);
    }

    @PutMapping("/{id}")
    public Doctorant update(@PathVariable Long id,
                            @RequestBody Doctorant doctorant) {
        return service.update(id, doctorant);
    }

    @GetMapping("/{id}")
    public Doctorant getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<Doctorant> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PostMapping("/{id}/upload")
    public Doctorant uploadDossier(@PathVariable Long id,
                                   @RequestParam("file") MultipartFile file)
            throws IOException {
        return service.uploadDossier(id, file);
    }
}
