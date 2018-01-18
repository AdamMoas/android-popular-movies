package com.portfolio.moas.adam.popularmovies;

/**
 * Created by adam on 17/01/2018.
 */

public class Movie {
    public String title;
    public String popularity;
    public String release_date;
    public String poster_path;
    public String overview;
    public double vote_average;
    public int id;
    public boolean video;

    public Movie(String title, String popularity, String release_date,
                 String poster_path, String overview,
                 double vote_average, int id, boolean video) {

        this.title = title;
        this.popularity = popularity;
        this.release_date = release_date;
        this.poster_path = poster_path;
        this.overview = overview;
        this.vote_average = vote_average;
        this.id = id;
        this.video = video;
    }

}
