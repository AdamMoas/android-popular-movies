package com.portfolio.moas.adam.popularmovies.features.movie.detail;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.portfolio.moas.adam.popularmovies.R;
import com.portfolio.moas.adam.popularmovies.data.model.Movie;
import com.portfolio.moas.adam.popularmovies.data.model.Review;
import com.portfolio.moas.adam.popularmovies.data.model.Trailer;
import com.portfolio.moas.adam.popularmovies.features.ItemClickListener;
import com.portfolio.moas.adam.popularmovies.utils.Constants;
import com.portfolio.moas.adam.popularmovies.utils.ErrorHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailFragment extends Fragment implements MovieDetailContract.View, ItemClickListener {

    @NonNull
    private static final String ARGUMENT_MOVIE_DETAIL_ID = "MOVIE_DETAIL_ID";

    private MovieDetailContract.Presenter movieDetailPresenter;

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

    ArrayList<Movie> movies;
    List<Trailer> trailers;

    public static MovieDetailFragment newInstance(@Nullable String movieDetailId) {
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_MOVIE_DETAIL_ID, movieDetailId);
        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        movieDetailPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.movie_detail_fragment, container, false);
        setHasOptionsMenu(true);
        ButterKnife.bind(this, root);

        movies = getActivity().getIntent().getParcelableArrayListExtra(Constants.MOVIE_DETAIL_PARCEL_EXTRA);
        int movieItemPosition = getActivity().getIntent().getIntExtra(Constants.MOVIE_POSITION_EXTRA, 0);

        bindMovieDetailData(movies, movieItemPosition);
        setUpRecyclerView(mRVTrailers);
        setUpRecyclerView(rVReviews);

        return root;
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
        movieDetailPresenter.fetchMovieTrailers(position);
        movieDetailPresenter.fetchMovieReviews(position);
    }

    private void displayPosterImage(ImageView imageView, ArrayList<Movie> movie, int position) {
        String posterImagePath = movie.get(position).getPosterPath();
        String moviePosterImageSize = "w500";
        String moviePosterBaseUrl = "http://image.tmdb.org/t/p/" + moviePosterImageSize;

        Picasso.with(getContext())
                .load(moviePosterBaseUrl + posterImagePath)
                .placeholder(R.drawable.placeholder_background)
                .into(imageView);
        Log.d("ImagePath: ", moviePosterBaseUrl + posterImagePath);
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

    public void displayMovieTrailerTitle(List<Trailer> trailers) {
        if (trailers.size() > 0) {
            mTrailerTitle.setVisibility(View.VISIBLE);
            mRVTrailers.setVisibility(View.VISIBLE);
        } else {
            mTrailerTitle.setVisibility(View.GONE);
            mRVTrailers.setVisibility(View.GONE);
        }
    }

    public void displayMovieReviewTitle(List<Review> reviews) {
        if (reviews.size() > 0) {
            reviewTitle.setVisibility(View.VISIBLE);
            rVReviews.setVisibility(View.VISIBLE);
        } else {
            reviewTitle.setVisibility(View.GONE);
            rVReviews.setVisibility(View.GONE);
        }
    }

    private void setUpRecyclerView(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    public void setUpTrailerAdapter(int trailerCount, List<Trailer> trailers) {
        this.trailers = trailers;
        mTrailerAdapter = new TrailerRecyclerViewAdapter(getContext(), trailerCount, trailers);
        mTrailerAdapter.setClickListener(this);
        mRVTrailers.setAdapter(mTrailerAdapter);
    }

    public void setUpReviewAdapter(int reviewCount, List<Review> reviews) {
        reviewAdapter = new ReviewRecyclerViewAdapter(getContext(), reviewCount, reviews);
        rVReviews.setAdapter(reviewAdapter);
    }

    @Override
    public void displayError(int viewId, int messageResId) {
        if (getView() != null) {
            ErrorHelper.displayError(getContext(),
                    getView().findViewById(viewId),
                    getString(messageResId));
        }
    }

    @Override
    public void setPresenter(MovieDetailContract.Presenter presenter) {
        this.movieDetailPresenter = presenter;
    }

    @Override
    public void onItemClick(View view, int position) {
        String trailerKey = trailers.get(position).getKey();
        launchYoutube(getContext(), trailerKey);
    }
}
