package com.example.kosh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    VideoView splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        splash=findViewById(R.id.splash);
        String path="android.resource://" + getPackageName() + "/" + R.raw.animationsplash;
        Uri uri = Uri.parse(path);
        splash.setVideoURI(uri);
        splash.setOnPreparedListener(MediaPlayer::start);
        new Handler().postDelayed(() -> {
            Intent i= new Intent(MainActivity.this,home.class);
            startActivity(i);


        },4000);

    }
}