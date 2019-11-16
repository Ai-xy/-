package com.example.hp.animation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


public class SecondFragment extends Fragment {

    ImageView wxhd,xia,bf,next,before;
    TextView toptext,toptext_singer,tx2;
    int flag=0,it=0;
    MediaPlayer mediaPlayer=firstpage.mediaPlayer;
    String time2=firstpage.time2;
    public static SeekBar seekBar;
    public static TextView tx1;
    boolean isStop;
    Houtai houtai;
    List<Song> list=firstpage.list;
    int i2=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment2, container, false);


    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        tx1=getActivity().findViewById(R.id.tx1);


        tx2=getActivity().findViewById(R.id.tx2);
        tx2.setText(time2);

        seekBar=getActivity().findViewById(R.id.seekbar);


        houtai=new Houtai();
        houtai.executeOnExecutor(Executors.newCachedThreadPool());
        Log.d("hou","后台已启动");


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String s=Utils.formatTime(seekBar.getProgress());
                tx1.setText(s);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isStop=true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                isStop=false;
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        toptext=getActivity().findViewById(R.id.toptext);
        toptext_singer=getActivity().findViewById(R.id.toptext_singer);
        final Intent i=getActivity().getIntent();
        String s=i.getStringExtra("data");
        Log.d("data",s);
        if(s.contains("-")){
            String []str=s.split("-");
            toptext.setText(str[0]);
            toptext_singer.setText(str[1]);
            toptext.setSelected(true);
        }
        else{
            toptext.setText("我的天空");
            toptext.setSelected(true);
        }

        bf=getActivity().findViewById(R.id.bf);
        if(mediaPlayer.isPlaying()){
            bf.setBackgroundResource(R.drawable.zt);
        }
        else {
            bf.setBackgroundResource(R.drawable.bf);
        }
        bf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    bf.setBackgroundResource(R.drawable.bf);
                    mediaPlayer.pause();
                }
                else {
                    bf.setBackgroundResource(R.drawable.zt);
                    mediaPlayer.start();
                }

            }
        });




        xia = getActivity().findViewById(R.id.xiala);
        xia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                houtai.cancel(true);
                getActivity().setResult(RESULT_OK,(new Intent()));
                getActivity().finish();
            }
        });

        wxhd=getActivity().findViewById(R.id.wxhd);
        wxhd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==0){
                    wxhd.setBackgroundResource(R.drawable.wxhd2);
                    flag=1;
                }
                else {
                    wxhd.setBackgroundResource(R.drawable.wxhd);
                    flag=0;
                }

            }
        });


        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("path",MODE_PRIVATE);
        Intent intent=getActivity().getIntent();
        if(i2==0){
            i2=sharedPreferences.getInt("i",0);
        }
        else {
            i2=intent.getIntExtra("i",0);
        }


        next=getView().findViewById(R.id.next);//下一首
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i2++;
                Log.d("time",i2+"");
                String p = list.get(i2).path;
                String song=list.get(i2).song;
                String singer=list.get(i2).singer;
                int L=list.get(i2).duration;
                String time3=Utils.formatTime(L);
                Log.d("tim",time3+"");
                tx2.setText(time3);
                toptext.setText(song);
                toptext_singer.setText(singer);
                play(p);

                SharedPreferences sp = getActivity().getSharedPreferences("path", MODE_PRIVATE);
                SharedPreferences.Editor  editor=sp.edit();
                editor.putString("path",list.get(i2).path);
                editor.putString("song",list.get(i2).song);
                editor.putString("singer",list.get(i2).singer);
                editor.putInt("duration",list.get(i2).duration);
                editor.putString("flag","1");
                editor.putString("flag2","1");
                editor.putInt("i",i2);
                editor.apply();

            }
        });

        before=getView().findViewById(R.id.before);//上一首
        before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i2--;
                Log.d("time",i2+"");
                String p = list.get(i2).path;
                String song=list.get(i2).song;
                String singer=list.get(i2).singer;
                int L2=list.get(i2).duration;
                String time4=Utils.formatTime(L2);
                Log.d("tim",time4+"");
                tx2.setText(time4);
                toptext.setText(song);
                toptext_singer.setText(singer);
                play(p);

                SharedPreferences sp = getActivity().getSharedPreferences("path", MODE_PRIVATE);
                SharedPreferences.Editor  editor=sp.edit();
                editor.putString("path",list.get(i2).path);
                editor.putString("song",list.get(i2).song);
                editor.putString("singer",list.get(i2).singer);
                editor.putInt("duration",list.get(i2).duration);
                editor.putString("flag","1");
                editor.putString("flag2","1");
                editor.putInt("i",i2);
                editor.apply();
            }
        });
    }




    public void play(String path){
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}