package com.example.amanda.oscarapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class TelaInicial extends AppCompatActivity {

    private DbConnector db = new DbConnector(this);
    private Usuario usuario;
    private Intent intent;
    private Candidato filme;
    private Diretor diretor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        TextView tvBoasVindas = (TextView) findViewById(R.id.tvBoasVindas);

        intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {

                usuario = (Usuario) bundle.getSerializable("usuario");
                if (bundle.getSerializable("filme") != null)
                    filme = (Candidato) bundle.getSerializable("filme");
                if (bundle.getSerializable("diretor") != null)
                    diretor = (Diretor) bundle.getSerializable("diretor");

                if (usuario !=null){
                    tvBoasVindas.setText("Bem-vindo(a) "+ usuario.getNome());
                }

                if (diretor != null)
                    Log.v("log--------- ",diretor.getNome());
                if (filme != null)
                    Log.v("log--------- ",filme.getNome());
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_votacao,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){

        if (usuario.getVotou()==1){
            Toast.makeText(TelaInicial.this,"Não é possível votar novamente.",Toast.LENGTH_SHORT).show();
        }
        else{

            if (item.getTitle().equals("Confirmar Voto")){

                if (filme==null || diretor == null){
                    Toast.makeText(this,"É necessário votar para Filme e Diretor.",Toast.LENGTH_LONG).show();
                }
                else {
                    intent = new Intent(TelaInicial.this, ConfirmarVoto.class);
                    intent.putExtra("usuario",usuario);
                    intent.putExtra("filme",filme);
                    intent.putExtra("diretor",diretor);
                    startActivity(intent);
                }
            }
            else if (item.getTitle().equals("Votar Filme")){
                intent = new Intent(TelaInicial.this, VotacaoFilme.class);
                intent.putExtra("usuario",usuario);
                intent.putExtra("filme",filme);
                intent.putExtra("menuClicado",item.getTitle());
                startActivity(intent);
            }
            else {
                intent = new Intent(TelaInicial.this, VotacaoDiretor.class);
                intent.putExtra("usuario",usuario);
                intent.putExtra("diretor",diretor);
                intent.putExtra("menuClicado",item.getTitle());
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void confirmaVoto(View view){
        if (usuario.getVotou()!=1){
            DbConnector db = new DbConnector(this);
            db.open();
            db.confirmarVoto(usuario.getUsuario());
            Toast.makeText(TelaInicial.this,"Voto efetuado com sucesso.",Toast.LENGTH_SHORT).show();
            usuario.setVotou(1);
        }
        else
            Toast.makeText(TelaInicial.this,"Não é possível votar novamente.",Toast.LENGTH_SHORT).show();
    }
}
