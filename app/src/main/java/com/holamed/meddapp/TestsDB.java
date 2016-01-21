package com.holamed.meddapp;

//created by prabhat

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TestsDB  {
    private TestsDBHelper testsdbhelper;
    private SQLiteDatabase test_database;
    // All Static variables
	// Database Versio
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


    public TestsDB(Context context) {
//		super(context, DATABASE_NAME, null, DATABASE_VERSION);
        testsdbhelper = new TestsDBHelper(context);

    }
//    public PersonDB(Context context) {
  //  }
    public void open() throws SQLException {
        test_database = testsdbhelper.getWritableDatabase();
    }

    public void close() {
        testsdbhelper.close();
    }


    // Creating Tables
	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new test
	void addTests(TestsTableSqlite tests) {
		//SQLiteDatabase db = this.getWritableDatabase();
   		ContentValues values = new ContentValues();
		values.put(TEST_NAME, tests.getName()); // test Name
		values.put(TEST_ID, tests.getKey()); // test Phone
        values.put(TEST_TYPE, tests.getType()); // test type
        values.put(TEST_FREQUENCY, tests.getfre()); // test type
        values.put(TEST_ALIASES, tests.getaliases()); // test type

        // Inserting Row
        test_database.insert(TABLE_tests, null, values);
		test_database.close(); // Closing database connection
	}

	// Getting single test
	TestsTableSqlite getTests(int id) {
	//	SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = test_database.query(TABLE_tests, new String[] { ID,
				TEST_NAME, TEST_ID,TEST_TYPE,TEST_FREQUENCY,TEST_ALIASES}, ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		TestsTableSqlite test = new TestsTableSqlite(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getInt(4),cursor.getString(5));
        cursor.close();
        test_database.close();
		// return test
		return test;
	}
	
	// Getting All tests
	public List<TestsTableSqlite> getAllTests() {
		List<TestsTableSqlite> testList = new ArrayList<TestsTableSqlite>();
		// Select All Query
   		String selectQuery = "SELECT  * FROM " + TABLE_tests + " ORDER BY " + "CAST( "+TEST_FREQUENCY+" AS REAL )" +" DESC";

		//SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = test_database.rawQuery(selectQuery,null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				TestsTableSqlite test = new TestsTableSqlite();
			//	test.setID(Integer.parseInt(cursor.getString(0)));
				test.setName(cursor.getString(1));
				test.setKey(cursor.getString(2));
                test.setType(cursor.getString(3));
                test.setfre(cursor.getInt(4));
                test.setaliases(cursor.getString(5));

                // Adding test to list
				testList.add(test);
			} while (cursor.moveToNext());
		}

        cursor.close();
        test_database.close();
		// return test list
		return testList;
	}

	// Updating single test
	public int updateTest(TestsTableSqlite tests) {
		//SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(TEST_NAME, tests.getName());
		values.put(TEST_ID, tests.getKey());
        values.put(TEST_TYPE, tests.getType());
        values.put(TEST_FREQUENCY, tests.getfre());
        values.put(TEST_ALIASES, tests.getaliases());

		// updating row
		return test_database.update(TABLE_tests, values, ID + " = ?",
				new String[] { String.valueOf(tests.getID()) });
	}

	// Deleting single test
	public void deleteTest(TestsTableSqlite tests) {
		//SQLiteDatabase db = this.getWritableDatabase();
        test_database.delete(TABLE_tests, ID + " = ?",
				new String[] { String.valueOf(tests.getID()) });
        test_database.close();
	}


	// Getting tests Count
	public int getTestsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_tests;
	//	SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = test_database.rawQuery(countQuery, null);
		cursor.close();
         test_database.close();
		// return count
		return cursor.getCount();
	}
  public String getTestId(String testname)
  {
      Cursor cursor = test_database.query(TABLE_tests, new String[] { ID,
                      TEST_NAME, TEST_ID,TEST_FREQUENCY,TEST_ALIASES }, TEST_NAME + "=?",
              new String[] { testname }, null, null, null, null);
      if (cursor != null)
          cursor.moveToFirst();

      TestsTableSqlite test = new TestsTableSqlite();
      test.setName(cursor.getString(1));
      test.setKey(cursor.getString(2));
      cursor.close();
      test_database.close();
      // return test
      return test.getKey();

  }
    public String getTesttype(String testname)
    {
        Cursor cursor = test_database.query(TABLE_tests, new String[] { ID,
                        TEST_NAME, TEST_ID,TEST_TYPE,TEST_FREQUENCY}, TEST_NAME + "=?",
                new String[] { testname }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        TestsTableSqlite test = new TestsTableSqlite();
        test.setName(cursor.getString(1));
        test.setKey(cursor.getString(2));
        test.setType(cursor.getString(3));


        cursor.close();
        test_database.close();
        // return test
        return test.getType();

    }

}

