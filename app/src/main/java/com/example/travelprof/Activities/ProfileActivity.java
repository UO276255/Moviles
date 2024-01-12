package com.example.travelprof.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.travelprof.R;
import com.example.travelprof.modelo.Destino;
import com.example.travelprof.modelo.Usuario;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    private TextView editTextUsername;
    private TextView editTextTelefono;
    private TextView editTextDireccion;
    private TextView editTextEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Usuario u = LoginActivity.usuarioAplicacion;
        Log.i("a",u.getNombre());

        setContentView(R.layout.activity_profile);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        this.editTextUsername = findViewById(R.id.UsuarioPerfil);
        this.editTextTelefono= findViewById(R.id.UsuarioTelefono);
        this.editTextDireccion=findViewById(R.id.UsuarioDireccion);
        this.editTextEmail=findViewById(R.id.UsuarioEmail);

        this.editTextUsername.setText(u.getNombre());
        this.editTextTelefono.setText(u.getTelefono());
        this.editTextDireccion.setText(u.getDireccion());
        this.editTextEmail.setText(u.getEmail());
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
                    if (item.getItemId() == caso2) {
                        item.setChecked(true);
                        onFavsClick();
                        return true;
                    }
                    if (item.getItemId() == caso3) {
                        item.setChecked(true);
                        return true;
                    }
                    return false;
                }
            };

    private void onTravelProfClick() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void onFavsClick() {
        Intent intent = new Intent(this, FavoritosActivity.class);
        startActivity(intent);
    }




    private Destino obtenerProximoDestino() {
        List<Destino> destinos = LoginActivity.usuarioAplicacion.getFavoritos();
        Destino proximoDestino = null;

        for (Destino destino : destinos) {
            // Verifica que el destino tenga fechas
            if (destino.getFechaIncio() != null && destino.getFechaFin() != null) {
                // Verifica si aún no se ha asignado un próximo destino
                if (proximoDestino == null) {
                    proximoDestino = destino;
                } else {
                    // Compara las fechas y actualiza si encuentra una fecha más próxima
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        if (destino.getFechaIncio().isBefore(proximoDestino.getFechaIncio())) {
                            proximoDestino = destino;
                        }
                    }
                }
            }
        }

        return proximoDestino;
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