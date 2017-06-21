package com.example.amanda.oscarapp;

/**
 * Created by Amanda on 12/06/2017.
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


public class DiretorAdapter extends BaseAdapter {

    private ArrayList<Diretor> objects;
    private Context context;

    public DiretorAdapter(ArrayList<Diretor> objects, Context context) {
        this.objects = objects;
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        View v = convertView;

        ViewHolder vh;
        if (v == null) {
            vh = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_diretor, null);

            vh.nome = (TextView) v.findViewById(R.id.tvNomeDiretor);

            v.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }

        final Diretor diretor = objects.get(position);
        vh.nome.setText(diretor.getNome());

        return v;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Diretor getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        public TextView nome;
    }
}
