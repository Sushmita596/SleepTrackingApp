package com.example.sushmitakumari.sleeptracker;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class AlarmRecordActivity extends Activity {

    private Toast rToast;

    private MediaRecorder mediaRecorder = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String fileName = "record.amr";
        try {
            File SDCardpath = Environment.getExternalStorageDirectory();
            File myDataPath = new File(SDCardpath.getAbsolutePath() + "/media");
            if (!myDataPath.exists())
                myDataPath.mkdirs();
            File recodeFile = new File(SDCardpath.getAbsolutePath() + "/media/"
                    + fileName);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.RECORD_AUDIO },
                        10);
            } else {
                mediaRecorder = new MediaRecorder();
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                mediaRecorder.setOutputFile(recodeFile.getAbsolutePath());
                mediaRecorder.prepare();
                mediaRecorder.start();
            }

            rToast = Toast.makeText(getApplicationContext(), "Start recording",
                    Toast.LENGTH_LONG);
            rToast.show();

            setContentView(R.layout.recording);
            Button stopRecording = (Button) findViewById(R.id.stopRecordBtn);
            stopRecording.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mediaRecorder != null) {
                        rToast = Toast
                                .makeText(getApplicationContext(),
                                        "Stop recording, file saved",
                                        Toast.LENGTH_LONG);
                        rToast.show();
                        mediaRecorder.stop();
                        mediaRecorder.release();
                        mediaRecorder = null;
                    }
                    finish();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
