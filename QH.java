package com.example.hp.animation;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class QH extends AppCompatActivity {
    private ImageView imageView;
    private AnimationDrawable animationDrawable;
    Button but1;
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 1000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qh);

//            imageView=findViewById(R.id.show);
//            animationDrawable=(AnimationDrawable)imageView.getBackground();
//            animationDrawable.start();

            but1=findViewById(R.id.mymusic);
            but1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(QH.this,firstpage.class);
                    startActivity(i);

                }
            });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null!=animationDrawable&&animationDrawable.isRunning())
        {
            animationDrawable.stop();
        }
    }
}

