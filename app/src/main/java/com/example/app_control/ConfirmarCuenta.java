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

import com.example.app_control.utils.InputValidation;

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
    String codigo = "";
    String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_cuenta);

        tv_bienvenido = (TextView)findViewById(R.id.tv_c_bienvenido);
        tv_ingresa = (TextView)findViewById(R.id.tv_c_ingresa);
        tv_intentos = (TextView)findViewById(R.id.tv_c_intentos);
        tv_recibir = (TextView)findViewById(R.id.tv_c_recibir);
        et_codigo1 = (EditText)findViewById(R.id.txt_c_codigo1);
        et_codigo2 = (EditText)findViewById(R.id.txt_c_codigo2);
        et_codigo3 = (EditText)findViewById(R.id.txt_c_codigo3);
        et_codigo4 = (EditText)findViewById(R.id.txt_c_codigo4);
        et_codigo5 = (EditText)findViewById(R.id.txt_c_codigo5);
        et_codigo6 = (EditText)findViewById(R.id.txt_c_codigo6);
        btn_reenviar = (Button)findViewById(R.id.btn_c_reenviar);

        codigo = getIntent().getStringExtra("Codigo");
        message = "Su codigo es: "+ codigo;
        final String recipientEmail = "kacharpo.service@gmail.com";
        final String recipientPassword = "Kacharpo2000";
        final String subject = "Codigo de confrimacion";
        final String emailto = getIntent().getStringExtra("EmailTo");

        btn_reenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmailWithGmail(recipientEmail,recipientPassword,emailto,subject,message);
                c=5;
                tv_intentos.setText("Numero de intentos restantes: "+c);
            }
        });

        Intent aceptar = new Intent(getApplicationContext(),RecuperarContra.class);

        et_codigo1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode==KeyEvent.KEYCODE_ENTER ){

                    boolean codigo1_b = InputValidation.isValidEditText(et_codigo1,null);
                    boolean codigo2_b = InputValidation.isValidEditText(et_codigo2,null);
                    boolean codigo3_b = InputValidation.isValidEditText(et_codigo3,null);
                    boolean codigo4_b = InputValidation.isValidEditText(et_codigo4,null);
                    boolean codigo5_b = InputValidation.isValidEditText(et_codigo5,null);
                    boolean codigo6_b = InputValidation.isValidEditText(et_codigo6,null);

                    if(codigo1_b && codigo2_b && codigo3_b && codigo4_b && codigo5_b && codigo6_b){
                        Toast.makeText(getApplicationContext(), "Listo", Toast.LENGTH_SHORT).show();
                        codigotxt = et_codigo1.getText().toString()+""+et_codigo2.getText().toString()+""+et_codigo3.getText().toString()+""+et_codigo4.getText().toString()+""+et_codigo5.getText().toString()+""+et_codigo6.getText().toString();
                        if(codigotxt.equals(codigo)){
                            startActivity(aceptar);
                        }

                    }

                }

                return false;
            }
        });

        et_codigo2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode==KeyEvent.KEYCODE_ENTER ){

                    boolean codigo1_b = InputValidation.isValidEditText(et_codigo1,null);
                    boolean codigo2_b = InputValidation.isValidEditText(et_codigo2,null);
                    boolean codigo3_b = InputValidation.isValidEditText(et_codigo3,null);
                    boolean codigo4_b = InputValidation.isValidEditText(et_codigo4,null);
                    boolean codigo5_b = InputValidation.isValidEditText(et_codigo5,null);
                    boolean codigo6_b = InputValidation.isValidEditText(et_codigo6,null);

                    if(codigo1_b && codigo2_b && codigo3_b && codigo4_b && codigo5_b && codigo6_b){
                        Toast.makeText(getApplicationContext(), "Listo", Toast.LENGTH_SHORT).show();
                        codigotxt = et_codigo1.getText().toString()+""+et_codigo2.getText().toString()+""+et_codigo3.getText().toString()+""+et_codigo4.getText().toString()+""+et_codigo5.getText().toString()+""+et_codigo6.getText().toString();
                        if(codigotxt.equals(codigo)){
                            startActivity(aceptar);
                        }

                    }

                }

                return false;
            }
        });

        et_codigo3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode==KeyEvent.KEYCODE_ENTER ){

                    boolean codigo1_b = InputValidation.isValidEditText(et_codigo1,null);
                    boolean codigo2_b = InputValidation.isValidEditText(et_codigo2,null);
                    boolean codigo3_b = InputValidation.isValidEditText(et_codigo3,null);
                    boolean codigo4_b = InputValidation.isValidEditText(et_codigo4,null);
                    boolean codigo5_b = InputValidation.isValidEditText(et_codigo5,null);
                    boolean codigo6_b = InputValidation.isValidEditText(et_codigo6,null);

                    if(codigo1_b && codigo2_b && codigo3_b && codigo4_b && codigo5_b && codigo6_b){
                        Toast.makeText(getApplicationContext(), "Listo", Toast.LENGTH_SHORT).show();
                        codigotxt = et_codigo1.getText().toString()+""+et_codigo2.getText().toString()+""+et_codigo3.getText().toString()+""+et_codigo4.getText().toString()+""+et_codigo5.getText().toString()+""+et_codigo6.getText().toString();
                        if(codigotxt.equals(codigo)){
                            startActivity(aceptar);
                        }

                    }

                }

                return false;
            }
        });

        et_codigo4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode==KeyEvent.KEYCODE_ENTER ){

                    boolean codigo1_b = InputValidation.isValidEditText(et_codigo1,null);
                    boolean codigo2_b = InputValidation.isValidEditText(et_codigo2,null);
                    boolean codigo3_b = InputValidation.isValidEditText(et_codigo3,null);
                    boolean codigo4_b = InputValidation.isValidEditText(et_codigo4,null);
                    boolean codigo5_b = InputValidation.isValidEditText(et_codigo5,null);
                    boolean codigo6_b = InputValidation.isValidEditText(et_codigo6,null);

                    if(codigo1_b && codigo2_b && codigo3_b && codigo4_b && codigo5_b && codigo6_b){
                        Toast.makeText(getApplicationContext(), "Listo", Toast.LENGTH_SHORT).show();
                        codigotxt = et_codigo1.getText().toString()+""+et_codigo2.getText().toString()+""+et_codigo3.getText().toString()+""+et_codigo4.getText().toString()+""+et_codigo5.getText().toString()+""+et_codigo6.getText().toString();
                        if(codigotxt.equals(codigo)){
                            startActivity(aceptar);
                        }

                    }

                }

                return false;
            }
        });

        et_codigo5.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode==KeyEvent.KEYCODE_ENTER ){

                    boolean codigo1_b = InputValidation.isValidEditText(et_codigo1,null);
                    boolean codigo2_b = InputValidation.isValidEditText(et_codigo2,null);
                    boolean codigo3_b = InputValidation.isValidEditText(et_codigo3,null);
                    boolean codigo4_b = InputValidation.isValidEditText(et_codigo4,null);
                    boolean codigo5_b = InputValidation.isValidEditText(et_codigo5,null);
                    boolean codigo6_b = InputValidation.isValidEditText(et_codigo6,null);

                    if(codigo1_b && codigo2_b && codigo3_b && codigo4_b && codigo5_b && codigo6_b){
                        Toast.makeText(getApplicationContext(), "Listo", Toast.LENGTH_SHORT).show();
                        codigotxt = et_codigo1.getText().toString()+""+et_codigo2.getText().toString()+""+et_codigo3.getText().toString()+""+et_codigo4.getText().toString()+""+et_codigo5.getText().toString()+""+et_codigo6.getText().toString();
                        if(codigotxt.equals(codigo)){
                            startActivity(aceptar);
                        }

                    }

                }

                return false;
            }
        });

        et_codigo6.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode==KeyEvent.KEYCODE_ENTER ){

                    boolean codigo1_b = InputValidation.isValidEditText(et_codigo1,null);
                    boolean codigo2_b = InputValidation.isValidEditText(et_codigo2,null);
                    boolean codigo3_b = InputValidation.isValidEditText(et_codigo3,null);
                    boolean codigo4_b = InputValidation.isValidEditText(et_codigo4,null);
                    boolean codigo5_b = InputValidation.isValidEditText(et_codigo5,null);
                    boolean codigo6_b = InputValidation.isValidEditText(et_codigo6,null);

                    if(codigo1_b && codigo2_b && codigo3_b && codigo4_b && codigo5_b && codigo6_b){
                        Toast.makeText(getApplicationContext(), "Listo", Toast.LENGTH_SHORT).show();
                        codigotxt = et_codigo1.getText().toString()+""+et_codigo2.getText().toString()+""+et_codigo3.getText().toString()+""+et_codigo4.getText().toString()+""+et_codigo5.getText().toString()+""+et_codigo6.getText().toString();
                        if(codigotxt.equals(codigo)){
                            startActivity(aceptar);
                        }else{
                            c--;
                            if(c<0){
                                codigon = codigo(999999);
                                codigo = ""+codigon;
                                message = "Su codigo es: "+ codigo;
                                sendEmailWithGmail(recipientEmail,recipientPassword,emailto,subject,message);
                                c=5;
                            }
                            tv_intentos.setText("Numero de intentos restantes: "+c);
                        }

                    }

                }

                return false;
            }
        });

    }

    private void sendEmailWithGmail(final String recipientEmail, final String recipientPassword,
                                    String to, String subject, String message) {
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
    }

    private class SenderAsyncTask extends AsyncTask<String, String, String> {

        private String from, to, subject, message;
        private ProgressDialog progressDialog;
        private Session session;

        public SenderAsyncTask(Session session, String from, String to, String subject, String message) {
            this.session = session;
            this.from = from;
            this.to = to;
            this.subject = subject;
            this.message = message;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(ConfirmarCuenta.this, "", getString(R.string.sending_mail), true);
            progressDialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Message mimeMessage = new MimeMessage(session);
                mimeMessage.setFrom(new InternetAddress(from));
                mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                mimeMessage.setSubject(subject);
                mimeMessage.setContent(message, "text/html; charset=utf-8");
                Transport.send(mimeMessage);
            } catch (MessagingException e) {
                e.printStackTrace();
                return e.getMessage();
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            progressDialog.setMessage(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {
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
}