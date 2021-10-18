package com.example.calfcounting;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Medicine extends AppCompatActivity implements View.OnClickListener{

    DBHelper dbHelper;


    EditText editName;
    EditText editDescription;
    EditText editAmount;
    EditText editDateOff;
    EditText editDescriptionTreat;
    EditText editPrice;
    Button buttonAdd;


    private String name;
    private String description;
    private float amount;
    private String price;
    private int status;


    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setDescription(String description) {this.description = description;}
    public String getDescription(){return this.description;}
    public void setAmount(float amount){this.amount = amount;}
    public float getAmount(){return this.amount;}
    public void setPrice(String price){this.price = price;}
    public String getPrice(){return this.price;}
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);




        dbHelper = new DBHelper(this);

        editName = (EditText) findViewById(R.id.editTextMedicineName);
        editDescription = (EditText) findViewById(R.id.editTextMedicineDescription);
        editAmount = (EditText) findViewById(R.id.editTextMedicineAmount);
        editDateOff = (EditText) findViewById(R.id.editTextMedicineDateOff);
        editDescriptionTreat = (EditText) findViewById(R.id.editTextMedicineDescriptionTreat);
        editPrice = (EditText) findViewById(R.id.editTextMedicinePrice);

        buttonAdd = (Button) findViewById(R.id.buttonAddMedicineFromBlank);
        buttonAdd.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Intent intent = new Intent(this, MedKit.class);
        startActivity(intent);
    }
}
