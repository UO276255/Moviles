package com.example.travelprof.modelo;

import android.support.annotation.NonNull;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;
import androidx.room.Relation;

import java.util.ArrayList;
import java.util.List;
@Entity(tableName = "Usuario")
public class Usuario {

    @PrimaryKey
    @ColumnInfo(name="id")
    @NonNull
    private int id;

    @ColumnInfo(name="nombre")
    private String nombre;

    @ColumnInfo(name="password")
    private String password;

    @ColumnInfo(name="direccion")
    private String direccion;

    @ColumnInfo(name="telefono")
    private String telefono;


    @ColumnInfo(name="email")
    private String email;

    @Ignore
    private List<Destino> favoritos = new ArrayList<Destino>();

    public Usuario(int id,String nombre,String password,String direccion, String telefono,String email){
        this.id = id;
        this.nombre=nombre;
        this.password=password;
        this.direccion=direccion;
        this.telefono=telefono;
        this.email=email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String usuario) {
        this.nombre = usuario;
    }

    public void setPassword(String contraseña) {
        this.password = contraseña;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    public List<Destino> getFavoritos() {
        return favoritos;
    }

    public void CreateFavorites(){
        favoritos = new ArrayList<Destino>();
    }
    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

}
