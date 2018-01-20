package com.portfolio.moas.adam.popularmovies;

import com.google.gson.Gson;

/**
 * Created by adam on 17/01/2018.
 */

public final class MovieJSONUtils {

    public static MovieResponse getMovieJSONResponse(String apiResponse) {
        return new Gson().fromJson(apiResponse, MovieResponse.class);
    }

    public static int getMovieCount(String apiResponse) {
        return getMovieJSONResponse(apiResponse).results.length;
    }

    public static String getMoviePosterPath(String apiResponse, int position) {
        return getMovieJSONResponse(apiResponse).results[position].poster_path;
    }

    public static String getMovieTitle(String apiResponse, int position) {
        return getMovieJSONResponse(apiResponse).results[position].title;
    }

    public static String getMovieOverview(String apiResponse, int position) {
        return getMovieJSONResponse(apiResponse).results[position].overview;
    }

    public static String getMovieOReleaseDate(String apiResponse, int position) {
        return getMovieJSONResponse(apiResponse).results[position].release_date;
    }

    public static double getMovieVoteAverage(String apiResponse, int position) {
        return getMovieJSONResponse(apiResponse).results[position].vote_average;
    }
}
