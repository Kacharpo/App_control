package com.example.app_control;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.MapView;

public class Principal extends AppCompatActivity {

    private EditText et_ubicacion;
    private TextView tv_inicio, tv_final,tv_tiempo,tv_conductor, tv_disponibilidad,tv_iniciotxt,
            tv_finaltxt,tv_tiempotxt,tv_conductortxt, tv_disponibilidadtxt, tv_rutas, tv_ficha;
    private ImageView img_conductor;
    private MapView mv_destino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        et_ubicacion = (EditText)findViewById(R.id.txt_c_ubicacion);
        tv_inicio = (TextView)findViewById(R.id.tv_c_inicio);
        tv_final = (TextView)findViewById(R.id.tv_c_final);
        tv_tiempo = (TextView)findViewById(R.id.tv_c_tiempo);
        tv_conductor = (TextView)findViewById(R.id.tv_c_conductor);
        tv_disponibilidad = (TextView)findViewById(R.id.tv_c_disponibilidad);
        tv_iniciotxt = (TextView)findViewById(R.id.tv_c_iniciotxt);
        tv_finaltxt = (TextView)findViewById(R.id.tv_c_finaltxt);
        tv_tiempotxt = (TextView)findViewById(R.id.tv_c_tiempotxt);
        tv_conductortxt = (TextView)findViewById(R.id.tv_c_conductortxt);
        tv_disponibilidadtxt = (TextView)findViewById(R.id.tv_c_disponibilidadtxt);
        img_conductor = (ImageView)findViewById(R.id.img_c_conductor);
        mv_destino = (MapView)findViewById(R.id.mv_c_destino);
    }
}