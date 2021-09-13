package com.example.app_control.Registro;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.app_control.ConfirmarCuenta;
import com.example.app_control.R;
import com.example.app_control.utils.InputValidation;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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

public class RegistroControl extends AppCompatActivity {

    //Crear variables
    private String modelName;
    StorageReference storageRef;
    ProgressDialog progressDialog;

    DaoRegistro dao = new DaoRegistro();
    String key ="1";

    private Spinner sp_tipo;
    private EditText et_nombre, et_apellido, et_fecha,et_numero , et_correo, et_contrasena, et_confirmar, et_ruta, et_licencia;
    private Button btn_aceptar;
    private ImageView img_control;
    private RadioButton rb_terminos;
    private int codigo = codigo(999999);

    Button btn_Camara,btn_Subir;
    ImageView imgView;
    int foto = 0,n=0;
    StorageReference nStorage;
    int CAMARA_INTENT=1;
    int GALLERY_INTENT=2;
    String[] SUBIDAS = new String[100];

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
        img_control = (ImageView)findViewById(R.id.img_perfil);
        btn_aceptar = (Button)findViewById(R.id.btn_c_aceptar);
        sp_tipo = (Spinner)findViewById(R.id.sp_c_tipo);
        rb_terminos = (RadioButton)findViewById(R.id.rb_c_terminos);
        btn_Camara = findViewById(R.id.btn_c_camara);
        btn_Subir = findViewById(R.id.btn_c_subir);
        imgView = findViewById(R.id.img_perfil);

        String [] tipo = {"Tipo","Euroban", "Urban", "Combi"};

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipo);

        sp_tipo.setAdapter(adapter1);

        final String recipientEmail = "kacharpo.service@gmail.com";
        final String recipientPassword = "Kacharpo2000";
        final String subject = "Codigo de confrimacion";
        final String message = "Su codigo es: "+codigo;

        //AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "registro",null,1);

        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int id_control = 1;
                String nombre = et_nombre.getText().toString();
                boolean nombre_b = InputValidation.isValidEditText(et_nombre,"Campo requerido");
                String apellido = et_apellido.getText().toString();
                boolean apellido_b = InputValidation.isValidEditText(et_apellido, "Campo requerido");
                String fecha = et_fecha.getText().toString();
                boolean fecha_b = InputValidation.isValidEditText(et_fecha,"Campo requerido");
                String numero = et_numero.getText().toString();
                boolean numero_b = InputValidation.isValidEditText(et_numero, "Campo requerido");
                String correo = et_correo.getText().toString();
                boolean correo_b = InputValidation.isValidEditText(et_correo, "Campo requerido");
                String contrasena = et_contrasena.getText().toString();
                boolean contrasena_b = InputValidation.isValidEditText(et_contrasena,"Campo requerido");
                String confirmar = et_confirmar.getText().toString();
                boolean confirmar_b = InputValidation.isValidEditText(et_confirmar,"Campo requerido");
                String ruta = et_ruta.getText().toString();
                boolean ruta_b = InputValidation.isValidEditText(et_ruta, "Campo requerido");
                String licencia = et_licencia.getText().toString();
                boolean licencia_b = InputValidation.isValidEditText(et_licencia, "Campo requerido");
                String tipo = sp_tipo.getSelectedItem().toString();
                boolean terminos = rb_terminos.isChecked();

                if (nombre_b && apellido_b && fecha_b && numero_b && correo_b && contrasena_b && confirmar_b && ruta_b && licencia_b && !tipo.equals("Tipo") ) {
                    if (contrasena.equals(confirmar)) {
                        if (terminos == true) {
                            if(foto==1){
                                RegistroConstructor emp = new RegistroConstructor(key,nombre, apellido, fecha,numero , correo, contrasena, ruta, licencia);
                                dao.add(emp).addOnSuccessListener(suc ->
                                {
                                    Toast.makeText(getApplicationContext(), "Record is inserted", Toast.LENGTH_SHORT).show();
                                }).addOnFailureListener(er ->
                                {
                                    Toast.makeText(getApplicationContext(), "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                                //Eliminar archivos basura
                                Toast.makeText(getApplicationContext(), "Registro Exitoso", Toast.LENGTH_SHORT).show();
                                for(int i=0;i<n;i++){
                                    Toast.makeText(getApplicationContext(), ""+SUBIDAS[i]+"", Toast.LENGTH_SHORT).show();
                                }
                                sendEmailWithGmail(recipientEmail,recipientPassword, et_correo.getText().toString(),subject,message);
                                datos();
                            }else{
                                Toast.makeText(getApplicationContext(), "Debes tomarte una foto", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Debes aceptar los terminos y condiciones", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        et_contrasena.setText("");
                        contrasena_b = InputValidation.isValidEditText(et_contrasena, "!");
                        et_confirmar.setText("");
                        confirmar_b = InputValidation.isValidEditText(et_confirmar, "!");
                        Toast.makeText(getApplicationContext(), "Contraseñas incorrectas", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_Camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirCamara();
            }
        });

        btn_Subir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirAlbum();
            }
        });

        nStorage = FirebaseStorage.getInstance().getReference();
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

        SenderAsyncTask task = new RegistroControl.SenderAsyncTask(session, recipientEmail, to, subject, message);
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
            progressDialog = ProgressDialog.show(RegistroControl.this, "", getString(R.string.sending_mail), true);
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

    private void datos(){
        Intent correo = new Intent(getApplicationContext(), ConfirmarCuenta.class);
        correo.putExtra("EmailTo",et_correo.getText().toString());
        correo.putExtra("Codigo",""+codigo);
        startActivity(correo);
    }

    private int codigo(int max){
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());

        int numero = random.nextInt(max);
        return numero;
    }

    private void abrirCamara(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent,CAMARA_INTENT);
        }

    }

    private void abrirAlbum(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,GALLERY_INTENT);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == CAMARA_INTENT && resultCode == RESULT_OK ) ) {
            Bundle extras = data.getExtras();
            Bitmap imgBitmap = (Bitmap) extras.get("data");
            imgView.setImageBitmap(imgBitmap);
            foto = 1;
            Toast.makeText(getApplicationContext(), "Camara", Toast.LENGTH_SHORT).show();
        }
        if((requestCode == GALLERY_INTENT && resultCode == RESULT_OK )){
            Toast.makeText(getApplicationContext(), "SUbida", Toast.LENGTH_SHORT).show();
            Uri uri = data.getData();

            StorageReference filePath = nStorage.child("Perfil").child(uri.getLastPathSegment());

            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    SUBIDAS[n] = ""+uri.getLastPathSegment()+"";
                    Toast.makeText(getApplicationContext(), "Imagen Subida "+ SUBIDAS[n]+"", Toast.LENGTH_SHORT).show();
                    n++;
                    foto = 1;
                }
            });
        }
    }

}