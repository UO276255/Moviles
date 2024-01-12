package com.example.travelprof.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.travelprof.modelo.PuntoInteres;
import com.example.travelprof.modelo.Usuario;

import java.util.List;


@Dao
public interface PuntosInteresDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void add(PuntoInteres user);

    @Update
    void update(PuntoInteres user);

    @Delete
    void delete(Usuario user);

    @Query("SELECT * FROM PuntosInteres")
    List<PuntoInteres> getAll();

    @Query("SELECT * FROM PuntosInteres WHERE id = (:id)")
    PuntoInteres findById(int id);

}
