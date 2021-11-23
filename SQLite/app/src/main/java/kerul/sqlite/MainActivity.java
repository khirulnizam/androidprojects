package kerul.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText textName;
    private TextView res;
    private DBHelper dbhelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //textbox name
        textName=findViewById(R.id.textName);
        res=findViewById(R.id.res);

        //dbhelper initilize
        dbhelper = new DBHelper(MainActivity.this);
    }//end onCreate

    public void onClickAddRecord(View view){
        String name=textName.getText().toString();

        if (name.isEmpty()){//name isempty
            //alert to key-in name
            Toast.makeText(MainActivity.this, "Please enter all the data..",
                    Toast.LENGTH_SHORT).show();
        }else{
            dbhelper.addNewUser(name);
            Toast.makeText(MainActivity.this, "New record name added to sqlite ..",
                    Toast.LENGTH_SHORT).show();
        }

    }//onClickAddRecord

    private String TABLE_NAME="users";

    @SuppressLint("Range")
    public void onClickShowRecords(View view){
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursorUser = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        // iteration of the cursor
        // to print whole table
        if(cursorUser.moveToFirst()) {//start with first record
            StringBuilder strBuild=new StringBuilder();
            while (!cursorUser.isAfterLast()) {
                strBuild.append("\n"+cursorUser.getString(cursorUser.getColumnIndex("id"))+ "-"
                        + cursorUser.getString(cursorUser.getColumnIndex("name")));
                cursorUser.moveToNext();
            }
            res.setText(strBuild);//display all records to TextView
        }
        else {
            res.setText("No Records Found");
        }
    }
}