package com.example.nasaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NASA extends AppCompatActivity {

    // Declarations
    private Button Fav_Button;
    LinearLayout mains;
    int counter = 0;
    protected static final String TAG = "NASA";
    public TextView Date;
    public TextView Long;
    public TextView Lat;
    public EditText Long_val;
    public EditText Lat_val;
    public EditText Date_val;
    public ImageView Location;
    public ProgressBar ProgB;
    public String URLL;
    public String l1;
    String texts;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nasa);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("NASA Search.V3");
        setSupportActionBar(toolbar);

        //URLL = "https://api.nasa.gov/planetary/earth/imagery?lon=" + Long_val.getText() + "&lat="+ Lat_val.getText() + "&date="+Date_val.getText()+"&api_key=DEMO_KEY";
        Long_val = findViewById(R.id.Long_Text);
        Lat_val = findViewById(R.id.Lat_Text);
        Date_val = findViewById(R.id.Date_Text);
        mains = findViewById(R.id.mains);
        ProgB = findViewById(R.id.ProgB);

        Date  = (TextView)findViewById(R.id.Date_val);
        Long  = (TextView)findViewById(R.id.Long_val);
        Lat  = (TextView)findViewById(R.id.Lat_val);
        Location  = (ImageView)findViewById(R.id.location);







        // When the 'Enter' button is clicked, Retrieve image
        ((Button) findViewById(R.id.Enter_Button)).setOnClickListener(clk -> {
            ProgB.setVisibility(View.VISIBLE);

            NASA.NasaQuery req = new NASA.NasaQuery();
            req.execute("https://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric");

        });

        // When the 'Favorites' button is clicked, launch the favorites activity
        ((Button) findViewById(R.id.Fav_Button)).setOnClickListener(clk -> {
            startActivity(new Intent(this, favs.class));
            Snackbar.make(mains, "Favorite Button Pressed", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            //addDataToDatabase( Long.getText(), Lat,Date);
        });

        // When the 'save' button is clicked, launch the snackbar and progressbar
        ((Button) findViewById(R.id.Save_Button)).setOnClickListener(clk -> {
            Long_val.getText().clear();
            Lat_val.setText("");
            Date_val.setText("");
            Log.d("NASA", "add to database");
            addDataToDatabase(URLL);
            Log.d("adapter", URLL);
            Snackbar.make(mains, "Save Button Pressed", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        });

    }



    /// inflate menu items for tool bar
    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.menu, m);
        return true;

    }
    /// Switch statement for when menu items are selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Context context = getApplicationContext();
        // menu selection
        switch (item.getItemId()) {
            case android.R.id.home:
                //openDrawer(drawerLayout);
                Log.d("home", "home selected");
            case R.id.action_one:
                Log.d("Toolbar", "item 1 selected");
                startActivity(new Intent(this, MainActivity.class));
                texts="You clicked on item 1";
                Toast.makeText(context, texts, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_two:
                Log.d("Toolbar", "item 2 selected");
                startActivity(new Intent(this, favs.class));
                texts="You clicked on item 2";
                Toast.makeText(context, texts, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_three:
                Log.d("Toolbar", "item 3 selected");
                startActivity(new Intent(this, NASA.class));
                texts="You clicked on item 3";
                Toast.makeText(context, texts, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_overflow:
                texts="You clicked on overflow";
                openDialog();
                Toast.makeText(context, texts, Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    // opens the alert dialog when called
    private void openDialog() {
        newDialogue newDialog = new newDialogue();
        newDialog.show(getSupportFragmentManager(),"Nasa activity");

    }


    ///Async Section///
    class NasaQuery extends AsyncTask<String, Integer, String> {

        private Bitmap NasaLoc;
        public String urlSave;

        @Override
        protected String doInBackground(String... args) {
            try {
                URL url = new URL(args[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream response = urlConnection.getInputStream();

                // Download image
                Log.i("NASA", "Getting.png");
                URLL = "https://api.nasa.gov/planetary/earth/imagery?lon=" + Long_val.getText() + "&lat="+ Lat_val.getText() + "&date="+Date_val.getText()+"&api_key=NC9eAVEGArcxezqkc9kLT0dnEQB183KFK1oQXL53";
                //url = new URL("https://api.nasa.gov/planetary/earth/imagery?lon=100.75&lat=1.5&date=2014-02-01&api_key=DEMO_KEY");
                url = new URL(URLL);

                Log.i("NASA", URLL);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == 200) {
                    NasaLoc = BitmapFactory.decodeStream(urlConnection.getInputStream());
                }
                if (fileExistance(".png")) {
                    //Bitmap object section
                    Log.i("NASA", "Downloading"  + ".png");
                    FileOutputStream outputStream = openFileOutput(".png", Context.MODE_PRIVATE); // Saving to local storage
                    NasaLoc.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                    outputStream.flush();
                    outputStream.close();
                } else {
                    Log.i("NASA", "Already exists, retrieving .png");
                    FileInputStream fis = null;
                    try {
                        fis = openFileInput(getBaseContext().getFileStreamPath( ".png").toString());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    NasaLoc = BitmapFactory.decodeStream(fis);
                }

            } catch (Exception e) {

            }

            return "Done";
        }
        // checks if file exists
        public boolean fileExistance(String fname) {
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }

        public void onProgressUpdate(Integer... args) {
            ProgB.setProgress(args[0]);
        }

        // set values of user input
        public void onPostExecute(String fromDoInBackground) {

            Long.setText("Longitude: " +Long_val.getText());
            Lat.setText("Latitude: "+Lat_val.getText());
            Date.setText("Date: "+Date_val.getText());
            Location.setImageBitmap(NasaLoc);
            ProgB.setVisibility(View.INVISIBLE);

        }
    }


    // add selected item to database
    private void addDataToDatabase(String urls){
        SQLiteManager  dbOpener = new SQLiteManager (this);
        SQLiteDatabase db = dbOpener.getWritableDatabase();
        ContentValues cValues = new ContentValues(2);
        cValues.put(SQLiteManager .URL_FIELD,urls);
        Log.d("add to database",urls);
        long newId = db.insert(SQLiteManager.TABLE_NAME, null, cValues);
        //messageList.add(new Message( message, sender,newId));

    }
}