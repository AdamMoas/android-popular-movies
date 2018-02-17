//package com.portfolio.moas.adam.popularmovies.utils;
//
//import com.google.gson.Gson;
//import com.portfolio.moas.adam.popularmovies.models.MovieResponse;
//
///**
// * Created by adam on 17/01/2018.
// */
//
//public final class MovieJSONUtils {
//    // Referenced from: https://futurestud.io/tutorials/gson-mapping-of-arrays-and-lists-of-objects
//    public static MovieResponse getMovieJSONResponse(String apiResponse) {
//        return new Gson().fromJson(apiResponse, MovieResponse.class);
//    }
//
//    public static int getMovieCount(String apiResponse) {
//        return getMovieJSONResponse(apiResponse).results.length;
//    }
//
//    public static String getMoviePosterPath(String apiResponse, int position) {
//        return getMovieJSONResponse(apiResponse).results[position].poster_path;
//    }
//
//    public static String getMovieTitle(String apiResponse, int position) {
//        return getMovieJSONResponse(apiResponse).results[position].title;
//    }
//
//    public static String getMovieOverview(String apiResponse, int position) {
//        return getMovieJSONResponse(apiResponse).results[position].overview;
//    }
//
//    public static String getMovieOReleaseDate(String apiResponse, int position) {
//        return getMovieJSONResponse(apiResponse).results[position].release_date;
//    }
//
//    public static double getMovieVoteAverage(String apiResponse, int position) {
//        return getMovieJSONResponse(apiResponse).results[position].vote_average;
//    }
//
//    public static int getMovieId(String apiResponse, int position) {
//        return getMovieJSONResponse(apiResponse).results[position].id;
//    }
//}
