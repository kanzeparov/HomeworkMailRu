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

    Button btn;
    TextView textView1;
    CountDownTimer countDownTimer;
    final int maxTimeSecond = 1001; // Количество секунд
    String state; // Кнопка состояния
    final String LOG_TAG = "myLogs";
    static final String STATE_BUTTON = "stateButton";
    static final String STATE_SECOND1 = "stateSecond";
    public static volatile long currentSecond = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        Log.d(LOG_TAG, "onCreate " + state);

        btn = (Button) findViewById(R.id.button_count);
        textView1 = (TextView) findViewById(R.id.textView_count);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(LOG_TAG, "OnClick " + state + currentSecond);

                if (btn.getText().toString().equals(getString(R.string.start_button))){
                    btn.setText(R.string.stop_button);
                    state = "Stop";

                    countDownTimer = new CountDownTimer(maxTimeSecond * 1000, 1_000) {

                        public void onTick(long millisUntilFinished) {
                            currentSecond = maxTimeSecond - millisUntilFinished / 1000; // Сохраняем количество секунд для onSave
                            textView1.setText(stringCount(Math.abs(maxTimeSecond - millisUntilFinished  / 1000)));
                        }

                        public void onFinish() {
                            btn.setText(R.string.start_button);
                        }
                    }.start();
                } else {
                    if (countDownTimer != null)
                        countDownTimer.cancel();
                    btn.setText(R.string.start_button);
                    state = "Start";
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

        if(state != null && state.equals("Stop")) {
            btn.setText(R.string.stop_button);
            textView1.setText(stringCount(currentSecond));

            countDownTimer = new CountDownTimer((maxTimeSecond - currentSecond) * 1000, 1_000) {

                public void onTick(long millisUntilFinished) {
                    currentSecond = maxTimeSecond - millisUntilFinished / 1000;
                    Log.d(LOG_TAG, String.valueOf(Math.abs(maxTimeSecond - millisUntilFinished  / 1000) ) + " " + this +
                            " current from OnResume " + currentSecond);
                    textView1.setText(stringCount(Math.abs(maxTimeSecond - millisUntilFinished  / 1000)));
                }

                public void onFinish() {
                    btn.setText(R.string.start_button);
                }
            }.start();
        }
        else {
            btn.setText(R.string.start_button);
            if(currentSecond != 0) {
                textView1.setText(stringCount(currentSecond));
            }
        }

        Log.d(LOG_TAG, "onResume " + state + currentSecond);
    }

    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_BUTTON, state);
        outState.putLong(STATE_SECOND1, currentSecond);
        super.onSaveInstanceState(outState);
        Log.d(LOG_TAG, "onSaveInstanceState" + state + currentSecond);
    }
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        state = savedInstanceState.getString(STATE_BUTTON);
        currentSecond = savedInstanceState.getLong(STATE_SECOND1);
        Log.d(LOG_TAG, "onRestoreInstanceState" + state + currentSecond);
    }

    public String stringCount(long second) {
        String[] stringSecond = {"один","два","три","четыре","пять","шесть","семь","восемь",
                "девять","десять","одиннадцать","двенадцать","тринадцать","четырнадцать","пятьнадцать","шестнадцать","семнадцать",
                "восемнадцать","девятнадцать"};
        String[] stringSecondDozen = {"двадцать","тридцать","сорок","пятьдесят","шестьдесят",
                "семьдесят","восемьдесят","девяносто"};
        String[] stringSecondHundred = {"сто","двести","триста","четыресто",
                "пятьсот","шестьсот","семьсот","восемьсот","девятьсот"};
        StringBuilder answer = new StringBuilder();

        if (second == 1000) {
            return "тысяча";
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
