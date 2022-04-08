package com.example.calfcounting;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.calfcounting.compounds.Compound;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;
import java.util.TimeZone;

public class WorkerParseJSON extends Worker{

//    public static final String SERVER_CONNECTION_SERVICE_ACTION= "com.example.calfcounting.action.SERVER_CONNECTION";
    public static final String START_SERVICE_MESSAGE ="Идет обновление данных";
    public static final String FINISH_SERVICE_MESSAGE ="Данные обновлены";
//    public static final String PROCESS_STATUS = "com.example.calfcounting.broadcast.Message";

    public static final String WARNING_NO_INTERNET_CONNECTION = "Отсутствует интернет-соеднинение!";
    public static final String WARNING_SERVER_NO_RESPONSE = "Не удалось соединиться с сервером!";
    public static final String WARNING_NO_DATA_FROM_SERVER = "Данные с сервера не были получены!";

    Context context;
    WorkerParameters workerParams;
    Handler handler;


    //Отправляем сообщение о старте
    String processStatus="";
    String data = "";



    public WorkerParseJSON(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
        this.workerParams = workerParams;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public Result doWork() {
        //Отображаем пользователю статус выполнения процесса

        handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Toast.makeText(context, processStatus, Toast.LENGTH_SHORT).show();
            }
        };



        //DB must be opened once!!!!
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //check internet connection
        if (isInternetAvailable()){
            //check the time
            if (checkTheTime(db)){
                processStatus = START_SERVICE_MESSAGE;
                handler.obtainMessage(1,START_SERVICE_MESSAGE).sendToTarget();
                Log.d("MyLOG", START_SERVICE_MESSAGE);
                //download data from Server
                HashMap<Boolean, ArrayList<Compound>> serverData = parseJsonFromMyServer();
                if (serverData.containsKey(true)) {
                    ArrayList<Compound> compoundArrayList = serverData.get(true);
                    //If we got any data
                    if (!Objects.requireNonNull(compoundArrayList).isEmpty()) {
                        //clear previous table of Compounds
                        if (DBHelper.deleteAllFromCompoundTable(context)) {
                            //add arrayList to DataBase
                            //Отправляем сообщение о финише
                            if (DBHelper.addCompoundArrayListToDB(context, compoundArrayList)) {
                                processStatus = FINISH_SERVICE_MESSAGE;
                                handler.obtainMessage(2,FINISH_SERVICE_MESSAGE).sendToTarget();
                                Log.d("MyLOG", FINISH_SERVICE_MESSAGE);
                                return Result.success();
                            }
                        } else {
                            Log.d("MyLog", "Проблемы с удалением из ДБ");
                        }
                    } else {
                        processStatus = WARNING_NO_DATA_FROM_SERVER;
                        handler.obtainMessage(0,WARNING_NO_DATA_FROM_SERVER).sendToTarget();
                        Log.d("MyLog", WARNING_NO_DATA_FROM_SERVER);
                    }
                } else {
                    processStatus = WARNING_SERVER_NO_RESPONSE;
                    handler.obtainMessage(0,WARNING_SERVER_NO_RESPONSE).sendToTarget();
                    Log.d("MyLog", WARNING_SERVER_NO_RESPONSE);
                }
            }else {Log.d("MyLog", "Время не наступило");}
        } else{
            processStatus = WARNING_NO_INTERNET_CONNECTION;
            handler.obtainMessage(0,WARNING_NO_INTERNET_CONNECTION).sendToTarget();
            Log.d("MyLog", WARNING_NO_INTERNET_CONNECTION);
        }

