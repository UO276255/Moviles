package com.example.travelprof.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.travelprof.modelo.Usuario;

import java.util.List;


@Dao
public interface UsuarioDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void add(Usuario user);

    @Update
    void update(Usuario user);

    @Delete
    void delete(Usuario user);

    @Query("SELECT * FROM Usuario")
    List<Usuario> getAll();

    @Query("SELECT * FROM Usuario WHERE id = (:id)")
    Usuario findById(int id);

    @Query("SELECT max(id) FROM Usuario")
    int findLastId();

}
