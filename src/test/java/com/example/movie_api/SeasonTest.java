package com.example.movie_api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.example.movie_api.model.Season;
import com.example.movie_api.model.Series;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SeasonTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @Test
    void getSeasonList() {
        ResponseEntity<Season[]> response = restTemplate.getForEntity("/seasons", Season[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(response.getBody());
        assertThat(response.getBody().length).isEqualTo(2);
    }

    @Test
    @DirtiesContext//series is essential
    void createSeasonNoSeries() {
        Season season = new Season(1, "The Wire", 2002, 9.0);
        Series seriesA = restTemplate.getForObject("/series/1", Series.class);
        season.setSeries(seriesA);
        URI new_season_location = restTemplate.postForLocation("/seasons", season, Void.class);
        ResponseEntity<Season> response = restTemplate.getForEntity(new_season_location, Season.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getSeason_number()).isEqualTo(season.getSeason_number());
        assertThat(response.getBody().getSummary()).isEqualTo(season.getSummary());
        assertThat(response.getBody().getRelease_year()).isEqualTo(season.getRelease_year());
        assertThat(response.getBody().getImdb_rating()).isEqualTo(season.getImdb_rating());
        Series series = restTemplate.getForObject("/series/1", Series.class);
        assertThat(series.getSeasons().size()).isEqualTo(1);
        assertThat(series.getSeasons().contains(response.getBody())).isTrue();
    }

    //or you can  write like this but you have to change service method

    // public Season createSeason(Long seriesId, Season season) {
    //     Series series = seriesService.getSeries(seriesId);
    //     season.setSeries(series);
    //     return seasonRepository.save(season);
    // }

    //use this instead of series setter

    //        URI new_season_location = restTemplate.postForLocation("/seasons?series_id=1", season, Void.class);

    @Test//series is essential foreign key
    @DirtiesContext//there might be error -> because you need to provide series
    void updateSeason() {
        Season season = new Season(1, "The Wire", 2002, 9.0);
        restTemplate.put("/seasons/1", season);
        ResponseEntity<Season> response = restTemplate.getForEntity("/seasons/1", Season.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getSeason_number()).isEqualTo(1);
        assertThat(response.getBody().getSummary()).isEqualTo("The Wire");
        assertThat(response.getBody().getRelease_year()).isEqualTo(2002);
        assertThat(response.getBody().getImdb_rating()).isEqualTo(9.0);
    }

    @Test
    @DirtiesContext//might be error ex not found but actual 200 / You have to remove the foreign key in service layer
    void deleteSeason() {//beacuse of handler method error carful with get method
        restTemplate.delete("/seasons/1");
        ResponseEntity<Season> response = restTemplate.getForEntity("/seasons/1", Season.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        Series series = restTemplate.getForObject("/series/3", Series.class);
        assertThat(series.getSeasons().size()).isEqualTo(1);
    }



}
