package com.example.travelprof.modelo;

import android.support.annotation.NonNull;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "DestinosFavoritos", primaryKeys = {"idUsuario", "idDestino"})
public class DestinosFavoritos {


    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdDestino() {
        return idDestino;
    }

    public void setIdDestino(int idDestino) {
        this.idDestino = idDestino;
    }

    @NonNull
    private int idUsuario;

    @NonNull
    private int idDestino;
}

