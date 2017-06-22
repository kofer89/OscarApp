package com.example.amanda.oscarapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class ConfirmarVoto extends AppCompatActivity  {

    private Intent intent;
    private Candidato filme;
    private Diretor diretor;
    private Usuario usuario;
    private TextView tvNomeFilme,tvGeneroFilme, tvNomeDiretor,tvGeneroDiretor;
    private ImageView fotoFilme,fotoDiretor;
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

        btnConfirmarVoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usuario.getVotou()!=1){
                    DbConnector db = new DbConnector(ConfirmarVoto.this);
                    db.open();
                    db.confirmarVoto(usuario.getUsuario(), filme.getNome(), diretor.getNome());
                    Toast.makeText(ConfirmarVoto.this,"Voto efetuado com sucesso.",Toast.LENGTH_SHORT).show();
                    usuario.setVotou(1);
                }

                intent = new Intent(ConfirmarVoto.this,TelaInicial.class);
                intent.putExtra("usuario",usuario);
                intent.putExtra("filme",filme);
                intent.putExtra("diretor",diretor);
                startActivity(intent);
            }
        });

    }
}
