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

public class EnviarActivity extends AppCompatActivity {

    TextView regresar;
    EditText cuenta,monto;
    Button enviardinero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar);

        Bundle campos = getIntent().getExtras();
        regresar = findViewById(R.id.regresar);
        regresar.setOnClickListener(view -> {
            Intent a = new Intent(EnviarActivity.this,MenuActivity.class);
            a.putExtras(campos);
            startActivity(a);
            finish();
        });
        cuenta = findViewById(R.id.et1);
        monto = findViewById(R.id.et2);
        enviardinero = findViewById(R.id.enviarbutton);
        enviardinero.setOnClickListener(view -> {
            if(validarcampos()) {
                Model model = new Model();
                if(model.validarcuenta(EnviarActivity.this,cuenta.getText().toString())) {
                    if(Double.parseDouble(monto.getText().toString())>0) {
                        if (campos.getDouble("monto") - Double.parseDouble(monto.getText().toString()) >= 0) {
                            Bundle campos2 = model.enviardinero(EnviarActivity.this, campos, cuenta.getText().toString(), Double.parseDouble(monto.getText().toString()));
                            if (campos2.getBoolean("rsql")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                                builder.setTitle("¡Transacción exitosa!");
                                builder.setMessage("Saldo enviado: $" + monto.getText() + " Su nuevo saldo es: $" + campos2.getDouble("monto"));
                                builder.setPositiveButton(Html.fromHtml("<font color=#00000>Aceptar</font>"), (dialogInterface, i) -> {
                                    Intent a = new Intent(EnviarActivity.this, MenuActivity.class);
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
                            builder.setMessage("Saldo insuficiente para enviar.");
                            builder.setPositiveButton(Html.fromHtml("<font color=#00000>Aceptar</font>"), null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }else{
                        cuenta.setError("El valor ingresado debe ser mayor a 0");
                    }
                }else{
                    cuenta.setError("La cuenta ingresada no existe");
                }
            }else{
                Toast.makeText(this, "Complete los campos", Toast.LENGTH_SHORT).show();
            }
        });
    }
    boolean validarcampos(){
        boolean v=true;
        if(cuenta.getText().toString().matches("")){
            v=false;
            cuenta.setError("Campo vacío");
        }
        if (monto.getText().toString().matches("")){
            v=false;
            monto.setError("Campo vacío");
        }
        return v;
    }
}