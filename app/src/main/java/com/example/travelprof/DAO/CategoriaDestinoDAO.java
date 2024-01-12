package com.example.travelprof.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.travelprof.modelo.Categoria;
import com.example.travelprof.modelo.Destino;

import java.util.List;


@Dao
public interface CategoriaDestinoDAO {

    @Query("select c.nombre from Categoria c, Destino d, CategoriasDestino s where c.id = s.categoriaId and s.destinoId = (:id) and s.destinoId = d.id ")
    List<String> findById(int id);

    @Query("select d.* from destino d, CategoriasDestino s, Categoria c where s.categoriaId = c.id and c.nombre = (:nombre) and s.destinoId = d.id ")
    List<Destino> findDestinoByCategory(String nombre);



}
