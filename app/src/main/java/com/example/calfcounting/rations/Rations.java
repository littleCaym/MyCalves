package com.example.calfcounting.rations;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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

public class Rations extends AppCompatActivity implements View.OnClickListener, ListView.OnItemClickListener {

    DBHelper dbHelper; SQLiteDatabase db;

    private ArrayList<Ration> rationArrayList;
    private String stringList[];

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rations);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Рационы питания");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);



        dbHelper = new DBHelper(this);

        listView = findViewById(R.id.list_Rations);

        rationArrayList = new ArrayList<>();
        rationArrayList = readDB();

        stringList = new String[rationArrayList.size()];
        for (int i=0; i < rationArrayList.size(); ++i){ //???????? че с i?
            stringList[i] = "День " + rationArrayList.get(i).getStringContinue();
        }
        //делаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.animals_list_layout,
                R.id.textViewAnimalsName, stringList); //нужен массив строк
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, RationInfo.class);
        //мы тут ничего не сортировали по имени, так что подойдет и обычный position


        intent.putExtra("continue", rationArrayList.get(position).getStringContinue());
        intent.putExtra("prod1", rationArrayList.get(position).getProd1());
        intent.putExtra("prod1_portion", rationArrayList.get(position).getProd1_portion());
        intent.putExtra("prod2", rationArrayList.get(position).getProd2());
        intent.putExtra("prod2_portion", rationArrayList.get(position).getProd2_portion());
        intent.putExtra("prod3", rationArrayList.get(position).getProd3());
        intent.putExtra("prod3_portion", rationArrayList.get(position).getProd3_portion());
        intent.putExtra("prod4", rationArrayList.get(position).getProd4());
        intent.putExtra("prod4_portion", rationArrayList.get(position).getProd4_portion());
        intent.putExtra("prod5", rationArrayList.get(position).getProd5());
        intent.putExtra("prod5_portion", rationArrayList.get(position).getProd5_portion());
        intent.putExtra("prod6", rationArrayList.get(position).getProd6());
        intent.putExtra("prod6_portion", rationArrayList.get(position).getProd6_portion());
        intent.putExtra("prod7", rationArrayList.get(position).getProd7());
        intent.putExtra("prod7_portion", rationArrayList.get(position).getProd7_portion());

        startActivity(intent);
    }

    private ArrayList<Ration> readDB(){
        ArrayList<Ration> rationArrayList = new ArrayList<>();
        // создаем объект для данных
        ContentValues cv = new ContentValues();
        db = dbHelper.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT rations.continue FROM rations GROUP BY rations.continue ORDER BY rations.id",new String[]{});
        logCursor(c);
        if (c.moveToFirst()) {
            do {
                Ration r = new Ration();
                r.setStringContinue(c.getString(c.getColumnIndex("continue")));
                rationArrayList.add(r);
            } while (c.moveToNext());
        }

        Cursor c1 = db.rawQuery("SELECT myWareHouse.name, rations.prod1_portion FROM rations LEFT JOIN myWareHouse ON rations.prod1_id = myWareHouse.id GROUP BY rations.continue ORDER BY rations.id",new String[]{});
        logCursor(c1);
        int  i = 0;
        if (c1.moveToFirst()) {
            do {
                rationArrayList.get(i).setProd1(c1.getString(c1.getColumnIndex("name")));
                rationArrayList.get(i).setProd1_portion(c1.getInt(c1.getColumnIndex("prod1_portion")));
                i++;
            } while (c1.moveToNext());
        }

        Cursor c2 = db.rawQuery("SELECT myWareHouse.name, rations.prod2_portion FROM rations LEFT JOIN myWareHouse ON rations.prod2_id = myWareHouse.id GROUP BY rations.continue ORDER BY rations.id",new String[]{});
        logCursor(c2);
        i = 0;
        if (c2.moveToFirst()) {
            do {
                rationArrayList.get(i).setProd2(c2.getString(c2.getColumnIndex("name")));
                rationArrayList.get(i).setProd2_portion(c2.getInt(c2.getColumnIndex("prod2_portion")));
                i++;
            } while (c2.moveToNext());
        }

        Cursor c3 = db.rawQuery("SELECT myWareHouse.name, rations.prod3_portion FROM rations LEFT JOIN myWareHouse ON rations.prod3_id = myWareHouse.id GROUP BY rations.continue ORDER BY rations.id",new String[]{});
        logCursor(c3);
        i = 0;
        if (c3.moveToFirst()) {
            do {
                rationArrayList.get(i).setProd3(c3.getString(c3.getColumnIndex("name")));
                rationArrayList.get(i).setProd3_portion(c3.getInt(c3.getColumnIndex("prod3_portion")));
                i++;
            } while (c3.moveToNext());
        }

        Cursor c4 = db.rawQuery("SELECT myWareHouse.name, rations.prod4_portion FROM rations LEFT JOIN myWareHouse ON rations.prod4_id = myWareHouse.id GROUP BY rations.continue ORDER BY rations.id",new String[]{});
        logCursor(c4);
        i = 0;
        if (c4.moveToFirst()) {
            do {
                rationArrayList.get(i).setProd4(c4.getString(c4.getColumnIndex("name")));
                rationArrayList.get(i).setProd4_portion(c4.getInt(c4.getColumnIndex("prod4_portion")));
                i++;
            } while (c4.moveToNext());
        }

        Cursor c5 = db.rawQuery("SELECT myWareHouse.name, rations.prod5_portion FROM rations LEFT JOIN myWareHouse ON rations.prod5_id = myWareHouse.id GROUP BY rations.continue ORDER BY rations.id",new String[]{});
        logCursor(c5);
        i = 0;
        if (c5.moveToFirst()) {
            do {
                rationArrayList.get(i).setProd5(c5.getString(c5.getColumnIndex("name")));
                rationArrayList.get(i).setProd5_portion(c5.getInt(c5.getColumnIndex("prod5_portion")));
                i++;
            } while (c5.moveToNext());
        }

        Cursor c6 = db.rawQuery("SELECT myWareHouse.name, rations.prod6_portion FROM rations LEFT JOIN myWareHouse ON rations.prod6_id = myWareHouse.id GROUP BY rations.continue ORDER BY rations.id",new String[]{});
        logCursor(c6);
        i = 0;
        if (c6.moveToFirst()) {
            do {
                rationArrayList.get(i).setProd6(c6.getString(c6.getColumnIndex("name")));
                rationArrayList.get(i).setProd6_portion(c6.getInt(c6.getColumnIndex("prod6_portion")));
                i++;
            } while (c6.moveToNext());
        }

        Cursor c7 = db.rawQuery("SELECT myWareHouse.name, rations.prod7_portion FROM rations LEFT JOIN myWareHouse ON rations.prod7_id = myWareHouse.id GROUP BY rations.continue ORDER BY rations.id",new String[]{});
        logCursor(c7);
        i = 0;
        if (c7.moveToFirst()) {
            do {
                rationArrayList.get(i).setProd7(c7.getString(c7.getColumnIndex("name")));
                rationArrayList.get(i).setProd7_portion(c7.getInt(c7.getColumnIndex("prod7_portion")));
                i++;
            } while (c7.moveToNext());
        }

        return rationArrayList;

    }

    void logCursor(Cursor c) {
        if (c != null) {
            if (c.moveToFirst()) {
                String str;
                do {
                    str = "";
                    for (String cn : c.getColumnNames()) {
                        str = str.concat(cn + " = " + c.getString(c.getColumnIndex(cn)) + "; ");
                    }
                    Log.d("TAG", str);
                } while (c.moveToNext());
            }
        } else
            Log.d("TAG", "Cursor is null");
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
