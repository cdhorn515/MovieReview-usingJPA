package com.cdhorn.Models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String genre;
    private String imdblink;
    private Date releasedate;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Review> reviews;

    public Movie() {}

    public Movie(String title, String genre, String imdblink, Date releasedate) {
        this.title = title;
        this.genre = genre;
        this.imdblink = imdblink;
        this.releasedate = releasedate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getImdblink() {
        return imdblink;
    }

    public void setImdblink(String imdbLink) {
        this.imdblink = imdbLink;
    }

    public Date getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(Date releasedate) {
        this.releasedate = releasedate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
