package com.example.hp.animation;

import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

public class MyThread implements Runnable {

    SeekBar seekBar=SecondFragment.seekBar;
    int progress=firstpage.timeend;
    public static int t=0;


    public void run(){
        for (int i=0;i<progress;)
        {
            try {
                Thread.sleep(1000);
                seekBar.setMax(progress);
                seekBar.setProgress(i);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i=i+1000;
            t++;
        }
    }
}
