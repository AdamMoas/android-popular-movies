package com.portfolio.moas.adam.popularmovies.features.movie.detail;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.portfolio.moas.adam.popularmovies.R;
import com.portfolio.moas.adam.popularmovies.data.ApiUtils;
import com.portfolio.moas.adam.popularmovies.data.model.Movie;
import com.portfolio.moas.adam.popularmovies.data.model.Review;
import com.portfolio.moas.adam.popularmovies.data.model.ReviewResponse;
import com.portfolio.moas.adam.popularmovies.data.model.Trailer;
import com.portfolio.moas.adam.popularmovies.data.model.TrailerResponse;
import com.portfolio.moas.adam.popularmovies.data.remote.MovieDbService;
import com.portfolio.moas.adam.popularmovies.features.ItemClickListener;
import com.portfolio.moas.adam.popularmovies.utils.Constants;
import com.portfolio.moas.adam.popularmovies.utils.ErrorHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieDetailActivity extends AppCompatActivity implements ItemClickListener {

    @BindView(R.id.movie_title)
    TextView mMovieTitle;
    @BindView(R.id.movie_overview)
    TextView mMovieOverview;
    @BindView(R.id.movie_release_date)
    TextView mMovieReleaseDate;
    @BindView(R.id.movie_vote_average)
    TextView mMovieVoteAverage;
    @BindView(R.id.movie_poster)
    ImageView mMoviePosterImage;
    @BindView(R.id.movie_favourite)
    ImageView mFavouriteStarImage;

    @BindView(R.id.trailer_heading_title)
    TextView mTrailerTitle;

    @BindView(R.id.rv_trailers)
    RecyclerView mRVTrailers;

    @BindView(R.id.review_heading_title)
    TextView reviewTitle;

    @BindView(R.id.rv_reviews)
    RecyclerView rVReviews;

    TrailerRecyclerViewAdapter mTrailerAdapter;
    ReviewRecyclerViewAdapter reviewAdapter;

    private MovieDbService movieDbService;
    private ArrayList<Movie> mMovies;
    private List<Trailer> mTrailers;
    private List<Review> reviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        movieDbService = ApiUtils.getMovieDbService();
        mMovies = getIntent().getParcelableArrayListExtra(Constants.MOVIE_DETAIL_PARCEL_EXTRA);
        int movieItemPosition = getIntent().getIntExtra(Constants.MOVIE_POSITION_EXTRA, 0);

        bindMovieDetailData(mMovies, movieItemPosition);
        setUpRecyclerView(mRVTrailers);
        setUpRecyclerView(rVReviews);

    }

    private void bindMovieDetailData(ArrayList<Movie> movie, int position) {
        float voteAverage = movie.get(position).getVoteAverage();
        int scoreTotal = 10;
        // Referenced from: https://developer.android.com/guide/topics/resources/string-resource.html#FormattingAndStyling
        String formattedVotedAverage = getResources().getString(R.string.user_rating, voteAverage, scoreTotal);

        mMovieTitle.setText(movie.get(position).getTitle());
        mMovieOverview.setText(movie.get(position).getOverview());
        mMovieReleaseDate.setText(movie.get(position).getReleaseDate());
        mMovieVoteAverage.setText(formattedVotedAverage);
        displayPosterImage(mMoviePosterImage, movie, position);
        fetchMovieTrailers(position);
        fetchMovieReviews(position);
    }

    private void displayPosterImage(ImageView imageView, ArrayList<Movie> movie, int position) {
        String posterImagePath = movie.get(position).getPosterPath();
        String moviePosterImageSize = "w500";
        String moviePosterBaseUrl = "http://image.tmdb.org/t/p/" + moviePosterImageSize;

        Picasso.with(this)
                .load(moviePosterBaseUrl + posterImagePath)
                .placeholder(R.drawable.placeholder_background)
                .into(imageView);
        Log.d("ImagePath: ", moviePosterBaseUrl + posterImagePath);
    }

    private void fetchMovieTrailers(int position) {
        final int movieId = mMovies.get(position).getId();
        String apiKey = getString(R.string.TMDB_API_KEY);
        if (apiKey.isEmpty()) {
            Log.d("API-KEY", "API Key missing");
            return;
        }

        Call<TrailerResponse> call = movieDbService.getTrailers(movieId, apiKey);
        call.enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                if (response.isSuccessful()) {
                    mTrailers = response.body().getResults();
                    int numberOfTrailers = mTrailers.size();
                    setUpTrailerAdapter(numberOfTrailers);
                    displayMovieTrailerTitle();
                }
            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {
                ErrorHelper.displayError(MovieDetailActivity.this,
                        findViewById(R.id.movie_detail_layout),
                        getString(R.string.network_error_message));
            }
        });
    }

    private void fetchMovieReviews(int position) {
        final int movieId = mMovies.get(position).getId();
        String apiKey = getString(R.string.TMDB_API_KEY);
        if (apiKey.isEmpty()) {
            Log.d("API-KEY", "API Key missing");
            return;
        }

        Call<ReviewResponse> call = movieDbService.getReviews(movieId, apiKey);
        call.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                if (response.isSuccessful()) {
                    reviews = response.body().getResults();
                    int numberOfReviews = reviews.size();
                    setUpReviewAdapter(numberOfReviews);
                    displayMovieReviewTitle();
                }
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                ErrorHelper.displayError(MovieDetailActivity.this,
                        findViewById(R.id.movie_detail_layout),
                        getString(R.string.network_error_message));
            }
        });
    }

    private void setUpRecyclerView(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void setUpTrailerAdapter(int trailerCount) {
        mTrailerAdapter = new TrailerRecyclerViewAdapter(this, trailerCount, mTrailers);
        mTrailerAdapter.setClickListener(this);
        mRVTrailers.setAdapter(mTrailerAdapter);
    }

    private void setUpReviewAdapter(int reviewCount) {
        reviewAdapter = new ReviewRecyclerViewAdapter(this, reviewCount, reviews);
        rVReviews.setAdapter(reviewAdapter);
    }

    private void launchYoutube(Context context, String youtubeKey) {
        Intent youTubeAppIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.youtube_app_intent) + youtubeKey));
        Intent websiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.youtube_web_intent) + youtubeKey));

        if (context != null) {
            try {
                context.startActivity(youTubeAppIntent);
            } catch (ActivityNotFoundException e) {
                context.startActivity(websiteIntent);
            }
        }
    }

    private void displayMovieTrailerTitle() {
        if (mTrailers.size() > 0) {
            mTrailerTitle.setVisibility(View.VISIBLE);
            mRVTrailers.setVisibility(View.VISIBLE);
        } else {
            mTrailerTitle.setVisibility(View.GONE);
            mRVTrailers.setVisibility(View.GONE);
        }
    }

    private void displayMovieReviewTitle() {
        if (reviews.size() > 0) {
            reviewTitle.setVisibility(View.VISIBLE);
            rVReviews.setVisibility(View.VISIBLE);
        } else {
            reviewTitle.setVisibility(View.GONE);
            rVReviews.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        String trailerKey = mTrailers.get(position).getKey();
        launchYoutube(this, trailerKey);
    }
}
