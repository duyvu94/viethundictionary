package com.duyvu.viethundictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.duyvu.viethundictionary.data.WordListDatabase;
import com.duyvu.viethundictionary.models.Word;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.SplashTheme);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.AppTheme);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

        //setContentView(R.layout.activity_splash);
    }
}
