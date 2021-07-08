package com.example.app_control;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

public class RegistroControl extends AppCompatActivity {

    private Spinner sp_tipo;
    private EditText et_nombre, et_apellido, et_fecha,et_numero , et_correo, et_contrasena, et_confirmar, et_ruta, et_licencia;
    private Button btn_aceptar;
    private ImageView img_control;

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

        String [] tipo = {"Euroban", "Urban", "Combi"};

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);

        sp_tipo.setAdapter(adapter1);
    }

    public void Aceptar(){

    }

}