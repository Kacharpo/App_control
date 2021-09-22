package com.example.app_control.Registro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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

import kotlin.jvm.internal.Intrinsics;

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

    CardView cv_Camara,cv_Subir;
    int foto = 0,n=0;
    StorageReference nStorage;
    int INTENT = 0;
    int CAMARA_INTENT=1;
    int GALLERY_INTENT=2;
    String[] SUBIDAS = new String[100];
    Uri uri;
    StorageReference filePath;
    int PCAMARA =100;
    int TAKE = 101;
    int WRITE = 200;
    Bitmap imgBitmap;
    private Bitmap bitmap;
    private Uri photo;
    Intent var1;


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
        cv_Camara = findViewById(R.id.cv_c_c);
        cv_Subir = findViewById(R.id.cv_c_s);

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
                                if(INTENT==CAMARA_INTENT){
                                    Toast.makeText(getApplicationContext(), "Subida Camara", Toast.LENGTH_SHORT).show();
                                    filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            Toast.makeText(getApplicationContext(), "Imagen Subida "+ modelName+"", Toast.LENGTH_SHORT).show();
                                        }
                                    });
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

        cv_Camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               checkPermissionCamera();
            }

        });

        cv_Subir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirAlbum();
            }
        });

        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA)==PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},PCAMARA);
        }

        nStorage = FirebaseStorage.getInstance().getReference();
        img_control.setImageResource(R.drawable.perfil);
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
        var1 = new Intent("android.media.action.IMAGE_CAPTURE");
        boolean var2 = false;
        boolean var3 = false;
        boolean var5 = false;
        this.photo = this.takeAndSavePicture();

        if (var1.resolveActivity(this.getPackageManager()) != null) {
            boolean var7 = false;
            boolean var8 = false;
            boolean var10 = false;
            var1.putExtra("output", (Parcelable)this.photo);
            this.startActivityForResult(var1, CAMARA_INTENT);
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
            if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {

                imgBitmap = BitmapFactory.decodeFile(modelName);
                img_control.setImageBitmap(imgBitmap);
                foto = 1;
                Toast.makeText(getApplicationContext(), "Camara 28", Toast.LENGTH_SHORT).show();
                INTENT=CAMARA_INTENT;
            }else {
                abrirAlbum();
            }
        }
            if((requestCode == GALLERY_INTENT && resultCode == RESULT_OK )){
            Toast.makeText(getApplicationContext(), "SUbida", Toast.LENGTH_SHORT).show();
            uri = data.getData();
            modelName = ""+uri.getLastPathSegment()+".jpg";
            filePath = nStorage.child("Perfil").child(modelName);

            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    SUBIDAS[n] = modelName;
                    Toast.makeText(getApplicationContext(), "Imagen Subida "+ SUBIDAS[n]+"", Toast.LENGTH_SHORT).show();
                    foto = 1;
                }
            });

            //Mensaje para cargar proceso
            progressDialog = new ProgressDialog(RegistroControl.this);
            progressDialog.setMessage("Fetching image...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Conexion con Firebase Storage
            FirebaseStorage mFirebaseStorage = FirebaseStorage.getInstance();
            try{
                storageRef = mFirebaseStorage.getReference("Perfil/"+modelName+"");
                File localfile = File.createTempFile("tempfile",".jpg");
                storageRef.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        if(progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                        Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                        //binding.imgLpWallpaper.setImageBitmap(bitmap);
                        img_control.setImageBitmap(bitmap);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if(progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                        Toast.makeText(getApplicationContext(), "Faileed", Toast.LENGTH_SHORT).show();
                    }
                });
            }catch (IOException e){
                e.printStackTrace();
            }
            n++;
            INTENT=GALLERY_INTENT;
        }
    }

    private File createImage() throws IOException{
        File imagen=null;
        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            String nombre = "foto_";
            File directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            imagen = File.createTempFile(nombre,".jpg",directorio);
            modelName=imagen.getAbsolutePath();
        }
        return imagen;
    }

    private final Uri takeAndSavePicture() {
        OutputStream fos = (OutputStream)null;
        File file = (File)null;
        Uri uri = (Uri)null;
        String fileName;
        boolean var9;
        if (Build.VERSION.SDK_INT >= 29) {
            ContentResolver resolver = this.getContentResolver();
            fileName = System.currentTimeMillis() + "Image_Example";
            ContentValues var7 = new ContentValues();
            boolean var8 = false;
            var9 = false;
            boolean var11 = false;
            var7.put("_display_name", fileName);
            var7.put("mime_type", "image/jpeg");
            var7.put("relative_path", "Pictures/MyApp");
            var7.put("is_pending", 1);
            uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, var7);

            try {
                OutputStream var10000;
                if (uri != null) {
                    var8 = false;
                    var9 = false;
                    var11 = false;
                    var10000 = resolver.openOutputStream(uri);
                } else {
                    var10000 = null;
                }

                fos = var10000;
            } catch (FileNotFoundException var14) {
                var14.printStackTrace();
            }

            var7.clear();
            var7.put("is_pending", 0);
            if (uri != null) {
                resolver.update(uri, var7, (String)null, (String[])null);
            }
        } else {
            String imageDir = String.valueOf(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES));
            fileName = System.currentTimeMillis() + ".jpg";
            file = new File(imageDir, fileName);

            try {
                fos = (OutputStream)(new FileOutputStream(file));
            } catch (FileNotFoundException var13) {
                var13.printStackTrace();
            }
        }

        boolean var19;
        label60: {
            Bitmap var18 = this.bitmap;
            if (var18 != null) {
                if (var18.compress(Bitmap.CompressFormat.JPEG, 100, fos)) {
                    var19 = true;
                    break label60;
                }
            }
            var19 = false;
        }

        boolean save = var19;
        if (save) {
            Toast.makeText(getApplicationContext(), "Picture save successfully", Toast.LENGTH_SHORT).show();        }

        if (fos != null) {
            boolean var6 = false;
            boolean var17 = false;
            var9 = false;
            try {
                fos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (fos != null) {
            try {
                fos.flush();
                fos.close();
            } catch (IOException var12) {
                var12.printStackTrace();
            }
        }

        if (file != null) {
            MediaScannerConnection.scanFile((Context)this, new String[]{file.toString()}, (String[])null, (MediaScannerConnection.OnScanCompletedListener)null);
        }

        return uri;
    }

    private final void checkPermissionCamera() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission((Context)this, "android.permission.CAMERA") != 0) {
                ActivityCompat.requestPermissions((Activity)this, new String[]{"android.permission.CAMERA"}, 100);
            } else {
                abrirCamara();
            }
        } else {
            abrirCamara();
        }

    }

    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        Intrinsics.checkNotNullParameter(permissions, "permissions");
        Intrinsics.checkNotNullParameter(grantResults, "grantResults");
        if (requestCode == 100) {
            boolean var5 = false;
            boolean var7 = false;
            if (grantResults.length != 0 && grantResults[0] == 0) {
                //this.abrirCamara();
            } else {
                Toast.makeText(getApplicationContext(), "Camera Permission is Required to Use camera.", Toast.LENGTH_SHORT).show();            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}