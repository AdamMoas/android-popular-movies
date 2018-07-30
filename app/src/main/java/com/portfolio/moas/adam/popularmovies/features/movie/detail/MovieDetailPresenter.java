package com.portfolio.moas.adam.popularmovies.features.movie.detail;

import android.support.annotation.NonNull;
import android.util.Log;

import com.portfolio.moas.adam.popularmovies.R;
import com.portfolio.moas.adam.popularmovies.data.model.Movie;
import com.portfolio.moas.adam.popularmovies.data.model.Review;
import com.portfolio.moas.adam.popularmovies.data.model.ReviewResponse;
import com.portfolio.moas.adam.popularmovies.data.model.Trailer;
import com.portfolio.moas.adam.popularmovies.data.model.TrailerResponse;
import com.portfolio.moas.adam.popularmovies.data.remote.MovieDbService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.portfolio.moas.adam.popularmovies.utils.Secrets.*;

public class MovieDetailPresenter implements MovieDetailContract.Presenter {

    private MovieDetailContract.View movieDetailView;
    private MovieDbService movieDbService;
    private ArrayList<Movie> movies;
    private List<Trailer> trailers;
    private List<Review> reviews;

    MovieDetailPresenter(@NonNull MovieDetailContract.View movieDetailView,
                         @NonNull MovieDbService movieDbService,
                         @NonNull ArrayList<Movie> movies,
                         @NonNull List<Trailer> trailers,
                         @NonNull List<Review> reviews) {
        this.movieDetailView = movieDetailView;
        this.movieDbService = movieDbService;
        this.movies = movies;
        this.trailers = trailers;
        this.reviews = reviews;
        movieDetailView.setPresenter(this);
    }

    @Override
    public void start() {
    }

    public void fetchMovieTrailers(int position) {
        final int movieId = movies.get(position).getId();
        if (API_KEY.isEmpty()) {
            Log.d("API-KEY", "API Key missing");
            return;
        }

        Call<TrailerResponse> call = movieDbService.getTrailers(movieId, API_KEY);
        call.enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                if (response.isSuccessful()) {
                    trailers = response.body().getResults();
                    int numberOfTrailers = trailers.size();
                    movieDetailView.setUpTrailerAdapter(numberOfTrailers, trailers);
                    movieDetailView.displayMovieTrailerTitle(trailers);
                }
            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {
                movieDetailView.displayError(R.id.movie_detail_layout, R.string.network_error_message);
            }
        });
    }

    public void fetchMovieReviews(int position) {
        final int movieId = movies.get(position).getId();
        if (API_KEY.isEmpty()) {
            Log.d("API-KEY", "API Key missing");
            return;
        }

        Call<ReviewResponse> call = movieDbService.getReviews(movieId, API_KEY);
        call.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                if (response.isSuccessful()) {
                    reviews = response.body().getResults();
                    int numberOfReviews = reviews.size();
                    movieDetailView.setUpReviewAdapter(numberOfReviews, reviews);
                    movieDetailView.displayMovieReviewTitle(reviews);
                }
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                movieDetailView.displayError(R.id.movie_detail_layout, R.string.network_error_message);
            }
        });
    }
}
