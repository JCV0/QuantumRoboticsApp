package com.app.quantumrobotics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.VideoView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import io.github.controlwear.virtual.joystick.android.JoystickView;

public class MainActivity extends AppCompatActivity {
    VideoView videoV;
    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        videoV= findViewById(R.id.video);

        JoystickView joystick = (JoystickView) findViewById(R.id.jV);
        joystick.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {
                // do whatever you want
            }
        });
        videoV.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.video);
        videoV.start();
        videoV.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });

        initJointLay();
    }

    private void initJointLay(){
        final LinearLayout joints_layout= findViewById(R.id.layoutJoints);
        final BottomSheetBehavior<LinearLayout> bottomSheetBehavior= BottomSheetBehavior.from(joints_layout);
        spinner=(Spinner)findViewById(R.id.spinner);
        String [] joints={"1","2","3","4","5"};
        ArrayAdapter <String> adapter= new ArrayAdapter<String>(this, R.layout.spinner_text,joints);
        adapter.setDropDownViewResource(R.layout.dropdown);
        spinner.setAdapter(adapter);

        joints_layout.findViewById(R.id.layoutJoints).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bottomSheetBehavior.getState()!=BottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }else{
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
    }
}