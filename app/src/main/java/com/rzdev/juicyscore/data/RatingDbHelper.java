package com.rzdev.juicyscore.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class RatingDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "rating.db";

    private static final int DATABASE_VERSION = 4;

    public RatingDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_RATING_TABLE = String.format("CREATE TABLE %1$s (" +
                        "%2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%3$s VARCHAR(255) NOT NULL, " +
                        "%4$s REAL NOT NULL, " +
                        "%5$s REAL NOT NULL, " +
                        "%6$s REAL NOT NULL, " +
                        "%7$s TEXT NOT NULL, " +
                        "%8$s DATETIME NOT NULL" +
                        ")",
                Rating.RatingEntry.TABLE_NAME,
                Rating.RatingEntry._ID,
                Rating.RatingEntry.COLUMN_RESTAURANT_NAME,
                Rating.RatingEntry.COLUMN_DECORATION_RATE,
                Rating.RatingEntry.COLUMN_FOOD_RATE,
                Rating.RatingEntry.COLUMN_SERVICE_RATE,
                Rating.RatingEntry.COLUMN_DESCRIPTION,
                Rating.RatingEntry.COLUMN_DATETIME
        );

        db.execSQL(SQL_CREATE_RATING_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Rating.RatingEntry.TABLE_NAME);
        onCreate(db);
    }
}
