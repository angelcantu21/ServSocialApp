package com.soft.angelcanturamirez.servsocialapp.RecyclerViews;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soft.angelcanturamirez.servsocialapp.R;
import com.soft.angelcanturamirez.servsocialapp.entidades.HistorialModelo;

import java.util.List;

public class RecyclerViewServicios extends RecyclerView.Adapter<RecyclerViewServicios.ViewHolder> implements View.OnClickListener {

    public List<HistorialModelo> ListaServicio;
    private View.OnClickListener listener;

    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.onClick(v);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        //Variables
        private TextView nombre, desc_pet, desc_status, accion, fecha;

        public ViewHolder(View itemView) {
            super(itemView);
            nombre = (TextView)itemView.findViewById(R.id.txtNombreHistorial);
            desc_pet = (TextView)itemView.findViewById(R.id.txtDescripcion_peticion);
            desc_status = (TextView)itemView.findViewById(R.id.txtDescripcion_status);
            accion = (TextView)itemView.findViewById(R.id.txtAccionHistorial);
            fecha = (TextView)itemView.findViewById(R.id.txtFechaHistorial);
        }
    }

    public RecyclerViewServicios(List<HistorialModelo> listaServicio) {
        ListaServicio = listaServicio;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_historial, null,false);
        RecyclerView.LayoutParams layParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layParams);
        view.setOnClickListener(this);
        //ViewHolder viewHolder = new ViewHolder(view);
        return new RecyclerViewServicios.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewServicios.ViewHolder holder, int position) {
        holder.nombre.setText(ListaServicio.get(position).getNombre());
        holder.desc_pet.setText(ListaServicio.get(position).getDescripcion_peticion());
        holder.desc_status.setText(ListaServicio.get(position).getDescripcion_status());
        holder.accion.setText(ListaServicio.get(position).getAccion());
        holder.fecha.setText(ListaServicio.get(position).getFecha());
    }

    @Override
    public int getItemCount() {return ListaServicio.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }



}
