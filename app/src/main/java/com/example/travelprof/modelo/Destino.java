package com.example.travelprof.modelo;

import android.support.annotation.NonNull;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity(tableName = "Destino")
public class Destino {

    @PrimaryKey
    @NonNull
    private int id;

    @ColumnInfo(name="nombre")
    private String nombre;

    @ColumnInfo(name="urlImagen")
    private String urlImagen;  // Identificador de recurso de la imagen

    @ColumnInfo(name="descripcion")
    private String descripcion;

    @ColumnInfo(name="descripcionDetalle")
    private String descripcionDetalle;

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Destino destino = (Destino) o;
        return Objects.equals(nombre, destino.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }

    @Ignore
    private List<Categoria> categorias;

    @ColumnInfo(name="longitud")
    private String longitud;

    @ColumnInfo(name="latitud")
    private String latitud;

    public String getLongitud() {
        return longitud;
    }
    public String getLatitud() {
        return latitud;
    }
    @Ignore
    LocalDate inicio = null;
    @Ignore
    LocalDate fin = null;


    @Ignore
    public Destino(String nombre, String latitud, String longitud) {
        this.nombre = nombre;
        this.longitud = longitud;
        this.latitud=latitud;
    }
    @Ignore
    public Destino(String nombre, String latitud, String longitud,String url) {
        this.nombre = nombre;
        this.longitud = longitud;
        this.latitud=latitud;
        this.urlImagen = url;
    }
    @Ignore
    public Destino(String nombre, String urlImagen, String descripcion, String descripcionDetalle,List<Categoria> c) {
        this.nombre = nombre;
        this.urlImagen = urlImagen;
        this.descripcion = descripcion;
        this.descripcionDetalle = descripcionDetalle;
        this.categorias = c;
    }
    public Destino(String nombre, String urlImagen, String descripcion, String descripcionDetalle,String latitud, String longitud) {
        this.nombre = nombre;
        this.urlImagen = urlImagen;
        this.descripcion = descripcionDetalle;
        this.descripcionDetalle = descripcion;
        this.longitud = longitud;
        this.latitud=latitud;
    }

    public Destino(String nombre, String urlImagen, String descripcion, String descripcionDetalle,String latitud, String longitud,List<Categoria> c) {
        this.nombre = nombre;
        this.urlImagen = urlImagen;
        this.descripcion = descripcion;
        this.descripcionDetalle = descripcionDetalle;
        this.longitud = longitud;
        this.latitud=latitud;
        this.categorias = c;
    }



    public void setUrlImagen(String url){
        this.urlImagen = url;
    }

    public void setDescripcion(String des){
        this.descripcion = des;
    }
    public String getUrlImagen() {
        return urlImagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public List<Categoria> getCategorias(){
        return this.categorias;
    }
    public String getNombre() {
        return nombre;
    }

    public String getDescripcionDetalle() { return descripcionDetalle; }

    public LocalDate getFechaIncio(){
        return inicio;
    }
    public LocalDate getFechaFin(){
        return fin;
    }

    public void ponerFechas(LocalDate inicio, LocalDate fin){
        this.inicio = inicio;
        this.fin = fin;
    }
    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }
}
