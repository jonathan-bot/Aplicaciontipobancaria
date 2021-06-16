package com.example.aplicacintipobancaria;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RetirarActivity extends AppCompatActivity {

    TextView regresar;
    EditText et1;
    Button retirar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retirar);

        Bundle campos = getIntent().getExtras();
        et1 = findViewById(R.id.et1);
        regresar = findViewById(R.id.regresar);
        regresar.setOnClickListener(view -> {
            Intent a = new Intent(RetirarActivity.this,MenuActivity.class);
            a.putExtras(campos);
            startActivity(a);
            finish();
        });
        retirar = findViewById(R.id.retirarbutton);
        retirar.setOnClickListener(view -> {
            if(!et1.getText().toString().matches("")) {
                if(Double.parseDouble(et1.getText().toString())>0) {
                    if (campos.getDouble("monto") - Double.parseDouble(et1.getText().toString()) >= 0) {
                        Model m = new Model();
                        Bundle campos2 = m.retirar(RetirarActivity.this, campos, Double.parseDouble(et1.getText().toString()));
                        if (campos2.getBoolean("rsql")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setTitle("¡Transacción exitosa!");
                            builder.setMessage("Su nuevo saldo es: $" + campos2.getDouble("monto"));
                            builder.setPositiveButton(Html.fromHtml("<font color=#00000>Aceptar</font>"), (dialogInterface, i) -> {
                                Intent a = new Intent(RetirarActivity.this, MenuActivity.class);
                                a.putExtras(campos2);
                                startActivity(a);
                                finish();
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        } else {
                            Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Transacción no realizada");
                        builder.setMessage("Saldo insuficiente para retirar.");
                        builder.setPositiveButton(Html.fromHtml("<font color=#00000>Aceptar</font>"), null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }else{
                    et1.setError("El valor ingresado debe ser mayor a 0");
                }
            }else{
                et1.setError("Campo vacío");
            }
        });
    }
}