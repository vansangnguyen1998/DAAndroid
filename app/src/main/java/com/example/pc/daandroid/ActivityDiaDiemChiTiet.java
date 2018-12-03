package com.example.pc.daandroid;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
import java.util.HashMap;
import java.util.Map;

public class ActivityDiaDiemChiTiet extends Activity implements AdapterView.OnItemSelectedListener{

    Button HienThiDiaDiem,ThoiTiet, btnNhanXet;
    ViewGroup viewGroup;
    EditText editText;
    RatingBar ratingBar;
    Integer [] Imageview= {R.drawable.android1,R.drawable.android3,R.drawable.bien};
    Spinner spinner;
    TextView thongTin;
    ScrollView scrollView;
    float numberStar;
    ListView lvNhanXet;

    String [] adapterSpinner = {"Dịch Vụ","Nhà Hàng", "Khách Sạn", "ATM"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_diem_chi_tiet);

        final Bundle bundle = this.getIntent().getExtras();

        //diadiemchitiet diadiem= (diadiemchitiet) bundle.getSerializable("diadiemchitiet");



        AnhXa();

        if(bundle!=null) {

            thongTin.setText(bundle.getString("mota"));

            scrollView.fullScroll(View.FOCUS_DOWN);
        }

        // set picture horiontalImage.
        for(int i=0;i<Imageview.length;i++){
            final View singleFrame = getLayoutInflater().inflate(
                    R.layout.icon_trang_thong_tin, null);

            singleFrame.setId(i);

            ImageView image = (ImageView) singleFrame.findViewById(R.id.iconScoll);

            image.setImageResource(Imageview[i]);

            viewGroup.addView(singleFrame);
        }

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                numberStar=rating;
            }
        });

        // build adapter spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, adapterSpinner);

        adapter.setDropDownViewResource
                (android.R.layout.simple_list_item_single_choice);

        spinner.setAdapter(adapter);

        // nhấn vào nút nhận xet để cập nhật lên database.
        btnNhanXet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sosao= String.valueOf(numberStar);
                String txtNhanXet= editText.getText().toString();
                BackgroundTask1 backgroundTask1 = new BackgroundTask1(ActivityDiaDiemChiTiet.this);
                backgroundTask1.execute("NhanXet", sosao, txtNhanXet,bundle.getString("tentinh"),
                        bundle.getString("tendiadanh"));
                editText.setText("");
            }
        });
    }


    private void AnhXa(){

        btnNhanXet = (Button) findViewById(R.id.btnNhanXet);
        thongTin = (TextView) findViewById(R.id.ThongTin);
        scrollView =(ScrollView) findViewById(R.id.iconScoll);
        HienThiDiaDiem = (Button) findViewById(R.id.HienThiDiaDiem);
        ThoiTiet = (Button) findViewById(R.id.ThoiTiet);
        viewGroup = (ViewGroup) findViewById(R.id.viewgroup);
        editText = (EditText) findViewById(R.id.NhanXet);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar1);
        spinner = (Spinner) findViewById(R.id.DichVu);
        lvNhanXet = (ListView) findViewById(R.id.lvNhanXet);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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

            String loaidl_url = "http://android1998.000webhostapp.com/php/insertNhanXet.php";

            String method = params[0];

            if (method.equals("NhanXet")){

                try {
                    URL url = new URL(loaidl_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));

                    String data = URLEncoder.encode("_SoSao", "UTF-8")  + "=" + URLEncoder.encode(params[1], "UTF-8")+"&"+
                            URLEncoder.encode("_NhanXet", "UTF-8")  + "=" + URLEncoder.encode(params[2], "UTF-8")+"&"+
                            URLEncoder.encode("_TenTinh", "UTF-8")  + "=" + URLEncoder.encode(params[3], "UTF-8")+"&"+
                            URLEncoder.encode("_TenDiaDanh", "UTF-8")  + "=" + URLEncoder.encode(params[4], "UTF-8");

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
