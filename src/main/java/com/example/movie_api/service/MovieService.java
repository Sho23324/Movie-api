package com.example.movie_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.movie_api.model.Movie;
import com.example.movie_api.repository.MovieRepository;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    public Iterable<Movie> getMovieList() {
        return movieRepository.findAll();
    }

    public Movie getMovie(Long movieId) {
        return movieRepository.findById(movieId).get();
    }

    public Movie createMovie(Movie newMovie) {
        return movieRepository.save(newMovie);
    }

    public void updateMovie(Long movieId, Movie updatedMovie) {
        Movie movie = getMovie(movieId);
        updatedMovie.setId(movie.getId());
        movieRepository.save(updatedMovie);
    }

    public void deleteMovie(Long movieId) {
        movieRepository.deleteById(movieId);
    }
}
