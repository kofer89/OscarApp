package com.example.amanda.oscarapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class DetalhesDiretor extends AppCompatActivity {

    private TextView nomeDetalhes;
    private Diretor diretor;
    private Candidato filme;
    private Button btnVotar;
    private Intent intent;
    private String tipoVotacao;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_diretor);

        nomeDetalhes = (TextView) findViewById(R.id.nomeDiretorDetalhes);
        btnVotar = (Button) findViewById(R.id.btnVotar);


        intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                filme = (Candidato) intent.getSerializableExtra("filme");
                tipoVotacao = intent.getStringExtra("tipoVotacao");
                diretor = (Diretor) intent.getSerializableExtra("objetoDiretor");
                usuario = (Usuario) intent.getSerializableExtra("usuario");
                nomeDetalhes.setText("Diretor: " + diretor.getNome());

            }
        }

        btnVotar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(DetalhesDiretor.this, TelaInicial.class);
                intent.putExtra(tipoVotacao, diretor);
                intent.putExtra("usuario", usuario);
                intent.putExtra("diretor", diretor);
                intent.putExtra("filme", filme);
                intent.putExtra("tipoVotacao", tipoVotacao);
                startActivity(intent);
            }
        });
    }
}