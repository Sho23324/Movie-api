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

import com.example.movie_api.model.Genre;
import com.example.movie_api.service.GenreService;

@RestController
public class GenreController {
    @Autowired
    private GenreService genreService;

    @GetMapping("/genres")
    public ResponseEntity<Iterable<Genre>> getGenreList() {
        return ResponseEntity.ok(genreService.getGenreList());
    }

    @PostMapping("/genres")
    public ResponseEntity<Void> createGenre(@RequestBody Genre newGenre) {
        Genre createdGenre = genreService.createGenre(newGenre);
        URI new_genre_location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{resourceId}")
                .buildAndExpand(createdGenre.getId())
                .toUri();
        return ResponseEntity.created(new_genre_location).build();
    }

    @GetMapping("/genres/{genreId}")
    public ResponseEntity<Genre> getGenre(@PathVariable Long genreId) {
        Genre genre;
        try {
            genre = genreService.getGenre(genreId);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(genre);
    }

    @DeleteMapping("/genres/{genreId}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long genreId) {
        genreService.deleteGenre(genreId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/genres/{genreId}")
    public ResponseEntity<Void> updateGenre(@PathVariable Long genreId, @RequestBody Genre updatedGenre) {
        try {
            genreService.updateGenre(genreId, updatedGenre);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
