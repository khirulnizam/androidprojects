package net.kerul.videorec;

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
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static int CAMERA_PERMISSION_CODE=100;
    private static int VIDEO_RECORDING_CODE=101;
    private Uri videoPath;
    ImageView animage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //animate video logo
        animage=findViewById(R.id.animage);


        //checking camera presence
        if (isCameraPresence()){
            //display in log-cat
            Log.i("VIDEO_RECORD_TAG", "YES camera detected");
            getCameraPermission();
        }else{
            //display in log-cat info
            Log.i("VIDEO_RECORD_TAG", "NO camera not  detected");
        }
    }//end onCreate

    public void animageOnClick(View view){
        animage.animate().setDuration(1000);
        animage.animate().rotationYBy(360.0f);
        //animage.animate().setDuration(1000);
        //animage.animate().rotationXBy(360.0f);
    }

    private boolean isCameraPresence(){//check camera presence
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)){
            return true;//yes camera exist
        }else{
            return false;//no camera exist
        }
    }//end isCameraPresence

    //get camera permission
    private void getCameraPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)==PackageManager.PERMISSION_DENIED){
            //if check permission denied, then request CAMERA permission
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        }
    }//end getCameraPermission

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

    public void recordVideoButton(View view){
        //call ActivityResultLauncher to invoke Video Recording facilities
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        activityResultLauncher.launch(intent);
    }//end recordVideoButton

}//end MainActivity