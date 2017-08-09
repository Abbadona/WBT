package com.example.karol.wbt;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class  SplashActivity extends AppCompatActivity {

    private final String PREFERENCES_NAME = "myPreferences";
    private final String PREFERENCES_IS_LOGGED= "islogged";
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final ImageView image = (ImageView) findViewById(R.id.title_image);
        final Animation animation = AnimationUtils.loadAnimation(getBaseContext(),R.anim.around_animation);
        preferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);

        image.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Wczytyujemy ustawienia - czy użytkownik jest zalogowany

                        boolean isLogged = false;
                        Intent intent;
                        try{
                            isLogged = preferences.getBoolean(PREFERENCES_IS_LOGGED,false);
                        }catch (Exception e){

                        }
                        Log.d("TAG_ANIM_BOUNDLE",isLogged+"");
                        //TODO
                        //  Jeżeli chcesz się rejestrować się wykomentuj:
                        // ***POCZATEK*****
                        //    isLogged = true;
                        // ****KONIEC******

                        if (isLogged){
                            intent = new Intent(SplashActivity.this,MenuActivity.class);
                        }
                        else {
                            intent = new Intent(SplashActivity.this,SignInUpActivity.class);
                        }

                        SplashActivity.this.startActivity(intent);
                        SplashActivity.this.finish();
                        overridePendingTransition(R.anim.fade_in_animation, R.anim.fade_out_animation);
                    }},2000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
