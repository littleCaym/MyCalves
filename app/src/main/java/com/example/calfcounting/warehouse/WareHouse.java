package com.example.calfcounting.warehouse;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calfcounting.DBHelper;
import com.example.calfcounting.MainActivity;
import com.example.calfcounting.R;

import java.util.ArrayList;

public class WareHouse extends AppCompatActivity implements View.OnClickListener, ListView.OnItemClickListener,
ListView.OnItemLongClickListener{

    Button add_button;
    ListView listView;

    private ArrayList<Food> foodArrayList;
    private String stringList[];

    SQLiteDatabase db;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ware_house);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Склад");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        dbHelper = new DBHelper(this);

        listView = findViewById(R.id.list_MyFood);
        //создаем массив классов Animal
        foodArrayList = new ArrayList<>();
        //Заполняем его
        foodArrayList = readDB();

        //теперь создаем массив строк размера массива классов
        stringList = new String[foodArrayList.size()];
        for (int i=0; i < foodArrayList.size(); ++i){ //???????? че с i?
            stringList[i] = foodArrayList.get(i).getName();
        }
        //делаем адаптер
        WareHouseArrayAdapter adapter = new WareHouseArrayAdapter(this, foodArrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
    }

    @Override
    public void onClick(View v) {


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int i = (int) id;

        Intent intent = new Intent(this, FoodInfo.class);

        intent.putExtra("position", position);
        intent.putExtra("name", foodArrayList.get(i).getName());
        intent.putExtra("price", foodArrayList.get(i).getPrice());
        intent.putExtra("amount", foodArrayList.get(i).getAmount());

        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        return false;
    }


    private ArrayList<Food> readDB(){
        ArrayList<Food> foodArrayList = new ArrayList<>();
        ContentValues cv = new ContentValues();
        db = dbHelper.getWritableDatabase();

        Cursor c = db.query("myWareHouse",null, null,null,null,null,"name");


        if(c.moveToFirst()){
            int nameColIndex = c.getColumnIndex("name");
            int priceColIndex = c.getColumnIndex("price");
            int amountColIndex = c.getColumnIndex("amount");
            int statusColIndex = c.getColumnIndex("status");

            do{
               Food f = new Food();
               f.setName(c.getString(nameColIndex));
               f.setPrice(c.getString(priceColIndex));
               f.setAmount(c.getInt(amountColIndex));
               f.setStatus(c.getInt(statusColIndex));
               foodArrayList.add(f);

            }while (c.moveToNext());
        }
        c.close();

        return foodArrayList;
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.button_home:
                Intent intent = new Intent(this, MainActivity.class);
                finish();
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
