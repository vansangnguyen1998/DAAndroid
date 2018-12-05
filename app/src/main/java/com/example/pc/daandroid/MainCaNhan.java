package com.example.pc.daandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.DoubleBuffer;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainCaNhan extends Fragment implements FragmentCallBack {
    TextView hTen,ngSinh,gTinh;
    ImageView anhDaiDien;
    MainActivity main;
    Context context;
    String User="";
    boolean thongtin=false;
    private int mYear=1998,mMonth=1,mDay=1;


    public static MainCaNhan newInstance(String strArg) {
        MainCaNhan frag = new MainCaNhan();
        Bundle args = new Bundle();
        args.putString("strArg1", strArg);
        frag.setArguments(args);
        return frag;
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final RelativeLayout canhan = (RelativeLayout) inflater.inflate(R.layout.activity_main_ca_nhan,null);

// loi~
        hTen=(TextView) canhan.findViewById(R.id.ViewHoTen);
        ngSinh=(TextView) canhan.findViewById(R.id.ViewNgaySinh);
        gTinh=(TextView) canhan.findViewById(R.id.ViewGioiTinh);
        anhDaiDien=(ImageView) canhan.findViewById(R.id.anhDaiDien);

        BackgroundTask1 backgroundTask1 = new BackgroundTask1(context);
        backgroundTask1.execute("kt_thongtin",CheckLogin.User);

        String hten = hTen.getText().toString();

        FloatingActionButton btnupdate = (FloatingActionButton) canhan.findViewById(R.id.update);

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            Toast.makeText(context,"Cập nhật thông tin.",Toast.LENGTH_SHORT).show();

            final AlertDialog.Builder builder = new AlertDialog.Builder(main);

            View view = getLayoutInflater().inflate(R.layout.update_thong_tin,null);

            final Button btnSelect=(Button) view.findViewById(R.id.btnselect);
            Button btnUpdate = (Button) view.findViewById(R.id.btnUpdate);

            final TextView eddate =(TextView) view.findViewById(R.id.edtdate);

            RadioGroup rg= (RadioGroup) view.findViewById(R.id.rg);
            final RadioButton radioNam= (RadioButton) view.findViewById(R.id.radioNam);
            RadioButton radioNu = (RadioButton) view.findViewById(R.id.radioNu);

            final EditText Ten = (EditText) view.findViewById(R.id.edtten);
                builder.setView(view);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

            btnSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Calendar newCalendar = Calendar.getInstance();
                   mYear= newCalendar.get(Calendar.YEAR);
                     mMonth= newCalendar.get(Calendar.MONTH);
                     mDay= newCalendar.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    eddate.setText(""+dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            });

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Ten.getText().toString().equals("")){
                        Toast.makeText(context,"Bạn chưa nhập tên.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        String ten=Ten.getText().toString();
                        String GT="";
                        if(radioNam.isChecked()){
                            GT="Nam";
                        }else{
                            GT="Nu";
                        }
                        String NS=eddate.getText().toString();

                        BackgroundTask1 backgroundTask1 = new BackgroundTask1(context);
                        backgroundTask1.execute("Update",ten,GT,NS,User);
                        hTen.setText(ten);
                        gTinh.setText(GT);
                        ngSinh.setText(NS);
                        alertDialog.cancel();
                    }
                }
            });


        }

        }
        );

        return canhan;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context = getActivity();
            main = (MainActivity) getActivity();
        }catch  (IllegalStateException e) {
            throw new IllegalStateException(
                    "MainActivity must implement callbacks");
        }
    }

    @Override
    public void onMsgFromMainToFragment(String strValue) {
        User=strValue;
    }




    private class BackgroundTask1 extends AsyncTask<String,Void,String> {
        private Context ctx;
        //private String User;

        public BackgroundTask1(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            String url_update="http://android1998.000webhostapp.com/php/update.php";
            String url_kt="http://android1998.000webhostapp.com/php/kt_thongtincanhan.php";

            String method = params[0];
            //User=params[1];
            if (method.equals("Update")){

                try {
                    URL url = new URL(url_update);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));

                    String data = URLEncoder.encode("_Ten", "UTF-8")  + "=" + URLEncoder.encode(params[1], "UTF-8")+"&"+
                            URLEncoder.encode("_GioiTinh", "UTF-8")  + "=" + URLEncoder.encode(params[2], "UTF-8")+"&"+
                            URLEncoder.encode("_NgaySinh", "UTF-8")  + "=" + URLEncoder.encode(params[3], "UTF-8")+"&"+
                            URLEncoder.encode("_User", "UTF-8")  + "=" + URLEncoder.encode(params[4], "UTF-8");

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

            }else if(method.equals("kt_thongtin")){
                try {
                    URL url = new URL(url_kt);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));

                    String data = URLEncoder.encode("_User", "UTF-8")  + "=" + URLEncoder.encode(params[1], "UTF-8");

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
            if(thongtin==false){
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    JSONObject jsonObject = (JSONObject) jsonArray.get(0);
                    hTen.setText(jsonObject.getString("ten"));
                    gTinh.setText(jsonObject.getString("gioitinh"));
                    ngSinh.setText(jsonObject.getString("ngaysinh"));
                    thongtin=true;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
