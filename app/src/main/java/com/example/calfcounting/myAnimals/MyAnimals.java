package com.example.calfcounting.myAnimals;

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
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.calfcounting.DBHelper;
import com.example.calfcounting.MainActivity;
import com.example.calfcounting.R;

import java.util.ArrayList;


//если edittext пустой - нужно, чтобы список был полным
public class MyAnimals extends AppCompatActivity implements View.OnClickListener,
        ListView.OnItemClickListener, ListView.OnItemLongClickListener, SearchView.OnQueryTextListener {

    SearchView searchView;
    Button add_button;
    ListView listView;

    DBHelper dbHelper;

    private ArrayList<Animal> animalArrayList;
    private String stringList[];

    int[] listToDelete; int id_listToDelete;


    Button button_delete;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_animals);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Мои животные");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        add_button = (Button) findViewById(R.id.button_Add);
        add_button.setOnClickListener(this);

        dbHelper = new DBHelper(this);

        listView = findViewById(R.id.list_MyAnimals);
        //создаем массив классов Animal


        animalArrayList = new ArrayList<>();
        //Заполняем его
        animalArrayList = readDB();

        //теперь создаем массив строк размера массива классов
        stringList = new String[animalArrayList.size()];
        for (int i=0; i < animalArrayList.size(); ++i){ //???????? че с i?
            stringList[i] = animalArrayList.get(i).getName();
        }
        //делаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.animals_list_layout,
                R.id.textViewAnimalsName, stringList); //нужен массив строк
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);

        //Надо создать список для удаления:
        listToDelete = new int[animalArrayList.size()];
        //listToDelete[0]=9000;

    }

    private ArrayList<Animal> readDB() {

        ArrayList<Animal> animalArrayList = new ArrayList<>();
        // создаем объект для данных
        db = dbHelper.getWritableDatabase();

        Cursor c = db.query("myAnimals", null, null, null, null, null, "name");

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {
            int nameColIndex = c.getColumnIndex("name");
            int birthColIndex = c.getColumnIndex("birth");
            int weightColIndex = c.getColumnIndex("weight");
            int statusColIndex = c.getColumnIndex("stat_zdor"); //int
            int illnessColIndex = c.getColumnIndex("id_illness");
            int dateStrartTreatColIndex = c.getColumnIndex("date_start_treat"); //long

            do {
                Animal a = new Animal();
                a.setName(c.getString(nameColIndex));
                a.setBirth(c.getLong(birthColIndex));
                a.setWeight(c.getFloat(weightColIndex));
                a.setStatus(c.getInt(statusColIndex));
                a.setIdIllness(c.getInt(illnessColIndex));
                a.setDateStartTreat(c.getLong(dateStrartTreatColIndex));

                animalArrayList.add(a);
            } while (c.moveToNext());
        }
        c.close();

        return animalArrayList;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_Add:
                //нужен новый action
                Intent intent = new Intent(this, Animal.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(this, AnimalInfo.class);
        //нужно передать инфу по выбранному
        //найдем нужное
        int i = (int) id;

        intent.putExtra("id", i);
        intent.putExtra("name", animalArrayList.get(i).getName());
        intent.putExtra("birth", animalArrayList.get(i).getBirth());
        intent.putExtra("weight", animalArrayList.get(i).getWeight());
        intent.putExtra("stat_zdor", animalArrayList.get(i).getStatus());
        intent.putExtra("id_illness", animalArrayList.get(i).getIdIllness());
        intent.putExtra("date_start_treat", animalArrayList.get(i).getDateStartTreat());
        startActivity(intent);

    }

    @Override
    public boolean onItemLongClick(final AdapterView<?> parent, View view, int position, long id) {
        //Сделать тост с предложением удаления
        Intent intent = getIntent();

        DeleteAnimal alert = new DeleteAnimal(animalArrayList.get(position), dbHelper, intent);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        alert.show(transaction,"dialog");


        return true;
    }



    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

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
