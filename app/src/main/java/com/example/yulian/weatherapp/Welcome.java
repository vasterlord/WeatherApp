package com.example.yulian.weatherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Welcome extends AppCompatActivity {

    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);
        mImageView = (ImageView) findViewById(R.id.imageViewSplash);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.welcome_animation);
        mImageView.setAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
                startActivity(new Intent(getApplicationContext(), Login.class));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
