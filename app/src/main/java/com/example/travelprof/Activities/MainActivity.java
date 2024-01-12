package com.example.travelprof.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.PopupWindow;
import android.widget.SearchView;

import com.example.travelprof.Activities.Adapter.ListaDestinosAdapter;
import com.example.travelprof.R;
import com.example.travelprof.modelo.Destino;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    SearchView buscador;
    FloatingActionButton filtrado;
    ListaDestinosAdapter adapterDestinos;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterDestinos = new ListaDestinosAdapter(this,cargarDestinos());  // "destinos" es la lista de destinos cargados desde el archivo
        recyclerView.setAdapter(adapterDestinos);
        changeStatusBarColor(getResources().getColor(R.color.colorPrimary)); // Reemplaza "tu_color" con el color deseado

        buscador = findViewById(R.id.buscador);
        buscador.setOnQueryTextListener(this);
        filtrado = findViewById(R.id.botonFiltros);
        filtrado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFilterClick();
            }
        });

        Button reiniciar = findViewById(R.id.buttonMostarTodo);
        reiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterDestinos.filtrado(new ArrayList<String>());
            }
        });

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int caso1 = R.id.navigation_travel_prof;
                    int caso2 = R.id.navigation_favs;
                    int caso3 = R.id.navigation_profile;
                    if (item.getItemId() == caso1) {
                        item.setChecked(true);
                        return true;
                    }

                    if (item.getItemId() == caso2) {
                        item.setChecked(true);
                        onFavsClick();
                        return true;
                    }

                    if (item.getItemId() == caso3) {
                        item.setChecked(true);
                        onProfileClick();
                        return true;
                    }
                    return false;
                }
            };

    // Método para abrir la actividad de perfil
    private void onProfileClick() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
    private void onFavsClick() {
        Intent intent = new Intent(this, FavoritosActivity.class);
        startActivity(intent);
    }


    private void onFilterClick() {
        View v = View.inflate(this, R.layout.popup_filtros, null);

        // Obtener referencias a las vistas después de inflar el diseño
        Button b = v.findViewById(R.id.button3);
        CheckBox c1 = v.findViewById(R.id.checkBoxMuseo);
        CheckBox c2 = v.findViewById(R.id.checkBoxCiudad);
        CheckBox c3 = v.findViewById(R.id.checkBoxCultural);
        CheckBox c4 = v.findViewById(R.id.checkBoxFlora);
        CheckBox c5 = v.findViewById(R.id.checkBoxRestaurante);
        CheckBox c6 = v.findViewById(R.id.checkBoxOriental);

        List<String> res = new ArrayList<String>();

        int width = 900;
        int height = 1200;
        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(v, width, height, focusable);
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(c1.isChecked())
                    res.add("museos");
                if(c2.isChecked())
                    res.add("gran ciudad");
                if(c3.isChecked())
                    res.add("cultura");
                if(c4.isChecked())
                    res.add("fauna y flora");
                if(c5.isChecked())
                    res.add("restaurante");
                if(c6.isChecked())
                    res.add("oriental");
                adapterDestinos.filtrado(res);
                popupWindow.dismiss();
            }
        });
    }
    protected List<Destino> cargarDestinos() {
        List<Destino> destinos = LoginActivity.dataBase.getDestinosFavoritosDAO().findDestinosByUser(LoginActivity.usuarioAplicacion.getId());
        if(destinos.size() > 0){
            Map<String, Integer> categoriasFrecuentes = new HashMap<>();

            // Paso 1: Contar la frecuencia de cada categoría
            for(Destino d : destinos){
                List<String> categorias = LoginActivity.dataBase.getCategoriaDestinoDAO().findById(d.getId());
                for(String categoria : categorias){
                    categoriasFrecuentes.put(categoria, categoriasFrecuentes.getOrDefault(categoria, 0) + 1);
                }
            }

            // Paso 2: Encontrar las categorías más repetidas
            List<String> categoriasMasRepetidas = new ArrayList<>();
            int maxFrecuencia = 0;
            for(Map.Entry<String, Integer> entry : categoriasFrecuentes.entrySet()){
                int frecuencia = entry.getValue();
                if(frecuencia > maxFrecuencia){
                    categoriasMasRepetidas.clear();
                    categoriasMasRepetidas.add(entry.getKey());
                    maxFrecuencia = frecuencia;
                } else if(frecuencia == maxFrecuencia){
                    categoriasMasRepetidas.add(entry.getKey());
                }
            }

            // Paso 3: Filtrar destinos con categorías más repetidas
            //List<Destino> destinosRelacionados = new ArrayList<>();
            //for(Destino d : destinos){
            // List<Categoria> categorias = d.getCategorias();
            //if(categorias.stream().anyMatch(categoriasMasRepetidas::contains)){
            //    destinosRelacionados.add(d);
            // }
            //}

            // Paso 3: Mostrar países relacionados con las categorías más repetidas
            List<Destino> listaDestinos;
            List<Destino> result=new ArrayList<Destino>();


            listaDestinos = LoginActivity.dataBase.getDestinoDAO().getAll();
            for(Destino destino:listaDestinos){
                List<String> categoriasDestino = LoginActivity.dataBase.getCategoriaDestinoDAO().findById(destino.getId());
                for(String c : categoriasMasRepetidas){
                    if(categoriasDestino.contains(c)){
                        result.add(destino);
                        break;
                    }

                }
            }
            return result;
        }
        return LoginActivity.dataBase.getDestinoDAO().getAll();

    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapterDestinos.filtrado(s);
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void changeStatusBarColor(int color) {
        Window window = getWindow();

        // Habilita la función de cambio de color de la barra de notificaciones
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // Establece el color de la barra de notificaciones
        window.setStatusBarColor(color);
    }
}