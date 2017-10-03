package com.example.franck.homework;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    static final String STATE_SECOND = "stateSecond";
    public static volatile long currentSecond = 0;
    final String LOG_TAG = "myLogs";
    final int timeShow = 2000;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Intent intent = new Intent(this, CountActivity.class);

//        final TextView textView = (TextView) findViewById(R.id.textView);

        countDownTimer = new CountDownTimer(timeShow-currentSecond, 1) {

            public void onTick(long millisUntilFinished) {
                currentSecond = millisUntilFinished;
//                textView.setText("seconds remaining: " + currentSecond / 1000.0);
            }

            public void onFinish() {
//                textView.setText("done!");
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
        Log.d(LOG_TAG, "onSaveInstanceState" + currentSecond);
    }


    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentSecond = savedInstanceState.getLong(STATE_SECOND);
//        Log.d(LOG_TAG, "onRestoreInstanceState" + currentSecond);
    }
}
