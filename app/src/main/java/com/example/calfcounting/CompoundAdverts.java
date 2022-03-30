package com.example.calfcounting;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CompoundAdverts extends AppCompatActivity implements AdapterView.OnItemClickListener {

    TextView textViewDate;
    ListView listView;

    DBHelper dbHelper;
    SQLiteDatabase db;

    ArrayList<Compound> compoundArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compound_adverts);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Продавцы комбикормов");

        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        textViewDate = findViewById(R.id.textViewKombikormPricesDate);
        listView = findViewById(R.id.listViewKombikormPricesList);

        compoundArrayList = new ArrayList<>();

            //получаем дату последнего обновления
            dbHelper = new DBHelper(this);
            db = dbHelper.getWritableDatabase();
            Cursor cursor = db.query("COMPOUNDS", null, null, null, null, null, null);
            cursor.moveToLast(); //На последний


        String lastUpdateString= cursor.getString(
                   cursor.getColumnIndex(
                           Compound.CONNECTION_TIME));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date lastUpdate = null;
        try {
            lastUpdate = dateFormat.parse(
                        lastUpdateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (lastUpdate != null){
            textViewDate.setText(lastUpdateString); //TODO: нужен вывод времени
        }

            //Дергаем из БД последнее
            if (cursor.moveToFirst()){
                do{
                    Compound compound = new Compound();
                    compound.setConnection_time(
                            new Date(
                                    cursor.getLong(
                                            cursor.getColumnIndex(Compound.CONNECTION_TIME))));
                    if (compound.getConnection_time() != null){ //TODO: лучше отказаться

                        compound.setId(cursor.getLong(cursor.getColumnIndex(Compound.ID)));
                        compound.setName(cursor.getString(cursor.getColumnIndex(Compound.NAME)));
                        compound.setSeller(cursor.getString(cursor.getColumnIndex(Compound.SELLER)));
                        compound.setRating(cursor.getFloat(cursor.getColumnIndex(Compound.RATING)));
                        compound.setUpload_advert_date(cursor.getString(cursor.getColumnIndex(Compound.UPLOAD_ADVERT_DATE)));
                        compound.setDescription(cursor.getString(cursor.getColumnIndex(Compound.DESCRIPTION)));
                        compound.setLink_to_advert(cursor.getString(cursor.getColumnIndex(Compound.LINK_TO_ADVERT)));
                        compound.setPrice(cursor.getFloat(cursor.getColumnIndex(Compound.PRICE)));

                        compoundArrayList.add(compound);
                    }
                } while (cursor.moveToNext());
            }


        CompoundAdvertsArrayAdapter adapter = new CompoundAdvertsArrayAdapter(this, compoundArrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_compounds, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(this, CompoundInfo.class);
        int i = (int) id;

        intent.putExtra(Compound.class.getSimpleName(), (Parcelable) compoundArrayList.get(i));
        startActivity(intent);

//        Intent intent = new Intent(this, IllnessesInfo.class);
//        //нужно передать инфу по выбранному
//        //найдем нужное
//        int i = (int) id;
//
//        intent.putExtra("id", i);
//        intent.putExtra("name", illnessesArrayList.get(i).getName());
//        intent.putExtra("symptoms", illnessesArrayList.get(i).getSymptoms());
//        intent.putExtra("description", illnessesArrayList.get(i).getDescription());
//        intent.putExtra("treatment", illnessesArrayList.get(i).getTreatment());
//
//        startActivity(intent);
    }
}