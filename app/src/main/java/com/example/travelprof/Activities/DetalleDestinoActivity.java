package com.example.travelprof.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.travelprof.R;
import com.example.travelprof.modelo.Destino;
import com.example.travelprof.modelo.DestinosFavoritos;
import com.example.travelprof.modelo.PuntoInteres;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DetalleDestinoActivity extends AppCompatActivity implements OnMapReadyCallback {
    private MapView mapView;
    private List<Destino> destinos;
    Destino actual = null;

    List<PuntoInteres> puntosInteres = new ArrayList<>();

    LocalDate fechaInicio = null;
    LocalDate fechaFin = null;

    Boolean agregable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        destinos = LoginActivity.dataBase.getDestinoDAO().getAll();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_destino);
        Intent intent = getIntent();
        List<Destino> favoritos = LoginActivity.dataBase.getDestinosFavoritosDAO().findDestinosByUser(LoginActivity.usuarioAplicacion.getId());
        String nombre = intent.getStringExtra("nombre");
        String imagenUrl = intent.getStringExtra("imagen");
        String descripcion = intent.getStringExtra("descripcion");

        // Ahora puedes mostrar estos datos en las vistas del diseño
        TextView nombreTextView = findViewById(R.id.textNombre);
        ImageView imagenImageView = findViewById(R.id.imageView);
        TextView descripcionTextView = findViewById(R.id.textDescription);
        nombreTextView.setText(nombre);
        // Carga la imagen utilizando una biblioteca como Picasso o Glide
        // Ejemplo con Picasso:
        Picasso.get().load(imagenUrl).into(imagenImageView);
        imagenImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        descripcionTextView.setText(descripcion);

        // Inicializa el MapView
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        changeStatusBarColor(getResources().getColor(R.color.colorPrimary)); // Reemplaza "tu_color" con el color deseado

        mapView.getMapAsync(this);
        CheckBox boton = findViewById(R.id.Check);
        ImageButton calendar = findViewById(R.id.calendario);
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarCalendario();
            }
        });
        ImageButton buttonVolver = findViewById(R.id.buttonVolver);
        for (Destino des : favoritos) {
            if (des.getNombre().toLowerCase().equals(nombre.toLowerCase())) {
                boton.setChecked(true);
            }
        }
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DestinosFavoritos d = new DestinosFavoritos();
                d.setIdDestino(actual.getId());
                d.setIdUsuario(LoginActivity.usuarioAplicacion.getId());
                if (boton.isChecked()) {
                    if(agregable){
                        LoginActivity.dataBase.getDestinosFavoritosDAO().add(d);
                        //LoginActivity.usuarioAplicacion.getFavoritos().add(actual);
                    } else {
                        mostrarAlerta("Debes seleccionar las fechas antes de agregar un destino a favoritos");
                        boton.setChecked(false);

                    }
                } else {
                    for (Destino des : favoritos) {
                        if (des.getNombre().toLowerCase().equals(nombre.toLowerCase())) {
                            LoginActivity.dataBase.getDestinosFavoritosDAO().delete(d);
                            agregable = false;
                        }
                    }
                }
            }
        });

        buttonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDetailClick(view);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
    public void onDetailClick(View view) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        return;
    }
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        Destino destino = null;

        TextView nombre = findViewById(R.id.textNombre);
        TextView coste = findViewById(R.id.editTextCoste);
        for (Destino d : destinos) {
            if (d.getNombre().equals(nombre.getText().toString())) {
                destino = d;
                break;
            }
        }

        // Coordenadas del destino seleccionado
        LatLng destinoLatLng = new LatLng(Float.parseFloat(destino.getLongitud()), Float.parseFloat(destino.getLatitud()));

        // Configuración de la posición de la cámara en el destino seleccionado
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(destinoLatLng)
                .zoom(12.0f) // Puedes ajustar el nivel de zoom según tus preferencias
                .build();

        // Mueve la cámara a la posición del destino seleccionado
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        String address = LoginActivity.usuarioAplicacion.getDireccion();
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocationName(address, 1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LatLng latlong = null;
        if (addresses != null && addresses.size() > 0) {
            double latitude = addresses.get(0).getLatitude();
            double longitude = addresses.get(0).getLongitude();
            latlong = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
        }else{
            latlong = new LatLng(43.3614, -5.8593);
        }



        // Calcula la distancia entre Asturias y el destino
        double distanciaEnKilometros = SphericalUtil.computeDistanceBetween(latlong, destinoLatLng) / 1000;
        coste.setText(String.valueOf((calcularCostoViaje(distanciaEnKilometros))) + "€");

        // Agrega marcadores para todos los destinos
        puntosInteres = LoginActivity.dataBase.getPuntosInteresDAO().getAll();
        for (PuntoInteres d : puntosInteres) {
            if (d.getCiudad().equals(nombre.getText().toString())) {
                LatLng markerLatLng = new LatLng(Float.parseFloat(d.getLongitud()),Float.parseFloat(d.getLatitud()) );
                googleMap.addMarker(new MarkerOptions().position(markerLatLng).title(d.getNombre()));
            }
        }

        // Agrega una ruta turística
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.BLUE);
        polylineOptions.width(5);
        for (PuntoInteres d : puntosInteres) {
            if (d.getCiudad().equals(nombre.getText().toString())) {
                LatLng polylineLatLng = new LatLng( Float.parseFloat(d.getLongitud()),Float.parseFloat(d.getLatitud()));
                polylineOptions.add(polylineLatLng);
            }
        }
        actual = destino;
        googleMap.addPolyline(polylineOptions);

    }

    public static int calcularCostoViaje(double distanciaEnKilometros) {
        // Tarifa base por kilómetro
        double tarifaBasePorKilometro = 0.2;

        // Tarifa adicional por otros factores (peajes, etc.)
        double tarifaAdicional = 0.0;

        // Cálculo del costo total
        double costoTotal = distanciaEnKilometros * tarifaBasePorKilometro + tarifaAdicional;

        return (int) costoTotal;
    }

    private void mostrarCalendario() {
        LocalDate fechaActual = null;

        DatePickerDialog datePickerDialog = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            fechaActual = LocalDate.now();
            datePickerDialog = new DatePickerDialog(
                    this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            LocalDate fechaSeleccionada = null;
                            fechaSeleccionada = LocalDate.of(year, month + 1, dayOfMonth);

                            if (fechaInicio == null) {
                                fechaInicio = fechaSeleccionada;
                                mostrarCalendario();
                            } else if (fechaFin == null) {
                                fechaFin = fechaSeleccionada;
                                if(fechaFin.isBefore(fechaInicio)){
                                    mostrarCalendario();
                                    mostrarAlerta("La fecha de fin no puede ser anterior a la de inicio");
                                    fechaFin = null;
                                    fechaInicio = null;
                                } else {
                                    actual.ponerFechas(fechaInicio, fechaFin);
                                    agregable = true;
                                    fechaFin = null;
                                    fechaInicio = null;
                                }

                            }
                        }
                    },
                    fechaActual.getYear(),
                    fechaActual.getMonthValue() - 1,
                    fechaActual.getDayOfMonth()
            );
        }

        datePickerDialog.show();
    }

    private void mostrarAlerta(String titulo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(titulo)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Acción al presionar el botón "Aceptar"
                        dialog.dismiss(); // Cierra el diálogo
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
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


