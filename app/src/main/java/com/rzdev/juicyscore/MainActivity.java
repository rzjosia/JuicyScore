package com.rzdev.juicyscore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button mButtonNewScore = (Button) findViewById(R.id.button_new_score);
        mButtonNewScore.setOnClickListener(mButtonNewScoreClickListener);
    }

    private final View.OnClickListener mButtonNewScoreClickListener = view -> {
        Intent newScoreIntent = new Intent(MainActivity.this, NewScore.class);
        startActivity(newScoreIntent);
    };
}