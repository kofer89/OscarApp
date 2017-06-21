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

import java.io.IOException;

public class DetalhesCandidato extends AppCompatActivity {

    private ImageView img;
    private TextView nomeDetalhes;
    private TextView generoDetalhes;
    private Candidato candidato, filme;
    private Button btnVotar;
    private Intent intent;
    private String tipoVotacao;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_candidato);

        img = (ImageView) findViewById(R.id.imageViewDetalhesCandidato);
        nomeDetalhes = (TextView) findViewById(R.id.nomeCandidatoDetalhes);
        generoDetalhes = (TextView) findViewById(R.id.generoCandidatoDetalhes);
        btnVotar = (Button) findViewById(R.id.btnVotar);


        intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                tipoVotacao = intent.getStringExtra("tipoVotacao");
                candidato = (Candidato) intent.getSerializableExtra("objetoCandidato");
                usuario = (Usuario) intent.getSerializableExtra("usuario");
                if (bundle.getSerializable("filme") != null)
                    filme = (Candidato) bundle.getSerializable("filme");
                    nomeDetalhes.setText("Filme: " + candidato.getNome());
                    generoDetalhes.setText("Gênero: " + candidato.getGenero());
                try {
                    img.setImageBitmap(new AuxiliarImg().baixarImagem(candidato.getLinkFoto()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        btnVotar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(DetalhesCandidato.this, TelaInicial.class);
                intent.putExtra(tipoVotacao, candidato);
                intent.putExtra("usuario", usuario);
                intent.putExtra("filme", filme);
                intent.putExtra("tipoVotacao", tipoVotacao);
                startActivity(intent);
            }
        });
    }
}