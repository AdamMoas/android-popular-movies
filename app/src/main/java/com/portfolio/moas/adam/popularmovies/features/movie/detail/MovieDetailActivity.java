package com.portfolio.moas.adam.popularmovies.features.movie.detail;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.portfolio.moas.adam.popularmovies.R;
import com.portfolio.moas.adam.popularmovies.data.ApiUtils;
import com.portfolio.moas.adam.popularmovies.data.model.Movie;
import com.portfolio.moas.adam.popularmovies.data.model.TrailerResponse;
import com.portfolio.moas.adam.popularmovies.data.remote.MovieDbService;
import com.portfolio.moas.adam.popularmovies.utils.ErrorHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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

    @BindView(R.id.trailer_text)
    TextView mTrailerText;

    private MovieDbService movieDbService;
    private ArrayList<Movie> mMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        movieDbService = ApiUtils.getMovieDbService();
        mMovies = getIntent().getParcelableArrayListExtra("dataParcelTest");
        int movieItemPosition = getIntent().getIntExtra("dataPosition", 0);

        bindMovieDetailData(mMovies, movieItemPosition);
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

    private void setupYouTubeLink(Bundle extras) {
        String baseUrl = "http://api.themoviedb.org/3/movie";
        String apiKey = getString(R.string.TMDB_API_KEY);
        int id = extras.getInt(getString(R.string.movie_trailer_key));
        String finalUrl = baseUrl + "/" + id + "/" + "videos?api_key=" + apiKey;
        https:
//www.youtube.com/watch?v=xKJmEC5ieOk
        System.out.println("FinalURL: " + finalUrl);
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
                    int numberOfTrailers = response.body().getResults().size();
                    List<String> youtubeKeys = new ArrayList<>();

                    Log.d("Youtube Key number: ", "" + numberOfTrailers);
                    Log.d("Youtube Key number: id", "" + movieId);

                    for (int i = 0; i <= numberOfTrailers - 1; i++) {
                        youtubeKeys.add(response.body().getResults().get(i).getKey());
                        System.out.println("Key position: " + i + " - Key value: " + youtubeKeys.get(i));
                        mTrailerText.append(youtubeKeys.get(i) + "\n ");
                    }
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

    private void launchYoutubeIntent(Context context, String youtubeKey) {
        Intent youTubeAppIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + youtubeKey));
        Intent websiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://youtube.com/watch?v=" + youtubeKey));

        if (context != null) {
            try {
                context.startActivity(youTubeAppIntent);
            } catch (ActivityNotFoundException e) {
                context.startActivity(websiteIntent);
            }
        }
    }
}
