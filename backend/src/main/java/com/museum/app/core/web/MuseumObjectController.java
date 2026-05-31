package com.museum.app.core.web;

import com.museum.app.core.dto.MuseumObjectDtos.MuseumObjectRequest;
import com.museum.app.core.dto.MuseumObjectDtos.MuseumObjectResponse;
import com.museum.app.core.service.MuseumObjectService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/objects")
public class MuseumObjectController {

    private final MuseumObjectService museumObjectService;

    public MuseumObjectController(MuseumObjectService museumObjectService) {
        this.museumObjectService = museumObjectService;
    }

    @GetMapping
    public Page<MuseumObjectResponse> list(@PageableDefault(size = 20) Pageable pageable) {
        return museumObjectService.list(pageable);
    }

    @GetMapping("/search")
    public List<MuseumObjectResponse> searchByInventar(@RequestParam String inventarNumber) {
        return museumObjectService.findByInventarNumber(inventarNumber);
    }

    @GetMapping("/{id}")
    public MuseumObjectResponse getById(@PathVariable UUID id) {
        return museumObjectService.getById(id);
    }

    @PostMapping
    public ResponseEntity<MuseumObjectResponse> create(@Valid @RequestBody MuseumObjectRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(museumObjectService.create(request));
    }

    @PutMapping("/{id}")
    public MuseumObjectResponse update(@PathVariable UUID id, @Valid @RequestBody MuseumObjectRequest request) {
        return museumObjectService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        museumObjectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
