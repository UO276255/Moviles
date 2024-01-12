package com.example.travelprof.modelo;

import android.support.annotation.NonNull;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;
@Entity(tableName = "Categoria")
public class Categoria {
    @PrimaryKey
    @NonNull
    private long id;



    @ColumnInfo(name="nombre")
    String nombre;

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categoria categoria = (Categoria) o;
        return Objects.equals(nombre, categoria.nombre);
    }

    public int hashCode() {
        return Objects.hash(nombre);
    }

    public Categoria(String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){
        return this.nombre;
    }
    public Long getId(){
        return this.id;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setId(Long id){
        this.id = id;
    }
}
