package com.stocktwt.glutenchecker;

import android.app.ProgressDialog;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class volley_api {

    private static final String TAG = volley_api.class.getSimpleName();
    private String ret;
    String response;
    private ProgressDialog pDialog;




    public void ReturnGluten (final String name){
        String tag_string_reg = "req_gluten";

        StringRequest strReg = new StringRequest(Request.Method.POST, AppConfig.URL_API,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");
                            Log.d(TAG, response);
                            final String gluten = jObj.getString("gluten");
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Logging error: " + error.getMessage());
            }})  {
            @Override
            protected Map<String, String> getParams() {
                //Posting weight params to weight url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "gluten_check");
                params.put("name", name);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReg, tag_string_reg);


    }


}
