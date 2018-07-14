package com.portfolio.moas.adam.popularmovies.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by adam on 17/01/2018.
 */

public final class NetworkUtils {

    // Suggested in code review. Referenced from: https://developer.android.com/training/monitoring-device-state/connectivity-monitoring.html
    public static boolean isDeviceConnectedToNetwork(Context context) {
        boolean isConnected = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return isConnected;
    }
}
