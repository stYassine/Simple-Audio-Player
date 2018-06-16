package com.example.yassine.audioplayer;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private TextView time_passed;
    private TextView total_duration;
    private Button btn_prev;
    private Button btn_play;
    private Button btn_next;
    private Thread thread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUI();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if(fromUser){
                    mediaPlayer.seekTo(progress);

                    int currentPos =mediaPlayer.getCurrentPosition();
                    int duration =mediaPlayer.getDuration();

                    SimpleDateFormat simpleDateFormat =new SimpleDateFormat("mm ss");

                    String timePassedValue =String.valueOf(simpleDateFormat.format(new Date(currentPos)));
                    String totalDurationValue =String.valueOf(simpleDateFormat.format(new Date(duration - currentPos)));

                    time_passed.setText(timePassedValue);
                    total_duration.setText(totalDurationValue);

                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    public void setupUI(){

        mediaPlayer =new MediaPlayer();
        mediaPlayer =MediaPlayer.create(getApplicationContext(), R.raw.song);

        seekBar =(SeekBar) findViewById(R.id.seekBar);
        time_passed =(TextView) findViewById(R.id.time_passed);
        total_duration =(TextView) findViewById(R.id.total_duration);

        btn_prev =(Button) findViewById(R.id.btn_prev);
        btn_play =(Button) findViewById(R.id.btn_play);
        btn_next =(Button) findViewById(R.id.btn_next);

        btn_prev.setOnClickListener(this);
        btn_play.setOnClickListener(this);
        btn_next.setOnClickListener(this);

    }

    public void playAudio(){
        if(mediaPlayer != null){
            mediaPlayer.start();
            setupUiThread();
            btn_play.setBackgroundResource(R.drawable.ic_pause);
        }
    }

    public void pauseAudio(){
        if(mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            btn_play.setBackgroundResource(R.drawable.ic_play);
        }
    }


    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.btn_prev:
                /// CODE
                break;
            case R.id.btn_play:
                if(mediaPlayer.isPlaying()){
                    pauseAudio();
                }else{
                    playAudio();
                }
                break;
            case R.id.btn_next:
                /// CODE
                break;
        }

    }


    public void setupUiThread(){

        thread =new Thread(){

            @Override
            public void run() {

                try {


                    while(mediaPlayer != null && mediaPlayer.isPlaying()){
                        Thread.sleep(500);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                int currentPos =mediaPlayer.getCurrentPosition();
                                int duration =mediaPlayer.getDuration();

                                seekBar.setMax(duration);
                                seekBar.setProgress(currentPos);

                                SimpleDateFormat simpleDateFormat =new SimpleDateFormat("mm ss");

                                time_passed.setText(String.valueOf(simpleDateFormat.format(new Date((currentPos)))));
                                total_duration.setText(String.valueOf(simpleDateFormat.format(new Date((duration - currentPos)))));

                            }
                        });

                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        thread.start();

    }



}
