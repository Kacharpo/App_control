package com.example.app_control;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ConfirmarCuenta extends AppCompatActivity {

    private TextView tv_bienvenido,tv_ingresa,tv_intentos,tv_recibir;
    private EditText et_codigo;
    private Button btn_reenviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_cuenta);

        tv_bienvenido = (TextView)findViewById(R.id.tv_c_bienvenido);
        tv_ingresa = (TextView)findViewById(R.id.tv_c_ingresa);
        tv_intentos = (TextView)findViewById(R.id.tv_c_intentos);
        tv_recibir = (TextView)findViewById(R.id.tv_c_recibir);
        et_codigo = (EditText)findViewById(R.id.txt_c_codigo);
        btn_reenviar = (Button)findViewById(R.id.btn_c_reenviar);
    }

    public void reenviar(View view){
        String codigo = et_codigo.getText().toString();
        if(codigo.equals("abcd")){
            Intent aceptar = new Intent(this,RecuperarContra.class);
            startActivity(aceptar);
        }

    }
}