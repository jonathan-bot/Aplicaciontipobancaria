package com.example.aplicacintipobancaria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText correo,contra;
    Button btningresar;
    Button btnregistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        correo = findViewById(R.id.correo);
        contra = findViewById(R.id.contra);
        btningresar = findViewById(R.id.btningresar);

        btningresar.setOnClickListener(view ->{
            boolean verificar = true;
            if (correo.getText().toString().matches("")) {
                correo.setError("Introduce un Correo");
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
                verificar=false;
            }
            if (contra.getText().toString().matches("")) {
                contra.setError("Introduce una Contraseña");
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
                verificar=false;
            }
            if (verificar){
                if (!(correo.getText().toString().equals("admin@wposs.com") && contra.getText().toString().equals("Admin123*"))){
                    Model model = new Model();
                    Bundle campos;
                    campos = model.readlogin2(MainActivity.this,correo.getText().toString(),contra.getText().toString());
                    if (campos.getBoolean("rsql")) {
                        if (campos.getString("estado").equals("habilitado")) {
                            Toast.makeText(this, "Usuario ha iniciado sesion", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                            intent.putExtras(campos);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(this, "El usuario no se encuentra en el sistema", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Intent intent = new Intent(MainActivity.this,AdministradorActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        btnregistrarse = findViewById(R.id.btnregistrarse);
        btnregistrarse.setOnClickListener(view ->{
            Intent intent = new Intent(MainActivity.this,RegistroActivity.class);
            startActivity(intent);
        });
    }
}