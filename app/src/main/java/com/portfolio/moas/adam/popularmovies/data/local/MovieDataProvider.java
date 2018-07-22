package com.portfolio.moas.adam.popularmovies.data.local;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class MovieDataProvider extends ContentProvider {

    public static final int CODE_FAVOURITES = 100;
    public static final int CODE_FAVOURITES_WITH_ID = 101;


    private static final UriMatcher uriMatcher = buildUriMatcher();
    private FavouritesDbHelper favouritesDbHelper;


    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = FavouritesContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, FavouritesContract.PATH_FAVOURITES, CODE_FAVOURITES);
        matcher.addURI(authority, FavouritesContract.PATH_FAVOURITES + "/#", CODE_FAVOURITES_WITH_ID);

        return matcher;
    }


    @Override
    public boolean onCreate() {
        favouritesDbHelper = new FavouritesDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor cursor;

        switch (uriMatcher.match(uri)) { //TODO ADD WITH ID
            case CODE_FAVOURITES: {
                cursor = favouritesDbHelper.getReadableDatabase().query(
                        FavouritesContract.FavouritesEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new RuntimeException("Implement this when adding poster images to content provider");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase database = favouritesDbHelper.getWritableDatabase();

        switch (uriMatcher.match(uri)) {
            case CODE_FAVOURITES:
                database.beginTransaction();

                try {
                    long id = database.insert(FavouritesContract.FavouritesEntry.TABLE_NAME, null, contentValues);

                    if (id > 0) {
                        database.setTransactionSuccessful();
                    } else {
                        throw new SQLException("Failed to insert two into " + uri);
                    }
                } finally {
                    database.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);

            default:
                throw new UnsupportedOperationException("Unknown uri" + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int numberOfRowsDeleted;

        if (selection == null) selection = "1";

        switch (uriMatcher.match(uri)) {

            case CODE_FAVOURITES:
                numberOfRowsDeleted = favouritesDbHelper.getWritableDatabase().delete(
                        FavouritesContract.FavouritesEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (numberOfRowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numberOfRowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        int favouritesUpdated = 0;
        int match = uriMatcher.match(uri);

        switch (match) {
            case CODE_FAVOURITES_WITH_ID:
                String id = uri.getPathSegments().get(1);
                favouritesUpdated = favouritesDbHelper.getWritableDatabase().update(FavouritesContract.FavouritesEntry.TABLE_NAME, contentValues, "_id=?", new String[]{id});
                break;

            case CODE_FAVOURITES:
                favouritesUpdated = favouritesDbHelper.getWritableDatabase().update(FavouritesContract.FavouritesEntry.TABLE_NAME, contentValues, null, null);
                break;
        }
        if (favouritesUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);

        }
        return favouritesUpdated;
    }
}
