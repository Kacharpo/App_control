package  com.example.app_control ;

import  androidx.appcompat.app.AppCompatActivity ;

import  android.content.Intent ;
import  android.database.Cursor ;
import  android.database.sqlite.SQLiteDatabase ;
import  android.os.Bundle ;
import  android.view.View ;
import  android.widget.EditText ;
import android.widget.TextView;
import  android.widget.Toast ;

import  com.example.app_control.utils.InputValidation ;

public class Log_in extends AppCompatActivity {
    private EditText et_usuario, et_contrasena;
    private TextView tv_recuperar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        et_usuario = (EditText)findViewById(R.id.txt_c_usr);
        et_contrasena = (EditText)findViewById(R.id.txt_c_pass);
        tv_recuperar = (TextView)findViewById(R.id.tv_c_recuperar);

        tv_recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recuperar_contra();
            }
        });
    }

    public void registro(View view){
        Intent i = new Intent(Log_in.this, RegistroControl.class);
        startActivity(i);
        finish();
    }
    public void recuperar_contra(){
        Intent i = new Intent(Log_in.this, RecuperarContra.class);
        startActivity(i);
        finish();
    }

    public void ingresar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "registro",null,1);

        String usuario = et_usuario.getText().toString();
        boolean usuario_b = InputValidation.isValidEditText(et_usuario, getString(R.string.field_is_required));
        String contrasena = et_contrasena.getText().toString();
        boolean contrasena_b = InputValidation.isValidEditText(et_contrasena, getString(R.string.field_is_required));

        if(usuario_b && contrasena_b){
            boolean b = false;
            for(int i = 0;b == false;i++) {
                SQLiteDatabase db = admin.getWritableDatabase();
                Cursor fila = db.rawQuery("select correo,contrasena from registro_control where id_control =" + i, null);
                if (fila.moveToFirst()) {
                    if (usuario.equals(fila.getString(0)) && contrasena.equals(fila.getString(1))) {
                        Intent a = new Intent(getApplicationContext(), Principal.class);
                        startActivity(a);
                        b = true;
                        finish();
                    } else if(usuario.equals(fila.getString(0)) && !contrasena.equals(fila.getString(1))) {
                        b = true;
                        Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    b = true;
                    Toast.makeText(this, "Usuario incorrecto", Toast.LENGTH_SHORT).show();
                }
                db.close();
            }
        }else {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

}