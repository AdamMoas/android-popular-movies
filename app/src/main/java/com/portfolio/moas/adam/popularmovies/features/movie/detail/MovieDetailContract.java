package com.portfolio.moas.adam.popularmovies.features.movie.detail;

import com.portfolio.moas.adam.popularmovies.BasePresenter;
import com.portfolio.moas.adam.popularmovies.BaseView;
import com.portfolio.moas.adam.popularmovies.data.model.Review;
import com.portfolio.moas.adam.popularmovies.data.model.Trailer;

import java.util.List;

public interface MovieDetailContract {

    interface View extends BaseView<Presenter> {
        void displayError(int viewId, int messageResId);

        void setUpTrailerAdapter(int trailerCount, List<Trailer> trailers);

        void setUpReviewAdapter(int reviewCount, List<Review> reviews);

        void displayMovieTrailerTitle(List<Trailer> trailers);

        void displayMovieReviewTitle(List<Review> reviews);
    }

    interface Presenter extends BasePresenter {
        void fetchMovieTrailers(int position);

        void fetchMovieReviews(int position);
    }

}




