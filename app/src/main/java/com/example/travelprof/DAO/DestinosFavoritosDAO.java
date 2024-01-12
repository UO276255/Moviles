package com.example.travelprof.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.travelprof.modelo.Destino;
import com.example.travelprof.modelo.DestinosFavoritos;

import java.util.List;


@Dao
public interface DestinosFavoritosDAO {

    @Query("SELECT * FROM Destino d INNER JOIN DestinosFavoritos s ON d.id = s.idDestino WHERE s.idUsuario = (:id)")
    List<Destino> findDestinosByUser(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void add(DestinosFavoritos user);

    @Delete
    void delete(DestinosFavoritos user);

}
