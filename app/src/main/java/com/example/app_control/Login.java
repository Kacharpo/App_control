package com.example.app_control;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app_control.utils.InputValidation;

public class Login extends AppCompatActivity {

    private EditText et_usuario, et_contrasena,et_correo_r,et_contrasena_r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_usuario = (EditText)findViewById(R.id.txt_c_usr);
        et_contrasena = (EditText)findViewById(R.id.txt_c_pass);

    }
    public void ingresar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "registro",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String usuario = et_usuario.getText().toString();
        boolean usuario_b = InputValidation.isValidEditText(et_usuario, getString(R.string.field_is_required));
        String contrasena = et_contrasena.getText().toString();
        boolean contrasena_b = InputValidation.isValidEditText(et_contrasena, getString(R.string.field_is_required));

        if(usuario_b && contrasena_b){
            Cursor fila = db.rawQuery
                    ("select correo, contrasena from registro_control where id_control =" + 1 ,null);
            if(fila.moveToFirst()) {
                if(usuario.equals(fila.getString(0)) && contrasena.equals(fila.getString(1))){
                    Intent i = new Intent(Login.this, Principal.class);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(this, "Usuario y/o Contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this, "No exite el registro", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }else {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void registrar(View view){
        Intent i = new Intent(Login.this, RegistroControl.class);
        startActivity(i);
        finish();
    }
}