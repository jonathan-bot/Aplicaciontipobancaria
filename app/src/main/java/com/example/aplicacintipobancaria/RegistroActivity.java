package com.example.aplicacintipobancaria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

public class RegistroActivity extends AppCompatActivity {

    EditText nombre, apellido, identificacion, correo, contrasena;
    TextView numcuenta1;
    Button registrarse;
    String numcuenta="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        nombre = findViewById(R.id.nombre);
        apellido = findViewById(R.id.apellido);
        identificacion = findViewById(R.id.identificacion);
        correo = findViewById(R.id.correo);
        contrasena = findViewById(R.id.contrasena);
        numcuenta1= findViewById(R.id.numcuenta);
        generarnumcuenta();



        registrarse = findViewById(R.id.registrarse);
        registrarse.setOnClickListener(view ->{
            if (revisarcampo()){
                Model model = new Model();
                Usuario usuario = new Usuario();
                usuario.setNombre(nombre.getText().toString());
                usuario.setApellido(apellido.getText().toString());
                usuario.setIdentificacion(identificacion.getText().toString());
                usuario.setCorreo(correo.getText().toString());
                usuario.setContrasena(contrasena.getText().toString());
                usuario.setCuenta(numcuenta);
                usuario.setMonto(1000000);
                usuario.setEstado("habilitado");
                model.insertar(RegistroActivity.this, usuario);
                Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
    void generarnumcuenta(){
        boolean g = true;
        while (g){
            if(numcuenta.length()<9){
                numcuenta = String.valueOf((int)(Math.random()*1000000000)+1);
            }else{
                numcuenta=numcuenta + String.valueOf((int)(Math.random()*9)+1);
                numcuenta1.setText(numcuenta1.getText()+" "+numcuenta);
                g=false;
            }
        }
    }
    boolean revisarcontrasena(){
        boolean v=false,v1=false,v2=false;
        if(Pattern.compile("[0-9]").matcher(contrasena.getText().toString()).find())
            v1=true;
        if(Pattern.compile("[a-z]").matcher(contrasena.getText().toString()).find())
            v2=true;
        if(v1 && v2)
            v=true;
        return v;
    }

    boolean revisarcampo(){
        boolean revc = true;
        if(nombre.getText().toString().matches("")){
            nombre.setError("Introduce un nombre");
            revc=false;
        }
        if(apellido.getText().toString().matches("")){
            apellido.setError("Introduce un apellido");
            revc=false;
        }
        if(identificacion.getText().toString().matches("")){
            identificacion.setError("Introduce una identificacion");
            revc=false;
        }
        if(correo.getText().toString().matches("")){
            correo.setError("Introduce un correo");
            revc=false;
        }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(correo.getText().toString().trim()).matches()){
            revc=false;
            correo.setError("Correo inválido");
        }
        if(contrasena.getText().toString().matches("")){
            contrasena.setError("Introduce tu contraseña");
            revc=false;
        }else if(!revisarcontrasena()){
            contrasena.setError("La contraseña debe contener numeros y letras");
            revc=false;
        }
        if (revc){
            Toast.makeText(this, "Usuario completados", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Debe completar correctamente todo los campos", Toast.LENGTH_SHORT).show();
        }
        return revc;
    }
}