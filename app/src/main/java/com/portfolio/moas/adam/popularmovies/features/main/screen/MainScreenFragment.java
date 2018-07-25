package com.portfolio.moas.adam.popularmovies.features.main.screen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ProgressBar;

import com.portfolio.moas.adam.popularmovies.R;
import com.portfolio.moas.adam.popularmovies.features.ItemClickListener;

import butterknife.BindView;

import static com.google.common.base.Preconditions.checkNotNull;

public class MainScreenFragment extends Fragment implements MainScreenContract.View, ItemClickListener {

    @NonNull
    private static final String ARGUMENT_MAIN_SCREEN_ID = "MAIN_SCREEN_ID";

    private MainScreenContract.Presenter mainScreenPresenter;

    @BindView(R.id.pb_loading)
    ProgressBar progressBar;

    public static MainScreenFragment newInstance(@Nullable String mainScreenId) {
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_MAIN_SCREEN_ID, mainScreenId);
        MainScreenFragment fragment = new MainScreenFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void setPresenter(MainScreenContract.Presenter presenter) {
        mainScreenPresenter = checkNotNull(presenter);
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    private void showLoadingIndicator() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoadingIndicator() {
        progressBar.setVisibility(View.INVISIBLE);
    }


}
