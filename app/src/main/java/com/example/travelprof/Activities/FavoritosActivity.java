package com.example.travelprof.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.example.travelprof.Activities.Adapter.ListaDestinosAdapter;
import com.example.travelprof.R;
import com.example.travelprof.modelo.Destino;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class FavoritosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);
        destinosFavoritos();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        changeStatusBarColor(getResources().getColor(R.color.colorPrimary)); // Reemplaza "tu_color" con el color deseado

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
                        onTravelProfClick();
                        return true;
                    }
                    if (item.getItemId() == caso1) {
                        item.setChecked(true);
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

    private void onTravelProfClick() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void onProfileClick() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }


    private void destinosFavoritos(){
        List<Destino> destinos =
                LoginActivity.dataBase.getDestinosFavoritosDAO()
                        .findDestinosByUser(LoginActivity.usuarioAplicacion.getId());
        for (Destino d: destinos) {
            if(d.getFechaIncio() != null && d.getFechaFin() != null ) {
                d.setDescripcion("Ida: " + d.getFechaIncio() + "        Vuelta: " + d.getFechaFin());
            }
        }
        RecyclerView recyclerView = findViewById(R.id.recylcerPerfil);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ListaDestinosAdapter adapterDestinos = new ListaDestinosAdapter(this,destinos,true);  // "destinos" es la lista de destinos cargados desde el archivo
        recyclerView.setAdapter(adapterDestinos);
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