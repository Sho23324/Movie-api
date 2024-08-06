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

import com.example.movie_api.model.Season;
import com.example.movie_api.service.SeasonService;

@RestController
public class SeasonController {
    @Autowired
    private SeasonService seasonService;

    @GetMapping("/seasons")
    public ResponseEntity<Iterable<Season>> getSeasonList() {
        return ResponseEntity.ok(seasonService.getSeasonList());
    }

    @GetMapping("/seasons/{seasonId}")
    public ResponseEntity<Season> getSeason(@PathVariable Long seasonId) {
        Season season;
        try {
            season = seasonService.getSeason(seasonId);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(season);
    }

    @PostMapping("/seasons")
    public ResponseEntity<Void> createSeason(@RequestBody Season season) {
        Season createdSeason = seasonService.createSeason(season);
        URI new_season_location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{resourceId}")
                .buildAndExpand(createdSeason.getId())
                .toUri();
        return ResponseEntity.created(new_season_location).build();

    }

    @PutMapping("/seasons/{seasonId}")
    public ResponseEntity<Void> updateSeason(@PathVariable Long seasonId, @RequestBody Season newSeason) {
        try {
            seasonService.updateSeason(seasonId, newSeason);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/seasons/{seasonId}")
    public ResponseEntity<Void> deleteSeason(@PathVariable Long seasonId) {
        seasonService.deleteSeason(seasonId);
        return ResponseEntity.noContent().build();
    }
}
