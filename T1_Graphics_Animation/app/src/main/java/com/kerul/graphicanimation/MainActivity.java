package com.kerul.graphicanimation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        imageView =findViewById(R.id.imageView);
    }

    //animate
    public void animOnClick(View view){
        imageView.animate().setDuration(1000);//miliseconds
        imageView.animate().rotationYBy(360f);
    }
}