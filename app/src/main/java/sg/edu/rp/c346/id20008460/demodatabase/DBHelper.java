package sg.edu.rp.c346.id20008460.demodatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VER = 1;
    private static final String DATABASE_NAME = "task.db" ;

    private static final String TABLE_TASK = "task";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_DESCRPTION = "description";
    private static final String COLUMN_DATE = "date";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { // when database is not there
        // CREATE TABLE Task (_id INTEGER PRIMARY KEY AUTOINCREMENT, description TEXT , date TEXT)
        String createTableSql = "CREATE TABLE " + TABLE_TASK + "("
        + COLUMN_ID + " " + "INTEGER PRIMARY KEY AUTOINCREMENT,"
        + COLUMN_DESCRPTION + " TEXT,"
        + COLUMN_DATE + " TEXT)" ;

        db.execSQL(createTableSql);
        Log.i("info", "creating tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { // will be called if dataversion changed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        onCreate(db);
    }

    public void insertTask(String description, String date) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_DESCRPTION, description);
        values.put(COLUMN_DATE, date);
         db.insert(TABLE_TASK, null, values);
         db.close();
    }

    public ArrayList<String> getTaskContent() {
        ArrayList<String> tasks = new ArrayList<>();
        //SELECT description FROM TASK

        String selectQuery = "SELECT " + COLUMN_DESCRPTION + " FROM " + TABLE_TASK ;
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                tasks.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return tasks;
    }

    public ArrayList<task> getTasks(boolean asc) {
        ArrayList<task> tasks = new ArrayList<task>();
        String selectQuery = "SELECT " + COLUMN_ID + ", "
                + COLUMN_DESCRPTION + ", "
                + COLUMN_DATE
                + " FROM " + TABLE_TASK
                + " ORDER BY " +  COLUMN_DESCRPTION;

        if (asc) {
            selectQuery += " ASC ";

        } else {
            selectQuery += " DESC ";
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String description = cursor.getString(1);
                String date = cursor.getString(2);
                task obj = new task(id, description, date);
                tasks.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tasks;

    }

}
