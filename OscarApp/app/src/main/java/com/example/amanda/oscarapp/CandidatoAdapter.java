package com.example.amanda.oscarapp;

/**
 * Created by Amanda on 10/06/2017.
 */


import android.content.Context;
import android.graphics.Bitmap;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;


public class CandidatoAdapter extends BaseAdapter {

    private ArrayList<Candidato> objects;
    private Context context;

    public CandidatoAdapter(ArrayList<Candidato> objects, Context context) {
        this.objects = objects;
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        View v = convertView;

        ViewHolder vh;
        if (v == null) {
            vh = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_candidato, null);

            vh.nome = (TextView) v.findViewById(R.id.tvNomeCandidato);
            vh.genero = (TextView) v.findViewById(R.id.tvGeneroCandidato);
            vh.fotoCandidato = (ImageView) v.findViewById(R.id.fotoCandidato);

            v.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }

        final Candidato candidato = objects.get(position);
        vh.nome.setText(candidato.getNome());
        vh.genero.setText(String.valueOf(candidato.getGenero()));
        Bitmap bitmap = null;


        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            try {
                bitmap = new AuxiliarImg().baixarImagem(candidato.getLinkFoto());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        vh.fotoCandidato.setImageBitmap(bitmap);

        return v;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Candidato getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        public TextView nome;
        public ImageView fotoCandidato;
        public TextView genero;
    }
}
