package com.soft.angelcanturamirez.servsocialapp;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.soft.angelcanturamirez.servsocialapp.RecyclerViews.RecyclerViewServicios;
import com.soft.angelcanturamirez.servsocialapp.entidades.HistorialModelo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HistorialActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {

    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;

    ArrayList<HistorialModelo> listaHistorial;
    RecyclerView recyclerView;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        listaHistorial = new ArrayList<>();

        recyclerView=(RecyclerView) findViewById(R.id.recyclerHistorial);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        RecyclerViewServicios adaptador = new RecyclerViewServicios(listaHistorial);
        recyclerView.setAdapter(adaptador);

        //Shared Preferences
        SharedPreferences sharedPreferences = getSharedPreferences("LoginSp", MODE_PRIVATE);
        id = sharedPreferences.getString("ID", "No hay datos");

        String url_edit= "http://"+getString(R.string.url)+"/ServSocial/consultarHistorial.php?id="+id;
        //Toast.makeText(getApplicationContext(), url_edit, Toast.LENGTH_SHORT).show();
        jsonObjectRequest= new JsonObjectRequest(Request.Method.GET,url_edit,null,this,this);
        requestQueue.add(jsonObjectRequest);
    }

    //METODO PARA PONER BOTON DE REGRESAR EN LA PARTE DE LA BARRA
    private void setupActionBar(){
        ActionBar regresar = getSupportActionBar();//SE INSTANCIA LA ACCION DE LA BARRA
        if (regresar != null){//MIENTRAS SEA DIFERENTE DE NULO
            regresar.setDisplayHomeAsUpEnabled(true);//SE MANTIENE HABILITADO
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, ""+error.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        HistorialModelo historial= null;
        JSONArray json=response.optJSONArray("Historial");



        try {
            for(int x=0;x<json.length();x++){
                historial=new HistorialModelo();
                JSONObject jsonObject=null;
                jsonObject=json.getJSONObject(x);
                historial.setNombre(jsonObject.optString("Nombre")+" "+jsonObject.optString("Apellidos"));
                historial.setFecha(jsonObject.optString("Fecha"));
                historial.setAccion(jsonObject.optString("accion"));
                historial.setDescripcion_peticion(jsonObject.optString("Descripcion_Peticion"));
                historial.setDescripcion_status(jsonObject.optString("Descripcion_status"));
                listaHistorial.add(historial);
            }

            RecyclerViewServicios adaptador=new RecyclerViewServicios(listaHistorial);
            recyclerView.setAdapter(adaptador);

            adaptador.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "Seleccion: "+listaHistorial.get(recyclerView.getChildAdapterPosition(view)).getFecha(), Toast.LENGTH_LONG).show();
                    //Intent abrir = new Intent(getApplicationContext(), EdificioDetallesActivity.class);
                    //abrir.putExtra("titulo", listaEdificios.get(recyclerView.getChildAdapterPosition(view)).getNombre());
                    //abrir.putExtra("descripcion", listaEdificios.get(recyclerView.getChildAdapterPosition(view)).getDescripcion_larga());
                    //startActivity(abrir);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
