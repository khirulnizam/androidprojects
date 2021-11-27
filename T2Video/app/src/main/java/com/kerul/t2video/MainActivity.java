package com.kerul.t2video;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static int CAMERA_PERMISSION_CODE=100;
    private Uri videoPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        if(isCameraPresence()==true){
            Toast.makeText(this,"YA kamera wujud",Toast.LENGTH_LONG).show();
            getCameraPermission();
        }else{
            Toast.makeText(this,"TIADA kamera",Toast.LENGTH_LONG).show();
        }
    }

    //check camera wujud
    private boolean isCameraPresence(){
        if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)){
            return true;
        }else{
            return false;
        }
    }//end isCameraPresence

    //dapatkan permission penggunaan kamera
    private void getCameraPermission(){
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)==PackageManager.PERMISSION_DENIED){
            //request permission sekali lagi
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_CODE);
        }
    }//end getCameraPermission

    public void onClickRecordVideo(View view){
        //call ActivityResultLauncher to invoke Video Recording facilities
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        activityResultLauncher.launch(intent);
    }//end recordVideoButton

    // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        //recordVideo();

                        videoPath=data.getData();
                        Toast.makeText(MainActivity.this,"Recording video is available at path "+videoPath,
                                Toast.LENGTH_LONG).show();
                        Log.i("VIDEO_RECORD_TAG", "Recording video is available at path "+videoPath);
                    }
                    else if (result.getResultCode() == Activity.RESULT_CANCELED){
                        Toast.makeText(MainActivity.this,"Recording video cancelled",
                                Toast.LENGTH_LONG).show();
                        Log.i("VIDEO_RECORD_TAG", "Recording video cancelled");
                    }
                    else{
                        Log.i("VIDEO_RECORD_TAG", "Recording video has got errors");
                    }

                }
            });//end ActivityResultLauncher
}