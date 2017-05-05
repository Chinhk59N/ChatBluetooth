package com.example.root.bluetoothchat;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class activity_chat extends Activity {
    ContactEnity con;
    TextView tv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        tv2 = (TextView)findViewById(R.id.txt2);
        Bundle b = getIntent().getExtras();
        con = (ContactEnity) b.getSerializable("INFOR");
        tv2.setText(con.getName());
    }

}
