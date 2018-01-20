package com.portfolio.moas.adam.popularmovies;

import android.content.Intent;
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

    //TODO This is temporary. Move this into constants along with the same in Adapter
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

        Intent intent = getIntent();

        mMovieTitle.setText(intent.getExtras().getString("title"));
        mMovieOverview.setText(intent.getExtras().getString("overview"));
        mMovieReleaseDate.setText(intent.getExtras().getString("releaseDate"));
        mMovieVoteAverage.setText(Double.toString(intent.getExtras().getDouble("voteAverage")) + "/10");
        displayPosterImage(intent, mMoviePosterImage);

    }

    private void displayPosterImage(Intent intent, ImageView imageView) {
        String posterImagePath = intent.getExtras().getString("posterPath");

        if (this != null) {
            Picasso.with(this)
                    .load(MOVIE_POSTER_BASE_URL + posterImagePath)
                    .into(imageView);
            System.out.println("ImagePath: " + MOVIE_POSTER_BASE_URL + posterImagePath);
        } else {
            Log.d("Picasso", "Picasso context is null");
        }
    }


}
