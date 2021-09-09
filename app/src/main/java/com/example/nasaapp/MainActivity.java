package com.example.nasaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    // Declarations
    private Button Fav_Button;
    LinearLayout mains;
    ProgressBar ProgB;
    int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mains = findViewById(R.id.mains);
        ProgB = findViewById(R.id.ProgB);
        ProgB.setVisibility(View.INVISIBLE);

        // When the 'Favorites' button is clicked, launch the favorites activity
        ((Button) findViewById(R.id.Fav_Button)).setOnClickListener(clk -> {
            startActivity(new Intent(this, favs.class));
        });

        // When the 'save' button is clicked, launch the snackbar and progressbar
        ((Button) findViewById(R.id.Save_Button)).setOnClickListener(clk -> {
            Snackbar.make(mains, "This is a snackbar placeholder", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            Progrs();
            ProgB.setVisibility(View.VISIBLE);
        });

    }

    public void Progrs(){
        final Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                counter++;
                ProgB.setProgress(counter);
                if (counter ==10){
                    t.cancel();
                    ProgB.setVisibility(View.INVISIBLE);
                }
            }
        };
        t.schedule(tt,0,100);
    }
}