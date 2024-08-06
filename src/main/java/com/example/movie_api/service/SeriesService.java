package com.example.movie_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.movie_api.model.Series;
import com.example.movie_api.repository.SeriesRepository;

@Service
public class SeriesService {
    @Autowired
    private SeriesRepository seriesRepository;

    public Iterable<Series> getSeriesList() {
        return seriesRepository.findAll();
    }
    public Series getSeries(Long id) {
        return seriesRepository.findById(id).get();
    }
      
    public Series createSeires(Series series) {
        return seriesRepository.save(series);
    }

    public void updateSeries(Long seriesId, Series newSeries) {
        Series series = getSeries(seriesId);
        newSeries.setId(series.getId());
        seriesRepository.save(newSeries);
    }

    public void deleteSeries(Long seriesId) {
        seriesRepository.deleteById(seriesId);
    }
}
