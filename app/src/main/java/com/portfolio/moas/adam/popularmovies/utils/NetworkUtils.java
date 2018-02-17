//package com.portfolio.moas.adam.popularmovies.utils;
//
//import android.net.Uri;
//import android.util.Log;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.Scanner;
//
//import static com.portfolio.moas.adam.popularmovies.utils.Constants.SORT_BY_POPULARITY;
//import static com.portfolio.moas.adam.popularmovies.utils.Constants.SORT_BY_TOP_RATED;
//
///**
// * Created by adam on 17/01/2018.
// */
//
//public final class NetworkUtils {
//
//    private static final String TAG = NetworkUtils.class.getSimpleName();
//
//    private static final String MOVIE_TRAILER_ENDPOINT =
//            "http://api.themoviedb.org/3/movie";
//
//    private static final String POPULAR_MOVIE_ENDPOINT =
//            "http://api.themoviedb.org/3/movie/popular";
//
//    private static final String TOP_RATED_MOVIE_ENDPOINT =
//            "http://api.themoviedb.org/3/movie/top_rated";
//
//    private final static String QUERY_PARAM = "api_key";
//
//    private static String setBaseUrl(String sortBy) {
//        switch (sortBy) {
//            case SORT_BY_POPULARITY:
//                return POPULAR_MOVIE_ENDPOINT;
//            case SORT_BY_TOP_RATED:
//                return TOP_RATED_MOVIE_ENDPOINT;
//        }
//        return null;
//    }
//
//    public static URL buildUrl(String api_key, String sortBy) {
//        Uri builtUri = Uri.parse(setBaseUrl(sortBy)).buildUpon()
//                .appendQueryParameter(QUERY_PARAM, api_key)
//                .build();
//
//        URL url = null;
//        try {
//            url = new URL(builtUri.toString());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//
//        Log.v(TAG, "Built URL " + url);
//
//        return url;
//    }
//
////    static URL buildTrailerUrl(String api_key, String videoKey) {
////        Uri builtUri = Uri.parse(MOVIE_TRAILER_ENDPOINT).buildUpon()
////                .appendPath(MovieJSONUtils.getMovieId())
////                .appendQueryParameter(QUERY_PARAM, api_key)
////                .build();
////
////        URL url = null;
////        try {
////            url = new URL(builtUri.toString());
////        } catch (MalformedURLException e) {
////            e.printStackTrace();
////        }
////
////        Log.v(TAG, "Built URI " + url);
////
////        return url;
////    }
//
//    // Referenced from: Udacity Nanodegree coursework material:
//    public static String getResponseFromHttpUrl(URL url) throws IOException {
//        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//        try {
//            InputStream in = urlConnection.getInputStream();
//
//            Scanner scanner = new Scanner(in);
//            scanner.useDelimiter("\\A");
//
//            boolean hasInput = scanner.hasNext();
//            if (hasInput) {
//                return scanner.next();
//            } else {
//                return null;
//            }
//        } finally {
//            urlConnection.disconnect();
//        }
//    }
//
//}
