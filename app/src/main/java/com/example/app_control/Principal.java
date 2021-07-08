package com.example.app_control;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Principal extends FragmentActivity implements OnMapReadyCallback {

    private EditText et_ubicacion;
    private TextView tv_inicio, tv_final,tv_tiempo,tv_conductor, tv_disponibilidad,tv_iniciotxt,
            tv_finaltxt,tv_tiempotxt,tv_conductortxt, tv_disponibilidadtxt, tv_rutas, tv_ficha;
    private ImageView img_conductor;
    private MapView mv_destino;

    private GoogleMap mMap;
    private boolean UbiAct = false;

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

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mv_c_destino);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionCheck2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        if (permissionCheck!= PackageManager.PERMISSION_GRANTED && permissionCheck2!= PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){

            }else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
            return;
        }

        if(mMap.isMyLocationEnabled()==false){
            LatLng CDMX= new LatLng(19.3168, -99.08671 );
            mMap.addMarker(new MarkerOptions().position(CDMX).title("Marcador en la CDMX"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(CDMX));
        }

        LocationManager locationManager = (LocationManager) Principal.this.getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if(mMap.isMyLocationEnabled()==true&&UbiAct==false){
                    mMap.clear();
                    LatLng TiempoReal= new LatLng(location.getLatitude(), location.getLongitude() );
                    mMap.addMarker(new MarkerOptions().position(TiempoReal).title("Marcador de tu Ubicacion"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(TiempoReal));
                    UbiAct=true;
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        } ;

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }
}