 package com.example.jordan.appcw;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class MyContentProvider extends ContentProvider {

    public static final String CONTENT_AUTHORITY = new String("com.example.jordan.appcw");
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_SPENDING = "SPENDING_TABLE";
    public static final String PATH_USERS = "USERS_TABLE";
    public static final String PATH_GOALS = "GOAL_TABLE";
    public static final String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/"+CONTENT_AUTHORITY +"/"+PATH_SPENDING ;
    public static final String CONTENT_TYPE_ITEM = "vnd.android.cursor.item/"+CONTENT_AUTHORITY +"/"+PATH_SPENDING;



    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(CONTENT_AUTHORITY,PATH_SPENDING,1);
        uriMatcher.addURI(CONTENT_AUTHORITY,PATH_SPENDING,2);
        uriMatcher.addURI(CONTENT_AUTHORITY,PATH_SPENDING,3);

        uriMatcher.addURI(CONTENT_AUTHORITY,PATH_USERS,4);
        uriMatcher.addURI(CONTENT_AUTHORITY,PATH_USERS,5);

        uriMatcher.addURI(CONTENT_AUTHORITY,PATH_GOALS,6);
        uriMatcher.addURI(CONTENT_AUTHORITY,PATH_GOALS,7);

    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(
            @NonNull Uri uri,
            @Nullable String[] projection,
            @Nullable String selection,
            @Nullable String[] selectionArgs,
            @Nullable String sortOrder) {
        SpendingDBHelper DBHelper = new SpendingDBHelper(getContext(),SpendingDBHelper.DB_NAME,null,SpendingDBHelper.DB_VERSION);
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        Cursor cursor;
/*
        switch (uriMatcher.match(uri)) {
            case 1:

                cursor = db.query(
                        "SPEND_TABLE", // Table to Query
                        columns,
                        columnsWhere, // Columns for the "where" clause
                        username, // Values for the "where" clause
                        null, // columns to group by
                        null, // columns to filter by row groups
                        null // sort order
                );
                break;

            case 2:

                break;

            case 3:

                break;

            case 4:

                break;

            case 5:

                break;

            case 6:

                break;

            case 7:

                break;

            default:
                return null;
                break;
        }*/
    return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
