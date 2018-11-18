package com.example.pc.daandroid;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

public class SetLogin extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_trangchu);

        Bundle bundle = getIntent().getExtras();
        String check = bundle.getString("login");
        if(check.equals("true")){
            TextView textView = (TextView) findViewById(R.id.nameuser);
            textView.setText(bundle.getString("User"));
        }


    }
}
