package com.example.movie_api.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "ratings")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "value")
    private Double value;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany(mappedBy = "ratings")
    private Set<Movie> movies = new HashSet<Movie>();

    @ManyToMany(mappedBy = "ratings")
    private Set<Series> series = new HashSet<Series>();

    @ManyToMany(mappedBy = "ratings")
    private Set<Season> seasons = new HashSet<Season>();

    @ManyToMany(mappedBy = "ratings")
    private Set<Episode> episodes = new HashSet<Episode>();

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    public Set<Series> getSeries() {
        return series;
    }

    public void setSeries(Set<Series> series) {
        this.series = series;
    }

    public Set<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(Set<Season> seasons) {
        this.seasons = seasons;
    }

    public Set<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(Set<Episode> episodes) {
        this.episodes = episodes;
    }

    public Rating() {
    }

    public Rating(Double value, User user, Set<Movie> movies, Set<Series> series, Set<Season> seasons,
            Set<Episode> episodes) {
        this.value = value;
        this.user = user;
        this.movies = movies;
        this.series = series;
        this.seasons = seasons;
        this.episodes = episodes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
