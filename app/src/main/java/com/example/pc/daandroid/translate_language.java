package com.example.pc.daandroid;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class translate_language {
    public static String Translate(String data, final Context context)
    {
        final String[] result = new String[1];
        String dataPare=data.replace(" ","%20");
        String url_translate="https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20190115T151627Z.84e88e1173010a4e.d275e747d1331490dafec1ee476318d0e18c7c75&text="+dataPare+"&lang=vi-en";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_translate,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject j = new JSONObject(response);
                            JSONArray arrJson = j.getJSONArray("text");
                            String arr = new String();
                            arr=arrJson.getString(0);
                            result[0] =arr;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //Toast.makeText(ActivityDiaDiemChiTiet.this,""+error+"",Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(stringRequest);
        return result[0];
    }
}
