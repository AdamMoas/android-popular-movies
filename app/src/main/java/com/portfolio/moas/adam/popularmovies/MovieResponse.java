package com.portfolio.moas.adam.popularmovies;


/**
 * Created by adam on 19/01/2018.
 */

public class MovieResponse {
    public int page;
    public int total_results;
    public int total_pages;
    public Movie[] results;

    public static class Movie {
        public String title;
        public String popularity;
        public String release_date;
        public String poster_path;
        public String overview;
        public double vote_average;
        public int id;
        public boolean video;
    }

}
