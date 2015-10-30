package com.example.kianfar.producthunt_danakianfar.content;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.kianfar.producthunt_danakianfar.DataPool;

import java.util.HashMap;
import java.util.Map;

public class ProductHuntStringRequest extends StringRequest{

    public ProductHuntStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public ProductHuntStringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }


    @Override
    public Map getHeaders() throws AuthFailureError {
        Map headers = new HashMap();
        headers.put("Authorization", "Bearer " + DataPool.access_token);
        return headers;
    }
}
