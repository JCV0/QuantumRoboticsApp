package com.app.quantumrobotics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import io.github.controlwear.virtual.joystick.android.JoystickView;

public class MainActivity extends AppCompatActivity {
    VideoView videoV;
    private Spinner spinner;
    SeekBar seekBar;
    TextView seekTV;
    Button configbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        videoV= findViewById(R.id.video);
        configbtn= (Button)findViewById(R.id.Configs);
        configbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openConfig();
            }
        });
        //Joystick
        JoystickView joystick = (JoystickView) findViewById(R.id.jV);
        joystick.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {
                // Direccion del rover
            }
        });

        //Configuración de la cámara (Video)
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

    //Layout de move joint
    private void initJointLay(){
        final LinearLayout joints_layout= findViewById(R.id.layoutJoints);
        final BottomSheetBehavior<LinearLayout> bottomSheetBehavior= BottomSheetBehavior.from(joints_layout);
        seekBar=(SeekBar)findViewById(R.id.seekBar);
        seekTV=(TextView)findViewById(R.id.textViewSB);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekTV.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

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
    private void openConfig(){
        Intent intent= new Intent(this,Configurations2.class);
        startActivity(intent);
    }
}