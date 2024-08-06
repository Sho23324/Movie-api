package com.example.movie_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.movie_api.model.Cast;
import com.example.movie_api.repository.CastRepository;

@Service
public class CastService {
    @Autowired
    private CastRepository castRepository;

    public Cast createCast(Cast cast) {
        return castRepository.save(cast);
    }

    public Cast getCast(Long id) {
        return castRepository.findById(id).get();
    }

    public Iterable<Cast> getCastList() {
        return castRepository.findAll();
    }

    public void updateCast(Long castId, Cast updatedCast) {
        Cast cast = getCast(castId);
        updatedCast.setId(cast.getId());
        castRepository.save(updatedCast);
    }

    public void deleteCast(Long castId) {
        castRepository.deleteById(castId);
    }
}
