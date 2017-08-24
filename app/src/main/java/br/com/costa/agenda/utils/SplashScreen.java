package br.com.costa.agenda.utils;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import br.com.costa.agenda.R;
import br.com.costa.agenda.StudentsListActivity;

import static br.com.costa.agenda.R.drawable.animationsplash;

public class SplashScreen extends Activity {

    private static  int splashIntervalo  = 1800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //set layout
        setContentView(R.layout.activity_splash_screen);

         //animação logo
        ImageView imgView = (ImageView)findViewById(R.id.imgAnimationSplash);
        imgView.setBackgroundResource(animationsplash);
        AnimationDrawable framAnimation = (AnimationDrawable) imgView.getBackground();
        framAnimation.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, StudentsListActivity.class);
                startActivity(i);
                this.finish();
            }
            private  void finish(){
            }
        }, splashIntervalo);
    }
}