package com.example.movie_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.movie_api.model.Genre;
import com.example.movie_api.repository.GenreRepository;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    public Genre createGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    public Genre getGenre(Long id) {
        return genreRepository.findById(id).get();
    }

    public void deleteGenre(Long id) {
        genreRepository.deleteById(id);
    }

    public Iterable<Genre> getGenreList() {
        return genreRepository.findAll();
    }

    public void updateGenre(Long genreId, Genre updatedGenre) {
        Genre genre = getGenre(genreId);
        updatedGenre.setId(genre.getId());
        genreRepository.save(updatedGenre);
    }
}
