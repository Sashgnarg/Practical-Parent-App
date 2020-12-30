package com.cmpt276.iteration1practicalparent.UI.Timer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cmpt276.iteration1practicalparent.R;

import java.util.Locale;

/*
 * Timer activity gives user the option to set a timer for a whole number of minutes
 * or easily select between the options of: 1,2,3,5,10 minutes
 * Allows for pausing and resetting & continues to run when on a different activity or exiting the app
 * */

public class TimerActivity extends AppCompatActivity {
    public static final int TIME_BETWEEN_DECREMENTS_SPEED_LESS_THAN_100 = 2000;
    public static final int TIME_BETWEEN_DECREMENTS_SPEED_GREATER_THAN_100 = 500;
    //code for timer referenced from the following: https://www.youtube.com/playlist?list=PLrnPJCHvNZuB8wxqXCwKw2_NkyEmFwcSd
    private EditText editTextMinutesInput;
    private TextView txtCountDown;
    private Button btnSet;
    private Button btnStartPause;
    private Button btnReset;
    private ProgressBar progressBar;

    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private long startTimeInMillis;
    private long timeLeftInMillis = startTimeInMillis;
    private long endTime;
    private boolean clicked25;
    private boolean clicked50;
    private boolean clicked75;
    private boolean clicked100;
    private boolean clicked200;
    private boolean clicked300;
    private boolean clicked400;
    float speedPerc;



    Toolbar myToolbar;
    Spinner mySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitle("Timer");
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mySpinner = (Spinner) findViewById(R.id.spinner);
        editTextMinutesInput = findViewById(R.id.editTextTimerInput);
        txtCountDown = findViewById(R.id.txtCountDown);
        btnStartPause = findViewById(R.id.btnStartPause);
        btnReset = findViewById(R.id.btnReset);
        btnSet = findViewById(R.id.btnSetMinutes);
        progressBar = findViewById(R.id.countDownProgress);

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
                editTextMinutesInput.setText(""); }
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
        setupSpinner();

    }

    private void setTime(long milliseconds){
        startTimeInMillis = milliseconds;
        resetTimer();
    }

    private void startTimer(){
        endTime = System.currentTimeMillis() + timeLeftInMillis;
        if(clicked25!=true&&clicked50!=true&&clicked75!=true&&clicked100!=true&&clicked200!=true&&clicked300!=true&&clicked400!=true) {
            clicked100 = true;
        }
        timerRunning = true;
        setTimer();
        updateButtons();
    }

    private void pauseTimer(){
        timerRunning = false;
        updateButtons();

    }

    private void resetTimer(){
        timeLeftInMillis = startTimeInMillis;
        updateCountDownText();
        updateButtons();
        closeKeyboard();
        setProgressBarValues();
    }

    private void updateCountDownText(){
        int hours = (int) (timeLeftInMillis / 1000 ) /3600;
        int minutes = (int) (( timeLeftInMillis / 1000 ) % 3600) / 60; //left over minutes after calculating hours
        int seconds = (int) ( timeLeftInMillis / 1000 ) % 60; //left over seconds after calculating mins


        /*if (clicked25){

            setSpeed(25);
        }
        if (clicked50){

            setSpeed(50);
        }
        if(clicked100) {
            setSpeed(100);
        }

        if(clicked75){
            setSpeed(75);
        }

        if (clicked200){

            setSpeed(200);
        }
        if (clicked300){

            setSpeed(300);
        }
        if (clicked400){

            setSpeed(400);
        }*/

        String timeLeftFormatted;
        {
            if (hours > 0) {
                timeLeftFormatted = String.format(Locale.getDefault(),
                        "%d:%02d:%02d", hours, minutes, seconds);
            } else {
                timeLeftFormatted = String.format(Locale.getDefault(),
                        "%02d:%02d", minutes, seconds);

            }
            txtCountDown.setText(timeLeftFormatted);
        }


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
            countDownTimer = null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        startTimeInMillis = prefs.getLong("startTimeInMillis", 600000);
        timeLeftInMillis = prefs.getLong("millisLeft", startTimeInMillis);
        timerRunning = prefs.getBoolean("timerRunning", false);
        setProgressBarValues();

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

    private void setProgressBarValues() {
        progressBar.setMax((int) startTimeInMillis / 1000);
        progressBar.setProgress((int) timeLeftInMillis / 1000);
    }

    //creating a spinner in the toolbar video tutorial: https://www.youtube.com/watch?v=CvO0Ng-_C6A
    private void setupSpinner(){
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(TimerActivity.this, R.layout.custom_spinner_item, getResources().getStringArray(R.array.timer_speed));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
        mySpinner.setSelection(3); //position 3 is 100%, so we set that as the default value
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                TextView timerSpeed = (TextView) findViewById(R.id.timerSpeedTxtView);
                timerSpeed.setText("Timer is at " + mySpinner.getSelectedItem().toString());
                //25%
                if (position == 0){
                    clicked25 = true;
                    clicked50 = clicked75 =clicked100 = clicked200 = clicked300 = clicked400 = false;
                    setTimer();
                    //timeLeftInMillis = timeLeftInMillis*4;
                }
                //50%
                if (position == 1){
                    clicked50 = true;
                    clicked25 = clicked75=clicked100 = clicked200 = clicked300 = clicked400 = false;
                    setTimer();
                    //timeLeftInMillis = timeLeftInMillis*2;
                }
                //75%
                if (position == 2){
                    clicked75 = true;
                    clicked50 = clicked25 =clicked100 = clicked200 = clicked300 = clicked400 = false;
                    setTimer();
                }
                //100%: do nothing
                if (position == 3){
                    clicked100 = true;
                    clicked25= clicked50 = clicked75 = clicked200 = clicked300 = clicked400 = false;
                    setTimer();
                }
                //200%
                if (position == 4){
                    clicked200 = true;
                    clicked50 = clicked75=clicked100 = clicked25 = clicked300 = clicked400 = false;
                    setTimer();
                    //timeLeftInMillis = timeLeftInMillis/2;
                }
                //300%
                if (position == 5){
                    clicked300 = true;
                    clicked50 = clicked75=clicked100 = clicked200 = clicked25 = clicked400 = false;
                    setTimer();
                    //timeLeftInMillis = timeLeftInMillis/3;

                }
                //400%
                if (position == 6){
                    clicked400 = true;
                    clicked50 = clicked75=clicked100 = clicked200 = clicked300 = clicked25 = false;
                    setTimer();
                    //timeLeftInMillis = timeLeftInMillis/4;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setTimer() {
        if(clicked25){
            setSpeed(25);
        }
        if(clicked50){
            setSpeed(50);
        }
        if(clicked75){
            setSpeed(75);        }
        if(clicked100){
            setSpeed(100);        }
        if(clicked200){
            setSpeed(200);        }
        if(clicked300){
            setSpeed(300);        }
        if(clicked400){
            setSpeed(400);        }
    }

    private void setSpeed(float speed) {
        speedPerc = speed/100;
        if(countDownTimer!=null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        countDownTimer = new CountDownTimer((long)(startTimeInMillis/(speedPerc)), 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = (long)(millisUntilFinished*speedPerc);
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
                setProgressBarValues();
            }
        };

        if(timerRunning==true){
            countDownTimer.start();
        }

    }


    private static void sleep(long timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}

