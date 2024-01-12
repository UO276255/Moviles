package com.example.travelprof;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.travelprof.DAO.CategoriaDestinoDAO;
import com.example.travelprof.DAO.DestinoDAO;
import com.example.travelprof.DAO.DestinosFavoritosDAO;
import com.example.travelprof.DAO.PuntosInteresDAO;
import com.example.travelprof.DAO.UsuarioDAO;
import com.example.travelprof.modelo.Categoria;
import com.example.travelprof.modelo.CategoriasDestino;
import com.example.travelprof.modelo.Destino;
import com.example.travelprof.modelo.DestinosFavoritos;
import com.example.travelprof.modelo.PuntoInteres;
import com.example.travelprof.modelo.Usuario;


/*
    Room trabaja con migraciones. Cada vez que añadamos o modifiquemos una entidad
    es necesario incrementar la versión (número entero) y generar una migración.
    NO lo vamos a gestionar, pero tienes info aquí:
        https://developer.android.com/training/data-storage/room/migrating-db-versions?hl=es-419

    Lo que haremos será desinstalar la app cuando hagamos modificaciones.

    Fíjate que ademas indicamos las entidades anotadas con @Entity.
    Por ahora, solamente la clase Pelicula.

 */
@Database(version= 3, entities = {Usuario.class, Destino.class, PuntoInteres.class, Categoria.class, CategoriasDestino.class, DestinosFavoritos.class})
public abstract class AppDatabase extends RoomDatabase {

    /*Aquí iremos añadiendo los DAO */

    public abstract UsuarioDAO getUsuarioDAO();
    public abstract DestinoDAO getDestinoDAO();
    public abstract PuntosInteresDAO getPuntosInteresDAO();
    public abstract CategoriaDestinoDAO getCategoriaDestinoDAO();
    public abstract DestinosFavoritosDAO getDestinosFavoritosDAO();

    public static final String DB_NOMBRE = "dataBase.db";

    private static AppDatabase db;

    // Singleton
    public static AppDatabase getDatabase(Context applicationContext) {
       //applicationContext.deleteDatabase(DB_NOMBRE);
        if (db == null) {
            /*
                allowMainThreadQueries() implica que utilizaremos el hilo principal.
                Esto es un crimen (bloqueamos la interfaz y demás problemas).
                Lo trabajaremos la semana que viene con Kotlin.
             */
            db = Room.databaseBuilder(applicationContext, AppDatabase.class, DB_NOMBRE)
                    .allowMainThreadQueries().createFromAsset("dataBase.db")
                    .build();
        }
        return db;
    }
}
