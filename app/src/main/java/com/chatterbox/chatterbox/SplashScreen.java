package com.chatterbox.chatterbox;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Project: ChatterBox
 * Package: com.chatterbox.chatterbox
 * Created by lusinabrian on 13/08/16 at 07:47
 * <p/>
 * Description: Handles the SplashScreen Activity, which is the first thing to open when app starts
 */
public class SplashScreen extends AppCompatActivity{
    private final String SPLASHSCREEN_TAG = SplashScreen.class.getSimpleName();
    private TextView appName;
    private ImageView appIcon;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*request full screen*/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splashscreen_layout);
        initUICtrls();
        /*Sets the timer*/
        Thread timer = new Thread(){
            @Override
            public void run(){
                try{
                    sleep(3000);
                }catch(InterruptedException ie){
                    ie.printStackTrace();
                    Log.d(ie.toString(),SPLASHSCREEN_TAG);
                }
                Intent openMain = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(openMain);
            }
        };
        timer.start();
    }
    /**initialized the UI controls for this splash screen*/
    public void initUICtrls(){

    }
    /*kill this splash screen to save memory*/
    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }
/*CLASS END*/
}
