package com.soft.angelcanturamirez.servsocialapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener {

    ImageView imag;
    ImageButton imgButton;
    Button btn_ingresar;
    EditText user, pass;
    android.support.v7.widget.CardView card;
    android.support.v7.widget.Toolbar tool;

    String id="";
    Boolean login_correcto=false;
    SharedPreferences sharedPreferences;

    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;

    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences("LoginSp", MODE_PRIVATE);

        if (ObtenerEstado()) {
            Intent mainActivity = new Intent(getApplicationContext(), InicioActivity.class);
            startActivity(mainActivity);
            finish();
        } else {

            imag = findViewById(R.id.imageView);
            user = (EditText) findViewById(R.id.txtEmail);
            pass = (EditText) findViewById(R.id.txtPassword);
            btn_ingresar = findViewById(R.id.btLogInButton);
            card = findViewById(R.id.cv);
            tool = findViewById(R.id.bgHeader);

            Animation animation = AnimationUtils.loadAnimation(LoginActivity.this,R.anim.uptodown);
            imag.setAnimation(animation);
            tool.setAnimation(animation);

            Animation animation_cv = AnimationUtils.loadAnimation(LoginActivity.this,R.anim.downtoup);
            card.setAnimation(animation_cv);
            btn_ingresar.setAnimation(animation_cv);

            //WebService
            requestQueue = Volley.newRequestQueue(getApplicationContext());

            btn_ingresar.setOnClickListener(this);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btLogInButton:
                if (!(user.getText().toString().equals("") && pass.getText().toString().equals(""))) {
                    //Mensaje de espera
                    loading = ProgressDialog.show(this, "Verificando datos", "Espere por favor...", false, false);
                    String url_edit = "http://" + getString(R.string.url) + "/ServSocial/login.php?correo=" + user.getText().toString()+"&password="+pass.getText().toString();
                   // Toast.makeText(getApplicationContext(),url_edit, Toast.LENGTH_LONG).show();
                    jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url_edit, null, this, this);
                    requestQueue.add(jsonObjectRequest);
                } else {
                    Toast.makeText(getApplicationContext(), "Datos vacios", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(),"Datos incorrectos"+error.toString(), Toast.LENGTH_LONG).show();
        login_correcto = false;
        loading.dismiss();
    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray json=response.optJSONArray("Login");
        JSONObject jsonObject=null;

        try {
            jsonObject=json.getJSONObject(0);
            id = jsonObject.optString("ID");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        login_correcto = true;
        if (login_correcto) {
            sharedPreferences.edit().putString("ID", id).apply();
            loading.dismiss();
            Intent start = new Intent(getApplicationContext(), InicioActivity.class);
            startActivity(start);
            GuardarEstado();
            finish();
        }
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);//Se crea un alerta builder
        builder.setMessage("¿Seguro quieres salir?");//Mensaje de la alerta
        builder.setTitle("Confirmación");//Titulo de la alerta
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });//EN CASO DE QUE SI
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });//EN CASO DE QUE NO

        AlertDialog dialog = builder.create();//Se crea esa alerta
        dialog.show();//Se muestra esa alerta
    }
    public void GuardarEstado(){
        sharedPreferences.edit().putBoolean("Logeado", true).apply();
    }

    public Boolean ObtenerEstado(){
        return sharedPreferences.getBoolean("Logeado", false);
    }
}
