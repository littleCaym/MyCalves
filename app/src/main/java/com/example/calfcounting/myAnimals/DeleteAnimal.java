package com.example.calfcounting.myAnimals;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.calfcounting.DBHelper;

public class DeleteAnimal extends DialogFragment {
    private Animal animal;
    private DBHelper dbHelper;
    private Intent intent;
    public DeleteAnimal(Animal animal, DBHelper dbHelper, Intent intent) {
        this.animal = animal;
        this.dbHelper = dbHelper;
        this.intent = intent;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Удаление элемента");  // заголовок
        builder.setMessage("Удалить животное "+animal.getName()+"?"); // сообщение
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("myAnimals","name = ?",new String[]{animal.getName()});
                Toast.makeText(getActivity(), "Теленок "+animal.getName()+" удален",
                        Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        });
        builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        builder.setCancelable(true);
        return builder.create();
    }

}
