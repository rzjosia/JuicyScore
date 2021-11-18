package com.rzdev.juicyscore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.rzdev.juicyscore.data.Rating;
import com.rzdev.juicyscore.data.RatingDbHelper;

public class MainActivity extends AppCompatActivity {
    private RatingListAdapter mRatingListAdapter;
    private RecyclerView mRecyclerView;
    private int mPosition = RecyclerView.NO_POSITION;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button mButtonNewScore = (Button) findViewById(R.id.button_new_score);
        mButtonNewScore.setOnClickListener(mButtonNewScoreClickListener);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_rating);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        RatingDbHelper dbHelper = new RatingDbHelper(this);
        mDb = dbHelper.getWritableDatabase();
        Cursor cursor = getAllRating();
        Log.e("ItemCount", String.valueOf(cursor.getCount()));

        mRatingListAdapter = new RatingListAdapter(this, cursor);
        mRecyclerView.setAdapter(mRatingListAdapter);
    }

    private final View.OnClickListener mButtonNewScoreClickListener = view -> {
        Intent newScoreIntent = new Intent(MainActivity.this, NewScore.class);
        startActivity(newScoreIntent);
    };

    private Cursor getAllRating() {
        return mDb.query(
                Rating.RatingEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }
}