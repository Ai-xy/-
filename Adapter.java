package com.example.hp.animation;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Adapter extends BaseAdapter {

    private Context context;
    private List<Song> list;

    public Adapter(firstpage mainActivity, List<Song> list) {
        this.context = mainActivity;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Myholder myholder= null;

        if (view==null)
        {
            myholder=new Myholder();
            view=View.inflate(context,R.layout.activity_list,null);
            myholder.t_song=(TextView) view.findViewById(R.id.song);
            myholder.t_singer=(TextView) view.findViewById(R.id.singer);
            view.setTag(myholder);


        }
        else{
            myholder=(Myholder)view.getTag();
        }


        myholder.t_song.setText(list.get(i).song.toString());
        myholder.t_singer.setText(list.get(i).singer.toString());

        return view;
    }

    class Myholder {
        TextView t_song, t_singer;
    }

}
