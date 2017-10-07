package com.example.franck.homework;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Franck on 29.09.2017.
 */

public class CountActivity extends AppCompatActivity {

    private Button btn;
    private TextView textView1;
    private CountDownTimer countDownTimer;
    private final int maxTimeSecond = 1001;
    private boolean state;
    private String STATE_BUTTON;
    private String STATE_SECOND1;
    private long currentSecond = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        STATE_BUTTON = this.getString(R.string.start_button);
        STATE_SECOND1 = this.getString(R.string.state_second);

        btn = (Button) findViewById(R.id.button_count);
        textView1 = (TextView) findViewById(R.id.textView_count);
        state = true;


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (btn.getText().toString().equals(getString(R.string.start_button))){
                    btn.setText(getString(R.string.stop_button));
                    state = false;

                    countDownTimer = new CountDownTimer(maxTimeSecond * 1000, 1_000) {

                        public void onTick(long millisUntilFinished) {
                            currentSecond = maxTimeSecond - millisUntilFinished / 1000;
                            textView1.setText(stringCount(Math.abs(maxTimeSecond - millisUntilFinished  / 1000)));
                        }

                        public void onFinish() {
                            btn.setText(getString(R.string.start_button));
                        }
                    }.start();
                } else {
                    if (countDownTimer != null)
                        countDownTimer.cancel();
                    btn.setText(getString(R.string.start_button));
                    state = true;
                }
            }
        });
    }

    protected void onStop() {
        super.onStop();
        if(countDownTimer != null) {
            countDownTimer.cancel();
        }
    }


    protected void onResume() {
        super.onResume();

        if(!state) {
            btn.setText(this.getString(R.string.stop_button));
            textView1.setText(stringCount(currentSecond));

            countDownTimer = new CountDownTimer((maxTimeSecond - currentSecond) * 1000, 1_000) {

                public void onTick(long millisUntilFinished) {
                    currentSecond = maxTimeSecond - millisUntilFinished / 1000;
                    textView1.setText(stringCount(Math.abs(maxTimeSecond - millisUntilFinished  / 1000)));
                }

                public void onFinish() {
                    btn.setText(getString(R.string.start_button));
                }
            }.start();
        }
        else {
            btn.setText(this.getString(R.string.start_button));
            if(currentSecond != 0) {
                textView1.setText(stringCount(currentSecond));
            }
        }

    }

    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE_BUTTON, state);
        outState.putLong(STATE_SECOND1, currentSecond);
        super.onSaveInstanceState(outState);
    }
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        state = savedInstanceState.getBoolean(STATE_BUTTON);
        currentSecond = savedInstanceState.getLong(STATE_SECOND1);
    }

    public String stringCount(long second) {
        String[] stringSecond = getResources().getStringArray(R.array.countstringSecond);
        String[] stringSecondDozen = getResources().getStringArray(R.array.countDozen);
        String[] stringSecondHundred = getResources().getStringArray(R.array.countHundred);

        StringBuilder answer = new StringBuilder();

        if (second == 1000) {
            return getResources().getString(R.string.lastNum);
        }

        long hundred = second / 100;
        long dozen = second - hundred * 100;

        if (hundred != 0) {
            answer.append(stringSecondHundred[(int)hundred - 1] + " ");
        }

        if (dozen <= 19 & dozen > 0) {
            answer.append(stringSecond[(int)dozen - 1]);
        } else if (dozen % 10 == 0 & dozen != 0){
            answer.append(stringSecondDozen[(int)dozen / 10 - 2]);
        } else if (dozen != 0) {
            answer.append(stringSecondDozen[(int)dozen / 10 - 2] + " " + stringSecond[(int)dozen % 10 - 1]);
        }

        return new String(answer);
    }

}
