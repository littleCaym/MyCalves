package com.example.calfcounting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;

    Button dayList_button;
    Button myAnimals_button;
    Button ration_button;
    Button sklad_button;
    Button aptechka_button;
    Button spravochnikBolezney_button;
    Button compoundAdverts_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Главное меню");

        dayList_button = (Button) findViewById(R.id.button_Daily);
        dayList_button.setOnClickListener(this);

        ration_button = (Button) findViewById(R.id.button_rations);
        ration_button.setOnClickListener(this);

        myAnimals_button = (Button) findViewById(R.id.button_MyAnimal);
        myAnimals_button.setOnClickListener(this);

        sklad_button = (Button) findViewById(R.id.button_Sklad);
        sklad_button.setOnClickListener(this);

        aptechka_button = (Button) findViewById(R.id.button_Aptechka);
        aptechka_button.setOnClickListener(this);

        spravochnikBolezney_button = (Button) findViewById(R.id.button_Illnesses);
        spravochnikBolezney_button.setOnClickListener(this);

        compoundAdverts_button = findViewById(R.id.button_compound_adverts);
        compoundAdverts_button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_Daily:
                Intent intent = new Intent(this, DayList.class);
                startActivity(intent);
                break;
            case R.id.button_MyAnimal:
                Intent intent1 = new Intent(this, MyAnimals.class);
                startActivity(intent1);
                break;
            case R.id.button_Sklad:
                Intent intent4 = new Intent(this, WareHouse.class);
                startActivity(intent4);
                break;
            case R.id.button_Aptechka:
                Intent intent5 = new Intent(this, MedKit.class);
                startActivity(intent5);
                break;
            case R.id.button_rations:
                Intent intent9 = new Intent(this, Rations.class);
                startActivity(intent9);
                break;
            case R.id.button_Illnesses:
                Intent intent6 = new Intent(this, Illnesses.class);
                startActivity(intent6);
                break;
                //The restored info must be opened
            case R.id.button_compound_adverts:
                Intent intent7 = new Intent(this, CompoundAdverts.class);
                startActivity(intent7);

        }
    }


}
