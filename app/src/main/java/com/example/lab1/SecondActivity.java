package com.example.lab1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class SecondActivity extends AppCompatActivity {

    public static final String txtData = "123445";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        AlertDia();
        MyDatabase mydb = new MyDatabase(this);

        SQLiteDatabase sqdb = mydb.getWritableDatabase();


        String insertQuery = "INSERT INTO " +
                MyDatabase.TABLE_NAME +" (" + MyDatabase.UNAME + ") VALUES ('" +
                txtData.toString() + "')";
        sqdb.execSQL(insertQuery);


        Cursor cursor = sqdb.query(MyDatabase.TABLE_NAME,new String[]{
                        MyDatabase.UID,MyDatabase.UNAME},
                null,
                null,
                null,
                null,
                null
        );

        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(MyDatabase.UID));
            String name = cursor.getString(cursor.getColumnIndex(MyDatabase.UNAME));
            Log.i("LOG_TAG", "ROW" + id + "HAS UNAME" + name);
        }
        cursor.close();

        sqdb.close();
        mydb.close();
    }

    public void goBack(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void AlertDia(){
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
        alt_bld.setMessage("Видим диалоговое окно?")
                .setCancelable(false)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alert = alt_bld.create();
        alert.setTitle("Это тестовое диалоговое окно");
        alert.show();
    }
}