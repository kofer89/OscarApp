package com.example.amanda.oscarapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ConfirmarVoto extends AppCompatActivity implements Response.Listener,Response.ErrorListener, View.OnClickListener  {

    public static final String REQUEST_TAG = "ConfirmarVoto";
    private RequestQueue mQueue;
    private Intent intent;
    private ProgressDialog pDialog;
    private Candidato filme;
    private Diretor diretor;
    private Usuario usuario, nome;
    private TextView tvNomeFilme,tvGeneroFilme, tvNomeDiretor,tvGeneroDiretor;
    private ImageView fotoFilme;
    private Button btnAlterarVoto, btnConfirmarVoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_voto);

        tvNomeFilme = (TextView) findViewById(R.id.textViewNomeFilme);
        tvNomeDiretor = (TextView) findViewById(R.id.textViewNomeDiretor);
        tvGeneroFilme = (TextView) findViewById(R.id.textViewGeneroFilme);
        tvGeneroDiretor = (TextView) findViewById(R.id.textViewGeneroDiretor);

        fotoFilme = (ImageView) findViewById(R.id.imageViewFilme);

        btnAlterarVoto = (Button) findViewById(R.id.btnAlterarVoto);
        btnConfirmarVoto = (Button) findViewById(R.id.btnConfirmarVoto);
        btnConfirmarVoto.setOnClickListener(this);

        intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                usuario = (Usuario) bundle.getSerializable("usuario");
                filme = (Candidato) bundle.getSerializable("filme");
                diretor = (Diretor) bundle.getSerializable("diretor");
            }
        }

        tvNomeFilme.setText(filme.getNome());
        tvNomeDiretor.setText(diretor.getNome());
        tvGeneroFilme.setText(filme.getGenero());
        tvGeneroDiretor.setText(diretor.getNome());

        try {
            fotoFilme.setImageBitmap(new AuxiliarImg().baixarImagem(filme.getLinkFoto()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        btnAlterarVoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(ConfirmarVoto.this,TelaInicial.class);
                intent.putExtra("usuario",usuario);
                intent.putExtra("filme",filme);
                intent.putExtra("diretor",diretor);
                startActivity(intent);
            }
        });
    }

    private void confirmaVoto(){
        mQueue = CustomVolleyRequestQueue.getRequestQueue(this.getApplicationContext()).getRequestQueue();

        final Response.Listener<JSONObject> list=this;
        final Response.ErrorListener errorListener =this;

        if (usuario.getVotou()!=1){

            String url = "http://192.168.25.121:8090/OscarAppServer/ConfirmaVoto?nome=" + usuario.getNome() +
                    "&filme=" + filme + "&diretor=" + diretor;

            final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method.POST, url, new JSONObject(),
                    list, errorListener);
            jsonRequest.setTag(REQUEST_TAG);

            mQueue.add(jsonRequest);
            pDialog = new ProgressDialog(this);
            pDialog.setMessage("Confirmação em progresso...");
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.show();

        }else{
            Toast.makeText(ConfirmarVoto.this,"Você já votou! Ação não permitida...",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v){
        confirmaVoto();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(Object response) {
        String responseWs = ("Resposta: " + response);
        System.out.println(responseWs);
        pDialog.dismiss();
        try{
            if ( (((JSONObject) response).getString("message")).equals("Voto confirmado")){
                intent = new Intent(ConfirmarVoto.this,TelaInicial.class);
                startActivity(intent);
            }else
                Toast.makeText(this,"Falha ao registrar voto.",Toast.LENGTH_LONG).show();
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop(){
        super.onStop();
        if (mQueue != null){
            mQueue.cancelAll(REQUEST_TAG);
        }
    }
}
