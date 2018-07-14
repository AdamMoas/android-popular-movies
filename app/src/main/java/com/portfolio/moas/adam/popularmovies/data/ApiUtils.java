package com.portfolio.moas.adam.popularmovies.data;

import com.portfolio.moas.adam.popularmovies.data.remote.MovieDbService;
import com.portfolio.moas.adam.popularmovies.data.remote.RetrofitClient;

/**
 * Created by adam on 09/02/2018.
 */

public class ApiUtils {

    public static MovieDbService getMovieDbService() {
        return RetrofitClient.getClient().create(MovieDbService.class);
    }
}
