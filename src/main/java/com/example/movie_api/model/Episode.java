package com.example.movie_api.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "episodes")
public class Episode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "episode_number")
    private Integer episode_number;

    @Column(name = "title")
    private String title;

    @Column(name = "air_date")
    private Date air_date;

    @Column(name = "imdb_rating")
    private Double imdb_rating;

    @ManyToMany
    @JoinTable(
        name = "episode_rating",
        joinColumns = @JoinColumn(name="episode_id"),
        inverseJoinColumns = @JoinColumn(name="rating_id")
    )
    private Set<Rating> ratings = new HashSet<Rating>();

    public Episode(Integer episode_number, String title, Date air_date, Double imdb_rating, Set<Rating> ratings,
            Season season) {
        this.episode_number = episode_number;
        this.title = title;
        this.air_date = air_date;
        this.imdb_rating = imdb_rating;
        this.ratings = ratings;
        this.season = season;
    }

    public Episode() {
    }

    @ManyToOne
    @JoinColumn(name = "season_id", nullable = false)
    private Season season;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEpisode_number() {
        return episode_number;
    }

    public void setEpisode_number(Integer episode_number) {
        this.episode_number = episode_number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getAir_date() {
        return air_date;
    }

    public void setAir_date(Date air_date) {
        this.air_date = air_date;
    }

    public Double getImdb_rating() {
        return imdb_rating;
    }

    public void setImdb_rating(Double imdb_rating) {
        this.imdb_rating = imdb_rating;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public Set<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }
}
