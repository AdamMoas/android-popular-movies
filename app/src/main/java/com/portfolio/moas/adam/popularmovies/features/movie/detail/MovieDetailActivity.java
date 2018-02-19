package com.portfolio.moas.adam.popularmovies.features.movie.detail;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.portfolio.moas.adam.popularmovies.R;
import com.portfolio.moas.adam.popularmovies.data.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieDetailActivity extends AppCompatActivity {

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
    @BindView(R.id.movie_trailer_list)
    ListView mMovieTrailerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        Resources res = getResources();

        ArrayList<Movie> movie = getIntent().getParcelableArrayListExtra("dataParcelTest");
        int movieItemPosition = getIntent().getIntExtra("dataPosition", 0);

        System.out.println("Test---" + movie.get(0).getTitle());

        bindMovieDetailData(extras, res, movie, movieItemPosition); // TODO experiment
    }

    private void bindMovieDetailData(Bundle extras, Resources res, ArrayList<Movie> movie, int position) {
        if (extras != null) {
            float voteAverage = movie.get(position).getVoteAverage();
            int scoreTotal = 10;
            // Referenced from: https://developer.android.com/guide/topics/resources/string-resource.html#FormattingAndStyling
            String formattedVotedAverage = res.getString(R.string.user_rating, voteAverage, scoreTotal);

            mMovieTitle.setText(movie.get(position).getTitle());
            mMovieOverview.setText(movie.get(position).getOverview());
            mMovieReleaseDate.setText(movie.get(position).getReleaseDate());
            mMovieVoteAverage.setText(formattedVotedAverage);
            displayPosterImage(extras, mMoviePosterImage, movie, position);
            setupYouTubeLink(extras);
        }

    }

    private void displayPosterImage(Bundle extras, ImageView imageView, ArrayList<Movie> movie, int position) {
//        String posterImagePath = extras.getString(getString(R.string.poster_path_key));
        String posterImagePath = movie.get(position).getPosterPath();
        String moviePosterImageSize = "w500";
        String moviePosterBaseUrl = "http://image.tmdb.org/t/p/" + moviePosterImageSize;

        Picasso.with(this)
                .load(moviePosterBaseUrl + posterImagePath)
                .placeholder(R.drawable.placeholder_background)
                .into(imageView);
        Log.d("ImagePath: ", moviePosterBaseUrl + posterImagePath);
    }

    private void setupYouTubeLink(Bundle extras) {
        String baseUrl = "http://api.themoviedb.org/3/movie";
        String apiKey = getString(R.string.TMDB_API_KEY);
        int id = extras.getInt(getString(R.string.movie_trailer_key));
        String finalUrl = baseUrl + "/" + id + "/" + "videos?api_key=" + apiKey;
        https:
//www.youtube.com/watch?v=xKJmEC5ieOk
        System.out.println("FinalURL: " + finalUrl);
    }
}
