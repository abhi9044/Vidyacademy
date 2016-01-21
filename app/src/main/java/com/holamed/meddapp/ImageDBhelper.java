package com.holamed.meddapp;

/**
 * Created by Pradeep on 23-05-2015.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

public class ImageDBhelper {

    public static final String LOCAL_ID = "id";
    public static final String NAME = "labName";
    public static final String ID= "labID";
    public static final String PHOTO = "image";
    public static final String TYPE="type";

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "labimage.db";
    private static final int DATABASE_VERSION = 4;

    private static final String IMAGE_TABLE = "LabImages";

    private static final String CREATE_IMAGE_TABLE = "create table "
            + IMAGE_TABLE + " (" + LOCAL_ID
            + " integer primary key autoincrement, " + PHOTO
            + " blob not null, " + ID + " text not null unique, "
            +NAME + " text, "+ TYPE+" text);";

    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_IMAGE_TABLE);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + IMAGE_TABLE);
            onCreate(db);
        }
    }

    public void Reset() {
        mDbHelper.onUpgrade(this.mDb, 1, 1);
    }

    public ImageDBhelper(Context ctx) {
        mCtx = ctx;
        mDbHelper = new DatabaseHelper(mCtx);
    }

    public ImageDBhelper open() throws SQLException {

        mDb = mDbHelper.getWritableDatabase();
        Log.d("ImgDB","Opened");
        return this;
    }

    public boolean isOpen(){
        try {
            return mDb.isOpen();
        }
        catch (NullPointerException e){
            return  false;
        }
    }
    public void close() {
        mDbHelper.close();
        Log.d("ImgDB", "closed");
    }

    public void insertElement(ImageDBElement element) {
       try {
           ContentValues cv = new ContentValues();
           cv.put(PHOTO, ImageUtility.getBytes(element.getImage()));
           cv.put(NAME, element.getName());
           cv.put(ID, element.getId());
           cv.put(TYPE,element.getType());
           mDb.insertOrThrow(IMAGE_TABLE, null, cv);
       }
       catch (Exception e){

           Log.d("imgDbHelper","error in insert");
           e.printStackTrace();
       }
    }

    public ImageDBElement retriveElement(String searchID,String searchType) throws SQLException {
        try {
            /*
            Cursor cur = mDb.query(true, IMAGE_TABLE, new String[]{LAB_PHOTO,
                    LAB_NAME, LAB_ID}, null, null, null, null, null, null);
                */
            String q="select * from "+IMAGE_TABLE+" where "+ID+"='"+searchID+"' AND "+TYPE+"='"+searchType+"'";
            Log.d("img sqlite", q);
            Cursor cur=mDb.rawQuery(q,null);
            if (cur.moveToFirst()) {
               // while (!cur.isLast()) {
                    // byte[] blob = cur.getBlob(cur.getColumnIndex(EMP_PHOTO));

                    String currID = cur.getString(cur.getColumnIndex(ID));
                    //int age = cur.getInt(cur.getColumnIndex(EMP_AGE));
                    //if (currID == labID) {
                        byte[] blob = cur.getBlob(cur.getColumnIndex(PHOTO));
                        String name = cur.getString(cur.getColumnIndex(NAME));
                        String currType=cur.getString(cur.getColumnIndex(TYPE));
                        cur.close();

                        return new ImageDBElement(ImageUtility.getPhoto(blob), name, currID,currType);
                    //}
               // }


            }

            cur.close();
            return null;
        }
        catch (Exception e){
            Log.d("imgDBhelper", "Error in retriving");
            e.printStackTrace();
            return null;

        }

    }

    public boolean isInDb(String searchID,String searchType) {
        String q = "select * from " + IMAGE_TABLE + " where " + ID + "='" + searchID+"' AND "+TYPE+"='"+searchType+"'";
        Log.d("img sqlite", q);
        try {
            Cursor cur = mDb.rawQuery(q, null);
            if (cur.moveToFirst()) {
                return true;
            }
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
