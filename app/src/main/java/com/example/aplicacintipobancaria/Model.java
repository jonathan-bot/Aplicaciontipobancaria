package com.example.aplicacintipobancaria;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class Model {
    public SQLiteDatabase conexion(Context context){
        BDatos conexion = new BDatos(context,"banco",null,1);
        SQLiteDatabase BDatos = conexion.getWritableDatabase();
        return BDatos;
    }
    boolean insertar(Context context, Usuario datos){
        boolean insert = false;
        ContentValues content = new ContentValues();
        content.put("nombre",datos.getNombre());
        content.put("apellido",datos.getApellido());
        content.put("identificacion",datos.getIdentificacion());
        content.put("correo",datos.getCorreo());
        content.put("contrasena",datos.getContrasena());
        content.put("cuenta",datos.getCuenta());
        content.put("monto",datos.getMonto());
        content.put("estado",datos.getEstado());
        SQLiteDatabase BDatos = this.conexion(context);
        try{
            BDatos.insert("cliente",null, content);
            insert=true;
        }catch (Exception e){
            Log.e(null,e.toString());
        }
        return  insert;
    }
    Bundle readlogin2(Context context, String correo, String contrasena){
        Bundle campos = new Bundle();
        campos.putBoolean("rsql",false);
        SQLiteDatabase BDatos = this.conexion(context);
        Cursor resultado = BDatos.query("cliente",new String[]{"*"},
                "correo='"+correo+"' AND contrasena='"+contrasena+"'",
                null,null,null,null);
        if(resultado.moveToFirst()){
            campos.putBoolean("rsql",true);
            campos.putString("nombre",resultado.getString(1));
            campos.putString("apellido",resultado.getString(2));
            campos.putString("identificacion",resultado.getString(3));
            campos.putString("correo",resultado.getString(4));
            campos.putString("contrasena",resultado.getString(5));
            campos.putString("cuenta",resultado.getString(6));
            campos.putDouble("monto",resultado.getDouble(7));
            campos.putString("estado",resultado.getString(8));
        }
        return campos;
    }
    Bundle consignar(Context context, Bundle bundle, Double monto){
        bundle.putBoolean("rsql",false);
        Double consignacion = (bundle.getDouble("monto") + monto)-2000;
        bundle.putDouble("monto",consignacion);
        SQLiteDatabase BDatos = this.conexion(context);
        try{
            Cursor resultado = BDatos.rawQuery("UPDATE cliente SET monto="+bundle.getDouble("monto")+" WHERE cuenta=?",new String[]{bundle.getString("cuenta")});
            resultado.moveToFirst();
            if(resultado!=null) {
                bundle.putBoolean("rsql", true);
            }
        }catch(Exception e){
            Log.e(null,e.toString());
        }
        return bundle;
    }
    Bundle retirar(Context context, Bundle bundle, Double monto){
        bundle.putBoolean("rsql",false);
        Double consignacion = (bundle.getDouble("monto") - monto)-5000;
        bundle.putDouble("monto",consignacion);
        SQLiteDatabase BDatos = this.conexion(context);
        try{
            Cursor resultado = BDatos.rawQuery("UPDATE cliente SET monto="+bundle.getDouble("monto")+" WHERE cuenta=?",new String[]{bundle.getString("cuenta")});
            resultado.moveToFirst();
            if(resultado!=null) {
                bundle.putBoolean("rsql", true);
            }
        }catch(Exception e){
            Log.e(null,e.toString());
        }
        return bundle;
    }
    Bundle enviardinero(Context context, Bundle bundle, String cuenta, Double monto){
        bundle.putBoolean("rsql",false);
        SQLiteDatabase BDatos = this.conexion(context);
        Cursor saldocuentaenvio =  BDatos.rawQuery("SELECT monto FROM cliente WHERE cuenta=?", new String[]{cuenta});
        if(saldocuentaenvio.moveToFirst()) {
            Double nuevosaldo = saldocuentaenvio.getDouble(0) + monto;
            try {
                Cursor resultado = BDatos.rawQuery("UPDATE cliente SET monto=" + nuevosaldo + " WHERE cuenta=?", new String[]{cuenta});
                resultado.moveToFirst();
                if (resultado != null) {
                    bundle.putBoolean("rsql", true);
                    bundle = retirar(context, bundle, (monto)-3000);
                }
            } catch (Exception e) {
                Log.e(null, e.toString());
            }
        }
        return bundle;
    }
    boolean validarcuenta(Context context, String cuenta){
        boolean  v = false;
        SQLiteDatabase BDatos = this.conexion(context);
        Cursor valcuenta =  BDatos.rawQuery("SELECT * FROM cliente WHERE cuenta=?", new String[]{cuenta});
        valcuenta.moveToFirst();
        if(valcuenta.getCount()>0){
            v= true;
        }
        return v;
    }
    Cursor usuarios(Context context){
        SQLiteDatabase BDatos = this.conexion(context);
        Cursor user = BDatos.rawQuery("SELECT * FROM cliente", null);
        return  user;
    }
    boolean verestado(Context context, String correo){
        boolean v;
        SQLiteDatabase BDatos = this.conexion(context);
        Cursor user = BDatos.rawQuery("SELECT estado FROM cliente WHERE correo=?", new String[]{correo});
        user.moveToFirst();
        if(user.getString(user.getColumnIndex("estado")).equals("habilitado")){
            v=true;
        }else{
            v=false;
        }
        return v;
    }
    boolean deshabilitarusuario(Context context, String correo){
        boolean v = false;
        SQLiteDatabase BDatos = this.conexion(context);
        try{
            Cursor resultado = BDatos.rawQuery("UPDATE cliente SET estado='deshabilitado' WHERE correo=?", new String[]{correo});
            resultado.moveToFirst();
            if(resultado != null){
                v=true;
            }
        }catch (Exception e){
            Log.e(null, e.toString());
        }
        return v;
    }

    boolean habilitarusuario(Context context, String correo){
        boolean v = false;
        SQLiteDatabase BDatos = this.conexion(context);
        try{
            Cursor resultado = BDatos.rawQuery("UPDATE cliente SET estado='habilitado' WHERE correo=?", new String[]{correo});
            resultado.moveToFirst();
            if(resultado != null){
                v=true;
            }
        }catch (Exception e){
            Log.e(null, e.toString());
        }
        return v;
    }


}
