package com.example.amanda.oscarapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError   ;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements Response.Listener,
        Response.ErrorListener {
    public static final String REQUEST_TAG = "UserAutentication";
    private RequestQueue mQueue;
    private EditText etUsuario,etSenha;
    private Button btnLoginWS;
    private Intent intent;
    private Usuario usu;
    String usuarioTxt,senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsuario = (EditText) findViewById(R.id.etUsuario);
        etSenha = (EditText) findViewById(R.id.etSenha);
        btnLoginWS = (Button) findViewById(R.id.btnLoginWS);
    }

    public void login(View view){
        int usuario=-1;
        usuarioTxt = etUsuario.getText().toString();
        senha = etSenha.getText().toString();

        if (!usuarioTxt.isEmpty())
            usuario = Integer.parseInt(usuarioTxt);

        DbConnector db = new DbConnector(MainActivity.this);
        db.open();

        usu = db.autenticaLogin(usuario,senha);

        if (usu.getNome()!=null){
            intent = new Intent(MainActivity.this,TelaInicial.class);
            intent.putExtra("usuario",usu); //aqui
            startActivity(intent);
        } else
            Toast.makeText(this,"Dados inválidos", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(Object response) {
        String responseWs = ("Resposta: " + response);
        try{
            if ( (((JSONObject) response).getString("message")).equals("Login correto")){
                intent = new Intent(MainActivity.this,TelaInicial.class);

                DbConnector db = new DbConnector(MainActivity.this);
                db.open();

                usu = db.autenticaLogin(Integer.parseInt(usuarioTxt),senha);
                intent.putExtra("usuario",usu);
                startActivity(intent);
            }else
                Toast.makeText(this,"Login incorreto",Toast.LENGTH_LONG).show();
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart(){
        super.onStart();

        mQueue = CustomVolleyRequestQueue.getmInstance(this.getApplicationContext()).getRequestQueue();
        final Response.Listener<JSONObject> list=this;
        final Response.ErrorListener errorListener =this;

        btnLoginWS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int usuario=-1;
                usuarioTxt = etUsuario.getText().toString();
                senha = etSenha.getText().toString();

                if (!usuarioTxt.isEmpty())
                    usuario = Integer.parseInt(usuarioTxt);

                String url = "http://10.0.2.2:8080/UserAutenticator/UserValidator?usuario="+ usuarioTxt+ "&senha="+senha;
                final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method.POST, url, new JSONObject(), list, errorListener);
                jsonRequest.setTag(REQUEST_TAG);

                mQueue.add(jsonRequest);
            }
        });
    }

    @Override
    protected void onStop(){
        super.onStop();
        if (mQueue != null){
            mQueue.cancelAll(REQUEST_TAG);
        }
    }

}
