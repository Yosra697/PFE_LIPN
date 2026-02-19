package com.lipn.app.controller;

import com.lipn.app.model.PropositionThese;
import com.lipn.app.service.PropositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/propositions")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class PropositionController {

    private final PropositionService service;

    @PostMapping
    public PropositionThese create(@RequestBody PropositionThese p) {
        return service.create(p);
    }

    @PutMapping("/{id}")
    public PropositionThese update(@PathVariable Long id,
                                   @RequestBody PropositionThese p) {
        return service.update(id, p);
    }

    @GetMapping("/{id}")
    public PropositionThese getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<PropositionThese> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PostMapping("/{id}/submit")
    public PropositionThese submit(@PathVariable Long id) {
        return service.submit(id);
    }

    @PostMapping("/{id}/upload")
    public PropositionThese uploadFile(@PathVariable Long id,
                                       @RequestParam("file") MultipartFile file)
            throws IOException {
        return service.uploadFile(id, file);
    }
}
