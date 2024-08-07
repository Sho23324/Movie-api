package com.example.movie_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.movie_api.model.Episode;
import com.example.movie_api.model.Season;
import com.example.movie_api.repository.EpisodeRepository;

@Service
public class EpisodeService {
    @Autowired
    private EpisodeRepository episodeRepository;

    @Autowired
    private SeasonService seasonService;

    public Episode getEpisode(Long id) {
        return episodeRepository.findById(id).get();
    }

    public Iterable<Episode> getEpisodeList() {
        return episodeRepository.findAll();
    }

    public Episode createEpisode(Long seasonId, Episode episode) {
        Season season = seasonService.getSeason(seasonId);
        episode.setSeason(season);
        return episodeRepository.save(episode);
    }

    public void updateEpisode(Long id, Episode newEpisode) {
        Episode episode = getEpisode(id);
        newEpisode.setId(episode.getId());
        if(newEpisode.getSeason() == null) {
            newEpisode.setSeason(episode.getSeason());
        }
        episodeRepository.save(newEpisode);
    }

    public void deleteEpisode(Long id) {
        Episode episode = getEpisode(id);
        Season season = episode.getSeason();
        season.getEpisodes().remove(episode);
        seasonService.updateSeason(season.getId(), season);
        episodeRepository.deleteById(id);
    }
}
