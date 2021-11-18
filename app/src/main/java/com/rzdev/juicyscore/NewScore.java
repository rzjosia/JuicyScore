package com.rzdev.juicyscore;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NewScore extends AppCompatActivity {
    final Calendar calendar = Calendar.getInstance();
    EditText mInputDate, mInputTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_score);

        mInputDate = (EditText) findViewById(R.id.input_new_score_date);
        mInputTime = (EditText) findViewById(R.id.input_new_score_time);

        updateInputDate();
        updateInputTime();

        mInputDate.setOnClickListener(mInputDateOnClickListener);
        mInputTime.setOnClickListener(mInputTimeOnClickListener);
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
         public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateInputDate();
        };
    };

    TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            updateInputTime();
        }
    };

    View.OnClickListener mInputDateOnClickListener = new View.OnClickListener() {

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

    View.OnClickListener mInputTimeOnClickListener = new View.OnClickListener() {

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
}