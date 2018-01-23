package com.portfolio.moas.adam.popularmovies;

import com.google.gson.Gson;

/**
 * Created by adam on 17/01/2018.
 */

public final class MovieJSONUtils {

    private static MovieResponse getMovieJSONResponse(String apiResponse) {
        return new Gson().fromJson(apiResponse, MovieResponse.class);
    }

    static int getMovieCount(String apiResponse) {
        return getMovieJSONResponse(apiResponse).results.length;
    }

    static String getMoviePosterPath(String apiResponse, int position) {
        return getMovieJSONResponse(apiResponse).results[position].poster_path;
    }

    static String getMovieTitle(String apiResponse, int position) {
        return getMovieJSONResponse(apiResponse).results[position].title;
    }

    static String getMovieOverview(String apiResponse, int position) {
        return getMovieJSONResponse(apiResponse).results[position].overview;
    }

    static String getMovieOReleaseDate(String apiResponse, int position) {
        return getMovieJSONResponse(apiResponse).results[position].release_date;
    }

    static double getMovieVoteAverage(String apiResponse, int position) {
        return getMovieJSONResponse(apiResponse).results[position].vote_average;
    }
}
