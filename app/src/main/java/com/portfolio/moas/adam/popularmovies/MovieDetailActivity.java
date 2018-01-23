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
            Double voteAverage = extras.getDouble(getString(R.string.vote_average_key));
            int scoreTotal = 10;
            // Referenced from: https://developer.android.com/guide/topics/resources/string-resource.html#FormattingAndStyling
            String formattedVotedAverage = res.getString(R.string.user_rating, voteAverage, scoreTotal);

            mMovieTitle.setText(extras.getString(getString(R.string.title_key)));
            mMovieOverview.setText(extras.getString(getString(R.string.overview_key)));
            mMovieReleaseDate.setText(extras.getString(getString(R.string.release_date_key)));
            mMovieVoteAverage.setText(formattedVotedAverage);
            displayPosterImage(extras, mMoviePosterImage);
        }
    }

    private void displayPosterImage(Bundle extras, ImageView imageView) {
        String posterImagePath = extras.getString(getString(R.string.poster_path_key));
        String moviePosterImageSize = "w500";
        String moviePosterBaseUrl = "http://image.tmdb.org/t/p/" + moviePosterImageSize;

        Picasso.with(this)
                .load(moviePosterBaseUrl + posterImagePath)
                .placeholder(R.drawable.placeholder_background)
                .into(imageView);
        Log.d("ImagePath: ", moviePosterBaseUrl + posterImagePath);
    }
}
