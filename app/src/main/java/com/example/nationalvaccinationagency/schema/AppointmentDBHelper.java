package com.example.nationalvaccinationagency.schema;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class AppointmentDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Vaccination.db";
    private static final String CREATE_APPOINTMENT_TABLE =
            "CREATE TABLE "+ AppointmentContract.AppointmentTable.TABLE_NAME+ " (\n"
            +AppointmentContract.AppointmentTable.COLUMN_NAME_NAME+" TEXT,\n"
            +AppointmentContract.AppointmentTable.COLUMN_NAME_SURNAME+" TEXT,\n"
            +AppointmentContract.AppointmentTable.COLUMN_NAME_SOCIAL_SECURITY_NUMBER+" TEXT,\n"
            +AppointmentContract.AppointmentTable.COLUMN_NAME_TEL+" TEXT,\n"
            +AppointmentContract.AppointmentTable.COLUMN_NAME_EMAIL+" TEXT PRIMARY KEY,\n"
            +AppointmentContract.AppointmentTable.COLUMN_NAME_APPOINTMENT_DATE+" TEXT,\n"
            +AppointmentContract.AppointmentTable.COLUMN_NAME_APPOINTMENT_TIME+" TEXT)";


    public AppointmentDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_APPOINTMENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
