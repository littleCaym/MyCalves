package com.example.calfcounting.orders;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calfcounting.DBHelper;
import com.example.calfcounting.MainActivity;
import com.example.calfcounting.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OrderList extends AppCompatActivity implements AdapterView.OnItemClickListener{

    ListView listView;

    DBHelper dbHelper;
    SQLiteDatabase db;

    ArrayList<Order> orderArrayList;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Мои заказы");

        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        listView = findViewById(R.id.listViewOrderList);

        orderArrayList = new ArrayList<>();

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("ORDERS", null, null, null, null, null, null);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if (cursor.moveToFirst()){
            do {
                Order order = new Order();
                order.setId(cursor.getLong(cursor.getColumnIndex(Order.ID)));
                order.setName(cursor.getString(cursor.getColumnIndex(Order.NAME)));
                order.setSeller(cursor.getString(cursor.getColumnIndex(Order.SELLER)));
                order.setRating(cursor.getFloat(cursor.getColumnIndex(Order.RATING)));
                order.setReviews_num(cursor.getInt(cursor.getColumnIndex(Order.REVIEWS_NUM)));
                order.setUpload_advert_date(cursor.getString(cursor.getColumnIndex(Order.UPLOAD_ADVERT_DATE)));
                order.setDescription(cursor.getString(cursor.getColumnIndex(Order.DESCRIPTION)));
                order.setLocation(cursor.getString(cursor.getColumnIndex(Order.LOCATION)));
                order.setLink_to_advert(cursor.getString(cursor.getColumnIndex(Order.LINK_TO_ADVERT)));
                order.setPrice(cursor.getFloat(cursor.getColumnIndex(Order.PRICE)));
                //TODO исправь здесь ошибку
                order.setDate_added(new Date(
                        cursor.getLong(
                                cursor.getColumnIndex(
                                        Order.DATE_ADDED
                                ))));
                order.setAmount(cursor.getFloat(cursor.getColumnIndex(Order.AMOUNT)));
                //TODO исправь здесь ошибку
                order.setDate_of_arrival(new Date(
                        cursor.getLong(
                                cursor.getColumnIndex(
                                        Order.DATE_OF_ARRIVAL
                                ))));
                order.setStatus(cursor.getInt(cursor.getColumnIndex(Order.STATUS)));

                orderArrayList.add(order);
            } while (cursor.moveToNext());
        }

        OrderListArrayAdapter adapter = new OrderListArrayAdapter(this, orderArrayList);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        System.out.println("onItemClick");
        Intent intent = new Intent(this, OrderInfo.class);
        int i = (int) id;

        intent.putExtra(Order.class.getSimpleName(), orderArrayList.get(i));
        startActivity(intent);
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
