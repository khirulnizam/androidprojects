package kerul.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


// creating a database helper class
public class DBHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;

    // declaring name of the database
    static final String DATABASE_NAME = "userdb";

    // declaring table name of the database
    static final String TABLE_NAME = "users";

    // declaring version of the database
    static final int DATABASE_VERSION = 1;

    // sql query to create the table
    static final String CREATE_DB_TABLE = " CREATE TABLE " + TABLE_NAME
            + " (id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + " name TEXT NOT NULL);";

    // defining a constructor
    DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // creating a table in the database
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_DB_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // sql query to drop a table
        // having similar name
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //add new user
    public void addNewUser(String username) {

        // writing data in our sqlite database.
        SQLiteDatabase db = this.getWritableDatabase();

        // variable for content values.
        ContentValues values = new ContentValues();

        // data key and value pair.
        values.put("name" /*fieldname*/, username /*value*/);

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }//end addNewUser
}//end DBHelper class
