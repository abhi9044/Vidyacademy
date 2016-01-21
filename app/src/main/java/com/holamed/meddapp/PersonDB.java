package com.holamed.meddapp;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Era on 3/27/2015.
 */
public class PersonDB {
    private PersonDBHelper persondbhelper;
    private SQLiteDatabase person_database;
    private static final int DATABASE_VERSION=4;
    private static final String DATABASE_NAME="people.db";
    private static final String TABLE_PERSON="person";
    private static final String COLUMN_MAIL="email";
    private static final String COLUMN_ID="person_id";
    private static final String COLUMN_NAME="Name";
    private static final String COLUMN_PHONE="phone";
    private static final String COLUMN_ADDRESS="address";
    private static final String COLUMN_AGE="age";
    private static final String COLUMN_GENDER="gender";
    private static final String COLUMN_CITY="city";
    private static final String COLUMN_STATE="state";
    private static final String COLUMN_PINCODE="pincode";
    private static final String COLUMN_COUNTRY="country";
    private static final String COLUMN_USERID="user_id";
    private static Context c;

    public PersonDB(Context context) {
        persondbhelper = new PersonDBHelper(context);
    }
    public void open() throws SQLException {
        person_database = persondbhelper.getWritableDatabase();
    }

    public void close() {
        persondbhelper.close();
    }
    public int getId(String name,long phone)
    {
        String selectQuery = "SELECT "+COLUMN_ID+" FROM "+TABLE_PERSON+" WHERE "+COLUMN_NAME+"='"+name+"' AND "+COLUMN_PHONE+"="+phone;
        Cursor c = person_database.rawQuery(selectQuery,null);
        int id=-1;
        Log.d("in get id","phew");
        if (c.moveToFirst()) {
            id = c.getInt(c.getColumnIndex(COLUMN_ID));
            Log.d("ingetid", String.valueOf(id));

        }
        c.close();
        return id;
    }
    public void deleteAll()
    {
        //SQLiteDatabase db = this.getWritableDatabase();
        // db.delete(TABLE_NAME,null,null);
        person_database.execSQL("delete from "+ TABLE_PERSON);
     // person_database.execSQL("TRUNCATE table" + TABLE_PERSON);
        person_database.close();
    }
    public void addPerson(PersonForm person)
    {
        ContentValues values=new ContentValues();
        values.put(COLUMN_NAME,person.getName());
        values.put(COLUMN_MAIL,person.getEmail());
        values.put(COLUMN_PHONE,person.getPhone());
        values.put(COLUMN_ADDRESS,person.getAddress());
        values.put(COLUMN_GENDER,person.getGender());
        values.put(COLUMN_AGE,person.getAge());
        values.put(COLUMN_CITY,person.getCity());
        values.put(COLUMN_STATE,person.getState());
        values.put(COLUMN_COUNTRY,person.getCountry());
        values.put(COLUMN_PINCODE,person.getPincode());
        values.put(COLUMN_USERID,person.getUser_id());
        Log.d("check db here",person.getUser_id());
       // SQLiteDatabase db=getWritableDatabase();
        person_database.insert(TABLE_PERSON,null,values);
        person_database.close();
    }

    public PersonForm getPhoneUser()
    {   PersonForm phoneuser=new PersonForm();

        String query = "SELECT * FROM " + TABLE_PERSON;
        Log.d("ingetchildren","yes");
        //Cursor points to a location in your results
        Cursor c = person_database.rawQuery(query, null);
        //Move to the first row in your results
        c.moveToFirst();

        //Position after the last row means the end of the results
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex(COLUMN_NAME)) != null) {
                phoneuser.setName(c.getString(c.getColumnIndex(COLUMN_NAME)));

            }
            if (c.getString(c.getColumnIndex(COLUMN_PHONE)) != null) {
                phoneuser.setPhone(Long.parseLong(c.getString(c.getColumnIndex(COLUMN_PHONE))));

            }
                Log.d("agecheck","yo");
            if (c.getString(c.getColumnIndex(COLUMN_AGE)) != null) {
                Log.d("agecheck",c.getString(c.getColumnIndex(COLUMN_AGE)));
                phoneuser.setAge(Integer.parseInt(c.getString(c.getColumnIndex(COLUMN_AGE))));

            }

            if (c.getString(c.getColumnIndex(COLUMN_MAIL)) != null) {
                phoneuser.setEmail(c.getString(c.getColumnIndex(COLUMN_MAIL)));

            }

            if (c.getString(c.getColumnIndex(COLUMN_ADDRESS)) != null) {
                phoneuser.setAddress(c.getString(c.getColumnIndex(COLUMN_ADDRESS)));

            }

            if (c.getString(c.getColumnIndex(COLUMN_PINCODE)) != null) {
                phoneuser.setPincode(c.getString(c.getColumnIndex(COLUMN_PINCODE)));

            }

            if (c.getString(c.getColumnIndex(COLUMN_GENDER)) != null) {
                phoneuser.setGender(c.getString(c.getColumnIndex(COLUMN_GENDER)));
            }
                if (c.getString(c.getColumnIndex(COLUMN_USERID)) != null) {
                    phoneuser.setUser_id(c.getString(c.getColumnIndex(COLUMN_USERID)));
                }
            c.moveToNext();
            return phoneuser;
        }

        person_database.close();

        return null;
    }
    public void updateRow(PersonForm person)
    {
        ContentValues values=new ContentValues();
        //values.put(COLUMN_FIRSTNAME,person.getFirstName());
        //values.put(COLUMN_LASTNAME,person.getLastName());
       // values.put(COLUMN_PHONE,person.getPhone());
        values.put(COLUMN_ADDRESS,person.getAddress());
        values.put(COLUMN_GENDER,person.getGender());
        values.put(COLUMN_AGE,String.valueOf(person.getAge()));
        values.put(COLUMN_CITY,person.getCity());
        values.put(COLUMN_STATE,person.getState());
        values.put(COLUMN_COUNTRY,person.getCountry());
        values.put(COLUMN_PINCODE,person.getPincode());
//        SQLiteDatabase db=getWritableDatabase();
        person_database.update(TABLE_PERSON,values,COLUMN_PHONE+"="+person.getPhone()+ " AND "+COLUMN_NAME+"=\""+person.getName()+"\"",null);
    }
}
