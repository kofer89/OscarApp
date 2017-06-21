package com.example.amanda.oscarapp;

/**
 * Created by Amanda on 10/06/2017.
 */


import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

public class CustomVolleyRequestQueue {

    private static CustomVolleyRequestQueue mInstance;
    private static Context mCtx;
    private RequestQueue mRequestQueue;

    private CustomVolleyRequestQueue(Context context){
        //recebe o contexto da aplicaçao
        // assim a fila dura o ciclo de vida do app
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized  CustomVolleyRequestQueue getmInstance(Context context){
        if (mInstance == null){
            mInstance = new CustomVolleyRequestQueue(context);
        }
        // retorna uma instância da fila
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        if (mRequestQueue == null){
            // cache de 10mb
            Cache cache = new DiskBasedCache(mCtx.getCacheDir(), 10 * 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());
            mRequestQueue = new RequestQueue(cache, network);
            mRequestQueue.start();
        }
        return mRequestQueue;
    }
}
