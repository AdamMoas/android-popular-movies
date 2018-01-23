package com.portfolio.moas.adam.popularmovies;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class MovieDetailActivity extends AppCompatActivity {

    TextView mMovieTitle;
    TextView mMovieOverview;
    TextView mMovieReleaseDate;
    TextView mMovieVoteAverage;
    ImageView mMoviePosterImage;

    private static final String MOVIE_POSTER_IMAGE_SIZE = "w500";
    private static final String MOVIE_POSTER_BASE_URL = "http://image.tmdb.org/t/p/" + MOVIE_POSTER_IMAGE_SIZE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        mMovieTitle = (TextView) findViewById(R.id.movie_title);
        mMovieOverview = (TextView) findViewById(R.id.movie_overview);
        mMovieReleaseDate = (TextView) findViewById(R.id.movie_release_date);
        mMovieVoteAverage = (TextView) findViewById(R.id.movie_vote_average);
        mMoviePosterImage = (ImageView) findViewById(R.id.movie_poster);

        Bundle extras = getIntent().getExtras();
        Resources res = getResources();

        if (extras != null) {
            Double voteAverage = extras.getDouble("voteAverage");
            int scoreTotal = 10;
            String formattedVotedAverage = res.getString(R.string.user_rating, voteAverage, scoreTotal);

            mMovieTitle.setText(extras.getString("title"));
            mMovieOverview.setText(extras.getString("overview"));
            mMovieReleaseDate.setText(extras.getString("releaseDate"));
            mMovieVoteAverage.setText(formattedVotedAverage);
            displayPosterImage(extras, mMoviePosterImage);
        }
    }

    private void displayPosterImage(Bundle extras, ImageView imageView) {
        String posterImagePath = extras.getString("posterPath");

        Picasso.with(this)
                .load(MOVIE_POSTER_BASE_URL + posterImagePath)
                .placeholder(R.drawable.placeholder_background)
                .into(imageView);
        Log.d("ImagePath: ", MOVIE_POSTER_BASE_URL + posterImagePath);
    }
}
