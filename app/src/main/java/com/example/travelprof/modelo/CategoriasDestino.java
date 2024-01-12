package com.example.travelprof.modelo;

import android.support.annotation.NonNull;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "CategoriasDestino", primaryKeys = {"categoriaId", "destinoId"})
public class CategoriasDestino {



    @NonNull
    private long categoriaId;


    @NonNull
    private long destinoId;
    public long getCategoriaId() {
        return categoriaId;
    }

    public long getDestinoId() {
        return destinoId;
    }

    public void setCategoriaId(long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public void setDestinoId(long destinoId) {
        this.destinoId = destinoId;
    }
}

