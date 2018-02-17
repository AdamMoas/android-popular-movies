package com.portfolio.moas.adam.popularmovies.data.remote;

import com.portfolio.moas.adam.popularmovies.data.model.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by adam on 09/02/2018.
 */

public interface MovieDbService {

//    String base = "http://api.themoviedb.org/3/movie/";
//    String movieTrailerURLQuery = "[ID]/videos?api_key=[API-KEY]";
//    String movieReviewURLQuery = "[ID]/reviews?api_key=[API-KEY]";
//    String exampleMovieId = "211672";
//
//    String movieListPopular = "popular?api_key=[API-KEY]";
//    String movieListTopRated = "top_rated?api_key=[API-KEY]";
//
//    String language = "&language=en-US";

    @GET("movie/{filter}")
    Call<MoviesResponse> getAllMovies(@Path("filter") String filter, @Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovies(@Query("api_key") String apiKey);

    //    @GET("movie/{id}")
//    Call<MoviesResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

}
