package com.example.aplicacintipobancaria;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;



import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AdministradorActivity extends AppCompatActivity {

    ListView lista;
    TextView cerrarsesion;
    List<String> listausuarios = new ArrayList<>();
    ArrayAdapter<String> usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);

        lista = findViewById(R.id.lista);
        Model model = new Model();
        Cursor usuarios = model.usuarios(this);
        while(usuarios.moveToNext()){
            listausuarios.add(usuarios.getString(usuarios.getColumnIndex("correo")));
            usuario = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                    listausuarios);
            lista.setAdapter(usuario);
        }
        lista.setOnItemClickListener((adapterView, view, i,l)->{
            String correolist = lista.getAdapter().getItem(i).toString().trim();
            Model model2 = new Model();
            if(model2.verestado(AdministradorActivity.this,correolist)){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("El usuario está habilitado");
                builder.setMessage("¿Desea deshabilitarlo?");
                builder.setPositiveButton(Html.fromHtml("<font color=#000000>Si</font>"),(dialogInterface, i1) -> {
                    model2.deshabilitarusuario(AdministradorActivity.this,correolist);
                    Toast.makeText(this, "Usuario deshabilitado", Toast.LENGTH_SHORT).show();
                });
                builder.setNegativeButton(Html.fromHtml("<font color=#000000>No</font>"),null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("El usuario está deshabilitado");
                builder.setMessage("¿Desea habilitarlo?");
                builder.setPositiveButton(Html.fromHtml("<font color=#000000>Si</font>"),(dialogInterface, i1) -> {
                    model2.habilitarusuario(AdministradorActivity.this,correolist);
                    Toast.makeText(this, "Usuario habilitado", Toast.LENGTH_SHORT).show();
                });
                builder.setNegativeButton(Html.fromHtml("<font color=#000000>No</font>"),null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        cerrarsesion = findViewById(R.id.cerrarsesion);
        cerrarsesion.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Cierre de sesión");
            builder.setMessage("¿Desea cerrar sesión como administrador?");
            builder.setPositiveButton(Html.fromHtml("<font color=#000000>Si</font>"),(dialogInterface, i) -> {
                Intent a = new Intent(this, MainActivity.class);
                startActivity(a);
                finish();
            });
            builder.setNegativeButton(Html.fromHtml("<font color=#000000>No</font>"),null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }
}