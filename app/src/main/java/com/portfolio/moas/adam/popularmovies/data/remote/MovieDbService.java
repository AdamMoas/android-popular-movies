package com.portfolio.moas.adam.popularmovies.data.remote;

import com.portfolio.moas.adam.popularmovies.data.model.MovieResponse;
import com.portfolio.moas.adam.popularmovies.data.model.ReviewResponse;
import com.portfolio.moas.adam.popularmovies.data.model.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by adam on 09/02/2018.
 */

public interface MovieDbService {

    @GET("movie/{filter}")
    Call<MovieResponse> getAllMovies(@Path("filter") String filter, @Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Call<TrailerResponse> getTrailers(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("movie/{id}/reviews")
    Call<ReviewResponse> getReviews(@Path("id") int id, @Query("api_key") String apiKey);

}
