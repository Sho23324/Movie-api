package com.example.movie_api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.movie_api.model.Genre;

@Repository
public interface GenreRepository extends CrudRepository<Genre, Long> {
    
}
