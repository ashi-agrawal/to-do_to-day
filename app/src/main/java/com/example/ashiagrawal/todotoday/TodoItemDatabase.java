package com.example.ashiagrawal.todotoday;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ashiagrawal on 6/14/16.
 */
public class TodoItemDatabase extends SQLiteOpenHelper {
    private static TodoItemDatabase database;
    private static final String DATABASE_NAME = "todoDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_TODO = "todolist";
    private static final String KEY_NAME = "task";

    public TodoItemDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized TodoItemDatabase getInstance (Context context) {
        if (database == null) {
            database = new TodoItemDatabase(context.getApplicationContext());
        }
        return database;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TODO_LIST = "CREATE TABLE " + TABLE_TODO + " (" + KEY_NAME + " TEXT" + ")";
        db.execSQL(CREATE_TODO_LIST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_TODO);
        onCreate(db);
    }

    public void addTodo(String todo) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, todo);
        db.insert(TABLE_TODO, null, values);
//        try {
//            db.insertOrThrow(TABLE_TODO, null, values);
//            db.setTransactionSuccessful();
//        } catch (Exception e) {
//            Log.d("can't add", "Error while trying to add item to database");
//        } finally {
//            db.endTransaction();
//        }
        db.endTransaction();
    }

    public void editTodo(String todo, String old) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, todo);
        db.update(TABLE_TODO, values, KEY_NAME + " = ?", new String[]{old});
//        try {
//            db.insertOrThrow(TABLE_TODO, values);
//            db.setTransactionSuccessful();
//        } catch (Exception e) {
//            Log.d(Tag, "Error while trying to add item to database");
//        } finally {
//            db.endTransaction();
//        }
    }

    public void deleteTodo(String toDelete) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_TODO, KEY_NAME + "=" + toDelete, null);
    }
}
