package com.example.calfcounting.warehouse;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calfcounting.DBHelper;
import com.example.calfcounting.MainActivity;
import com.example.calfcounting.R;


public class FoodInfo extends AppCompatActivity implements View.OnClickListener {

    TextView textViewName;
    EditText editTextPrice;
    TextView textViewAmount;

    ImageButton imageButtonMinus;
    ImageButton imageButtonPlus;
    Button buttonCancel;
    Button buttonSave;

    DBHelper dbHelper;
    ContentValues cv; SQLiteDatabase db;

    int amount;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_info);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Информация о продукте");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        textViewName = (TextView) findViewById(R.id.textViewFoodInfoName);
        editTextPrice = (EditText) findViewById(R.id.editTextFoodInfoPrice);
        textViewAmount = (TextView) findViewById(R.id.textViewFoodInfoAmount);

        //Подгружаем то, что уже имеется
        Intent intent = getIntent();
        textViewName.setText(intent.getStringExtra("name"));
        editTextPrice.setText(intent.getStringExtra("price"));
        textViewAmount.setText(String.valueOf(intent.getIntExtra("amount", 0)));
        position = intent.getIntExtra("position", -1);


        imageButtonMinus = (ImageButton) findViewById(R.id.imageButtonFoodInfoMinus);
        imageButtonMinus.setOnClickListener(this);
        imageButtonPlus = (ImageButton) findViewById(R.id.imageButtonFoodInfoPlus);
        imageButtonPlus.setOnClickListener(this);
        buttonCancel = (Button) findViewById(R.id.buttonFoodlnfoCancel);
        buttonCancel.setOnClickListener(this);
        buttonSave = (Button) findViewById(R.id.buttonFoodInfoSave);
        buttonSave.setOnClickListener(this);

        if (!textViewAmount.getText().toString().isEmpty())
            this.amount = Integer.parseInt(textViewAmount.getText().toString());

        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, WareHouse.class);

        switch (v.getId()){
            case R.id.imageButtonFoodInfoMinus:
                if (this.amount >= 100){
                    this.amount = this.amount - 100;
                    textViewAmount.setText(String.valueOf(this.amount));
                }
                break;
            case R.id.imageButtonFoodInfoPlus:
                this.amount = this.amount + 100;
                textViewAmount.setText(String.valueOf(this.amount));
                break;
            case R.id.buttonFoodlnfoCancel:
                startActivity(intent);
                break;
            case R.id.buttonFoodInfoSave:
                //обновим строку в таблице
                cv = new ContentValues();
                db = dbHelper.getWritableDatabase();

                cv.put("name", textViewName.getText().toString());
                cv.put("price", editTextPrice.getText().toString());
                cv.put("amount", Integer.parseInt(textViewAmount.getText().toString()));
                cv.put("status", 1);

                String[] queryArg = {textViewName.getText().toString()};
                db.update("myWareHouse",cv,"name = ?", queryArg);

                startActivity(intent);
                break;
        }
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
