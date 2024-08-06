package com.example.movie_api;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URI;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import com.example.movie_api.model.Cast;
import com.example.movie_api.model.Genre;
import com.example.movie_api.model.Production;
import com.example.movie_api.model.Season;
import com.example.movie_api.model.Series;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SeriesTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Test
    void getSeriesList() {
        ResponseEntity<Series[]> response = restTemplate.getForEntity("/series", Series[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(response.getBody());
        assertThat(response.getBody().length).isEqualTo(3);
    }

    @Test
    @DirtiesContext
    void createSeries() {
        Series series = new Series("Breaking Bad", "A high school chemistry teacher diagnosed with inoperable lung cancer turns to manufacturing and selling methamphetamine.", 2008, 49, "USA", 9.5, "breaking_bad.jpg");
        URI new_series_location = restTemplate.postForLocation("/series", series, Void.class);
        ResponseEntity<Series> response = restTemplate.getForEntity(new_series_location, Series.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getTitle()).isEqualTo(series.getTitle());
        assertThat(response.getBody().getRelease_year()).isEqualTo(series.getRelease_year());
        assertThat(response.getBody().getCountry()).isEqualTo(series.getCountry());
        assertThat(response.getBody().getImdb_rating()).isEqualTo(series.getImdb_rating());
    }

    @Test
    @DirtiesContext
    void createSeriesWithRelationships() {
        Genre[] genres = restTemplate.getForObject("/genres", Genre[].class);
        Cast[] casts = restTemplate.getForObject("/casts", Cast[].class);
        Production[] productions = restTemplate.getForObject("/productions", Production[].class);
		Series series = new Series("The Wire", "An American news satire and cable drama series.", 2002, 45, "USA", 9.0, "the_wire.jpg");
        series.addGenre(genres[0]);
        series.addGenre(genres[1]);
        series.addCast(casts[0]);
        series.addCast(casts[1]);
        series.addProduction(productions[0]);
        series.addProduction(productions[1]);
        URI new_series_location = restTemplate.postForLocation("/series", series, Void.class);
        ResponseEntity<Series> response = restTemplate.getForEntity(new_series_location, Series.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getGenres().contains(genres[0])).isTrue();
        assertThat(response.getBody().getGenres().contains(genres[1])).isTrue();
        assertThat(response.getBody().getCasts().contains(casts[0])).isTrue();
        assertThat(response.getBody().getCasts().contains(casts[1])).isTrue();
        assertThat(response.getBody().getProductions().contains(productions[0])).isTrue();
        assertThat(response.getBody().getProductions().contains(productions[1])).isTrue();
        Series createSeries = response.getBody();
        Genre g = restTemplate.getForObject("/genres/"+ genres[0].getId(), Genre.class);
        Cast c = restTemplate.getForObject("/casts/"+casts[0].getId(), Cast.class);
        Production p = restTemplate.getForObject("/productions/" + productions[0].getId(), Production.class);
        assertThat(g.getSeries().contains(createSeries)).isTrue();
        assertThat(c.getSeries().contains(createSeries)).isTrue();
        assertThat(p.getSeries().contains(createSeries)).isTrue();
    }

    @Test
    @DirtiesContext
    void updateSeries() {
        Series series = new Series("The Wire", "An American news satire and cable drama series.", 2002, 45, "USA", 9.0, "the_wire.jpg");
        restTemplate.put("/series/1", series);
        Series updatedSeries = restTemplate.getForObject("/series/1", Series.class);
        assertThat(updatedSeries.getTitle()).isEqualTo(series.getTitle());
        assertThat(updatedSeries.getRelease_year()).isEqualTo(series.getRelease_year());
        assertThat(updatedSeries.getCountry()).isEqualTo(series.getCountry());
        assertThat(updatedSeries.getImdb_rating()).isEqualTo(series.getImdb_rating());
    }

    @Test
    @DirtiesContext
    void updateSeriesWithRelationships() {
        Genre[] genres = restTemplate.getForObject("/genres", Genre[].class);
        Cast[] casts = restTemplate.getForObject("/casts", Cast[].class);
        Production[] productions = restTemplate.getForObject("/productions", Production[].class);
        Series series = restTemplate.getForObject("/series/1", Series.class);
        series.addGenre(genres[0]);
        series.addCast(casts[0]);
        series.addProduction(productions[0]);
        restTemplate.put("/series/1", series);
        Series updatedSeries = restTemplate.getForObject("/series/1", Series.class);
        assertThat(updatedSeries.getGenres().contains(genres[0])).isTrue();
        assertThat(updatedSeries.getCasts().contains(casts[0])).isTrue();
        assertThat(updatedSeries.getProductions().contains(productions[0])).isTrue();
        Genre g = restTemplate.getForObject("/genres/"+ genres[0].getId(), Genre.class);
        Cast c = restTemplate.getForObject("/casts/"+casts[0].getId(), Cast.class);
        Production p = restTemplate.getForObject("/productions/" + productions[0].getId(), Production.class);
        assertThat(g.getSeries().contains(updatedSeries)).isTrue();
        assertThat(c.getSeries().contains(updatedSeries)).isTrue();
        assertThat(p.getSeries().contains(updatedSeries)).isTrue();
        updatedSeries.getGenres().clear();
        updatedSeries.getCasts().clear();
        updatedSeries.getProductions().clear();
        restTemplate.put("/series/1", updatedSeries);
        Series updatedSeries2 = restTemplate.getForObject("/series/1", Series.class);
        assertThat(updatedSeries2.getGenres().isEmpty()).isTrue();
        assertThat(updatedSeries2.getCasts().isEmpty()).isTrue();
        assertThat(updatedSeries2.getProductions().isEmpty()).isTrue();
    }

    @Test
    @DirtiesContext
    void deleteSeries() {
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/series/1", HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        ResponseEntity<Series> response = restTemplate.getForEntity("/series/1", Series.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext
    void deleteSeriesWithRelationships() {
        Series series = restTemplate.getForObject("/series/3", Series.class);
        Set<Genre> genres = series.getGenres();
        Set<Cast> casts = series.getCasts();
        Set<Production> productions = series.getProductions();
        Set<Season> seasons = series.getSeasons();
        restTemplate.delete("/series/3");
        ResponseEntity<Series> response = restTemplate.getForEntity("/series/3", Series.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        for (Genre genre : genres) {
            Genre g = restTemplate.getForObject("/genres/" + genre.getId(), Genre.class);
            assertThat(g.getSeries().contains(series)).isFalse();
        }
        for (Cast cast : casts) {
            Cast c = restTemplate.getForObject("/casts/" + cast.getId(), Cast.class);
            assertThat(c.getSeries().contains(series)).isFalse();
        }
        for (Production production : productions) {
            Production p = restTemplate.getForObject("/productions/" + production.getId(), Production.class);
            assertThat(p.getSeries().contains(series)).isFalse();
        }
        for(Season season : seasons) {
            ResponseEntity<Season> s = restTemplate.getForEntity("/seasons/" + season.getId(), Season.class);
            assertThat(s.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            
        }
    }
    
}
