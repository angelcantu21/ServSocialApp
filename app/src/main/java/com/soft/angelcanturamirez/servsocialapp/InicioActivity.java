package com.soft.angelcanturamirez.servsocialapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class InicioActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,Response.ErrorListener, Response.Listener<JSONObject>, View.OnClickListener {

    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;
    CircleImageView fotoUsuario,fotoUsuarioDetalles;
    String id_user="";
    SharedPreferences sharedPreferences;
    ProgressDialog loading;
    NavigationView navigationView;
    TextView txt_usuarioNombre, txt_apartamento;
    EditText nombre, direccion, correo;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setItemIconTintList(null);
        View header = navigationView.getHeaderView(0);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        btn = (Button)findViewById(R.id.btnAyudar);

        btn.setOnClickListener(this);

        txt_usuarioNombre = (TextView) header.findViewById(R.id.txtNombreUsuario);
        txt_apartamento = (TextView) header.findViewById(R.id.txtApartamento);
        fotoUsuario = (CircleImageView) header.findViewById(R.id.imageView);
        fotoUsuarioDetalles = findViewById(R.id.imgUsuarioDetalles);

        nombre = findViewById(R.id.txtNombreUsuarioDetalles);
        direccion = findViewById(R.id.txtDepartamentoUsuarioDetalles);
        correo = findViewById(R.id.txtCodigoUsuarioDetalles);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //WebService datos
        sharedPreferences = getSharedPreferences("LoginSp", MODE_PRIVATE);
        id_user = sharedPreferences.getString("ID", "No hay datos");

        //Mensaje de espera
        loading = ProgressDialog.show(this,"Cargando informacion","Espere por favor...",false,false);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        cargarWebService();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inicio) {
            // Handle the camera action
            Intent start = new Intent(getApplicationContext(), InicioActivity.class);
            startActivity(start);
        } else if (id == R.id.nav_acerca) {
            Intent start = new Intent(getApplicationContext(), AcercaActivity.class);
            startActivity(start);
        }else if(id == R.id.nav_historial){
            Intent start = new Intent(getApplicationContext(), HistorialActivity.class);
            startActivity(start);
        }else if(id == R.id.nav_mapa){
            Intent start = new Intent(getApplicationContext(), MapsActivity.class);
            startActivity(start);
        }
        else if (id == R.id.nav_creditos) {
            Intent start = new Intent(getApplicationContext(), CreditosActivity.class);
            startActivity(start);
        }else if(id==R.id.nav_sesion){
            sharedPreferences.edit().putBoolean("Logeado", false).apply();
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPostResume() {
        //requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url_edit= "http://"+getString(R.string.url)+"/ServSocial/consultarPersona.php?id="+id_user;
        jsonObjectRequest= new JsonObjectRequest(Request.Method.GET,url_edit,null,this,this);
        requestQueue.add(jsonObjectRequest);
        super.onPostResume();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        //Toast.makeText(getApplicationContext(), ""+error.toString(),Toast.LENGTH_LONG).show();
        Log.i("Error",error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray json = response.optJSONArray("personaRegistro");

        try {
            for (int x = 0; x < json.length(); x++) {
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(x);
                txt_usuarioNombre.setText(jsonObject.optString("Nombre")+" "+jsonObject.optString("Apellidos"));
                txt_apartamento.setText(jsonObject.optString("Correo"));

                nombre.setText(txt_usuarioNombre.getText());
                direccion.setText(jsonObject.optString("Direccion"));
                correo.setText(jsonObject.optString("Correo"));

            }

            loading.dismiss();
        }catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void cargarWebService(){
        String url = "http://"+getString(R.string.url)+"/ServSocial/uploads/"+id_user+".jpg";
        url = url.replace(" ","%20");

        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                fotoUsuario.setImageBitmap(response);
                fotoUsuarioDetalles.setImageBitmap(response);
                loading.dismiss();
            }
        }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error al cargar la imagen"+error, Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(imageRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAyudar:
                Intent start = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(start);
                break;
        }
    }
}
