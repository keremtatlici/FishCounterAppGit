package com.shakabrah.fishcountermobileappgit;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //--------------------------------------DEĞİŞKEN TANIMLARI------------------------------------------------------------------------//
    ArrayList<Database_Items> database_ıtems;
    LayoutInflater layoutInflater;
    Context context;
    TextView tarihtv,sayimtv,counttv,starttv,endtv;
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public GridAdapter(Activity activity, ArrayList<Database_Items> database_ıtems){
        this.database_ıtems = database_ıtems;
        this.context = activity;
        this.layoutInflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return database_ıtems.size();
    }

    @Override
    public Object getItem(int i) {
        return database_ıtems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.gridview_gorunumu,null);
        tarihtv = view.findViewById(R .id.tarihtv);
        sayimtv = view.findViewById(R.id.sayimtv);
        counttv = view.findViewById(R.id.counttv);
        starttv = view.findViewById(R.id.starttv);
        endtv = view.findViewById(R.id.endtv);

        tarihtv.setText(context.getResources().getString(R.string.Date_Log) + " "+ database_ıtems.get(i).getDateKey()+" ");
        sayimtv.setText(database_ıtems.get(i).getSayimKey()+" "+context.getResources().getString(R.string.Count_Num)+" ");
        counttv.setText(context.getResources().getString(R.string.Counter)+" "+database_ıtems.get(i).getCount()+" "+context.getResources().getString(R.string.fish));
        starttv.setText(context.getResources().getString(R.string.Start_Time_Log)+" "+database_ıtems.get(i).getStart());
        endtv.setText(context.getResources().getString(R.string.End_Time_Log)+" "+database_ıtems.get(i).getEnd());

        return view;
    }
}
