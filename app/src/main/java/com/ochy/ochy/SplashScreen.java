package com.ochy.ochy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class SplashScreen extends AppCompatActivity {

    private  final int splash_screen_delay = 2000;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        LinearLayout ll = findViewById(R.id.ll);
        Animation tablego = AnimationUtils.loadAnimation(this,R.anim.exiting);
        ll.startAnimation(tablego);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (firebaseUser!=null) {
                        assert firebaseUser!=null;
                        if (Objects.equals(firebaseUser.getEmail(), "ya@gmail.com")){
                            Intent mainIntent = new Intent(SplashScreen.this, MainAdmin.class);
                            SplashScreen.this.startActivity(mainIntent);

                            SplashScreen.this.finish();

                            overridePendingTransition(R.anim.exiting, R.anim.entering);

                            return;
                        }
                        Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
                        SplashScreen.this.startActivity(mainIntent);

                        SplashScreen.this.finish();

                        overridePendingTransition(R.anim.exiting, R.anim.entering);

                }

                else {
                    Intent mainIntent = new Intent(SplashScreen.this, RegisterActivity.class);
                    SplashScreen.this.startActivity(mainIntent);

                    SplashScreen.this.finish();

                    overridePendingTransition(R.anim.exiting, R.anim.entering);
                }
            }
        }, splash_screen_delay);
    }
}