package com.example.calfcounting.myAnimals;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calfcounting.DBHelper;
import com.example.calfcounting.MainActivity;
import com.example.calfcounting.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AnimalInfo extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

    //TODO: Создать кнопку на календарь
    EditText editTextName;
    TextView textViewBirth;
    EditText editTextWeight;
    Switch switchIllness;
    Spinner spinnerIllnesses;
    TextView textViewDateTreat;
    Button buttonCancel;
    Button buttonSave;

    LinearLayout linearLayoutInvis;

    DBHelper dbHelper;
    SQLiteDatabase db;

    int stat_zdor;
    long birth;
    long dateTreat;
    int illness_id;

    String prev_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_info);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Информация о животном");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        dbHelper = new DBHelper(this);
        ContentValues cv = new ContentValues();
        db = dbHelper.getWritableDatabase();

        editTextName = (EditText) findViewById(R.id.editTextAnimalInfoName);
        textViewBirth = (TextView) findViewById(R.id.textViewAnimalInfoBirth);
        editTextWeight = (EditText) findViewById(R.id.editTextAnimalInfoWeight);
        textViewDateTreat = (TextView) findViewById(R.id.textViewAnimalInfoDateTreat);
        buttonCancel = (Button) findViewById(R.id.buttonAnimalinfoCancel);
        buttonCancel.setOnClickListener(this);
        buttonSave = (Button) findViewById(R.id.buttonAnimalInfoSave);
        buttonSave.setOnClickListener(this);

        //скрываем начало болезни
        linearLayoutInvis = (LinearLayout) findViewById(R.id.linearAnimalInfoInvis);
        linearLayoutInvis.setVisibility(View.INVISIBLE);

        spinnerIllnesses = (Spinner) findViewById(R.id.spinnerAnimalInfo);
        spinnerIllnesses.setVisibility(View.INVISIBLE);
        adaptSpinner();
        spinnerIllnesses.setOnItemSelectedListener(this);

        switchIllness = (Switch) findViewById(R.id.switchAnimalInfo);
        if (switchIllness != null) {
            switchIllness.setOnCheckedChangeListener(this);
        }

        Intent intent = getIntent();
        prev_name = intent.getStringExtra("name");
        editTextName.setText(prev_name);
        this.birth = intent.getLongExtra("birth", 0);
        long date =new Date().getTime();
        textViewBirth.setText((String.valueOf( (date - birth)  / (1000*60*60*24)) ) + " дней");
        editTextWeight.setText(String.valueOf(intent.getFloatExtra("weight", 0)));

        //статус болезни
        int status = intent.getIntExtra("stat_zdor",0);
        if (status == 0)
            switchIllness.setChecked(false);
        if (status == 1)
            switchIllness.setChecked(true);

        //инфа по болезням
        if (intent.getIntExtra("id_illness", 0) != 0)
            spinnerIllnesses.setSelection( intent.getIntExtra("id_illness", 0) -1 );

        //дата начала болезни
        dateTreat = intent.getLongExtra("date_start_treat",0);
        if (dateTreat != 0){
            String stringShowDate = new SimpleDateFormat("d MM yyyy", Locale.getDefault()).format(dateTreat);
            textViewDateTreat.setText(stringShowDate);
        }
        else {
            String stringShowDate = new SimpleDateFormat("d MM yyyy", Locale.getDefault()).format(new Date().getTime());
        }

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MyAnimals.class);

        switch (v.getId()){
            case R.id.spinnerAnimalInfo:
                break;
            case R.id.buttonAnimalinfoCancel:
                startActivity(intent);
                break;
            case R.id.buttonAnimalInfoSave:
                ContentValues cv = new ContentValues();
                db = dbHelper.getWritableDatabase();

                cv.put("name", editTextName.getText().toString());
                cv.put("birth", birth);
                cv.put("weight", Float.parseFloat(editTextWeight.getText().toString()));
                cv.put("stat_zdor", stat_zdor);
                cv.put("id_illness", illness_id);
                cv.put("date_start_treat", dateTreat);

                db.update("myAnimals", cv, "name = ?", new String[]{prev_name});


                startActivity(intent);
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        ContentValues cv = new ContentValues();
        db = dbHelper.getWritableDatabase();

        if (isChecked){
            stat_zdor = 1;
            //откроем скрытый лэйаут
            linearLayoutInvis.setVisibility(View.VISIBLE);
            spinnerIllnesses.setVisibility(View.VISIBLE);

            dateTreat = new Date().getTime();
            String stringShowDate = new SimpleDateFormat("d MM yyyy", Locale.getDefault()).format(dateTreat);
            textViewDateTreat.setText(stringShowDate);

            //Установим в БД значение статуса болезни
            cv.put("stat_zdor", 1);
            cv.put("date_start_treat", new Date().getTime());
            db.update("myAnimals", cv, "name = ?", new String[]{prev_name});
        }
        else{
            stat_zdor = 0;
            linearLayoutInvis.setVisibility(View.INVISIBLE);
            spinnerIllnesses.setVisibility(View.INVISIBLE);

            cv.put("stat_zdor", 0);
            db.update("myAnimals", cv, "name = ?", new String[]{prev_name});
        }
    }

    public void adaptSpinner(){
        //Получим список болезней
        ArrayList<String> arrayList = new ArrayList<>();

        Cursor cursor = db.query("illnesses", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int nameColIndex = cursor.getColumnIndex("name");
            do {
                arrayList.add(cursor.getString(nameColIndex));
            } while (cursor.moveToNext());
        }
        cursor.close();

        String[] strings = new String[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++){
            strings[i] = arrayList.get(i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, strings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIllnesses.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        illness_id = position+1;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
    @Override
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

