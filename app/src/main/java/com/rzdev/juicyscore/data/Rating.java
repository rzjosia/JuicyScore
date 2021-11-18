package com.rzdev.juicyscore.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class Rating {
    public static final String CONTENT_AUTHORITY = "com.rzdev.android.juicyscore";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_RATING = "rating";

    public static final class RatingEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI
                .buildUpon()
                .appendPath(PATH_RATING)
                .build();

        public static final String TABLE_NAME = "rating";
        public static final String COLUMN_RESTAURANT_NAME = "restaurant_name";
        public static final String COLUMN_DATETIME = "datetime";
        public static final String COLUMN_DECORATION_RATE = "decoration_rate";
        public static final String COLUMN_FOOD_RATE = "food_rate";
        public static final String COLUMN_SERVICE_RATE = "service_rate";
        public static final String COLUMN_DESCRIPTION = "description";

        public static Uri buildRatingUriWithDate(long date) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Long.toString(date))
                    .build();
        }
    }
}
