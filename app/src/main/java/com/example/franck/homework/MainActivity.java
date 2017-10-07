package com.example.franck.homework;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {
    private String STATE_SECOND;
    private long currentSecond = 0;
    private final int timeShow = 2000;
    private CountDownTimer countDownTimer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        STATE_SECOND = this.getString(R.string.state_second);
        final Intent intent = new Intent(this, CountActivity.class);

        countDownTimer = new CountDownTimer(timeShow-currentSecond, 1) {

            public void onTick(long millisUntilFinished) {
                currentSecond = millisUntilFinished;
            }

            public void onFinish() {
                startActivity(intent);
                MainActivity.this.finish();
            }
        }.start();
    }



    protected void onStop() {
        super.onStop();
        currentSecond = timeShow - currentSecond;
        if(countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        outState.putLong(STATE_SECOND, currentSecond);
        super.onSaveInstanceState(outState);
    }


    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentSecond = savedInstanceState.getLong(STATE_SECOND);
    }
}
