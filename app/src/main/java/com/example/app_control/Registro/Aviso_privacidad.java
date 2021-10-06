package com.example.app_control.Registro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app_control.Log_in;
import com.example.app_control.R;

public class Aviso_privacidad extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aviso_privacidad);
    }

    public void regresar(View view){
        Intent i = new Intent(Aviso_privacidad.this, Log_in.class);
        startActivity(i);
        finish();
    }
}