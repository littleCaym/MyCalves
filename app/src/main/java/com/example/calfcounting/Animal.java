package com.example.calfcounting;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

public class Animal extends AppCompatActivity implements View.OnClickListener, CalendarView.OnDateChangeListener {

    private String name;
    private long birth;
    private float weight;
    private int status;
    private int idIllness;
    private long dateStartTreat;

    EditText editName;
    CalendarView calendarDate;
    EditText editWeight;
    Button buttonAdd;
    Button buttonCancel;

    DBHelper dbHelper;

    final Calendar calendar = Calendar.getInstance();


    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setBirth(long dayOfBirth) {
        this.birth = dayOfBirth;
    }
    public long getBirth() {
        return birth;
    }
    public void setWeight(float weight) {
        this.weight = weight;
    }
    public float getWeight() {
        return weight;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public int getStatus() {
        return status;
    }
    public void setIdIllness(int idIllness) {
        this.idIllness = idIllness;
    }
    public int getIdIllness() {
        return idIllness;
    }
    public void setDateStartTreat(long dateStartTreat) {
        this.dateStartTreat = dateStartTreat;
    }
    public long getDateStartTreat() {
        return dateStartTreat;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Добавление животного");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        editName = (EditText) findViewById(R.id.editTextName);

        calendarDate = (CalendarView) findViewById(R.id.calendarViewBirthDate);
        calendarDate.setOnDateChangeListener(this);
        calendarDate.setDate(new Date().getTime());

        editWeight = (EditText) findViewById(R.id.editTextWeight);

        buttonCancel = (Button) findViewById(R.id.buttonAnimalCancel);
        buttonCancel.setOnClickListener(this);
        buttonAdd = (Button) findViewById(R.id.buttonAnimalAdd);
        buttonAdd.setOnClickListener(this);

        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonAnimalAdd:
                ContentValues cv = new ContentValues();
                SQLiteDatabase db = dbHelper.getWritableDatabase();

              //  Привязку дня делать не нужно!!!!!!!!!

                cv.put("name", editName.getText().toString());
                //на вход поступает кол-во дней с момента рождения
                cv.put("birth", calendar.getTimeInMillis());

                cv.put("weight", editWeight.getText().toString());

                cv.put("stat_zdor", 0);
                // вставляем запись и получаем ее ID
                db.insert("myAnimals", null, cv);

                Toast.makeText(this, "Теленок "+editName.getText()+" появится в дневном обходе со следующего дня.",
                        Toast.LENGTH_LONG).show();
                break;
            case R.id.buttonAnimalCancel:
                break;
        }

        Intent intent = new Intent(this, MyAnimals.class);
        startActivity(intent);

    }

    public void bindRation(long dateBirth){

        /*
        //высчитываем количество дней, прошедших со дня рождения
        Date currDate = new Date();
        long currTime = currDate.getTime();
        int whatTheDayFromBirth = (int)( (currTime - dateBirth) / (1000*60*60*24) ) +1; //+1 так как 0 - это 1-й день

        //Выясним, какому периоду соответсвует текущий возраст бычка
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("rations", null,
                "(startDay >= ?) AND (? <= endDay)",
                new String[]{String.valueOf(whatTheDayFromBirth), String.valueOf(whatTheDayFromBirth)},
                null, null, null);

        if (cursor.moveToFirst())
            idRation = cursor.getInt(cursor.getColumnIndex("id"));
        else
            idRation = 1000;

        cursor.close();
        //теперь можно добавить id рациона в таблицу Бычков в функции onClick
        */
    }

    @Override
    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
        calendar.set(year, month, dayOfMonth);
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
