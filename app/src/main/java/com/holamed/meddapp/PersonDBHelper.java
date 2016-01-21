package com.holamed.meddapp;
/**
 * Created by Era on 4/13/2015.
 */import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PersonDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=4;
    private static final String DATABASE_NAME="people.db";
    private static final String TABLE_PERSON="person";
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
    private static final String COLUMN_MAIL="email";
    private static final String COLUMN_USERID="user_id";
    // Database creation sql statement
    private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_PERSON + "("
            + COLUMN_ID + " INTEGER AUTO INCREMENT, "
            + COLUMN_NAME + " TEXT, "
            + COLUMN_MAIL + " TEXT, "
            + COLUMN_PHONE + " INTEGER, " +
            COLUMN_AGE+" INTEGER, "+
            COLUMN_GENDER+" TEXT, "+
            COLUMN_ADDRESS+" TEXT, "+
            COLUMN_CITY+" TEXT, "+
            COLUMN_STATE+" TEXT, "+
            COLUMN_COUNTRY+" TEXT, "+
            COLUMN_USERID+" TEXT, "+
            COLUMN_PINCODE+" TEXT"+
            ");";
    private static Context c;
    public PersonDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.d("database created"," created");
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(PersonDBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSON);
        onCreate(db);
    }

}
