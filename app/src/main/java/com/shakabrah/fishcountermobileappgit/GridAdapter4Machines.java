package com.shakabrah.fishcountermobileappgit;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GridAdapter4Machines extends BaseAdapter {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //--------------------------------------DEĞİŞKEN TANIMLARI------------------------------------------------------------------------//
    ArrayList<String> userMachines;
    LayoutInflater layoutInflater;
    Context context;
    TextView btn;

    String mac_keys;
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public GridAdapter4Machines(Activity activity, ArrayList<String> userMachines){
        this.userMachines = userMachines;
        this.context = activity;
        this.layoutInflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return userMachines.size();
    }

    @Override
    public Object getItem(int i) {
        return userMachines.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public String getMac_keys() {
        return mac_keys;
    }

    public void setMac_keys(String mac_keys) {
        this.mac_keys = mac_keys;
    }

    @Override
    public View getView(final int i, final View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.gridview4machines,null);
        btn = view.findViewById(R.id.textView1);
        btn.setText(context.getResources().getString(R.string.MacNum)+" "+userMachines.get(i));

        return view;
    }
}
