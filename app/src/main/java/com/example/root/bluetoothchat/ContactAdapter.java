package com.example.root.bluetoothchat;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by DAN on 29/04/2017.
 */

public class ContactAdapter extends BaseAdapter {
    LayoutInflater inflater;
    LinearLayout lay;
    TextView txtName, txtAdd;
    Boolean cnt;

    public ContactAdapter(ArrayList<ContactEnity> listData, Context _Context) {
        this.listData = listData;
        inflater = (LayoutInflater) _Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /*public ArrayList<ContactEnity> getListData() {
        return listData;
    }

    public void setListData(ArrayList<ContactEnity> listData) {
        this.listData = listData;
    }*/

    ArrayList<ContactEnity> listData;

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    /**
     *
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /***
     * Neu danh sach doi tuong co 10 dong thi no se chay vao day 10 lan
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ContactEnity data = (ContactEnity) getItem(position);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.device, null);

        txtName = (TextView) convertView.findViewById(R.id.Name);
        txtAdd = (TextView) convertView.findViewById(R.id.macAddress);
        lay = (LinearLayout)convertView.findViewById(R.id.lay1);

        //Gan gia tri cho control
        txtName.setText(data.getName());
        txtAdd.setText(data.getAddress());
        cnt = data.getConnected();
        if (cnt == true){
            lay.setBackgroundColor(Color.GREEN);
        }
        return convertView;
    }
}