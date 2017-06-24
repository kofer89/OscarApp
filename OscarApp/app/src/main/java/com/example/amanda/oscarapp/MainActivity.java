package com.example.amanda.oscarapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;


public class MainActivity extends AppCompatActivity implements Response.Listener,Response.ErrorListener, View.OnClickListener {

    public static final String REQUEST_TAG = "UserAutentication";
    private RequestQueue mQueue;
    private EditText etUsuario,etSenha;
    private Button btnLoginWS;
    private Intent intent;
    private Usuario usu;
    private ProgressDialog pDialog;
    String usuarioTxt,senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsuario = (EditText) findViewById(R.id.etUsuario);
        etSenha = (EditText) findViewById(R.id.etSenha);
        btnLoginWS = (Button) findViewById(R.id.btnLoginWS);

        btnLoginWS.setOnClickListener(this);
    }

    private void loginWS() {

        mQueue = CustomVolleyRequestQueue.getRequestQueue(this.getApplicationContext()).getRequestQueue();

        final Response.Listener<JSONObject> list=this;
        final Response.ErrorListener errorListener =this;

        usuarioTxt = etUsuario.getText().toString();
        senha = etSenha.getText().toString();
        String url = "http://192.168.25.121:8090/OscarAppServer/UserValidator?login=" + usuarioTxt + "&senha=" + senha;

        final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method.POST, url, new JSONObject(), list, errorListener);
        jsonRequest.setTag(REQUEST_TAG);

        mQueue.add(jsonRequest);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Login em progresso...");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
    }

    @Override
    public void onClick(View v){
        loginWS();
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
            if ( (((JSONObject) response).getString("message")).equals("Login correto")){
                intent = new Intent(MainActivity.this,TelaInicial.class);
                DbConnector db = new DbConnector(MainActivity.this);
                db.open();
                usu = db.autenticaLogin(usuarioTxt,senha);
                intent.putExtra("usuario",usu);
                startActivity(intent);
            }else
                Toast.makeText(this,"Login incorreto",Toast.LENGTH_LONG).show();
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
	
	@Override
    protected void onResume(){
        if(getIntent().getBooleanExtra("SAIR", false)){
            finish();
        }
        super.onResume();
    }

    @Override
    protected void onStop(){
        super.onStop();
        if (mQueue != null){
            mQueue.cancelAll(REQUEST_TAG);
        }
    }
}
