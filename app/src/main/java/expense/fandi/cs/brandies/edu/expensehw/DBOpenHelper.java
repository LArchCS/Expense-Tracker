package expense.fandi.cs.brandies.edu.expensehw;

import android.content.Context;
import android.content.*;
import android.database.Cursor;
import android.database.sqlite.*;
import android.util.Log;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by difan on 11/20/2017.
 */

public class DBOpenHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "myDatabase.db1";
    static final String DATABASE_TABLE = "ExpenseTracker";
    static final int DATABASE_VERSION = 1;

    int ID = 0;
    static final String KEY_ID = "_id";
    static final String EXPENSE = "expense";
    static final String NOTE = "note";
    static final String TIME = "time";

    static final String DATABASE_CREATE = "create table "
            + DATABASE_TABLE
            + "("
            + KEY_ID + " integer primary key autoincrement, "
            + EXPENSE + " text not null, "
            + NOTE + " text,"
            + TIME  + " text not null"
            + ");";

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.e("Database operation: ", "Database created or opened");
    }

    /* Called when no database exists in disk, and the helper class needs to create a new one. */
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
        Log.e("Database operation: ", "Database created");
    }

    /* Called when there is a database version mismatch */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /*public SQLiteDatabase getWritableDatabase() {
        return null;
    }*/

    /*public SQLiteDatabase getReadableDatabase() {
        return null;
    }*/

    public void insertExpense(String expense, String note) {
        insertExpense(expense, note, null);
    }

    public void insertExpense(String expense, String note, String time) {
        ContentValues newValues = new ContentValues();
        // assign values for each row
        ID += 1;
        if (time == null) {
            time = (new Date()).toString();
        }
        //newValues.put(KEY_ID, ID);
        newValues.put(EXPENSE, expense);
        newValues.put(NOTE, note);
        newValues.put(TIME, time);
        // insert the row into table
        SQLiteDatabase db = getWritableDatabase();
        db.insert(DATABASE_TABLE, null, newValues);
        Log.e("Database operation: ", "One row inserted");
    }

    public void deleteExpense(int expenseId) {
        String where = KEY_ID + " = "  + expenseId;
        SQLiteDatabase db = getWritableDatabase();
        db.delete(DATABASE_TABLE, where, null);
        Log.e("Database operation: ", "One row deleted");
    }

    public void deteleteExpenses() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(DATABASE_TABLE, null, null);
    }

    public Cursor getAllExpenses() {
        String[] cols = {KEY_ID, EXPENSE, NOTE, TIME};
        Log.e("table schema: ", Arrays.toString(cols));
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(DATABASE_TABLE, cols, null, null, null, null, null);
        return cursor;
    }
}