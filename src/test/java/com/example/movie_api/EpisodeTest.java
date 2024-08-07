package com.example.movie_api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.example.movie_api.model.Episode;
import com.example.movie_api.model.Season;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EpisodeTest {
    @Autowired
    private TestRestTemplate restTemplate;

    //this is essential for Date data type// and don't give the date null!!!!
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    void getEpisodeList() {
        ResponseEntity<Episode[]> response = restTemplate.getForEntity("/episodes", Episode[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(response.getBody());
        assertThat(response.getBody().length).isEqualTo(2);
    }

    @Test
    @DirtiesContext
    void createEpisodeNoSeason() {
        Episode episode = new Episode(1, "The Wire", null, 9.0);
        ResponseEntity<Void> response = restTemplate.postForEntity("/episodes", episode, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);  
        response = restTemplate.postForEntity("/episodes?season_id=10", episode,  Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }

    @Test//why uri is null?->because date is null
    @DirtiesContext
    void createEpisode() throws ParseException {
        Episode episode = new Episode(3, "The Wire", df.parse("2013-11-14"), 9.0);
        URI new_episode_location = restTemplate.postForLocation("/episodes?season_id=1", episode, Void.class);
        ResponseEntity<Episode> response = restTemplate.getForEntity(new_episode_location, Episode.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getTitle()).isEqualTo(episode.getTitle());
        assertThat(response.getBody().getAir_date()).isEqualTo(episode.getAir_date());
        assertThat(response.getBody().getImdb_rating()).isEqualTo(episode.getImdb_rating());
    }

    @Test//there is no update->because date is null 
    @DirtiesContext
    void updateEpisode() throws ParseException {
        Episode episode = new Episode(1, "The Wire updated", df.parse("2013-11-14"), 9.0);
        restTemplate.put("/episodes/1", episode);
        ResponseEntity<Episode> response = restTemplate.getForEntity("/episodes/1", Episode.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getTitle()).isEqualTo(episode.getTitle());
        assertThat(response.getBody().getAir_date()).isEqualTo(episode.getAir_date());
        assertThat(response.getBody().getImdb_rating()).isEqualTo(episode.getImdb_rating());
    }


    @Test
    @DirtiesContext
    void updateEpisodeWithRelationships() throws ParseException {
        Season season = restTemplate.getForObject("/seasons/1", Season.class);
        Episode episode = new Episode(1, "The Wire updated",  df.parse("2013-11-14"), 9.0);
        episode.setSeason(season);
        restTemplate.put("/episodes/2", episode);
        season = restTemplate.getForObject("/seasons/1", Season.class);
        episode = restTemplate.getForObject("/episodes/2", Episode.class);
        assertThat(season.getEpisodes().size()).isEqualTo(2);    
    }
    @Test
    @DirtiesContext
    void deleteEpisode() {
        restTemplate.delete("/episodes/1");
        ResponseEntity<Episode> response = restTemplate.getForEntity("/episodes/1", Episode.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        Season season = restTemplate.getForObject("/seasons/1", Season.class);
        assertThat(season.getEpisodes().contains(response.getBody())).isFalse();
        assertThat(season.getEpisodes().size()).isEqualTo(1);
    }
}
