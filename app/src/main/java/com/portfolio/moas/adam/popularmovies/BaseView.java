package com.portfolio.moas.adam.popularmovies;

public interface BaseView<T extends BasePresenter> {
    void setPresenter(T presenter);
}

