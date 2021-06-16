package com.example.aplicacintipobancaria;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MenuActivity extends AppCompatActivity {

    TextView txt1,monto,numerocuenta;
    CardView consignarbutton, retirarbutton, enviarbutton;
    RelativeLayout cerrarsesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        DecimalFormat formatea = new DecimalFormat("###,###.##");

        txt1=findViewById(R.id.txt1);
        monto=findViewById(R.id.monto);
        Bundle campos = getIntent().getExtras();
        txt1.setText("Bienvenido "+ campos.getString("nombre"));
        monto.setText("$ "+campos.getDouble("monto"));
        numerocuenta = findViewById(R.id.numerocuenta);
        numerocuenta.setText("Cuenta #"+campos.getString("cuenta"));
        consignarbutton = findViewById(R.id.card1);
        consignarbutton.setOnClickListener(view -> {
            Intent a = new Intent(MenuActivity.this, ConsignarActivity.class);
            a.putExtras(campos);
            startActivity(a);
            finish();
        });
        enviarbutton = findViewById(R.id.card2);
        enviarbutton.setOnClickListener(view -> {
            Intent a = new Intent(MenuActivity.this,EnviarActivity.class);
            a.putExtras(campos);
            startActivity(a);
            finish();
        });
        retirarbutton = findViewById(R.id.card3);
        retirarbutton.setOnClickListener(view -> {
            Intent a = new Intent(MenuActivity.this,RetirarActivity.class);
            a.putExtras(campos);
            startActivity(a);
            finish();
        });
        cerrarsesion = findViewById(R.id.cerrarsesion);
        cerrarsesion.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Cierre de sesión");
            builder.setMessage("¿Desea cerrar sesión?");
            builder.setPositiveButton(Html.fromHtml("<font color=#00000>Si</font>"),(dialogInterface, i) -> {
                Intent a = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(a);
                finish();
            });
            builder.setNegativeButton(Html.fromHtml("<font color=#00000>No</font>"),null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }
}

