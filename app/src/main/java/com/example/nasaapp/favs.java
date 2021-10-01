package com.example.nasaapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toolbar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class favs extends AppCompatActivity {
    ListView listView;
    SQLiteDatabase db;
    String texts;
    public ProgressBar progressBar;
   // DrawerLayout drawerLayout;
   ArrayList<Images> ImageList = new ArrayList<>();
    private imageAdapter imgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favs);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Favorite Images.V3");
        setSupportActionBar(toolbar);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        imgAdapter =  new imageAdapter();

        listView = findViewById(R.id.Fav_List);
        listView.setAdapter(imgAdapter);










        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Delete this?")
                    .setMessage("Are you sure?")
                    .setPositiveButton("OK", (d, which) -> {

                        deleteDataFromDatabase(position, id);
                    })
                    .setNegativeButton("No", null)
                    .create();

            dialog.show();
            return true;
        });

        // When the 'Return' button is clicked, launch the Main activity
        ((Button) findViewById(R.id.Return_Button)).setOnClickListener(clk -> {
            startActivity(new Intent(this, NASA.class));
        });
        // When the 'help' button is clicked, launch the AlertDialog
        ((Button) findViewById(R.id.Help_Button)).setOnClickListener(clk -> {
            openDialog();
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
                texts="You clicked on item 2";
                startActivity(new Intent(this, favs.class));
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
        Dialoge newDialog = new Dialoge();
        newDialog.show(getSupportFragmentManager(),"Favs images");

    }
    ///
    /**
     * This ChatAdapter is what provides the content for our ListView
     */
    private class imageAdapter extends BaseAdapter {
        public imageAdapter (){
            Log.d("adapter", "calling http");

            NasaQuery req = new NasaQuery();
            req.execute("https://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric");
        }

        @Override
        public int getCount() {
            return ImageList.size();
        }

        @Override
        public Object getItem(int position) {
            return ImageList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            Images img = ImageList.get(position);
            Log.d("adapter view", "calling view");
            View view;
            view = inflater.inflate(R.layout.images,parent,false);


            // Populate the image in the images template
            ((ImageView) view.findViewById(R.id.receiveImage)).setImageBitmap(img.picture);
            Log.d("adapter view", "return view");
            return view;
        }
    }




    //


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

                SQLiteManager dbOpener = new SQLiteManager(favs.this);
                SQLiteDatabase db = dbOpener.getReadableDatabase();
                Cursor count = db.rawQuery("select count(*) from " + SQLiteManager.TABLE_NAME, null);
                count.moveToFirst();

                Cursor C = db.rawQuery("select * from " + SQLiteManager.TABLE_NAME, null);
                int urls = C.getColumnIndex(SQLiteManager.URL_FIELD);
                C.moveToFirst();
                String URLS="";



                Log.d("forllopo", String.valueOf(count.getInt(0)));


                for (int i = 0; i< count.getInt(0);i++){
                    Log.d("forw", "im in for loop");

                    URLS= C.getString(urls);
                    Log.d("url is",URLS);

                    url = new URL(URLS);
                    Log.d("im here","1");
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.connect();
                    int responseCode = urlConnection.getResponseCode();
                    Log.d("im here","2");
                    if (responseCode == 200) {
                        Log.d("im here","3");
                        NasaLoc = BitmapFactory.decodeStream(urlConnection.getInputStream());
                        ImageList.add(new Images(NasaLoc));
                    }
                    Log.d("im here","4");
                    C.moveToNext();
                    Log.d("c", "ic next");
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

        @Override
        public void onProgressUpdate(Integer... args) {
            progressBar.setProgress(args[0]);
        }

        @Override
        public void onPostExecute(String fromDoInBackground) {

            imgAdapter.notifyDataSetChanged();
            progressBar.setVisibility(View.INVISIBLE);

        }
    }
    private void deleteDataFromDatabase(int position, long id){
        SQLiteManager dbOpener = new SQLiteManager(this);
        SQLiteDatabase db = dbOpener.getWritableDatabase();

        db.delete(SQLiteManager.TABLE_NAME, SQLiteManager.ID_FIELD + " = ?", new String[] {Long.toString(id)});
        ImageList.remove(position);
        imgAdapter.notifyDataSetChanged();

    }

    // image class
    public static class Images {

        private Bitmap picture;
        private Images (Bitmap picture){
            this.picture=picture;
        }
    }
}