package com.example.travelprof.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelprof.R;
import com.example.travelprof.modelo.Usuario;

public class RegisterActivity extends AppCompatActivity {
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextEmail;
    private EditText editTextTelefono;
    private EditText editTextDireccion;

    private Usuario user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button b = findViewById(R.id.buttonRegistro2);
        editTextUsername = findViewById(R.id.editTextUsuarioR);
        editTextPassword = findViewById(R.id.editTextContraseñaR);
        editTextEmail = findViewById(R.id.editTextEmailR);
        editTextTelefono= findViewById(R.id.editTextTelefonoR);
        editTextDireccion= findViewById(R.id.editTextDireccionR);
        changeStatusBarColor(getResources().getColor(R.color.colorPrimary)); // Reemplaza "tu_color" con el color deseado

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comprobarValores();
            }
        });


    }

    private boolean comprobarValores() {
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        String email = editTextEmail.getText().toString();
        String telefono = editTextTelefono.getText().toString();
        String direccion = editTextDireccion.getText().toString();
        if (password.isEmpty()) {
            editTextPassword.setError("Campo no completado");
            editTextPassword.requestFocus();
            return false;

        }else if(username.isEmpty()){
            editTextUsername.setError("Campo no completado");
            editTextUsername.requestFocus();
            return false;

        }else if (email.isEmpty()){
            editTextEmail.setError("Campo no completado");
            editTextEmail.requestFocus();
            return false;

        } else if(telefono.isEmpty()){
            editTextTelefono.setError("Campo no completado");
            editTextTelefono.requestFocus();
            return false;

        } else if(direccion.isEmpty()){
            editTextDireccion.setError("Campo no completado");
            editTextDireccion.requestFocus();
            return false;

        } else {
            try{
                Integer.parseInt(telefono.toString());
                int id = LoginActivity.dataBase.getUsuarioDAO().findLastId() + 1;
                this.user = new Usuario(id,username.toString(),
                        password.toString(),direccion.toString(),
                        telefono.toString(),email.toString());
                LoginActivity.dataBase.getUsuarioDAO().add(user);
                LoginActivity.updateUsers();
                mostrarAlerta("Usuario añadido con exito");
            }catch(Exception e){
                editTextTelefono.setError("Telefono en formato incorrecto");
            }
        }
        return true;
    }

    public void cerrarActivity(){
        this.finish();
    }
    private void mostrarAlerta(String titulo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(titulo)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Acción al presionar el botón "Aceptar"
                        dialog.dismiss();
                        cerrarActivity();

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
