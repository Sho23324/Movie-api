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

import com.example.movie_api.model.Movie;
import com.example.movie_api.service.MovieService;

@RestController
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping("/movies")
    public ResponseEntity<Iterable<Movie>> getMovieList() {
        return ResponseEntity.ok(movieService.getMovieList());
    }

    @GetMapping("/movies/{movieId}")
    public ResponseEntity<Movie> getMovie(@PathVariable Long movieId) {
        Movie movie;
        try {
            movie = movieService.getMovie(movieId);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movie);
    }

    @PostMapping("/movies")
    public ResponseEntity<Void> createMovie(@RequestBody Movie newMovie) {
        Movie createdMovie = movieService.createMovie(newMovie);
        URI new_movie_location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{resourceId}")
                .buildAndExpand(createdMovie.getId())
                .toUri();
        return ResponseEntity.created(new_movie_location).build();
    }

    @PutMapping("/movies/{movieId}")
    public ResponseEntity<Void> updateMovie(@PathVariable Long movieId, @RequestBody Movie updatedMovie) {
        try {
            movieService.getMovie(movieId);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        movieService.updateMovie(movieId, updatedMovie);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/movies/{movieId}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long movieId) {
        movieService.deleteMovie(movieId);
        return ResponseEntity.noContent().build();
    }
}
