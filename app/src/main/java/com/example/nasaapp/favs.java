package com.example.nasaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class favs extends AppCompatActivity {
ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favs);

        listView = findViewById(R.id.Fav_List);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("placeholder#1");
        arrayList.add("placeholder#2");
        arrayList.add("placeholder#3");
        arrayList.add("placeholder#4");
        arrayList.add("placeholder#5");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Context context = getApplicationContext();
                Toast.makeText(context,"toast placeholder:"+position,Toast.LENGTH_SHORT).show();
            }
        });

        // When the 'Return' button is clicked, launch the Main activity
        ((Button) findViewById(R.id.Return_Button)).setOnClickListener(clk -> {
            startActivity(new Intent(this, MainActivity.class));
        });
    }
}