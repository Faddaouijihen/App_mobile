package com.example.formation;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {
    Timer timer;
    long delay = 3000;
    TimerTask timerTask;
    TextView txt_head_splash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        txt_head_splash = findViewById(R.id.txthome_splash);
        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/dbplus.otf");
        txt_head_splash.setTypeface(tf);
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                finish();
                startActivity(new Intent(SplashScreen.this,AuthenticationActivity.class));
            }
        };
        timer.schedule(timerTask,delay);
    }
}
