package net.kerul.vrecording2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
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
    private static int VIDEO_RECORDING_CODE=101;
    private Uri videoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //cecking for camera presence
        if (isCameraPresence()){
            //display in log-cat
            Log.i("VIDEO_RECORD_TAG", "YES camera detected");
            getCameraPermission();
        }else{
            //display in log-cat info
            Log.i("VIDEO_RECORD_TAG", "NO camera not  detected");
        }
    }//end oncreate

    public void recordVideoButton(View view){
        recordVideo();
    }


    private boolean isCameraPresence(){//check camera presence
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)){
            return true;//yes camera exist
        }else{
            return false;//no camera exist
        }
    }

    //get camera permission
    private void getCameraPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)==PackageManager.PERMISSION_DENIED){
            //if check permission denied, then request CAMERA permission
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        }
    }

    //call the video recording intent
    private void recordVideo(){
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        activityResultLauncher.launch(intent);

    }

    // ActivityResultLauncher to handle VIDEO RECORDING INTENT
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
            });



    /*@Override
    protected void onActivityResult (int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode, data);
        if (requestCode==VIDEO_RECORDING_CODE{
            if (resultCode==RESULT_OK){
                videoPath=data.getData();
                Log.i("VIDEO_RECORD_TAG", "Recording video is available at path "+videoPath);
            }
            else if (resultCode==RESULT_CANCELED){
                Log.i("VIDEO_RECORD_TAG", "Recording video cancelled");
            }
            else{
                Log.i("VIDEO_RECORD_TAG", "Recording video has got errors");
            }
        }//if request_code video-recording
    }*/

}