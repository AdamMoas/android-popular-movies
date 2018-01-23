package com.portfolio.moas.adam.popularmovies;


/**
 * Created by adam on 19/01/2018.
 */

public class MovieResponse {
    // Referenced from: https://futurestud.io/tutorials/gson-mapping-of-arrays-and-lists-of-objects
    public int page;
    public int total_results;
    public int total_pages;
    Movie[] results;

    public static class Movie {
        public String title;
        public String popularity;
        String release_date;
        String poster_path;
        String overview;
        double vote_average;
        public int id;
        public boolean video;
    }

}
