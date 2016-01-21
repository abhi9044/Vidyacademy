package com.holamed.meddapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Era on 5/18/2015.
 */
public class PastOrdersDB {
    private PastOrdersDBHelper pastordersdbhelper;
    private SQLiteDatabase pastorders_database;
    private static final int DATABASE_VERSION=4;
    private static final String COLUMN_DISCOUNT="discount";
    private static final String COLUMN_CHOICE="choice";
    private static final String COLUMN_CENTER_PHONE="phone";
    private static final String COLUMN_CENTER_EMAIL="email";
    private static final String DATABASE_NAME="pastorders";
    private static final String TABLE_ORDERED_TEST="orderedtest";
    private static final String COLUMN_ID="order_id";
    private static final String COLUMN_PATIENTNAME="patient_name";
    private static final String COLUMN_TESTS="tests";
    private static final String COLUMN_AMOUNT="amount";
    private static final String COLUMN_PATIENT_ID="patient_id";
    private static final String COLUMN_TESTGROUP_ID="testgroup_id";

    private static final String COLUMN_CENTER_ADDRESS="center_address";
    private static final String COLUMN_CENTER_NAME="center_name";
    private static final String COLUMN_COUPON_CODE="coupon_code";
    private static final String COLUMN_DATE="booking_date";
    private static Context c;

    public PastOrdersDB(Context context) {
        pastordersdbhelper = new PastOrdersDBHelper(context);
    }
    public void open() throws SQLException {
        pastorders_database = pastordersdbhelper.getWritableDatabase();
        Log.d("past", String.valueOf(pastorders_database));
    }

    public void close() {
        pastordersdbhelper.close();
    }

   public void addOder(PastOrdersObject order)//Object created for orders goes here
   {    ContentValues values=new ContentValues();
       values.put(COLUMN_PATIENTNAME,order.getPatientName());
       values.put(COLUMN_TESTS,order.getTests());
       values.put(COLUMN_AMOUNT,order.getAmount());
       values.put(COLUMN_CENTER_ADDRESS,order.getAddress());
       values.put(COLUMN_CENTER_NAME,order.getCenter());
       values.put(COLUMN_COUPON_CODE,order.getCoupon_code());
       values.put(COLUMN_DATE,order.getDateString());
       values.put(COLUMN_CHOICE,order.getChoice());
       values.put(COLUMN_DISCOUNT,order.getDiscount());
       values.put(COLUMN_PATIENT_ID,order.getPatient_id());
       values.put(COLUMN_TESTGROUP_ID,order.getTestgroup_id());
       values.put(COLUMN_CENTER_PHONE,order.getPhone());
       values.put(COLUMN_CENTER_EMAIL,order.getEmail());
       Log.i("checkdbetst",String.valueOf(order.getTestgroup_id()));
       // SQLiteDatabase db=getWritableDatabase();
       pastorders_database.insert(TABLE_ORDERED_TEST, null, values);
       pastorders_database.close();
   }
    public ArrayList<PastOrdersObject> getAll()//ArrayList<Object created for orders>
    {
          ArrayList<PastOrdersObject> allPastOrders=new ArrayList<>();
        Cursor cursor = pastorders_database.rawQuery("select * from "+TABLE_ORDERED_TEST,null);

        if (cursor .moveToFirst()) {

            while (cursor.isAfterLast() == false) {
                PastOrdersObject a=new PastOrdersObject();
                a.setCenter(cursor.getString(cursor
                        .getColumnIndex(COLUMN_CENTER_NAME)));
                a.setAddress(cursor.getString(cursor
                        .getColumnIndex(COLUMN_CENTER_ADDRESS)));
                a.setDateString(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                a.setTests(cursor.getString(cursor
                        .getColumnIndex(COLUMN_TESTS)));
                a.setCoupon_code(cursor.getString(cursor
                        .getColumnIndex(COLUMN_COUPON_CODE)));
                a.setAmount(cursor.getString(cursor
                        .getColumnIndex(COLUMN_AMOUNT)));
                a.setPatientName(cursor.getString(cursor
                        .getColumnIndex(COLUMN_PATIENTNAME)));
                a.setChoice(cursor.getString(cursor
                        .getColumnIndex(COLUMN_CHOICE)));
                a.setDiscount(cursor.getString(cursor
                        .getColumnIndex(COLUMN_DISCOUNT)));
                a.setPhone(cursor.getString(cursor
                        .getColumnIndex(COLUMN_CENTER_EMAIL)));
                a.setTestgroup_id(cursor.getString(cursor
                        .getColumnIndex(COLUMN_TESTGROUP_ID)));
                a.setPatient_id(cursor.getString(cursor
                        .getColumnIndex(COLUMN_PATIENT_ID)));
                allPastOrders.add(a);
                cursor.moveToNext();
            }
        }
        cursor.close();
        pastorders_database.close();
          return allPastOrders;
    }
}
