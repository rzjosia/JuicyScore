package com.rzdev.juicyscore;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rzdev.juicyscore.data.Rating;
import com.rzdev.juicyscore.data.RatingDbHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NewScore extends AppCompatActivity {
    final Calendar calendar = Calendar.getInstance();

    RatingBar mRatingDecoration, mRatingService, mRatingFood;
    EditText mInputDate, mInputTime, mInputRestaurantName, mInputDescription;
    Button mButtonSave;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_score);

        RatingDbHelper ratingDbHelper = new RatingDbHelper(this);
        database = ratingDbHelper.getWritableDatabase();

        mInputDate = (EditText) findViewById(R.id.input_new_score_date);
        mInputTime = (EditText) findViewById(R.id.input_new_score_time);
        mInputRestaurantName = (EditText) findViewById(R.id.input_restaurant_name);
        mRatingDecoration = (RatingBar) findViewById(R.id.input_decoration_rating);
        mRatingService = (RatingBar) findViewById(R.id.input_service_rating);
        mRatingFood = (RatingBar) findViewById(R.id.input_food_rating);
        mInputDescription = (EditText) findViewById(R.id.input_new_score_description);
        mButtonSave = (Button) findViewById(R.id.button_save_new_score);

        updateInputDate();
        updateInputTime();
        mInputDate.setOnClickListener(mInputDateOnClickListener);
        mInputTime.setOnClickListener(mInputTimeOnClickListener);
        mButtonSave.setOnClickListener(mButtonSaveOnClickListener);
    }


    private final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            updateInputTime();
        }
    };

    private final View.OnClickListener mInputDateOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            new DatePickerDialog(
                    NewScore.this,
                    date,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            ).show();
        }
    };

    private final View.OnClickListener mInputTimeOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            new TimePickerDialog(
                    NewScore.this,
                    time,
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
            ).show();
        }
    };

    private final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateInputDate();
        }

    };

    private final View.OnClickListener mButtonSaveOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            long ratingId = addNewRating(
                    mInputRestaurantName.getText().toString(),
                    calendar.getTimeInMillis(),
                    mRatingDecoration.getRating(),
                    mRatingFood.getRating(),
                    mRatingService.getRating(),
                    mInputDescription.getText().toString()
            );

            CharSequence message = "Données sauvegardées : " + ratingId;

            if (ratingId < 0) {
                message = "Vos données n'ont pas pu être sauvegardées";
                Log.e("Erreur SQL", mInputRestaurantName.getText().toString() + " " +
                                calendar.getTimeInMillis() + " " +
                                mRatingDecoration.getRating() + " " +
                                mRatingFood.getRating() + " " +
                        mRatingService.getRating());
            }

            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }
    };

    private void updateInputDate() {
        String dateFormat = "EEEE dd MMMM yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.FRANCE);
        mInputDate.setText(simpleDateFormat.format(calendar.getTime()));
    }

    private void updateInputTime() {
        String timeFormat = "kk:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timeFormat, Locale.FRANCE);
        mInputTime.setText(simpleDateFormat.format(calendar.getTime()));
    }

    private ContentValues setScoreContentValues(String restaurantName, Long timestamp,
                                                float decorationRate, float foodRate,
                                                float serviceRate, String description) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(Rating.RatingEntry.COLUMN_RESTAURANT_NAME, restaurantName);
        contentValues.put(Rating.RatingEntry.COLUMN_DATETIME, timestamp);
        contentValues.put(Rating.RatingEntry.COLUMN_DECORATION_RATE, decorationRate);
        contentValues.put(Rating.RatingEntry.COLUMN_FOOD_RATE, foodRate);
        contentValues.put(Rating.RatingEntry.COLUMN_SERVICE_RATE, serviceRate);
        contentValues.put(Rating.RatingEntry.COLUMN_DESCRIPTION, description);
        
        return contentValues;
    }

    private long addNewRating(String restaurantName, Long timestamp, float decorationRate,
                              float foodRate, float serviceRate, String description) {

        ContentValues contentValues = setScoreContentValues(
                restaurantName, timestamp, decorationRate, foodRate, serviceRate, description);

        return database.insert(Rating.RatingEntry.TABLE_NAME, null, contentValues);
    }
}