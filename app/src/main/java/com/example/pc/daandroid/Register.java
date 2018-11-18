package com.example.pc.daandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class Register extends Activity {

    EditText _Gmail,_Code;
    Button btnxacnhan;
    String Gmail;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dangnhap);

        RelativeLayout layout_dangnhap = (RelativeLayout) getLayoutInflater().inflate(R.layout.fragment_dangnhap,null);

       // _Gmail =(EditText) layout_dangnhap.findViewById(R.id.editemail);
        //_Code = (EditText) layout_dangnhap.findViewById(R.id.editcode);
        btnxacnhan = (Button) layout_dangnhap.findViewById(R.id.btnxacnhan);

    }

    public void userReg(View view){
        // function click đăng kí
        Gmail=_Gmail.getText().toString();
        String method = "register";

        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute(method,Gmail);
        finish();
    }

    public  void userLogin(View view){
        // function click login

    }


}
