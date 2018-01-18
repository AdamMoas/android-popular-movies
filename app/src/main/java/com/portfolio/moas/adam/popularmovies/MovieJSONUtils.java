package com.portfolio.moas.adam.popularmovies;

import com.google.gson.Gson;

/**
 * Created by adam on 17/01/2018.
 */

public final class MovieJSONUtils {

    public static Movie[] getMovieJSONResponse(String apiResponse) {
        return new Gson().fromJson(apiResponse, Movie[].class);
    }

    public static String getPosterPath(String apiResponse, int position) {
        return getMovieJSONResponse(apiResponse)[position].poster_path;
    }

    public static int getMovieCount(String apiResponse) {
        return getMovieJSONResponse(apiResponse).length;
    }
}
