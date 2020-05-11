package com.example.calculadorasaudavel;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SqlHelper extends SQLiteOpenHelper {


    //Objetov Singleton - Fazer com que ele esteja ativo durante o aplicativo sempre

    private static final String DB_NAME = "fitness.db";
    private static final int DB_BERSION = 1;

    private static SqlHelper INSTANCE;
    static final String TYPE_IMC = "imc";
    static final String TYPE_TMB = "tmb";

    static synchronized SqlHelper getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new SqlHelper(context.getApplicationContext());
        return INSTANCE;
    }


    private SqlHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_BERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE calc(id INTENGER PRIMARY KEY, type_calc TEXT, res DECIMAL, created_date DATETIME)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    List<Register> getRegisterBy(String type) {
        List<Register> registers = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM calc WHERE type_calc = ?", new String[]{type});

        try {
            if (cursor.moveToFirst()) {

                do {
                    Register register = new Register();
                    register.type = cursor.getString(cursor.getColumnIndex("type_calc"));
                    register.response = cursor.getDouble(cursor.getColumnIndex("res"));
                    register.createdDate = cursor.getString(cursor.getColumnIndex("created_date"));

                    registers.add(register);


                } while (cursor.moveToNext());

                db.setTransactionSuccessful();


            }

        } catch (Exception e) {
            Log.e("Teste", e.getMessage(), e);
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return registers;
    }


    long addItem(String type, double response) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        long calcId = 0;
        try {
            ContentValues values = new ContentValues();
            values.put("type_calc", type);
            values.put("res", response);

            SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:MM:SS", new Locale("pt", "BR"));
            String now = format.format(Calendar.getInstance().getTime());
            values.put("created_date", now);

            calcId = db.insertOrThrow("calc", null, values);
            db.setTransactionSuccessful();


        } catch (Exception e) {
            Log.e("Teste", e.getMessage(), e);
        } finally {
            db.endTransaction();
        }
        return calcId;
    }

    static class Register {
        String type;
        double response;
        String createdDate;
    }

}
