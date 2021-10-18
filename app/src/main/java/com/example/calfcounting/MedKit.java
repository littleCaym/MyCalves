package com.example.calfcounting;

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

import java.util.ArrayList;

public class MedKit extends AppCompatActivity implements View.OnClickListener, ListView.OnItemClickListener {

    Button add_button;;
    ListView listView;

    DBHelper dbHelper; SQLiteDatabase db;

    private ArrayList<Medicine> medicinesArrayList;
    private String stringList[];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_kit);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Апетчка");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);




        dbHelper = new DBHelper(this);

        listView = findViewById(R.id.list_MyMedicines);
        //создаем массив классов Animal
        medicinesArrayList = new ArrayList<>();
        //Заполняем его
        medicinesArrayList = readDB();
        //теперь создаем массив строк размера массива классов
        stringList = new String[medicinesArrayList.size()];
        for (int i=0; i < medicinesArrayList.size(); ++i){ //???????? че с i?
            stringList[i] = medicinesArrayList.get(i).getName();
        }
        //делаем адаптер
        MedKitArrayAdapter adapter = new MedKitArrayAdapter(this, medicinesArrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        int i = (int) id;
        Intent intent = new Intent(this, MedicineInfo.class);

        intent.putExtra("illness", medicinesArrayList.get(i).getName());
        intent.putExtra("amount", medicinesArrayList.get(i).getAmount());
        intent.putExtra("price", medicinesArrayList.get(i).getPrice());

        startActivity(intent);
    }

    private ArrayList<Medicine> readDB() {

        ArrayList<Medicine> medicinesArrayList = new ArrayList<>();
        // создаем объект для данных
        ContentValues cv = new ContentValues();
        db = dbHelper.getWritableDatabase();

        Cursor c = db.query("aptechka", null, null, null, null, null, "illness");

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {
            int nameColIndex = c.getColumnIndex("illness");
            int priceColIndex = c.getColumnIndex("price");
            int amountColIndex = c.getColumnIndex("amount");
            int statusColIndex = c.getColumnIndex("status");

            do {
                Medicine m = new Medicine();
                m.setName(c.getString(nameColIndex));
                m.setPrice(c.getString(priceColIndex));
                m.setAmount(c.getFloat(amountColIndex));
                m.setStatus(c.getInt(statusColIndex));
                medicinesArrayList.add(m);

            } while (c.moveToNext());
        }
        c.close();

        return medicinesArrayList;
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
