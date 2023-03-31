package com.example.countdowntimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final long START_TIME = 10000;

    private TextView countDownTextView;
    private Button player1Button;
    private Button player2Button;

    private CountDownTimer countDownTimer;
    private boolean isTimerRunning;
    private long timeLeft = START_TIME;

    private boolean isPlayer1;
    private boolean isPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countDownTextView = findViewById(R.id.textView);

        player1Button = findViewById(R.id.player1Button);
        player2Button = findViewById(R.id.player2Button);

        player1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPlayer1 = true;
                if (isTimerRunning) {
                    pauseTimer();
                    isPlayer1 = false;
                } else {
                    startTimer();
                }
            }
        });

        updateCountDownText();

        player2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPlayer2 = true;
                if (isTimerRunning) {
                    pauseTimer();
                    isPlayer2 = false;
                } else {
                    startTimer();
                }
            }
        });

        updateCountDownText();
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeft, 100) {
            @Override
            public void onTick(long timeLeftUntilFinish) {
                timeLeft = timeLeftUntilFinish;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                isTimerRunning = false;
                player1Button.setText("player 1");
                player2Button.setText("player 2");
            }
        }.start();

        isTimerRunning = true;
        if(isPlayer1)
            player1Button.setText("pause");
        else if(isPlayer2)
            player2Button.setText("pause");
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        isTimerRunning = false;
        player1Button.setText("player 1");
        player2Button.setText("player 2");
    }

    private void checkWinner() {
        if (timeLeft < 1000 && isPlayer1) {
            countDownTextView.setTextSize(30);
            countDownTextView.setText("Player 2 wins!! 😎");
        } else if (timeLeft < 1000 && isPlayer2) {
            countDownTextView.setTextSize(30);
            countDownTextView.setText("Player 1 wins!! 😎");
        }

    }
    private void updateCountDownText() {
        int min = (int) (timeLeft / 1000) / 60;
        int sec = (int) (timeLeft / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", min, sec);
        countDownTextView.setText(timeLeftFormatted);
        checkWinner();
    }
}