package com.example.hp.animation;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class firstpage extends AppCompatActivity {

    public static ListView mylist;
    public static List<Song> list;
    private Adapter myAdapter;

    public static MediaPlayer mediaPlayer=new MediaPlayer();
    Button start;
    LinearLayout bar;
    TextView music_name;
    ImageView back;
    String s,path,song,singer;
    public static int timeend=0,duration;
    public static String time2;
    int i_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstpage);
        initView();
        if (Build.VERSION.SDK_INT >=23) {
            if (ContextCompat.checkSelfPermission(firstpage.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager
                    .PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(firstpage.this, new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 1);
            } else {
                init();
            }
        }
         else {
         init();
        }

        Log.d("fr","开启新firstpage");

        music_name=findViewById(R.id.musicname);
        SharedPreferences sharedPreferences=getSharedPreferences("path",MODE_PRIVATE);

        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(firstpage.this,QH.class);
                startActivity(i);
            }
        });

        start=findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mediaPlayer.isPlaying()){//暂停
                    mediaPlayer.start();
                    start.setBackgroundResource(R.drawable.zt);
                }
                else{
                    mediaPlayer.pause();
                    start.setBackgroundResource(R.drawable.bf);
                }
            }
        });


        if(sharedPreferences.getString("flag","").equals("1")&&!mediaPlayer.isPlaying())//记录上一次
        {
            path=sharedPreferences.getString("path","");
            song=sharedPreferences.getString("song","");
            singer=sharedPreferences.getString("singer","");
            duration=sharedPreferences.getInt("duration",0);
            music_name.setText(song+"-"+singer);
            music_name.setSelected(true);
            s=music_name.getText().toString();
            timeend=duration;
            time2=Utils.formatTime(timeend);
            play2(path);


        }
        bar=findViewById(R.id.bar);
        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int i, long id) {
                Log.d("i",i+"");
                i_list=i;
                final String p=list.get(i).path;
                Log.d("path",p);
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    play(p);
                }
                else{
                    play(p);
                    start.setBackgroundResource(R.drawable.zt);
                }

                SharedPreferences sp = getSharedPreferences("path", MODE_PRIVATE);
                SharedPreferences.Editor  editor=sp.edit();
                editor.putString("path",list.get(i).path);
                editor.putString("song",list.get(i).song);
                editor.putString("singer",list.get(i).singer);
                editor.putInt("duration",list.get(i).duration);
                editor.putString("flag","1");
                editor.putString("flag2","1");
                editor.apply();

                String n=list.get(i).song;
                String m=list.get(i).singer;
                timeend=list.get(i).duration;
                time2=Utils.formatTime(timeend);

                music_name.setText(n+"-"+m);
                music_name.setSelected(true);
                s=music_name.getText().toString();

                bar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent ix = new Intent(firstpage.this,SongInformation.class);
                        ix.putExtra("data",s);
                        ix.putExtra("i",i_list);
                        startActivityForResult(ix,  1);


                    }
                });
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        play(p);
                    }
                });
            }
        });


        if(sharedPreferences.getString("flag2","").equals("1"))
        {
            bar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(firstpage.this,SongInformation.class);
                    i.putExtra("data",s);
                    startActivityForResult(i,  1);

                }
            });
        }


        if(mediaPlayer.isPlaying()){
            song=sharedPreferences.getString("song","");
            singer=sharedPreferences.getString("singer","");
            duration=sharedPreferences.getInt("duration",0);
            music_name.setText(song+"-"+singer);
            music_name.setSelected(true);
            s=music_name.getText().toString();
            timeend=duration;
            start.setBackgroundResource(R.drawable.zt);
            time2=Utils.formatTime(timeend);
        }
        else {
            song=sharedPreferences.getString("song","");
            singer=sharedPreferences.getString("singer","");
            duration=sharedPreferences.getInt("duration",0);
            music_name.setText(song+"-"+singer);
            music_name.setSelected(true);
            s=music_name.getText().toString();
            timeend=duration;
            time2=Utils.formatTime(timeend);
        }




    }

    private void initView() {
        mylist = (ListView) findViewById(R.id.mylist);
        list = new ArrayList<>();
    }
    private void init() {
        list = Utils.getmusic(this);
        myAdapter = new Adapter(this, list);
        mylist.setAdapter(myAdapter);
    }



    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults){
        switch (requestCode){
            case  1:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    init();
                }else{
                    Toast.makeText(this, "你拒绝了这个权限", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
            if (resultCode == RESULT_OK) {
                SharedPreferences sharedPreferences=getSharedPreferences("path",MODE_PRIVATE);
                song=sharedPreferences.getString("song","");
                singer=sharedPreferences.getString("singer","");
                duration=sharedPreferences.getInt("duration",0);
                music_name.setText(song+"-"+singer);
                music_name.setSelected(true);
            }break;
            default:
        }

    }

    public void play2(String path){
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {

                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}