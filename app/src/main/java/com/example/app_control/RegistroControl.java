package com.example.app_control;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class RegistroControl extends AppCompatActivity {

    private Spinner sp_tipo;
    private EditText et_nombre, et_apellido, et_fecha,et_numero , et_correo, et_contrasena, et_confirmar, et_ruta, et_licencia;
    private Button btn_aceptar;
    private ImageView img_control;
    private RadioButton rb_terminos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_control);

        et_nombre = (EditText)findViewById(R.id.txt_c_nombre);
        et_apellido = (EditText)findViewById(R.id.txt_c_apellido);
        et_fecha = (EditText)findViewById(R.id.txt_c_fecha);
        et_numero = (EditText)findViewById(R.id.txt_c_numero);
        et_correo = (EditText)findViewById(R.id.txt_c_correo);
        et_contrasena = (EditText)findViewById(R.id.txt_c_contrasena);
        et_confirmar = (EditText)findViewById(R.id.txt_c_confirmar);
        et_ruta = (EditText)findViewById(R.id.txt_c_ruta);
        et_licencia = (EditText)findViewById(R.id.txt_c_licencia);
        img_control = (ImageView)findViewById(R.id.img_c_control);
        btn_aceptar = (Button)findViewById(R.id.btn_c_aceptar);
        sp_tipo = (Spinner)findViewById(R.id.sp_c_tipo);
        rb_terminos = (RadioButton)findViewById(R.id.rb_c_terminos);

        String [] tipo = {"Tipo","Euroban", "Urban", "Combi"};

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipo);

        sp_tipo.setAdapter(adapter1);
    }

    public void Aceptar(View view){

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "registro",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();

        int id_control = 1;
        String nombre = et_nombre.getText().toString();
        String apellido = et_apellido.getText().toString();
        String fecha = et_fecha.getText().toString();
        String numero = et_numero.getText().toString();
        String correo = et_correo.getText().toString();
        String contrasena = et_contrasena.getText().toString();
        String confirmar = et_confirmar.getText().toString();
        String ruta = et_ruta.getText().toString();
        String licencia = et_licencia.getText().toString();
        String tipo = sp_tipo.getSelectedItem().toString();
        boolean terminos = rb_terminos.isChecked();

        if(!nombre.isEmpty() && !apellido.isEmpty() && !fecha.isEmpty() && !numero.isEmpty() && !correo.isEmpty() && !contrasena.isEmpty() && !confirmar.isEmpty() && !ruta.isEmpty() && !licencia.isEmpty() && !tipo.isEmpty() && !tipo.equals("Tipo")){
            if(contrasena.equals(confirmar)){
                if(terminos == true){
                    ContentValues registro = new ContentValues();
                    registro.put("id_control",id_control);
                    registro.put("nombre", nombre);
                    registro.put("apellido", apellido);
                    registro.put("fecha", fecha);
                    registro.put("numero", numero);
                    registro.put("correo", correo);
                    registro.put("contrasena", contrasena);
                    registro.put("ruta", ruta);
                    registro.put("licencia", licencia);
                    registro.put("tipo", tipo);
                    db.insert("registro_control",null,registro);
                    db.close();
                    Toast.makeText(this, "Registro Exitoso", Toast.LENGTH_SHORT).show();

                    Intent aceptar = new Intent(this,RecuperarContra.class);
                    startActivity(aceptar);
                }else{
                    Toast.makeText(this, "Debes aceptar los terminos y condiciones", Toast.LENGTH_SHORT).show();
                }

            }else {
                Toast.makeText(this, "Contraseñas incorrectas", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

}