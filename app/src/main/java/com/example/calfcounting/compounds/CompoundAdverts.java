package com.example.calfcounting.compounds;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calfcounting.DBHelper;
import com.example.calfcounting.MainActivity;
import com.example.calfcounting.R;
import com.example.calfcounting.orders.OrderList;

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
                        compound.setReviews_num(cursor.getInt(cursor.getColumnIndex(Compound.REVIEWS_NUM)));
                        compound.setUpload_advert_date(cursor.getString(cursor.getColumnIndex(Compound.UPLOAD_ADVERT_DATE)));
                        compound.setDescription(cursor.getString(cursor.getColumnIndex(Compound.DESCRIPTION)));
                        compound.setLocation(cursor.getString(cursor.getColumnIndex(Compound.LOCATION)));
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
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.button_home:
                Intent intent = new Intent(this, MainActivity.class);
                finish();
                startActivity(intent);
                break;
            case R.id.button_purchases:
                Intent intent1 = new Intent(this, OrderList.class);
                finish();
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
