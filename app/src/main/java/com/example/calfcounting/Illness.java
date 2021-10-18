package com.example.calfcounting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Illness extends AppCompatActivity {

    private String name;
    private String symptoms;
    private String description;
    private String treatment;

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setSymptoms(String symptoms) {this.symptoms =symptoms;}
    public String getSymptoms() {return this.symptoms;}
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }
    public String getTreatment() {
        return treatment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_illness);
    }
}
