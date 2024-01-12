package com.example.travelprof.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.travelprof.AppDatabase;
import com.example.travelprof.R;
import com.example.travelprof.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    public static Usuario usuarioAplicacion;
    public static AppDatabase dataBase;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private static List<Usuario> usuarios = new ArrayList<Usuario>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dataBase = AppDatabase.getDatabase(this);
        usuarios = dataBase.getUsuarioDAO().getAll();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         editTextUsername = findViewById(R.id.editTextUsuario);
         editTextPassword = findViewById(R.id.editTextContraseña);
        changeStatusBarColor(getResources().getColor(R.color.colorPrimary)); // Reemplaza "tu_color" con el color deseado

        Button buttonLogin = findViewById(R.id.button);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLoginClick(view);
            }
        });

        Button buttonRegister = findViewById(R.id.buttonRegistro);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPerfilClick(view);
            }
        });
    }
    public void onLoginClick(View view) {
        // Obtener texto de los EditText
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        Button buttonLogin = findViewById(R.id.button);

            if (password.isEmpty()) {
                editTextPassword.setError("Campo no completado");
                editTextUsername.requestFocus();

            }else if(username.isEmpty()){
                editTextUsername.setError("Campo no completado");

            }else{
                for(Usuario u: usuarios){
                    if (u.getNombre().equals(username) && u.getPassword().equals(password)){
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        usuarioAplicacion = u;
                        //u.CreateFavorites();
                        return;
                    }
                }
                buttonLogin.setError("Datos incorrectos");
            }
    }

    public void onPerfilClick(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public static void updateUsers(){
       usuarios = dataBase.getUsuarioDAO().getAll();
        for (Usuario d:
                usuarios) {
            Log.d("tag","assasap"+d.getNombre());
        }
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
