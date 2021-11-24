package net.kerul.appconsumedata;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    //URI from app that has ContentProvider class
    Uri CONTENT_URI = Uri.parse("content://net.kerul.appcontentproviderdata.provider/users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @SuppressLint("Range")
    public void onClickShowDetails(View view) {
        // inserting complete table details in this text field
        TextView resultView= (TextView) findViewById(R.id.res);

        // creating a cursor object of the
        // content URI
        Cursor cursor;
        try {
            cursor = getContentResolver().query(Uri.parse("content://net.kerul.appcontentproviderdata.provider/users"), null, null, null, null);


            // iteration of the cursor
            // to print whole table
            if (cursor.moveToFirst()) {
                StringBuilder strBuild = new StringBuilder();
                while (!cursor.isAfterLast()) {
                    strBuild.append("\n" + cursor.getString(cursor.getColumnIndex("id")) + "-" + cursor.getString(cursor.getColumnIndex("name")));
                    cursor.moveToNext();
                }
                resultView.setText(strBuild);
            } else {
                resultView.setText("No Records Found");
            }
        }catch(Exception ex) {
            Log.e("ContentResolver", ex.toString());
        }//end exception
    }
}
