package com.example.movie_api;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.movie_api.model.Cast;
import com.example.movie_api.model.Genre;
import com.example.movie_api.model.Production;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MovieApiApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void getGenreList() {
		ResponseEntity<Genre[]> response = restTemplate.getForEntity("/genres", Genre[].class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().length).isEqualTo(3);
	}

	@Test
	@DirtiesContext
	void createGenre() {
		Genre genre = new Genre("Horror");
		URI new_genre_location = restTemplate.postForLocation("/genres", genre, Void.class);
		assertNotNull(new_genre_location);
		ResponseEntity<Genre> response = restTemplate.getForEntity(new_genre_location, Genre.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getName()).isEqualTo("Horror");
	}

	@Test
	@DirtiesContext
	void deleteGenre() {
		ResponseEntity<Genre> response = restTemplate.getForEntity("/genres/1", Genre.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		ResponseEntity<Void> deleteResponse = restTemplate.exchange("/genres/1", HttpMethod.DELETE, null, Void.class);
		assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
		response = restTemplate.getForEntity("/genres/1", Genre.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	@DirtiesContext
	void updateGenre() {
		Genre genre = new Genre("Documentary");
		restTemplate.put("/genres/1", genre);
		Genre updatedGenre = restTemplate.getForObject("/genres/1", Genre.class);
		assertThat(updatedGenre.getName()).isEqualTo("Documentary");
	}

	@Test
	void updatedGenreNotFound() {
		Genre genre = new Genre("Documentary");
		restTemplate.put("/genres/10", genre);
		ResponseEntity<Genre> response = restTemplate.getForEntity("/genres/10", Genre.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isNull();
	}
	
	@Test
	@DirtiesContext
	void createCast() {
		Cast cast = new Cast("John Wick");
		URI new_cast_location = restTemplate.postForLocation("/casts", cast, Void.class);
		assertNotNull(new_cast_location);
		ResponseEntity<Cast> response = restTemplate.getForEntity(new_cast_location, Cast.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getName()).isEqualTo("John Wick");
	}

	@Test
	@DirtiesContext
	void deleteCast() {
		ResponseEntity<Cast> response = restTemplate.getForEntity("/casts/1", Cast.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		ResponseEntity<Void> deleteResponse = restTemplate.exchange("/casts/1", HttpMethod.DELETE, null, Void.class);
		assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
		response = restTemplate.getForEntity("/casts/1", Cast.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	void getCastList() {
		ResponseEntity<Cast[]> response = restTemplate.getForEntity("/casts", Cast[].class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().length).isEqualTo(6);
	}

	@Test
	@DirtiesContext
	void updateCast() {
		Cast cast = new Cast("Emma Watson");
		restTemplate.put("/casts/1", cast);
		Cast updatedCast = restTemplate.getForObject("/casts/1", Cast.class);
		assertThat(updatedCast.getName()).isEqualTo("Emma Watson");
	}

	@Test
	void updatedCastNotFound() {
		Cast cast = new Cast("Lara Croft");
		restTemplate.put("/casts/10", cast);
		ResponseEntity<Cast> response = restTemplate.getForEntity("/casts/10", Cast.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isNull();
	}

	@Test
	void getProductionList() {
		ResponseEntity<Production[]> response = restTemplate.getForEntity("/productions", Production[].class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().length).isEqualTo(6);
	}

	@Test
	@DirtiesContext
	void createProduction() {
		Production production = new Production("Star Wars");
		URI new_production_location = restTemplate.postForLocation("/productions", production, Void.class);
		assertNotNull(new_production_location);
		ResponseEntity<Production> response = restTemplate.getForEntity(new_production_location, Production.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getName()).isEqualTo("Star Wars");
	}

	@Test
	@DirtiesContext
	void updateProduction() {
		Production production = new Production("Marvel Cinematic Universe");
		restTemplate.put("/productions/1", production);
		Production updatedProduction = restTemplate.getForObject("/productions/1", Production.class);
		assertThat(updatedProduction.getName()).isEqualTo("Marvel Cinematic Universe");
	}

	@Test
	void updatedProductionNotFound() {
		Production production = new Production("20th Century Fox");
		restTemplate.put("/productions/10", production);
		ResponseEntity<Production> response = restTemplate.getForEntity("/productions/10", Production.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isNull();
	}

	@Test
	@DirtiesContext
	void deleteProduction() {
		ResponseEntity<Production> response = restTemplate.getForEntity("/productions/1", Production.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		ResponseEntity<Void> deleteResponse = restTemplate.exchange("/productions/1", HttpMethod.DELETE, null, Void.class);
		assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
		response = restTemplate.getForEntity("/productions/1", Production.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
}
