package com.example.calfcounting.illnesses;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calfcounting.DBHelper;
import com.example.calfcounting.MainActivity;
import com.example.calfcounting.R;

import java.util.ArrayList;

public class Illnesses extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    ListView listView;

    private ArrayList<Illness> illnessesArrayList;
    private String stringList[];

    DBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_illnesses);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Справочник болезней");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        dbHelper = new DBHelper(this);

        listView = (ListView) findViewById(R.id.list_Illnesses);

        illnessesArrayList = new ArrayList<>();
        illnessesArrayList = readDB();

        stringList = new String[illnessesArrayList.size()];
        for (int i=0; i < illnessesArrayList.size(); ++i){ //???????? че с i?
            stringList[i] = illnessesArrayList.get(i).getName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.animals_list_layout,
                R.id.textViewAnimalsName, stringList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
    }

    private ArrayList<Illness> readDB(){
        ArrayList<Illness> illnessArrayList = new ArrayList<>();
        db = dbHelper.getWritableDatabase();

        Cursor c = db.query("illnesses", null, null, null, null, null, "name");

        if (c.moveToFirst()) {
            int nameColIndex = c.getColumnIndex("name");
            int symptomsColIndex = c.getColumnIndex("symptoms");
            int descriptionColIndex = c.getColumnIndex("description");
            int treatmentColIndex = c.getColumnIndex("treatment");

            do {
                Illness i = new Illness();

                i.setName(c.getString(nameColIndex));
                i.setSymptoms(c.getString(symptomsColIndex));
                i.setDescription(c.getString(descriptionColIndex));
                i.setTreatment(c.getString(treatmentColIndex));

                illnessArrayList.add(i);
            } while (c.moveToNext());
        }
        c.close();

        return illnessArrayList;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(this, IllnessesInfo.class);
        //нужно передать инфу по выбранному
        //найдем нужное
        int i = (int) id;

        intent.putExtra("id", i);
        intent.putExtra("name", illnessesArrayList.get(i).getName());
        intent.putExtra("symptoms", illnessesArrayList.get(i).getSymptoms());
        intent.putExtra("description", illnessesArrayList.get(i).getDescription());
        intent.putExtra("treatment", illnessesArrayList.get(i).getTreatment());

        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
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
