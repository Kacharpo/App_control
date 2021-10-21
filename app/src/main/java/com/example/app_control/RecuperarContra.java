package com.example.app_control;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_control.utils.InputValidation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class RecuperarContra extends AppCompatActivity{

    private EditText et_correo;
    private TextView tv_olvidaste, tv_introducir;
    private Button btn_enviar;
    private ImageView img_recuperar;
    private FirebaseAuth auth;
    private boolean e=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_recuperar);

            et_correo = (EditText) findViewById(R.id.txt_c_correoContra);
            tv_olvidaste = (TextView) findViewById(R.id.tv_c_olvidaste);
            tv_introducir = (TextView) findViewById(R.id.tv_c_introducir);
            btn_enviar = (Button) findViewById(R.id.btn_c_enviar);
            img_recuperar = (ImageView) findViewById(R.id.img_c_recuperar);

            //Values received from another Activity (Screen)
            final String recipientEmail = "kacharpo.service@gmail.com";
            final String recipientPassword = "Kacharpo2000";
            final String subject = "Recuperar contraseña";
            final String message = "Su contraseña es: ";

            Intent aceptar = new Intent(this, Log_in.class);
            auth = FirebaseAuth.getInstance();

            btn_enviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (InputValidation.isValidEditText(et_correo, getString(R.string.field_is_required))) {

                        String correo = et_correo.getText().toString();
                        auth.setLanguageCode("es");
                        auth.sendPasswordResetEmail(correo).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(), "Correo enviado", Toast.LENGTH_SHORT).show();
                                    startActivity(aceptar);
                                }else{
                                    Toast.makeText(getApplicationContext(), "Correo no registrado", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
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

            SenderAsyncTask task = new RecuperarContra.SenderAsyncTask(session, recipientEmail, to, subject, message);
            task.execute();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error: "+e, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * AsyncTask to send email
     */
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
            progressDialog = ProgressDialog.show(RecuperarContra.this, "", getString(R.string.sending_mail), true);
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

}

