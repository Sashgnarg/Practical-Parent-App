package com.cmpt276.iteration1practicalparent.Timer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cmpt276.iteration1practicalparent.R;

import java.util.Locale;
import java.util.Objects;

public class TimerActivity extends AppCompatActivity {
    //code for timer referenced from the following: https://www.youtube.com/playlist?list=PLrnPJCHvNZuB8wxqXCwKw2_NkyEmFwcSd
    private static final int START_TIME_IN_MILLIS = 6000;

    private TextView txtCountDown;
    private Button btnStartPause;
    private Button btnReset;

    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private int timeLeftInMillis = START_TIME_IN_MILLIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        setTitle("Timer");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        txtCountDown = findViewById(R.id.txtCountDown);
        btnStartPause = findViewById(R.id.btnStartPause);
        btnReset = findViewById(R.id.btnReset);

        btnStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timerRunning){
                    pauseTimer();
                }
                else {
                    startTimer();
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });

        updateCountDownText();

    }

    private void startTimer(){
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = (int) millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                txtCountDown.setText("00:00");
                btnStartPause.setText("Start");
                btnStartPause.setVisibility(View.INVISIBLE);
                btnReset.setVisibility(View.VISIBLE);
            }
        }.start();

        timerRunning = true;
        btnStartPause.setText("Pause");
        btnReset.setVisibility(View.INVISIBLE);
    }

    private void pauseTimer(){
        countDownTimer.cancel();
        timerRunning = false;
        btnStartPause.setText("Start");
        btnReset.setVisibility(View.VISIBLE);
    }

    private void resetTimer(){
        timeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        btnReset.setVisibility(View.INVISIBLE);
        btnStartPause.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText(){
        int minutes = (int) ( timeLeftInMillis / 1000 ) / 60; //convert milliseconds to minutes
        int seconds = (int) ( timeLeftInMillis / 1000 ) % 60; //left over seconds after calculating mins

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        txtCountDown.setText(timeLeftFormatted);

    }
}