        //todo add Splash if thread is alive while moving to CompoundAdverts activity
        return Result.failure();
    }


    private boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");
        } catch (Exception e) {
            return false;
        }
    }

    //ОГРАНИЧЕНИЕ ТУТ: телефон у нас не старше 2017 года, когда вышла ОРЕО. 82,7% всех телефонов потянут
    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean checkTheTime(SQLiteDatabase db){
        Calendar calendarCurrent = Calendar.getInstance(TimeZone.getTimeZone("Europe/Moscow"));//todo место сервака вынести в strings

        //Когда мы последний раз соединялись по локальному времени:
        Calendar calendarLastCompoundListUpdate = Calendar.getInstance();
        calendarLastCompoundListUpdate.setTimeInMillis(
                DBHelper.getLastTimeStampsLong(db));
        Log.d("MyLog", "getLastTimeStampLong Вернул: " + String.valueOf(DBHelper.getLastTimeStampsLong(db)));
        //Теперь переводим в Московское:
        calendarLastCompoundListUpdate.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));

        LocalTime firstUpdateOfDay = LocalTime.of(9,15);
        LocalTime secondUpdateOfDay = LocalTime.of(18, 15);
        LocalTime currentLocalTime = LocalTime.of(calendarCurrent.get(Calendar.HOUR_OF_DAY),
                calendarCurrent.get(Calendar.MINUTE), 0);
        LocalTime lastUpdateLocalTime = LocalTime.of(calendarLastCompoundListUpdate.get(Calendar.HOUR_OF_DAY),
                calendarLastCompoundListUpdate.get(Calendar.MINUTE), 0);
        LocalDate currentLocalDate = LocalDate.of(calendarCurrent.get(Calendar.YEAR),
                calendarCurrent.get(Calendar.MONTH), calendarCurrent.get(Calendar.DAY_OF_MONTH));
        LocalDate lastUpdateLocalDate = LocalDate.of(calendarLastCompoundListUpdate.get(Calendar.YEAR),
                calendarLastCompoundListUpdate.get(Calendar.MONTH), calendarLastCompoundListUpdate.get(Calendar.DAY_OF_MONTH));

        //Проверяем было ли уже обновление данных в промежутке (например с 01.01.2022 с 9 до 18 и с 18 до 9 02.01.2022)
        //если текущее время в промежутке между 9:15 и 18:15 todo вставь из strings
        if (currentLocalTime.isAfter(firstUpdateOfDay) && currentLocalTime.isBefore(secondUpdateOfDay)){
            //Было ли обновление в этот день?
            if (currentLocalDate.equals(lastUpdateLocalDate)){
                //Обновление было в пределах установленного времени?
                //Завершаем
                return !lastUpdateLocalTime.isAfter(firstUpdateOfDay) || !lastUpdateLocalTime.isBefore(secondUpdateOfDay);
            }
            return true;
        }
        //если текущее время в промежутке между 18:15 и 9:15 todo вставь из strings
        else{
            Log.d("MyLog", "currentLocalDate.toEpochDay() - lastUpdateLocalDate.toEpochDay() "+String.valueOf(currentLocalDate.toEpochDay() - lastUpdateLocalDate.toEpochDay()));

            //Разница в датах - максимум 1 день
            if (currentLocalDate.toEpochDay() - lastUpdateLocalDate.toEpochDay() > 1) {
                return true;
            }
            //Обновление было в пределах установленного времени?
            return !currentLocalTime.isAfter(secondUpdateOfDay) && !currentLocalTime.isBefore(firstUpdateOfDay);
        }

    }

    private HashMap<Boolean, ArrayList<Compound>> parseJsonFromMyServer(){
        ArrayList<Compound> compoundArrayList = new ArrayList<>();
        HashMap<Boolean, ArrayList<Compound>> hashMap = new HashMap<>();
        try{
            URL url = new URL(String.valueOf(
                    context.getString(R.string.http_host) +
                    context.getString(R.string.my_notebook_local_ip_and_port) +
                    context.getString(R.string.server_page_avito)));
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            Timestamp connected_timeStamp = new Timestamp(System.currentTimeMillis());

            String line;
            while ((line = bufferedReader.readLine()) != null){
                data = data.concat(line);
            }
            if (!data.isEmpty()){
                JSONArray json = new JSONArray(data);
                compoundArrayList = new ArrayList<>();



                for (int i = 0; i < json.length(); i++) {
                    JSONObject o = json.getJSONObject(i);
                    Compound compound = new Compound();

                    compound.setConnection_time(connected_timeStamp);

                    compound.setId(i);
                    compound.setName(o.getString("title"));
                    compound.setSeller(o.getString("brand"));
                    compound.setRating(Float.parseFloat(o.getString("brand_rating")));
                    compound.setReviews_num(o.getInt("reviews_num"));
                    compound.setUpload_advert_date(o.getString("date_upload"));
                    compound.setDescription(o.getString("description"));
                    compound.setLocation(o.getString("location"));
                    compound.setPrice(Float.parseFloat(o.getString("price")));
                    compound.setLink_to_advert(o.getString("link"));

                    compoundArrayList.add(compound);
                }

            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            hashMap.put(false, compoundArrayList);
            return hashMap;

        }
        hashMap.put(true, compoundArrayList);
        return hashMap;
    }



}