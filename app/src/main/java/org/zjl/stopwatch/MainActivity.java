package org.zjl.stopwatch;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Chronometer chronometer;
    private Boolean running = Boolean.FALSE;
    private long pauseOffset;
    private Button startButton;
    private Button stopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chronometer = findViewById(R.id.chronometer);
        // init start button
        startButton = findViewById(R.id.start_button);
        startButton.setVisibility(View.VISIBLE);
        startButton.setOnClickListener(v -> start());
        // init stop button
        stopButton = findViewById(R.id.stop_button);
        stopButton.setVisibility(View.GONE);
        stopButton.setOnClickListener(v -> pause());
        // init clear button
        final Button resetButton = findViewById(R.id.clear_button);
        resetButton.setOnClickListener(v -> reset());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);
        // Restore state members from saved instance
        running = savedInstanceState.getBoolean("running");
        if (!running) {
            startButton.setVisibility(View.VISIBLE);
            stopButton.setVisibility(View.GONE);
        } else {
            startButton.setVisibility(View.GONE);
            stopButton.setVisibility(View.VISIBLE);
        }
        pauseOffset = savedInstanceState.getLong("pauseOffset");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putLong("pauseOffset", pauseOffset);
    }

    public void start() {
        if (running) {
            return;
        }
        chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
        chronometer.start();
        running = true;
        startButton.setVisibility(View.GONE);
        stopButton.setVisibility(View.VISIBLE);
    }

    public void pause() {
        if (!running) {
            return;
        }
        chronometer.stop();
        pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
        running = false;
        startButton.setVisibility(View.VISIBLE);
        stopButton.setVisibility(View.GONE);
    }

    public void reset() {
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.stop();
        pauseOffset = 0;
        running = false;
        startButton.setVisibility(View.VISIBLE);
        stopButton.setVisibility(View.GONE);
    }

}