package com.example.amanda.oscarapp;

/**
 * Created by Amanda on 10/06/2017.
 */

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpHandler {
    public String makeServiceCall(String reqUrl){
        String response = null;
        try {
            // faz o request
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            //pega a resposta
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = converStreamToString(in);
        } catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    private String converStreamToString(InputStream is){
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while((line = reader.readLine()) != null){
                sb.append(line).append('\n');
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (Exception e){
                e.printStackTrace();
            }
            return sb.toString();
        }
    }
}

