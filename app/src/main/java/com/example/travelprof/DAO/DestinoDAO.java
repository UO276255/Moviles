package com.example.travelprof.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.travelprof.modelo.Destino;

import java.util.List;


@Dao
public interface DestinoDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void add(Destino user);

    @Update
    void update(Destino user);

    @Delete
    void delete(Destino user);

    @Query("SELECT * FROM Destino")
    List<Destino> getAll();

    @Query("SELECT * FROM Destino WHERE id = (:id)")
    Destino findById(int id);

}
