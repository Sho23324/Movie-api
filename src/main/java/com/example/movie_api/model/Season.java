package com.example.movie_api.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "seasons")
public class Season {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "season_number")
    private Integer season_number;

    @Column(name = "summary")
    private String summary;

    @Column(name = "release_year")
    private Integer release_year;

    @Column(name = "imdb_rating")
    private Double imdb_rating;

    @ManyToOne
    @JoinColumn(name = "series_id", nullable = false)
    private Series series;

    @OneToMany(mappedBy = "season")
    private Set<Episode> episodes = new HashSet<Episode>();

    @ManyToMany
    @JoinTable(
        name = "season_rating",
        joinColumns = @JoinColumn(name="season_id"),
        inverseJoinColumns = @JoinColumn(name="rating_id")
    )
    private Set<Rating> ratings = new HashSet<Rating>();

    public Season(Integer season_number, String summary, Integer release_year, Double imdb_rating, Series series,
            Set<Episode> episodes, Set<Rating> ratings) {
        this.season_number = season_number;
        this.summary = summary;
        this.release_year = release_year;
        this.imdb_rating = imdb_rating;
        this.series = series;
        this.episodes = episodes;
        this.ratings = ratings;
    }

    public Season() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSeason_number() {
        return season_number;
    }

    public void setSeason_number(Integer season_number) {
        this.season_number = season_number;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getRelease_year() {
        return release_year;
    }

    public void setRelease_year(Integer release_year) {
        this.release_year = release_year;
    }

    public Double getImdb_rating() {
        return imdb_rating;
    }

    public void setImdb_rating(Double imdb_rating) {
        this.imdb_rating = imdb_rating;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public Set<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(Set<Episode> episodes) {
        this.episodes = episodes;
    }

    public Set<Rating> getRatsings() {
        return ratings;
    }

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }
}
