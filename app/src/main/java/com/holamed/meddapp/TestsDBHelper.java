package com.holamed.meddapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Era on 4/25/2015.
 */
public class TestsDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "tests4lessmanager";

    // Testss table name
    private static final String TABLE_tests = "tests";

    // tests Table Columns names
    private static final String ID = "row_id";
    private static final String TEST_NAME = "name";
    private static final String TEST_ID = "_id";
    private static final String TEST_TYPE = "type";
    private static final String TEST_FREQUENCY = "fre";
    private static final String TEST_ALIASES = "aliases";


    private static Context c;
    public TestsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_testS_TABLE = "CREATE TABLE " + TABLE_tests + "("
                + ID + " INTEGER AUTO INCREMENT," + TEST_NAME + " TEXT,"
                + TEST_ID + " TEXT," +  TEST_TYPE +" TEXT," +  TEST_FREQUENCY +" INT,"+ TEST_ALIASES +" TEXT"+")";
        db.execSQL(CREATE_testS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TestsDBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_tests);
        onCreate(db);
    }

}
