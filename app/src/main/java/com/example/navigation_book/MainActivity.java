package com.example.navigation_book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> names =new ArrayList<>();
    static  ArrayList<LatLng> locations=new ArrayList<>();
    static ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView=findViewById(R.id.listView);
        try {
            MapsActivity.database=this.openOrCreateDatabase("Places",MODE_PRIVATE,null);
            Cursor cursor=MapsActivity.database.rawQuery("SELECT * FROM Places",null);
            int nameIx=cursor.getColumnIndex("name");
            int latitudeIx=cursor.getColumnIndex("latitude");
            int longitudeIx=cursor.getColumnIndex("longitude");

            while(cursor.moveToNext()){
                String nameFromeDatabase=cursor.getString(nameIx);
                String latitudeFromDatabase=cursor.getString(latitudeIx);
                String longitudeFromDatabase=cursor.getString(longitudeIx);
                names.add(nameFromeDatabase);
                Double l1=Double.parseDouble(latitudeFromDatabase);
                Double l2=Double.parseDouble(longitudeFromDatabase);
                LatLng locationFromDatabase=new LatLng(l1,l2);
                locations.add(locationFromDatabase);
            }
            cursor.close();

        }catch (Exception e){

        }
        arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,names);
        listView.setAdapter(arrayAdapter);listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =new Intent(getApplicationContext(),MapsActivity.class);
                intent.putExtra("info","old");
                intent.putExtra("int i",i);

                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.add_place,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.add_place){
            Intent intent=new Intent(getApplicationContext(),MapsActivity.class);
            intent.putExtra("info","new");
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}