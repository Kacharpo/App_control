package com.example.app_control;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import android.app.Activity;
import android.os.Bundle;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class RecuperarContra extends Activity implements OnClickListener{

    private EditText et_correo;
    private TextView tv_olvidaste, tv_introducir;
    private Button btn_enviar;
    private ImageView img_recuperar;

    Session session = null;
    ProgressDialog pdialog = null;
    Context context = null;
    EditText reciep;
    String rec;
    String subject = "Recuperar contra";
    String textMessage = "Tu contraseña es: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar);

        et_correo = (EditText)findViewById(R.id.txt_c_correoContra);
        tv_olvidaste = (TextView)findViewById(R.id.tv_c_olvidaste);
        tv_introducir = (TextView)findViewById(R.id.tv_c_introducir);
        btn_enviar = (Button)findViewById(R.id.btn_c_enviar);
        img_recuperar = (ImageView)findViewById(R.id.img_c_recuperar);

        context = this;

    }

    @Override
    public void onClick(View v) {
        rec = et_correo.getText().toString();

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("tona23.tona14@gmail.com", "Gi0vann&MP2314");
            }
        });

        pdialog = ProgressDialog.show(context, "", "Sending Mail...", true);

        RetreiveFeedTask task = new RetreiveFeedTask();
        task.execute();

        Intent next = new Intent(this,Principal.class);
        startActivity(next);
    }

    class RetreiveFeedTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            try{
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("testfrom354@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(rec));
                message.setSubject(subject);
                message.setContent(textMessage, "text/html; charset=utf-8");
                Transport.send(message);
            } catch(MessagingException e) {
                e.printStackTrace();
            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            pdialog.dismiss();
            reciep.setText("");
            Toast.makeText(getApplicationContext(), "Message sent", Toast.LENGTH_LONG).show();
        }
    }
    public void Enviar(View view){
        /*Intent enviar = new Intent(Intent.ACTION_SENDTO);
        enviar.setData(Uri.parse("mailto:"));
        enviar.putExtra(Intent.EXTRA_EMAIL,new String[]{et_correo.getText().toString()});
        enviar.putExtra(Intent.EXTRA_SUBJECT,"Recuperar Contraseña");
        enviar.putExtra(Intent.EXTRA_TEXT,"Su contraseña es: ");
        startActivity(enviar);
        */

    }
}

