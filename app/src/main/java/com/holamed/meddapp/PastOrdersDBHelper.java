package com.holamed.meddapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Era on 5/18/2015.
 */
public class PastOrdersDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=4;
    private static final String DATABASE_NAME="pastorders";
    private static final String COLUMN_DISCOUNT="discount";
    private static final String COLUMN_CHOICE="choice";
    private static final String COLUMN_CENTER_PHONE="phone";
    private static final String COLUMN_CENTER_EMAIL="email";
    private static final String TABLE_ORDERED_TEST="orderedtest";
    private static final String COLUMN_PATIENTNAME="patient_name";
    private static final String COLUMN_ID="order_id";
    private static final String COLUMN_TESTS="tests";
    private static final String COLUMN_PATIENT_ID="patient_id";
    private static final String COLUMN_TESTGROUP_ID="testgroup_id";
    private static final String COLUMN_AMOUNT="amount";
    private static final String COLUMN_CENTER_ADDRESS="center_address";
    private static final String COLUMN_CENTER_NAME="center_name";
    private static final String COLUMN_COUPON_CODE="coupon_code";
    private static final String COLUMN_DATE="booking_date";
    // Database creation sql statement
    private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_ORDERED_TEST + "("
            + COLUMN_ID + " INTEGER AUTO INCREMENT, "
            + COLUMN_TESTS + " TEXT, "
            + COLUMN_AMOUNT + " TEXT, "
            + COLUMN_CENTER_ADDRESS + " TEXT, "
            + COLUMN_COUPON_CODE + " TEXT, "
            + COLUMN_DATE + " TEXT, "
            + COLUMN_PATIENTNAME + " TEXT, "
            + COLUMN_DISCOUNT + " TEXT, "
            + COLUMN_CHOICE + " TEXT, "
            + COLUMN_CENTER_EMAIL + " TEXT, "
            + COLUMN_TESTGROUP_ID + " TEXT, "
            + COLUMN_PATIENT_ID + " TEXT, "
            + COLUMN_CENTER_PHONE + " TEXT, "
            + COLUMN_CENTER_NAME + " TEXT" +
            ")";
    private static Context c;

    public PastOrdersDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.d("database created", " created");
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(PersonDBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERED_TEST);
        onCreate(db);
    }

}

