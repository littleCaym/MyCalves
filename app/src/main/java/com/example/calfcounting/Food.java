package com.example.calfcounting;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


public class Food extends AppCompatActivity implements View.OnClickListener{

    private String name;
    private String price;
    private int amount;
    private int status;

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setPrice(String price) {this.price = price;}
    public String getPrice() {return this.price;}
    public void setAmount(int amount) {this.amount = amount;}
    public int getAmount() {return this.amount;}

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    EditText editTextName;
    EditText editTextPrice;
    TextView textViewAmount;
    ImageButton buttonMinus;
    ImageButton buttonPlus;

    Button button_add;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("CalfCount app");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        this.amount = 0;

        editTextName = (EditText) findViewById(R.id.editTextFoodName);
        editTextPrice = (EditText) findViewById(R.id.editTextFoodPrice);
        textViewAmount = (TextView) findViewById(R.id.textViewFoodAmount);

        buttonMinus = (ImageButton) findViewById(R.id.imageButtonFoodMinus);
        buttonMinus.setOnClickListener(this);
        buttonPlus = (ImageButton) findViewById(R.id.imageButtonFoodPlus);
        buttonPlus.setOnClickListener(this);

        button_add = (Button) findViewById(R.id.button_AddFoodFromForm);
        button_add.setOnClickListener(this);

        dbHelper = new DBHelper(this);


    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.imageButtonFoodMinus:
                if (this.amount >= 100){
                    this.amount = this.amount - 100;
                    textViewAmount.setText(this.amount);
                }
                break;
            case R.id.imageButtonFoodPlus:
                this.amount = this.amount + 100;
                textViewAmount.setText(this.amount);
                break;
            case R.id.button_AddFoodFromForm:
                ContentValues cv = new ContentValues();
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                cv.put("name", editTextName.getText().toString());
                cv.put("price", editTextPrice.getText().toString());
                cv.put("amount", textViewAmount.getText().toString());

                db.insert("myWareHouse", null, cv);

                Intent intent = new Intent(this, WareHouse.class);
                startActivity(intent);
                break;
        }

    }

}
