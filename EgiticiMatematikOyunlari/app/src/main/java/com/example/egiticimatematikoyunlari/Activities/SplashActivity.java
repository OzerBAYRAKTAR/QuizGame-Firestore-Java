package com.example.egiticimatematikoyunlari.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.egiticimatematikoyunlari.R;

public class SplashActivity extends AppCompatActivity {
    private TextView app_name;
    private ImageView app_image;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        app_name=findViewById(R.id.app_name);
        app_image=findViewById(R.id.app_image);

        Typeface typeface= ResourcesCompat.getFont(this,R.font.blacklist);
        app_name.setTypeface(typeface);

        Animation anim= AnimationUtils.loadAnimation(this,R.anim.splash_anim);
        app_image.setAnimation(anim);

        Animation anim1=AnimationUtils.loadAnimation(this,R.anim.text_anim);
        app_name.setAnimation(anim1);

        new Thread() {
            @Override
            public void run()
            {
                try {
                    sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }

        }.start();



    }
}