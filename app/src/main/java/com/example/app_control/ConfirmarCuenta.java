package com.example.app_control;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_control.Registro.DaoRegistro;
import com.example.app_control.Registro.RegistroConstructor;
import com.example.app_control.utils.InputValidation;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ConfirmarCuenta extends AppCompatActivity {

    private TextView tv_bienvenido,tv_ingresa,tv_intentos,tv_recibir;
    private EditText et_codigo1,et_codigo2,et_codigo3,et_codigo4,et_codigo5,et_codigo6;
    private Button btn_reenviar;
    String codigotxt = "";
    int c = 5,codigon ;

    String message = "";
    DaoRegistro dao = new DaoRegistro();
   String key;
    String codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_cuenta);

    tv_bienvenido = (TextView) findViewById(R.id.tv_c_bienvenido);
    tv_ingresa = (TextView) findViewById(R.id.tv_c_ingresa);
    tv_intentos = (TextView) findViewById(R.id.tv_c_intentos);
    tv_recibir = (TextView) findViewById(R.id.tv_c_recibir);
    et_codigo1 = (EditText) findViewById(R.id.txt_c_codigo1);
    et_codigo2 = (EditText) findViewById(R.id.txt_c_codigo2);
    et_codigo3 = (EditText) findViewById(R.id.txt_c_codigo3);
    et_codigo4 = (EditText) findViewById(R.id.txt_c_codigo4);
    et_codigo5 = (EditText) findViewById(R.id.txt_c_codigo5);
    et_codigo6 = (EditText) findViewById(R.id.txt_c_codigo6);
    btn_reenviar = (Button) findViewById(R.id.btn_c_reenviar);
    codigo = getIntent().getStringExtra("Codigo");

    btn_reenviar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            reenviar();
            tv_intentos.setText("Numero de intentos restantes: " + c);
        }
    });

    et_codigo1.setOnKeyListener(new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {

            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                confirmar();
            }

            return false;
        }
    });

    et_codigo2.setOnKeyListener(new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {

            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                confirmar();
            }

            return false;
        }
    });

    et_codigo3.setOnKeyListener(new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {

            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                confirmar();
            }

            return false;
        }
    });

    et_codigo4.setOnKeyListener(new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {

            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                confirmar();
            }

            return false;
        }
    });

    et_codigo5.setOnKeyListener(new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {

            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                confirmar();
            }

            return false;
        }
    });

    et_codigo6.setOnKeyListener(new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {

            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                confirmar();
            }

            return false;
        }
    });
} catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error: "+e, Toast.LENGTH_SHORT).show();
}
    }

    private void sendEmailWithGmail(final String recipientEmail, final String recipientPassword,
                                    String to, String subject, String message) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");

            Session session = Session.getDefaultInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(recipientEmail, recipientPassword);
                }
            });

            SenderAsyncTask task = new ConfirmarCuenta.SenderAsyncTask(session, recipientEmail, to, subject, message);
            task.execute();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error: "+e, Toast.LENGTH_SHORT).show();
        }
    }

    private class SenderAsyncTask extends AsyncTask<String, String, String> {
            private String from, to, subject, message;
            private ProgressDialog progressDialog;
            private Session session;

        public
            SenderAsyncTask(Session session, String from, String to, String subject, String message)
            {
                this.session = session;
                this.from = from;
                this.to = to;
                this.subject = subject;
                this.message = message;
            }

            @Override
            protected void onPreExecute () {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(ConfirmarCuenta.this, "", getString(R.string.sending_mail), true);
            progressDialog.setCancelable(false);
        }

            @Override
            protected String doInBackground (String...params){
            try {
                Message mimeMessage = new MimeMessage(session);
                mimeMessage.setFrom(new InternetAddress(from));
                mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                mimeMessage.setSubject(subject);
                mimeMessage.setContent(message, "text/html; charset=utf-8");
                Transport.send(mimeMessage);
            } catch (MessagingException e) {
                Toast.makeText(getApplicationContext(), "Error: "+e, Toast.LENGTH_SHORT).show();
                return e.getMessage();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Error: "+e, Toast.LENGTH_SHORT).show();
                return e.getMessage();
            }
            return null;
        }

            @Override
            protected void onProgressUpdate (String...values){
            super.onProgressUpdate(values);
            progressDialog.setMessage(values[0]);
        }

            @Override
            protected void onPostExecute (String result){
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }

    }
    private int codigo(int max){
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());

        int numero = random.nextInt(max);
        return numero;
    }

    private void confirmar(){

        boolean codigo1_b = InputValidation.isValidEditText(et_codigo1, null);
        boolean codigo2_b = InputValidation.isValidEditText(et_codigo2, null);
        boolean codigo3_b = InputValidation.isValidEditText(et_codigo3, null);
        boolean codigo4_b = InputValidation.isValidEditText(et_codigo4, null);
        boolean codigo5_b = InputValidation.isValidEditText(et_codigo5, null);
        boolean codigo6_b = InputValidation.isValidEditText(et_codigo6, null);
        Intent aceptar = new Intent(getApplicationContext(), PrincipalMenuActivity.class);
        key = getIntent().getStringExtra("key");
        String nombre = getIntent().getStringExtra("nombre");
        String apellido = getIntent().getStringExtra("apellido");
        String fecha = getIntent().getStringExtra("fecha");
        String numero = getIntent().getStringExtra("numero");
        String correo = getIntent().getStringExtra("correo");
        String contrasena = getIntent().getStringExtra("contrasena");
        String ruta = getIntent().getStringExtra("ruta");
        String licencia = getIntent().getStringExtra("licencia");

        if (codigo1_b && codigo2_b && codigo3_b && codigo4_b && codigo5_b && codigo6_b) {
            Toast.makeText(getApplicationContext(), "Listo", Toast.LENGTH_SHORT).show();
            codigotxt = et_codigo1.getText().toString() + "" + et_codigo2.getText().toString() + "" + et_codigo3.getText().toString() + "" + et_codigo4.getText().toString() + "" + et_codigo5.getText().toString() + "" + et_codigo6.getText().toString();
            if (codigotxt.equals(codigo)) {
                try{

                    RegistroConstructor emp = new RegistroConstructor( nombre, apellido, fecha, numero, correo, contrasena, ruta, licencia,"false");
                    DatabaseReference bbdd;

                    bbdd = FirebaseDatabase.getInstance().getReference("RegistroConstructor");

                    Query q=bbdd.orderByChild("confirmado").equalTo("true");

                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            int cont =0;
                            for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                                cont++;
                                Toast.makeText(getApplicationContext(), "He encontrado "+cont, Toast.LENGTH_LONG).show();
                            }
                            key = ""+cont;
                            emp.setKey(key);
                            key = emp.getKey();
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("nombre", nombre);
                            hashMap.put("apellido", apellido);
                            hashMap.put("fecha", fecha);
                            hashMap.put("numero", numero);
                            hashMap.put("correo", correo);
                            hashMap.put("contrasena", contrasena);
                            hashMap.put("ruta", ruta);
                            hashMap.put("confirmado", "true");

                            hashMap.put("licencia", licencia);
                            Toast.makeText(getApplicationContext(), "" + key, Toast.LENGTH_SHORT).show();
                            dao.update(key, hashMap).addOnSuccessListener(suc ->
                            {
                                Toast.makeText(getApplicationContext(), "Record is updated", Toast.LENGTH_SHORT).show();
                            }).addOnFailureListener(er ->
                            {
                                Toast.makeText(getApplicationContext(), "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                            });

                            startActivity(aceptar);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }catch (Exception eo){
                    Toast.makeText(getApplicationContext(), "e"+eo, Toast.LENGTH_SHORT).show();
                }
            } else {
                c--;
                if (c < 0) {
                    reenviar();
                }
                tv_intentos.setText("Numero de intentos restantes: " + c);
            }

        }
    }

    private void reenviar(){
        final String recipientEmail = "kacharpo.service@gmail.com";
        final String recipientPassword = "Kacharpo2000";
        final String subject = "Codigo de confrimacion";
        final String emailto = getIntent().getStringExtra("EmailTo");
        codigon = codigo(999999);
        codigo = "" + codigon;
        message = "Su codigo es: " + codigo;
        sendEmailWithGmail(recipientEmail, recipientPassword, emailto, subject, message);
        c = 5;
    }
}