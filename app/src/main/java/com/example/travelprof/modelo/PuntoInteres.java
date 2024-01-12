package com.example.travelprof.modelo;

import android.support.annotation.NonNull;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "PuntosInteres")
public class PuntoInteres {
    @PrimaryKey
    @NonNull
    private int id;
    @ColumnInfo(name="nombre")

    private String nombre;
    @ColumnInfo(name="pais")
    private String pais;
    @ColumnInfo(name="longitud")
    private String longitud;
    @ColumnInfo(name="latitud")
    private String latitud;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }
    public String getLatitud() {
        return latitud;
    }
    public PuntoInteres(int id,String pais, String nombre, String latitud, String longitud) {
        this.id = id;
        this.pais = pais;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    @Ignore
    public PuntoInteres(String pais, String nombre, String latitud, String longitud) {
        this.id = id;
        this.pais = pais;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
    }


    public String getCiudad() {
        return pais;
    }

    public String getNombre() {
        return nombre;
    }

}
