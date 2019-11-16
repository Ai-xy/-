package com.example.hp.animation;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.Executor;

public class Houtai extends AsyncTask<Void,Integer,Boolean > {

    SeekBar seekBar=SecondFragment.seekBar;
    ListView listView=firstpage.mylist;
    int progress=firstpage.timeend;
    TextView tx1=SecondFragment.tx1;
    MediaPlayer mediaPlayer=firstpage.mediaPlayer;
    public static int t=0;
    int a=0,b=0,c=0;
    int pos;
    String s;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d("text","onPreExecute()执行");
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        Log.d("text","doInBackground执行");
            for (int i=0;i<progress;)
            {
                pos=mediaPlayer.getCurrentPosition();
                try {

                    seekBar.setMax(progress);
                    seekBar.setProgress(pos);
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                s=Utils.formatTime(pos);
                publishProgress();

            }
        return true;
    }



    @Override
    protected void onProgressUpdate(Integer... values) {
        tx1.setText(s);
        Log.d("text","onProgressUpdate执行");

    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        Log.d("text"," onPostExecute执行");
        super.onPostExecute(aBoolean);
    }

}
