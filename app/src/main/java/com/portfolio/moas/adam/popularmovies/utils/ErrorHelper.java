package com.portfolio.moas.adam.popularmovies.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.portfolio.moas.adam.popularmovies.R;

/**
 * Created by adam on 22/02/2018.
 */

public class ErrorHelper {

    public static void displayError(Context context, View view, String errorMessage) {
        Snackbar snackbar = Snackbar.make(view, errorMessage, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(context.getResources().getColor(R.color.errorRed));
        snackbar.show();
    }
}
