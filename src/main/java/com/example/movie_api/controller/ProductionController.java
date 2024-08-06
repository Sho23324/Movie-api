package com.example.movie_api.controller;

import java.net.URI;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.movie_api.model.Production;
import com.example.movie_api.service.ProductionService;

@RestController
public class ProductionController {

    @Autowired
    private ProductionService productionService;

    @GetMapping("/productions")
    public Iterable<Production> getProductionList() {
        return productionService.getProductionList();
    }

    @GetMapping("/productions/{productionId}")
    public ResponseEntity<Production> getProduction(@PathVariable Long productionId) {
        Production production;
        try {
            production = productionService.getProduction(productionId);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(production);
    }

    @PostMapping("/productions")
    public ResponseEntity<Void> createProduction(@RequestBody Production production) {
        Production createdProduction = productionService.createProduction(production);
        URI new_production_location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{resourceId}")
                .buildAndExpand(createdProduction.getId())
                .toUri();
        return ResponseEntity.created(new_production_location).build();
    }

    @PutMapping("/productions/{productionId}")
    public ResponseEntity<Void> updateProduction(@PathVariable Long productionId, @RequestBody Production updatedProduction) {
        try {
            productionService.updateProduction(productionId, updatedProduction);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/productions/{productionId}")
    public ResponseEntity<Void> deleteProduction(@PathVariable Long productionId) {
        productionService.deleteProduction(productionId);
        return ResponseEntity.noContent().build();
    }
}
