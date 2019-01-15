package com.example.pc.daandroid;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class dangnhap_class extends Fragment implements FragmentCallBack {
    private MainActivity main;
    private Context context;
    private Button btnXacNhan, btnDangKi;
    //private EditText edtUser,edtMK;
    private AppCompatEditText edtUser,edtMK;
    private TextInputLayout userLayout, passLayout;
    //private TextView txtname;
    public static dangnhap_class newInstance(String strArg){
        dangnhap_class tc=new dangnhap_class();
        Bundle args = new Bundle();
        args.putString("strArg1", strArg);
        tc.setArguments(args);
        return tc;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        main=(MainActivity)getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_dangnhap,container,false);

         RelativeLayout layout = view.findViewById(R.id.layoutMain);
         layout.setOnClickListener(null);
         btnDangKi = (Button) view.findViewById(R.id.btndangki);
         btnXacNhan = (Button) view.findViewById(R.id.btndangnhap);

         edtUser = (AppCompatEditText) view.findViewById(R.id.edtUser);
         edtMK = (AppCompatEditText) view.findViewById(R.id.edtmatkhau);
         userLayout = (TextInputLayout) view.findViewById(R.id.userLayout);
         passLayout = (TextInputLayout) view.findViewById(R.id.passLayout);

         userLayout.setCounterEnabled(true);
         userLayout.setCounterMaxLength(12);

        passLayout.setCounterEnabled(true);
        passLayout.setCounterMaxLength(12);

        edtUser.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(edtUser.getText().toString().isEmpty())
                {
                    userLayout.setErrorEnabled(true);
                    userLayout.setError("Vui lòng nhập User");
                }
                else{
                    userLayout.setErrorEnabled(false);
                }
            }
        });

        edtMK.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(edtMK.getText().toString().isEmpty())
                {
                    passLayout.setErrorEnabled(true);
                    passLayout.setError("Vui lòng nhập Password");
                }
                else{
                    userLayout.setErrorEnabled(false);
                }
            }
        });

         // click btn Dang Nhap
         btnXacNhan.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {


                 //Toast.makeText(context,"Click Dang nhap",Toast.LENGTH_SHORT).show();

                 final String User, Pass,method;
                 method="Login";
                 User=edtUser.getText().toString();
                 Pass=edtMK.getText().toString();
                 //Ma=edtMXN.getText().toString();
                 if(User.equals("")||Pass.equals("")){
                    Toast.makeText(context,"Bạn chưa nhập thông tin.",Toast.LENGTH_SHORT).show();
                 }else{
                     BackgroundTask1 backgroundTask = new BackgroundTask1(main);
                     backgroundTask.execute("Login",User,Pass);
                 }
             }
         });
        // nhans vao nut dang ki
         btnDangKi.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 AlertDialog.Builder builder = new AlertDialog.Builder(context);
                 View view = getLayoutInflater().inflate(R.layout.dang_ki,null);

                 Random random=new Random();
                 final int code=random.nextInt(9999-1000)+1001;

                 // xử lí bên đăng kí.
                 final EditText edtUser,edtPass,edtGamil,edtMaXN;
                 Button btnDangki;
                 FloatingActionButton btnNhanCode;
                 edtUser = (EditText) view.findViewById(R.id.edtUser);
                 edtPass = (EditText) view.findViewById(R.id.edtmatkhau);
                 edtGamil = (EditText) view.findViewById(R.id.edtemail);
                 edtMaXN = (EditText) view.findViewById(R.id.edtcode);
                 btnDangki = (Button) view.findViewById(R.id.btndangki);
                 btnNhanCode = (FloatingActionButton) view.findViewById(R.id.btnCode);

                 builder.setView(view);
                 final AlertDialog alertDialog = builder.create();
                 alertDialog.show();

                 btnNhanCode.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         // nhaanj code.

                         String Gmail=edtGamil.getText().toString();
                         if(Gmail.equals("")){
                             Toast.makeText(context,"Nhập Gmail để nhận Code.",Toast.LENGTH_SHORT).show();
                         }else {
                             String numCode = String.valueOf(code);
                             BackgroundTask1 backgroundTask1 = new BackgroundTask1(main);
                             backgroundTask1.execute("NhanCode",Gmail,numCode);
                             Toast.makeText(context, "Nhan code", Toast.LENGTH_SHORT).show();
                         }
                     }
                 });

                 // Xử lí nhấn nút đăng kí
                 btnDangki.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         String user,pass,gmail,numcode;
                         user=edtUser.getText().toString();
                         pass=edtPass.getText().toString();
                         gmail=edtGamil.getText().toString();
                         numcode=edtMaXN.getText().toString();

                         if(user.equals("")||pass.equals("")||gmail.equals("")||numcode.equals("")){
                                Toast.makeText(context,"Dữ liệu bạn nhập thiếu.",Toast.LENGTH_SHORT).show();
                         }else {
                             if(numcode.equals(String.valueOf(code))) {
                                 BackgroundTask1 backgroundTask1 = new BackgroundTask1(main);
                                 backgroundTask1.execute("DangKi", user, pass, gmail);

                                 alertDialog.cancel();
                                 //Toast.makeText(context, "Click Dang ki", Toast.LENGTH_SHORT).show();
                             }else{
                                 Toast.makeText(context,"Ma code khong dung.",Toast.LENGTH_SHORT).show();
                             }
                         }
                     }
                 });
                 

             }
         });

        return view;
    }

    @Override
    public void onMsgFromMainToFragment(String strValue) {
        // nhận giá trị từ main về
    }

    private class BackgroundTask1 extends AsyncTask<String,Void,String> {
        private Context ctx;
        private String User;

        public BackgroundTask1(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String reg_url = "http://android1998.000webhostapp.com/php/register.php";
            String code_url = "http://android1998.000webhostapp.com/php/sentcode.php";
            String login_url ="http://android1998.000webhostapp.com/php/kt_login.php";
            String method = params[0];
            User=params[1];
            if (method.equals("DangKi")){

                try {
                    URL url = new URL(reg_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));

                    String data = URLEncoder.encode("_User", "UTF-8")  + "=" + URLEncoder.encode(params[1], "UTF-8")+"&"+
                            URLEncoder.encode("_Pass", "UTF-8")  + "=" + URLEncoder.encode(params[2], "UTF-8")+"&"+
                            URLEncoder.encode("_Gmail", "UTF-8")  + "=" + URLEncoder.encode(params[3], "UTF-8");

                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    OS.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));


                    String response="",line="";

                    while((line=bufferedReader.readLine())!=null){
                        response+=line;
                    }

                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();

                    return response;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else if(method.equals("Login")){
                try {
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));

                    String data = URLEncoder.encode("_User", "UTF-8")  + "=" + URLEncoder.encode(params[1], "UTF-8")+"&"+
                            URLEncoder.encode("_Pass", "UTF-8")  + "=" + URLEncoder.encode(params[2], "UTF-8");

                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    OS.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));


                    String response="",line="";

                    while((line=bufferedReader.readLine())!=null){
                        response+=line;
                    }

                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();

                    return response;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if(method.equals("NhanCode")){
                try {
                    URL url = new URL(code_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));

                    String data = URLEncoder.encode("_Mail", "UTF-8")  + "=" + URLEncoder.encode(params[1], "UTF-8")+"&"+
                            URLEncoder.encode("_Code", "UTF-8")  + "=" + URLEncoder.encode(params[2], "UTF-8");//+"&"+
//                            URLEncoder.encode("_Gmail", "UTF-8")  + "=" + URLEncoder.encode(params[3], "UTF-8");

                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    OS.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));


                    String response="",line="";

                    while((line=bufferedReader.readLine())!=null){
                        response+=line;
                    }

                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();

                    return "Đã gữi mail.";
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return "false...";
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            Toast.makeText(ctx,result,Toast.LENGTH_SHORT).show();

            if(result.equals("Login Success...")){
                //txtname.setText(result);
                main.onMsgFromFragToMain("_User",User);
            }

        }
        public void GetValue(final String... param){
            RequestQueue requestQueue = Volley.newRequestQueue(ctx);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, param[0],
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(ctx,response,Toast.LENGTH_SHORT).show();

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(ctx,""+error+"",Toast.LENGTH_SHORT).show();
                        }
                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String> params = new HashMap<String, String>();
                    if(param[1].equals("DangKi")){
                        params.put("_User",param[2]);
                        params.put("_Pass",param[3]);
                        params.put("_Gmail",param[4]);
                    }
                    else if(param[1].equals("Login")){
                        params.put("_User",param[2]);
                        params.put("_Pass",param[3]);
                    }


                    return params;
                }
            };

            requestQueue.add(stringRequest);
        }
    }

}
