package com.cmpt276.iteration1practicalparent.Timer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cmpt276.iteration1practicalparent.R;

import java.util.Locale;
import java.util.Objects;

/*
 * Timer activity gives user the option to set a timer for a whole number of minutes
 * or easily select between the options of: 1,2,3,5,10 minutes
 * Allows for pausing and resetting & continues to run when on a different activity or exiting the app
 * */

public class TimerActivity extends AppCompatActivity {
    //code for timer referenced from the following: https://www.youtube.com/playlist?list=PLrnPJCHvNZuB8wxqXCwKw2_NkyEmFwcSd
    private EditText editTextMinutesInput;
    private TextView txtCountDown;
    private Button btnSet;
    private Button btnStartPause;
    private Button btnReset;

    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private long startTimeInMillis;
    private long timeLeftInMillis = startTimeInMillis;
    private long endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        setTitle("Timer");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        editTextMinutesInput = findViewById(R.id.editTextTimerInput);
        txtCountDown = findViewById(R.id.txtCountDown);
        btnStartPause = findViewById(R.id.btnStartPause);
        btnReset = findViewById(R.id.btnReset);
        btnSet = findViewById(R.id.btnSetMinutes);

        Button btnOneMin = findViewById(R.id.btnOneMin);
        Button btnTwoMin = findViewById(R.id.btnTwoMin);
        Button btnThreeMin = findViewById(R.id.btnThreeMin);
        Button btnFiveMin = findViewById(R.id.btnFiveMin);
        Button btnTenMin = findViewById(R.id.btnTenMin);

        btnOneMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTime(60000);
            }
        });

        btnTwoMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTime(120000);
            }
        });

        btnThreeMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTime(180000);
            }
        });

        btnFiveMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTime(300000);
            }
        });

        btnTenMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTime(600000);
            }
        });

        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = editTextMinutesInput.getText().toString();
                if (input.length() == 0){
                    Toast.makeText(TimerActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                long millisInput = Long.parseLong(input) * 60000;
                if (millisInput == 0){
                    Toast.makeText(TimerActivity.this, "Please enter a positive number", Toast.LENGTH_SHORT).show();
                    return;
                }
                setTime(millisInput);
                editTextMinutesInput.setText("");

            }
        });

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

    private void setTime(long milliseconds){
        startTimeInMillis = milliseconds;
        resetTimer();
    }

    private void startTimer(){
        endTime = System.currentTimeMillis() + timeLeftInMillis;
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                txtCountDown.setText("00:00");
                MediaPlayer timerSound = MediaPlayer.create(TimerActivity.this, R.raw.timer_sound);
                timerSound.start();
                Vibrator Vibration = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                Vibration.vibrate(2000);
                sendNotification();
                updateButtons();
            }
        }.start();

        timerRunning = true;
        updateButtons();
    }

    private void pauseTimer(){
        countDownTimer.cancel();
        timerRunning = false;
        updateButtons();
    }

    private void resetTimer(){
        timeLeftInMillis = startTimeInMillis;
        updateCountDownText();
        updateButtons();
        closeKeyboard();
    }

    private void updateCountDownText(){
        int hours = (int) (timeLeftInMillis / 1000 ) /3600;
        int minutes = (int) (( timeLeftInMillis / 1000 ) % 3600) / 60; //left over minutes after calculating hours
        int seconds = (int) ( timeLeftInMillis / 1000 ) % 60; //left over seconds after calculating mins

        String timeLeftFormatted;
        if (hours > 0){
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        }
        else{
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);

        }
        txtCountDown.setText(timeLeftFormatted);
    }

    private void updateButtons() {
        if (timerRunning){
            editTextMinutesInput.setVisibility(View.INVISIBLE);
            btnSet.setVisibility(View.INVISIBLE);
            btnReset.setVisibility(View.INVISIBLE);
            btnStartPause.setText("Pause");
        }
        //three cases: either timer finished, timer is at full time, or timer is somewhere in the middle
        else{
            editTextMinutesInput.setVisibility(View.VISIBLE);
            btnSet.setVisibility(View.VISIBLE);
            btnStartPause.setText("Start");

            if (timeLeftInMillis < 1000){
                btnStartPause.setVisibility(View.INVISIBLE);
            }
            else{
                btnStartPause.setVisibility(View.VISIBLE);
            }

            if (timeLeftInMillis < startTimeInMillis){
                btnReset.setVisibility(View.VISIBLE);
            }
            else{
                btnReset.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    //tutorial for code: https://www.youtube.com/watch?v=ATERxKKORbY
    private void sendNotification(){
        // Builds your notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Practical Parent: Timer Ended")
                .setContentText("Your timer has reached zero!");

        // Creates the intent needed to show the notification
        Intent notificationIntent = new Intent(this, TimerActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("startTimeInMillis", startTimeInMillis);
        editor.putLong("millisLeft", timeLeftInMillis);
        editor.putBoolean("timerRunning", timerRunning);
        editor.putLong("endTime", endTime);

        editor.apply();

        if (countDownTimer != null){
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        startTimeInMillis = prefs.getLong("startTimeInMillis", 600000);
        timeLeftInMillis = prefs.getLong("millisLeft", startTimeInMillis);
        timerRunning = prefs.getBoolean("timerRunning", false);

        updateCountDownText();
        updateButtons();

        if (timerRunning){
            endTime = prefs.getLong("endTime", 0);
            timeLeftInMillis = endTime - System.currentTimeMillis();

            if (timeLeftInMillis < 0) {
                timeLeftInMillis = 0;
                timerRunning = false;
                updateCountDownText();
                updateButtons();
            }
            else {
                startTimer();
            }
        }
    }
}

