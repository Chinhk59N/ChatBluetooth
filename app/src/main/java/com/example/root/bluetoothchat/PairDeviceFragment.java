package com.example.root.bluetoothchat;


import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Set;

public class PairDeviceFragment extends Fragment {
    ListView li;
    PairDeviceAdapter adapter;
    Button btn;
    public PairDeviceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_pair_device_frgment, container, false);
        li = (ListView) viewGroup.findViewById(R.id.list1);
        btn = (Button)viewGroup.findViewById(R.id.load);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                loadData();
            }
        });
        return viewGroup;
    }
    private void loadData() {
        ArrayList<ContactEnity> deviceItemList = new ArrayList<ContactEnity>();
        Set<BluetoothDevice> pairedDevices = MainActivity.BTAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                ContactEnity newDevice = new ContactEnity(device.getName(), device.getAddress(), "false");
                deviceItemList.add(newDevice);
            }
            adapter = new PairDeviceAdapter(deviceItemList, getActivity());
            li.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            itemClick(deviceItemList);
        }
    }
    private void itemClick(final ArrayList<ContactEnity> devicelist) {
        li.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContactEnity contact = devicelist.get(position);
                Intent click = new Intent(getActivity(), activity_chat.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("INFOR", contact);
                click.putExtras(bundle);
                startActivity(click);
            }
        });
    }

}
