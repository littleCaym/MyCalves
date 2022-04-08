package com.example.calfcounting.compounds;

import android.annotation.SuppressLint;
import android.content.Context;
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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

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

        //
        compoundArrayList = getCompoundArrayListFromDB(this, Compound.PRICE);


        CompoundAdvertsArrayAdapter adapter = new CompoundAdvertsArrayAdapter(this, compoundArrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }


    @SuppressLint({"Range", "SetTextI18n"})
    public ArrayList<Compound> getCompoundArrayListFromDB(Context context, String orderBy){
        compoundArrayList = new ArrayList<>();

        //получаем дату последнего обновления
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("COMPOUNDS", null, null, null, null, null, orderBy);
        cursor.moveToLast(); //На последний


//        String lastUpdateString= cursor.getString(
//                cursor.getColumnIndex(
//                        Compound.CONNECTION_TIME));
        Calendar lastUpdateCalendar = new GregorianCalendar();
        lastUpdateCalendar.setTimeInMillis(
                cursor.getLong(cursor.getColumnIndex(Compound.CONNECTION_TIME))
        );
        textViewDate.setText(
                lastUpdateCalendar.get(Calendar.DAY_OF_MONTH) + "."
                + lastUpdateCalendar.get(Calendar.MONTH) + "."
                + lastUpdateCalendar.get(Calendar.YEAR) + " "
                +lastUpdateCalendar.get(Calendar.HOUR_OF_DAY) + ":"
                + lastUpdateCalendar.get(Calendar.MINUTE)
        );

         //TODO: нужен вывод времени

        //Дергаем из БД последнее
        if (cursor.moveToFirst()){
            do{
                Compound compound = new Compound();
                compound.setConnection_time(
                        new Timestamp(
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
        return compoundArrayList;
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


    /*
    Отселживание статуса:

    WorkManager.getInstance().getStatusById(myWorkRequest.getId()).observe(this, new Observer<WorkStatus>() {
   @Override
   public void onChanged(@Nullable WorkStatus workStatus) {
       Log.d(TAG, "onChanged: " + workStatus.getState());
   }
});
     */
}
