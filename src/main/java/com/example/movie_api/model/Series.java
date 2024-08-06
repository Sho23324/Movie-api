package com.example.movie_api.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name = "series")
public class Series {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "summary")
    private String summary;

    @Column(name = "release_year")
    private Integer release_year;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "country")
    private String country;

    @Column(name = "imdb_rating")
    private Double imdb_rating;

    @Column(name = "cover")
    private String cover;

    @ManyToMany
    @JoinTable(
        name = "series_rating",
        joinColumns = @JoinColumn(name="series_id"),
        inverseJoinColumns = @JoinColumn(name="rating_id")
    )
    private Set<Rating> ratings = new HashSet<Rating>();

    public Set<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }

    public Set<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(Set<Season> seasons) {
        this.seasons = seasons;
    }

    @ManyToMany
    @JoinTable(
        name = "series_genre",
        joinColumns = @JoinColumn(name="series_id"),
        inverseJoinColumns = @JoinColumn(name="genre_id")
    )
    @JsonIgnoreProperties(value = {"movies", "series"})
    private Set<Genre> genres = new HashSet<Genre>();

    @ManyToMany
    @JoinTable(
        name = "series_cast",
        joinColumns = @JoinColumn(name="series_id"),
        inverseJoinColumns = @JoinColumn(name="cast_id")
    )
    @JsonIgnoreProperties(value = {"movies", "series"})
    private Set<Cast> casts = new HashSet<Cast>();

    @ManyToMany
    @JoinTable(
        name = "series_production",
        joinColumns = @JoinColumn(name="series_id"),
        inverseJoinColumns = @JoinColumn(name="production_id")
    )
    @JsonIgnoreProperties(value = {"movies", "series"})
    private Set<Production> productions = new HashSet<Production>();

    @OneToMany(mappedBy = "series", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = {"series"}, allowSetters = true)
    private Set<Season> seasons = new HashSet<Season>();

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Set<Cast> getCasts() {
        return casts;
    }

    public void setCasts(Set<Cast> casts) {
        this.casts = casts;
    }

    public Set<Production> getProductions() {
        return productions;
    }

    public void setProductions(Set<Production> productions) {
        this.productions = productions;
    }

    public Series() {
    }

    public Series(String title, String summary, Integer release_year, Integer duration, String country,
            Double imdb_rating, String cover, Set<Rating> ratings, Set<Genre> genres, Set<Cast> casts,
            Set<Production> productions, Set<Season> seasons) {
        this.title = title;
        this.summary = summary;
        this.release_year = release_year;
        this.duration = duration;
        this.country = country;
        this.imdb_rating = imdb_rating;
        this.cover = cover;
        this.ratings = ratings;
        this.genres = genres;
        this.casts = casts;
        this.productions = productions;
        this.seasons = seasons;
    }

    

    public Series(String title, String summary, Integer release_year, Integer duration, String country,
            Double imdb_rating, String cover) {
        this.title = title;
        this.summary = summary;
        this.release_year = release_year;
        this.duration = duration;
        this.country = country;
        this.imdb_rating = imdb_rating;
        this.cover = cover;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getImdb_rating() {
        return imdb_rating;
    }

    public void setImdb_rating(Double imdb_rating) {
        this.imdb_rating = imdb_rating;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void addGenre(Genre genre) {
        this.genres.add(genre);
    }

    public void addCast(Cast cast) {
        this.casts.add(cast);
    }

    public void addProduction(Production production) {
        this.productions.add(production);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Series other = (Series) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    
}
