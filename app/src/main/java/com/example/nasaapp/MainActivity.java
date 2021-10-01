package com.example.nasaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    // Declarations
    private EditText saveEmail;
    private Button login2;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT ="text";
    private String text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        saveEmail = (EditText) findViewById(R.id.saveEmail);
        login2 = (Button) findViewById(R.id.login2);


        // When the 'Login' button is clicked, Send to Nasa Activity
        login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveData();
                startActivity(new Intent(MainActivity.this,NASA.class));
            }
        });
        loadData();
        updateViews();
    }

    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(TEXT,saveEmail.getText().toString());

        editor.apply();

    }
    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        text = sharedPreferences.getString(TEXT,"");
    }

    public void updateViews(){
        saveEmail.setText(text);
    }


}