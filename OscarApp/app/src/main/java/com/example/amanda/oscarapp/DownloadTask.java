package com.example.amanda.oscarapp;

/**
 * Created by Amanda on 10/06/2017.
 */


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;


public class DownloadTask extends AsyncTask<String, Void, Bitmap> {
    private Context ctx;
    private ImageView image;
    private ProgressDialog progressDialog;

    public DownloadTask(Context ctx, ImageView image) {
        this.ctx = ctx;
        this.image = image;
    }

    protected void onPreExecute(){
        super.onPreExecute();
        progressDialog = ProgressDialog.show(ctx,"Por favor aguarde ...","Baixando imagem ...");
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap imagemBitmap = null;
        try{
            imagemBitmap = AuxiliarImg.baixarImagem(params[0]);
        } catch (IOException e){
            e.printStackTrace();
        }
        return imagemBitmap;
    }

    protected void onPostExecute(Bitmap bitmap){
        image.setImageBitmap(bitmap);
        progressDialog.dismiss();
    }
}
