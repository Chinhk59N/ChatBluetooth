package com.example.root.bluetoothchat;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;


public class NewDeviceFragment extends Fragment {
    Button bt1,bt2,scan;
    ArrayList<ContactEnity> deviceItemList = new ArrayList<ContactEnity>();
    ListView li;
    NewDeviceAdapter adapter;
//    private final Handler mHandler = null;
//    private ConnectThread mConnectThread;
    private static final String NAME = "BluetoothChat";
    private static final UUID MY_UUID = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");

    public NewDeviceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_new_device, container, false);
        scan = (Button) viewGroup.findViewById(R.id.scan);
        bt1 = (Button) viewGroup.findViewById(R.id.bt1);
        bt2 = (Button) viewGroup.findViewById(R.id.bt2);
        li = (ListView) viewGroup.findViewById(R.id.list2);
        bt1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                doDiscover();
        }
        });
        bt2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                deviceItemList.clear();
                off();
            }
        });
        adapter = new NewDeviceAdapter(deviceItemList,getActivity());
        li.setAdapter(adapter);
        itemClick();
        scan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                find(v);
                li.setVisibility(View.VISIBLE);
            }
        });
        return viewGroup;
    }
    private void itemClick() {
        li.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ContactEnity contact = deviceItemList.get(position);
//                Intent click = new Intent(getActivity(), activity_chat.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("INFOR", contact);
//                click.putExtras(bundle);
//                startActivity(click);
//                deviceItemList.get(position).setConnected(true);
                Toast.makeText(getActivity(), ""+MainActivity.BTAdapter.getRemoteDevice(deviceItemList.get(position).getAddress()), Toast.LENGTH_SHORT).show();
                ConnectThread a =  new ConnectThread();
                a.connect(MainActivity.BTAdapter.getRemoteDevice(deviceItemList.get(position).getAddress()),MY_UUID);

            }
        });
    }
//    private void connect(String address){
//        BluetoothDevice device = MainActivity.BTAdapter.getRemoteDevice(address);
//        BluetoothSocket tmp = null;
//        BluetoothSocket mmSocket = null;
//
//        try {
//            tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
//            Method m = device.getClass().getMethod("createRfcommSocket", new Class[] {int.class});
//            tmp = (BluetoothSocket) m.invoke(device, 1);
//        } catch (IOException e) {
//            Log.e(TAG, "create() failed", e);
//        }
//        mmSocket = tmp;
//    }

    private void doDiscover(){
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 100);
        startActivity(discoverableIntent);
    }
    private void off(){
        MainActivity.BTAdapter.disable();
    }
    public void find(View view){
        if(MainActivity.BTAdapter.isDiscovering()){
            MainActivity.BTAdapter.cancelDiscovery();
        }
        else {
            deviceItemList.clear();
            MainActivity.BTAdapter.startDiscovery();
            getActivity().registerReceiver(bReciever,new IntentFilter(BluetoothDevice.ACTION_FOUND));
        }
    }
    private final BroadcastReceiver bReciever = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                ContactEnity newDevice = new ContactEnity(device.getName(), device.getAddress(), "flase");
                deviceItemList.add(newDevice);
                adapter.notifyDataSetChanged();
            }
        }
    };
//    void startCommunication(BluetoothDevice device) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            ConnectThread mConnectThread = new ConnectThread(device);
//            mConnectThread.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//        } else {
//            ConnectThread mConnectThread = new ConnectThread(device);
//            mConnectThread.execute((Void) null);
//        }
//    }
//    public class ConnectThread extends AsyncTask<Void, Void, String> {
//
//        private BluetoothDevice btDevice;
//        public ConnectThread(BluetoothDevice device) {
//            this.btDevice = device;
//        }
//        @Override
//        protected String doInBackground(Void... params) {
//            try {
//                mmSocket = btDevice.createRfcommSocketToServiceRecord(MY_UUID);
//                MainActivity.BTAdapter.cancelDiscovery();
//                mmSocket.connect();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                return null;
//            }
//            return "";
//        }
//        @Override
//        protected void onPostExecute(final String result) {
//
//            if (result != null){/* Success */}
//            else {/* Connection Failed */}
//        }
//        @Override
//        protected void onCancelled() {}
//
//    }

    public class ConnectThread extends Thread{
        private BluetoothSocket bTSocket;

        public boolean connect(BluetoothDevice bTDevice, UUID mUUID) {
            try {
                bTSocket = bTDevice.createRfcommSocketToServiceRecord(mUUID);
            } catch (IOException e) {
                Log.d("CONNECTTHREAD","Could not create RFCOMM socket:" + e.toString());
                return false;
            }
            try {
                bTSocket.connect();
            } catch(IOException e) {
                Log.d("CONNECTTHREAD","Could not connect: " + e.toString());
                try {
                    bTSocket.close();
                } catch(IOException close) {
                    Log.d("CONNECTTHREAD", "Could not close connection:" + e.toString());
                    return false;
                }
            }
            return true;
        }

        public boolean cancel() {
            try {
                bTSocket.close();
            } catch(IOException e) {
                Log.d("CONNECTTHREAD","Could not close connection:" + e.toString());
                return false;
            }
            return true;
        }
    }
}
