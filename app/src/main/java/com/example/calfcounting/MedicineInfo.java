package com.example.calfcounting;

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

public class MedicineInfo extends AppCompatActivity implements View.OnClickListener {

    TextView textViewName;
    EditText editTextPrice;
    TextView textViewAmount;

    ImageButton imageButtonMinus;
    ImageButton imageButtonPlus;
    Button buttonCancel;
    Button buttonSave;

    DBHelper dbHelper;
    ContentValues cv; SQLiteDatabase db;

    float amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_info);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Информация о лекарстве");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        textViewName = (TextView) findViewById(R.id.textViewMedicineInfoName);
        editTextPrice = (EditText) findViewById(R.id.editTextMedicineInfoPrice);
        textViewAmount = (TextView) findViewById(R.id.textViewMedicineInfoAmount);
        imageButtonMinus = (ImageButton) findViewById(R.id.imageButtonMedicineInfoMinus);
        imageButtonMinus.setOnClickListener(this);
        imageButtonPlus = (ImageButton) findViewById(R.id.imageButtonMedicineInfoPlus);
        imageButtonPlus.setOnClickListener(this);
        buttonCancel = (Button) findViewById(R.id.buttonMedicineInfoCancel);
        buttonCancel.setOnClickListener(this);
        buttonSave = (Button) findViewById(R.id.buttonMedicineInfoSave);
        buttonSave.setOnClickListener(this);

        Intent intent = getIntent();
        textViewName.setText(intent.getStringExtra("illness"));
        editTextPrice.setText(intent.getStringExtra("price"));
        textViewAmount.setText(String.valueOf(intent.getFloatExtra("amount", 0)));

        if (!textViewAmount.getText().toString().isEmpty())
            this.amount = Float.parseFloat(textViewAmount.getText().toString());

        dbHelper = new DBHelper(this);
        
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MedKit.class);

        switch (v.getId()){
            case R.id.imageButtonMedicineInfoMinus:
                if (this.amount >= 10){
                    this.amount = this.amount - 10;
                    textViewAmount.setText(String.valueOf(this.amount));
                }
                break;
            case R.id.imageButtonMedicineInfoPlus:
                this.amount = this.amount + 10;
                textViewAmount.setText(String.valueOf(this.amount));
                break;
            case R.id.buttonMedicineInfoCancel:
                startActivity(intent);
                break;
            case R.id.buttonMedicineInfoSave:
                //обновим строку в таблице
                cv = new ContentValues();
                db = dbHelper.getWritableDatabase();

                cv.put("illness", textViewName.getText().toString());
                cv.put("price", editTextPrice.getText().toString());
                cv.put("amount", Float.parseFloat(textViewAmount.getText().toString()));
                cv.put("status", 1);

                String[] queryArg = {textViewName.getText().toString()};
                db.update("aptechka",cv,"illness = ?", queryArg);

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
