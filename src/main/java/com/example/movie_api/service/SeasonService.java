package com.example.movie_api.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.movie_api.model.Season;
import com.example.movie_api.model.Series;
import com.example.movie_api.repository.SeasonRepository;

@Service
public class SeasonService {
    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private SeriesService seriesService;

    public Season getSeason(Long seasonId) {
        return seasonRepository.findById(seasonId).get();
    }

    public Iterable<Season> getSeasonList() {
        return seasonRepository.findAll();
    }
    public Season createSeason(@RequestBody Season season) {
        return seasonRepository.save(season);
    }

    public void updateSeason(Long seasonId, Season newSeason) {
        Season season = getSeason(seasonId);
        newSeason.setId(season.getId());
        if(newSeason.getSeries() == null) {
            newSeason.setSeries(season.getSeries());
        }
        seasonRepository.save(newSeason);
    }

    public void deleteSeason(Long seasonId) {
        Season season = getSeason(seasonId);
        Series series = season.getSeries();
        series.getSeasons().remove(season);
        seriesService.updateSeries(series.getId(), series);
        seasonRepository.deleteById(seasonId);
    }
}
