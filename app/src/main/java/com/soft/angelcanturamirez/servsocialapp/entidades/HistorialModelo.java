package com.soft.angelcanturamirez.servsocialapp.entidades;

public class HistorialModelo {
    private String nombre;
    private String fecha;
    private String descripcion_peticion;
    private String descripcion_status;
    private String accion;

    public HistorialModelo(){

    }

    public HistorialModelo(String nombre, String fecha, String descripcion_peticion, String descripcion_status, String accion) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.descripcion_peticion = descripcion_peticion;
        this.descripcion_status = descripcion_status;
        this.accion = accion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion_peticion() {
        return descripcion_peticion;
    }

    public void setDescripcion_peticion(String descripcion_peticion) {
        this.descripcion_peticion = descripcion_peticion;
    }

    public String getDescripcion_status() {
        return descripcion_status;
    }

    public void setDescripcion_status(String descripcion_status) {
        this.descripcion_status = descripcion_status;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }
}
