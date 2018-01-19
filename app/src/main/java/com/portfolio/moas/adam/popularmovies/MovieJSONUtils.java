package com.portfolio.moas.adam.popularmovies;

import com.google.gson.Gson;

/**
 * Created by adam on 17/01/2018.
 */

public final class MovieJSONUtils {

    public static MovieResponse getMovieJSONResponse(String apiResponse) {
        return new Gson().fromJson(apiResponse, MovieResponse.class);
    }

    public static String getPosterPath(String apiResponse, int position) {
        return getMovieJSONResponse(apiResponse).results[position].poster_path;
    }

    public static int getMovieCount(String apiResponse) {
        return getMovieJSONResponse(apiResponse).results.length;
    }
}
