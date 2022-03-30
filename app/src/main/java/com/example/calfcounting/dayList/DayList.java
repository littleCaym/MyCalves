package com.example.calfcounting.dayList;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calfcounting.DBHelper;
import com.example.calfcounting.warehouse.Food;
import com.example.calfcounting.MainActivity;
import com.example.calfcounting.medkit.Medicine;
import com.example.calfcounting.R;
import com.example.calfcounting.myAnimals.Animal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DayList extends AppCompatActivity {

    TextView textViewDate;
    ListView listView;

    DBHelper dbHelper;
    SQLiteDatabase db;

    ArrayList<Plan> planArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Дневной обход");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        textViewDate = (TextView) findViewById(R.id.textViewDayListDate);
        listView = (ListView) findViewById(R.id.listViewDayList);

        planArrayList = new ArrayList<>();

        //ставим текущую дату
        Date date = new Date();
        textViewDate.setText(formatDate(date.getTime(), "dd MMMM"));

        //TODO: Сравнить текущую дату с той, что в базе
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("dayList",null,null,null,null,null,null);
        cursor.moveToFirst();
        Plan plan = new Plan();
        plan.setDate(cursor.getLong(cursor.getColumnIndex("date")));

        //объединим это все с рецептами и лекарствами
        Cursor cursor5 = db.rawQuery("SELECT myAnimals.name AS animal, (( (? - myAnimals.birth) / (1000*60*60*24))+1) AS dayBirth," +
                        "rations.continue, myWarehouse.id AS prod1_id, myWarehouse.name AS prod1, myWareHouse.status AS stF1, prod1_portion, " +
                        "foods2.id AS prod2_id, foods2.name AS prod2, foods2.status AS stF2, prod2_portion, " +
                        "foods3.id AS prod3_id, foods3.name AS prod3, foods3.status AS stF3, prod3_portion, " +
                        "foods4.id AS prod4_id, foods4.name AS prod4, foods4.status AS stF4, prod4_portion, " +
                        "foods5.id AS prod5_id, foods5.name AS prod5, foods5.status AS stF5, prod5_portion, " +
                        "foods6.id AS prod6_id, foods6.name AS prod6, foods6.status AS stF6, prod6_portion, " +
                        "foods7.id AS prod7_id, foods7.name AS prod7, foods7.status AS stF7, prod7_portion, " +
                        "myAnimals.stat_zdor, " +
                        "aptechka1.illness AS lek1, aptechka1.status AS stM1, myRecepts.med1_dose, " +
                        "aptechka2.illness AS lek2, aptechka2.status AS stM2, myRecepts.med2_dose," +
                        "myRecepts.continue_treat, myAnimals.date_start_treat " +
                        "FROM myAnimals LEFT JOIN rations ON dayBirth = rations.id " +
                        "LEFT JOIN myWareHouse ON rations.prod1_id = myWareHouse.id " +
                        "LEFT JOIN (SELECT * FROM myWareHouse) foods2  ON rations.prod2_id = foods2.id " +
                        "LEFT JOIN (SELECT * FROM myWareHouse) foods3  ON rations.prod3_id = foods3.id " +
                        "LEFT JOIN (SELECT * FROM myWareHouse) foods4  ON rations.prod4_id = foods4.id " +
                        "LEFT JOIN (SELECT * FROM myWareHouse) foods5  ON rations.prod5_id = foods5.id " +
                        "LEFT JOIN (SELECT * FROM myWareHouse) foods6  ON rations.prod6_id = foods6.id " +
                        "LEFT JOIN (SELECT * FROM myWareHouse) foods7  ON rations.prod7_id = foods7.id " +
                        "LEFT JOIN illnesses ON myAnimals.id_illness = illnesses.id " +
                        "LEFT JOIN myRecepts ON illnesses.id = myRecepts.illness_id " +
                        "LEFT JOIN (SELECT * FROM aptechka) aptechka1 ON myRecepts.med1_id = aptechka1.id " +
                        "LEFT JOIN (SELECT * FROM aptechka) aptechka2 ON myRecepts.med2_id = aptechka2.id " +
                        "ORDER BY animal",
                new String[]{String.valueOf(new Date().getTime())});
        logCursor(cursor5);
        Log.d("TAG", "--- --- ---");

        //Заполняем массив
        if (cursor5.moveToFirst()){
            do {
                Plan plan1 = new Plan();

                plan1.setAnimalName(cursor5.getString(cursor5.getColumnIndex("animal")));
                plan1.setDaysFromBirth(cursor5.getInt(cursor5.getColumnIndex("dayBirth")));
                plan1.setRationContinue(cursor5.getString(cursor5.getColumnIndex("continue")));

                plan1.setProd1_id(cursor5.getInt(cursor5.getColumnIndex("prod1_id")));
                plan1.setProd1(cursor5.getString(cursor5.getColumnIndex("prod1")));
                plan1.setStF1(cursor5.getInt(cursor5.getColumnIndex("stF1")));
                plan1.setProd1_portion(cursor5.getInt(cursor5.getColumnIndex("prod1_portion")));

                plan1.setProd2_id(cursor5.getInt(cursor5.getColumnIndex("prod2_id")));
                plan1.setProd2(cursor5.getString(cursor5.getColumnIndex("prod2")));
                plan1.setStF2(cursor5.getInt(cursor5.getColumnIndex("stF2")));
                plan1.setProd2_portion(cursor5.getInt(cursor5.getColumnIndex("prod2_portion")));

                plan1.setProd3_id(cursor5.getInt(cursor5.getColumnIndex("prod3_id")));
                plan1.setProd3(cursor5.getString(cursor5.getColumnIndex("prod3")));
                plan1.setStF3(cursor5.getInt(cursor5.getColumnIndex("stF3")));
                plan1.setProd3_portion(cursor5.getInt(cursor5.getColumnIndex("prod3_portion")));

                plan1.setProd4_id(cursor5.getInt(cursor5.getColumnIndex("prod4_id")));
                plan1.setProd4(cursor5.getString(cursor5.getColumnIndex("prod4")));
                plan1.setStF4(cursor5.getInt(cursor5.getColumnIndex("stF4")));
                plan1.setProd4_portion(cursor5.getInt(cursor5.getColumnIndex("prod4_portion")));

                plan1.setProd5_id(cursor5.getInt(cursor5.getColumnIndex("prod5_id")));
                plan1.setProd5(cursor5.getString(cursor5.getColumnIndex("prod5")));
                plan1.setStF5(cursor5.getInt(cursor5.getColumnIndex("stF5")));
                plan1.setProd5_portion(cursor5.getInt(cursor5.getColumnIndex("prod5_portion")));

                plan1.setProd6_id(cursor5.getInt(cursor5.getColumnIndex("prod6_id")));
                plan1.setProd6(cursor5.getString(cursor5.getColumnIndex("prod6")));
                plan1.setStF6(cursor5.getInt(cursor5.getColumnIndex("stF6")));
                plan1.setProd6_portion(cursor5.getInt(cursor5.getColumnIndex("prod6_portion")));

                plan1.setProd7_id(cursor5.getInt(cursor5.getColumnIndex("prod7_id")));
                plan1.setProd7(cursor5.getString(cursor5.getColumnIndex("prod7")));
                plan1.setStF7(cursor5.getInt(cursor5.getColumnIndex("stF7")));
                plan1.setProd7_portion(cursor5.getInt(cursor5.getColumnIndex("prod7_portion")));

                plan1.setStat_zdor(cursor5.getInt(cursor5.getColumnIndex("stat_zdor")));
                plan1.setLek1(cursor5.getString(cursor5.getColumnIndex("lek1")));
                plan1.setStM1(cursor5.getInt(cursor5.getColumnIndex("stM1")));
                plan1.setDose1(cursor5.getFloat(cursor5.getColumnIndex("med1_dose")));
                plan1.setLek2(cursor5.getString(cursor5.getColumnIndex("lek2")));
                plan1.setStM2(cursor5.getInt(cursor5.getColumnIndex("stM2")));
                plan1.setDose2(cursor5.getFloat(cursor5.getColumnIndex("med2_dose")));
                plan1.setContinue_treat(cursor5.getInt(cursor5.getColumnIndex("continue_treat")));
                plan1.setDate_start_treat(cursor5.getLong(cursor5.getColumnIndex("date_start_treat")));

                planArrayList.add(plan1);


                //Условие
                if ((date.getTime() - plan.getDate()) / (1000 * 60 * 60 * 24) > 0) {
                    //TODO: если лечение закончилось, поменять статус на здоров
                    if(plan1.getStat_zdor() == 1)
                        caseEndTreatment(plan1.getDate_start_treat(), plan1.getContinue_treat(), plan1.getAnimalName(), plan1.getStat_zdor());

                    if (plan1.getStat_zdor() == 1) { //если болен
                        if ((new Date().getTime() - plan1.getDate_start_treat()) / (1000 * 60 * 60 * 24) + 1 <= plan1.getContinue_treat()) {
                            // вычитание лекарств из аптечки
                            if (plan1.getLek1() != null) //если есть такое лекарство в списке
                                aptechkaMinusMeds(plan1.getLek1(), plan1.getDose1());
                            if (plan1.getLek2() != null) //если есть такое лекарство в списке
                                aptechkaMinusMeds(plan1.getLek2(), plan1.getDose2());

                            // изменение статуса лекарств в аптечке
                            if (plan1.getLek1() != null)
                                medicineChangeStatus(plan1.getLek1(), plan1.getDose1(), plan1.getContinue_treat(), plan1.getDate_start_treat());
                            if (plan1.getLek2() != null)
                                medicineChangeStatus(plan1.getLek2(), plan1.getDose2(), plan1.getContinue_treat(), plan1.getDate_start_treat());
                        }
                    } else {
                        // поменять статус здоровья в таблице Животных у текущего животного.
                        //animalChangeStatus(plan1.getAnimalName(), plan1.getStat_zdor());
                    }

                    //вычитание продуктов со склада
                    if (plan1.getProd1() != null){
                        warehouseMinusFood(plan1.getProd1(), plan1.getProd1_portion());
                    }
                    if (plan1.getProd2() != null){
                        warehouseMinusFood(plan1.getProd2(), plan1.getProd2_portion());
                    }
                    if (plan1.getProd3() != null){
                        warehouseMinusFood(plan1.getProd3(), plan1.getProd2_portion());
                    }
                    if (plan1.getProd4() != null){
                        warehouseMinusFood(plan1.getProd4(), plan1.getProd2_portion());
                    }
                    if (plan1.getProd5() != null){
                        warehouseMinusFood(plan1.getProd5(), plan1.getProd2_portion());
                    }
                    if (plan1.getProd6() != null){
                        warehouseMinusFood(plan1.getProd6(), plan1.getProd2_portion());
                    }
                    if (plan1.getProd7() != null){
                        warehouseMinusFood(plan1.getProd7(), plan1.getProd2_portion());
                    }




                    //изменение статуса продуктов на складе
                    if (plan1.getProd1() != null) {
                        warehouseChangeStatus(plan1.getRationContinue(), plan1.getProd1_id(), plan1.getProd1_portion(), plan1.getDaysFromBirth());
                    }
                    if (plan1.getProd2() != null) {
                        warehouseChangeStatus(plan1.getRationContinue(), plan1.getProd2_id(), plan1.getProd2_portion(), plan1.getDaysFromBirth());
                    }
                    if (plan1.getProd3() != null) {
                        warehouseChangeStatus(plan1.getRationContinue(), plan1.getProd3_id(), plan1.getProd3_portion(), plan1.getDaysFromBirth());
                    }
                    if (plan1.getProd4() != null) {
                        warehouseChangeStatus(plan1.getRationContinue(), plan1.getProd4_id(), plan1.getProd4_portion(), plan1.getDaysFromBirth());
                    }
                    if (plan1.getProd5() != null) {
                        warehouseChangeStatus(plan1.getRationContinue(), plan1.getProd5_id(), plan1.getProd5_portion(), plan1.getDaysFromBirth());
                    }
                    if (plan1.getProd6() != null) {
                        warehouseChangeStatus(plan1.getRationContinue(), plan1.getProd6_id(), plan1.getProd6_portion(), plan1.getDaysFromBirth());
                    }
                    if (plan1.getProd7() != null) {
                        warehouseChangeStatus(plan1.getRationContinue(), plan1.getProd7_id(), plan1.getProd7_portion(), plan1.getDaysFromBirth());
                    }
                }

            }while (cursor5.moveToNext());
            //обновляем список планов на день
            ContentValues cv = new ContentValues();
            cv.put("date", new Date().getTime());
            db.update("dayList", cv, null, null);

        }

        DayListArrayAdapter adapter = new DayListArrayAdapter(this, planArrayList);
        listView.setAdapter(adapter);
    }

    public static String formatDate(long date, String format) {
        Locale locale = new Locale("ru");
        SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
        return sdf.format(date);
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

    void aptechkaMinusMeds(String lek1, float dose1){
        SQLiteDatabase dbMeds = dbHelper.getWritableDatabase();
        Medicine medicine = new Medicine();
        float newDose = 0;
        //вычитаем первое лекарство
        Cursor cursorMeds = dbMeds.rawQuery("SELECT * FROM aptechka WHERE illness = ?", new String[]{lek1});
        if (cursorMeds.moveToFirst()){

            medicine.setName(cursorMeds.getString(cursorMeds.getColumnIndex("illness")));
            medicine.setPrice(cursorMeds.getString(cursorMeds.getColumnIndex("price")));
            medicine.setAmount(cursorMeds.getFloat(cursorMeds.getColumnIndex("amount")));
            medicine.setStatus(cursorMeds.getInt(cursorMeds.getColumnIndex("status")));

            newDose = medicine.getAmount() - dose1;

            medicine.setAmount(newDose);
        }
        cursorMeds.close();

        ContentValues cv = new ContentValues();
        cv.put("illness", medicine.getName());
        cv.put("price", medicine.getPrice());
        cv.put("amount", newDose);
        cv.put("status", medicine.getStatus());

        dbMeds.update("aptechka", cv, "illness = ?", new String[]{lek1});
        /*
        + "illness text,"
                + "price text,"
                + "amount integer,"
                + "status integer"
         */
    }

    void animalChangeStatus(String name, int status){
        if (status == 1){
            status = 0;
        }
        if (status == 0){
            status = 1;
        }
        SQLiteDatabase dbAnimals = dbHelper.getWritableDatabase();
        Animal animal = new Animal();

        Cursor cursorAnimals = dbAnimals.rawQuery("SELECT * FROM myAnimals WHERE name = ?", new String[]{name});
        if (cursorAnimals.moveToFirst()){
            animal.setName(cursorAnimals.getString(cursorAnimals.getColumnIndex("name")));
            animal.setBirth(cursorAnimals.getLong(cursorAnimals.getColumnIndex("birth")));
            animal.setWeight(cursorAnimals.getFloat(cursorAnimals.getColumnIndex("weight")));
            animal.setStatus(cursorAnimals.getInt(cursorAnimals.getColumnIndex("stat_zdor")));
            animal.setIdIllness(cursorAnimals.getInt(cursorAnimals.getColumnIndex("id_illness")));
            animal.setDateStartTreat(cursorAnimals.getLong(cursorAnimals.getColumnIndex("date_start_treat")));

            animal.setStatus(status);
        }
        cursorAnimals.close();

        ContentValues cv = new ContentValues();
        cv.put("name", animal.getName());
        cv.put("birth", animal.getBirth());
        cv.put("weight", animal.getWeight());
        cv.put("stat_zdor", animal.getStatus());
        cv.put("id_illness", animal.getIdIllness());
        cv.put("date_start_treat", animal.getDateStartTreat());

        dbAnimals.update("myAnimals", cv,"name = ?", new String[]{name});

        /*
         + "name text,"
                + "birth INT," //
                + "weight real,"
              //  + "idRation integer,"
                + "stat_zdor integer," // 0 - здоров 1 - болен
                + "id_illness integer,"
                + "date_start_treat INT"
         */
    }

    void medicineChangeStatus(String nameIllness, float dose, int continueTreat, long dateStartTreat){
        SQLiteDatabase dbMeds = dbHelper.getWritableDatabase();
        Medicine medicine = new Medicine();

        Cursor cursorMeds = dbMeds.rawQuery("SELECT * FROM aptechka WHERE illness = ?", new String[]{nameIllness});
        if (cursorMeds.moveToFirst()){

            medicine.setName(cursorMeds.getString(cursorMeds.getColumnIndex("illness")));
            medicine.setPrice(cursorMeds.getString(cursorMeds.getColumnIndex("price")));
            medicine.setAmount(cursorMeds.getInt(cursorMeds.getColumnIndex("amount")));
            medicine.setStatus(cursorMeds.getInt(cursorMeds.getColumnIndex("status")));

            /*
            продолжительность_лечения + день_начала_болезни - текущий_день = оставшиеся_дни_болезни
            оставшиеся_дни_болезни * доза1
            int restRation = maxDayRation - dayOfBirth;
             */
            long dateLong = new Date().getTime();
            long restTreat = continueTreat + (dateStartTreat / (1000*60*60*24) ) - (dateLong / (1000*60*60*24) );

            if(medicine.getName() != null){
                if (dose*restTreat >= medicine.getAmount()){
                    medicine.setStatus(2);
                }
                if (  (dose*1 <= medicine.getAmount()) && (dose*restTreat > medicine.getAmount())   ){
                    medicine.setStatus(1);
                }
                if ( medicine.getAmount() < dose*1){
                    medicine.setStatus(0);
                }
            }
        }
        cursorMeds.close();

        ContentValues cv = new ContentValues();
        cv.put("illness", medicine.getName());
        cv.put("price", medicine.getPrice());
        cv.put("amount", medicine.getAmount());
        cv.put("status", medicine.getStatus());

        dbMeds.update("aptechka", cv, "illness = ?", new String[]{nameIllness});

    }

    void warehouseChangeStatus(String recContinue, int prod_id, int portion, int dayOfBirth){
        //TODO: вывести таблицу рационов и получить макс. элемнт
        SQLiteDatabase dbRec = dbHelper.getWritableDatabase();
        int minDayRation = 0; int maxDayRation = 0;
        Cursor cursorRec = dbRec.rawQuery("SELECT MIN(id) AS min_id, MAX(id) AS max_id FROM rations WHERE continue = ? ", new String[]{recContinue});
        logCursor(cursorRec);
        Log.d("TAG", "--- --- ---");

        if (cursorRec.moveToFirst()){
            minDayRation = cursorRec.getInt(cursorRec.getColumnIndex("min_id"));
            maxDayRation = cursorRec.getInt(cursorRec.getColumnIndex("max_id"));
        }

        Food food = new Food();
        SQLiteDatabase dbWarehouse = dbHelper.getWritableDatabase();
        Cursor cursorWH = dbWarehouse.rawQuery("SELECT * FROM myWareHouse WHERE id = ? ", new String[]{String.valueOf(prod_id)});
        if (cursorWH.moveToFirst()){
            food.setName(cursorWH.getString(cursorWH.getColumnIndex("name")));
            food.setPrice(cursorWH.getString(cursorWH.getColumnIndex("price")));
            food.setAmount(cursorWH.getInt(cursorWH.getColumnIndex("amount")));
            food.setStatus(cursorWH.getInt(cursorWH.getColumnIndex("status")));
        }

        /*
            дата_окончания_рациона - текущий_день - день_начала_рациона = оставшиеся_дни_рациона
            оставшиеся_дни_рациона * порция1

             */
        long dateLong = new Date().getTime();
        //long restRation = maxDayRation - (dateLong / (1000*60*60*24) - minDayRation );

        int restRation = maxDayRation - dayOfBirth;
        if (food.getName() != null || maxDayRation != 0 || minDayRation != 0){
            if (portion*restRation >= food.getAmount()){
                food.setStatus(2);
            }
            if (    (portion*1 <= food.getAmount()) && (portion*restRation > food.getAmount()) ){
                food.setStatus(1);
            }
            if (food.getAmount() < portion*1){
                food.setStatus(0);
            }
        }

    }

    void warehouseMinusFood(String name, int portion){
        SQLiteDatabase dbWH = dbHelper.getWritableDatabase();
        Food food = new Food();
        int newPortion = 0;
        //вычитаем первое лекарство
        Cursor cursorMeds = dbWH.rawQuery("SELECT * FROM myWarehouse WHERE name = ?", new String[]{name});
        if (cursorMeds.moveToFirst()){

            food.setName(cursorMeds.getString(cursorMeds.getColumnIndex("name")));
            food.setPrice(cursorMeds.getString(cursorMeds.getColumnIndex("price")));
            food.setAmount(cursorMeds.getInt(cursorMeds.getColumnIndex("amount")));
            food.setStatus(cursorMeds.getInt(cursorMeds.getColumnIndex("status")));

            newPortion = food.getAmount() - portion;

            food.setAmount(newPortion);
        }
        cursorMeds.close();

        ContentValues cv = new ContentValues();
        cv.put("name", food.getName());
        cv.put("price", food.getPrice());
        cv.put("amount", newPortion);
        cv.put("status", food.getStatus());

        dbWH.update("myWareHouse", cv, "name = ?", new String[]{name});

    }

    void caseEndTreatment(long startTreat, int continueTreat, String name, int status){
        if ( ( (new Date().getTime() - startTreat) / (1000*60*60*24) ) > continueTreat){
            animalChangeStatus(name, status);
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
