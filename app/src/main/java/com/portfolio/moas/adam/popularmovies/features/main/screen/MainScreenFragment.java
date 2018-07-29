package com.portfolio.moas.adam.popularmovies.features.main.screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.portfolio.moas.adam.popularmovies.R;
import com.portfolio.moas.adam.popularmovies.data.model.Movie;
import com.portfolio.moas.adam.popularmovies.features.ItemClickListener;
import com.portfolio.moas.adam.popularmovies.features.movie.detail.MovieDetailActivity;
import com.portfolio.moas.adam.popularmovies.utils.Constants;
import com.portfolio.moas.adam.popularmovies.utils.ErrorHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.portfolio.moas.adam.popularmovies.utils.Constants.SORT_BY_POPULARITY;
import static com.portfolio.moas.adam.popularmovies.utils.Constants.SORT_BY_TOP_RATED;

public class MainScreenFragment extends Fragment implements MainScreenContract.View, ItemClickListener {

    @NonNull
    private static final String ARGUMENT_MAIN_SCREEN_ID = "MAIN_SCREEN_ID";

    private MainScreenContract.Presenter mainScreenPresenter;

    @BindView(R.id.pb_loading)
    ProgressBar progressBar;
    @BindView(R.id.rv_movie_thumbnails)
    RecyclerView recyclerView;

    ArrayList<Movie> movies;

    public static MainScreenFragment newInstance(@Nullable String mainScreenId) {
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_MAIN_SCREEN_ID, mainScreenId);
        MainScreenFragment fragment = new MainScreenFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        mainScreenPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.main_screen_fragment, container, false);
        setHasOptionsMenu(true);
        ButterKnife.bind(this, root);

        progressBar = new ProgressBar(getActivity());

        setUpRecyclerView();

        mainScreenPresenter.loadMovieData(SORT_BY_POPULARITY);

        return root;
    }

    @Override
    public void setPresenter(MainScreenContract.Presenter presenter) {
        mainScreenPresenter = checkNotNull(presenter);
    }

    public void showLoadingIndicator() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideLoadingIndicator() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void displayError(int viewId, int messageResId) {
        if (getView() != null) {
            ErrorHelper.displayError(getContext(),
                    getView().findViewById(viewId),
                    getString(messageResId));
        }
    }

    private void setUpRecyclerView() {
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(), numberOfColumns));
        recyclerView.setHasFixedSize(true);
    }

    public void setUpAdapter(int movieCount, ArrayList<Movie> movies) {
        this.movies = movies;
        MovieRecyclerViewAdapter movieRecyclerViewAdapter = new MovieRecyclerViewAdapter(getContext(), movieCount, movies);
        movieRecyclerViewAdapter.setClickListener(this);
        recyclerView.setAdapter(movieRecyclerViewAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent detailActivityIntent = new Intent(getContext(), MovieDetailActivity.class);
        detailActivityIntent.putExtra(Constants.MOVIE_DETAIL_PARCEL_EXTRA, this.movies);
        detailActivityIntent.putExtra(Constants.MOVIE_POSITION_EXTRA, position);
        startActivity(detailActivityIntent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.sort_popular_action:
                mainScreenPresenter.loadMovieData(SORT_BY_POPULARITY);
                break;
            case R.id.sort_top_rated_action:
                mainScreenPresenter.loadMovieData(SORT_BY_TOP_RATED);
                break;
            default:
                mainScreenPresenter.loadMovieData(SORT_BY_TOP_RATED);
        }
        return super.onOptionsItemSelected(item);
    }

}
