package  com.example.app_control ;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app_control.Registro.RegistroControl;
import com.example.app_control.utils.InputValidation;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.GoogleAuthProvider;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


public class Log_in extends AppCompatActivity {
    private EditText et_usuario, et_contrasena;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 1;
    String TAG = "GoogleSignIn";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        et_usuario = (EditText)findViewById(R.id.txt_usr);
        et_contrasena = (EditText)findViewById(R.id.txt_pass);

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if(isLoggedIn){
            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            "Sesion iniciada con facebook : "+accessToken, Toast.LENGTH_SHORT);

            toast1.show();
            Intent a = new Intent(getApplicationContext(), PrincipalMenuActivity.class);
            startActivity(a);
        }

        // Configurar Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Crear un GoogleSignInClient con las opciones especificadas por gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Inicializar Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();


        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Toast toast1 =
                        Toast.makeText(getApplicationContext(),
                                "Log in exitoso !", Toast.LENGTH_SHORT);

                toast1.show();
                Intent a = new Intent(getApplicationContext(), PrincipalMenuActivity.class);
                startActivity(a);

            }

            @Override
            public void onCancel() {
                Toast toast1 =
                        Toast.makeText(getApplicationContext(),
                                "Cancelado", Toast.LENGTH_SHORT);

                toast1.show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast toast1 =
                        Toast.makeText(getApplicationContext(),
                                "Error : "+exception, Toast.LENGTH_SHORT);

                toast1.show();
                Log.d("debug ", "Error : "+exception);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        //Resultado devuelto al iniciar el Intent de GoogleSignInApi.getSignInIntent (...);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            if(task.isSuccessful()){
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                    firebaseAuthWithGoogle(account.getIdToken());
                } catch (ApiException e) {
                    // Google Sign In fallido, actualizar GUI
                    Log.w(TAG, "Google sign in failed", e);
                }
            }else{
                Log.d(TAG, "Error, login no exitoso:" + task.getException().toString());
                Toast.makeText(this, "Ocurrio un error. "+task.getException().toString(),
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            //FirebaseUser user = mAuth.getCurrentUser();
                            Intent a = new Intent(getApplicationContext(), PrincipalMenuActivity.class);
                            startActivity(a);
//Iniciar DASHBOARD u otra actividad luego del SigIn Exitoso
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());

                        }
                    }
                });
    }

    public void Log_in_firebase(View view) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        //Intent i = new Intent(mGoogleSignInClient.getApplicationContext(), Registro_conductor.class);
        //startActivity(i);
        //finish();


        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    public void registro(View view){
        Intent i = new Intent(Log_in.this, RegistroControl.class);
        startActivity(i);
        finish();
    }
    public void recuperar_contra(View view){
        Intent i = new Intent(Log_in.this, RecuperarContra.class);
        startActivity(i);
        finish();
    }
    public void ingresar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "registro",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String usuario = et_usuario.getText().toString();
        boolean usuario_b = InputValidation.isValidEditText(et_usuario, getString(R.string.field_is_required));
        String contrasena = et_contrasena.getText().toString();
        boolean contrasena_b = InputValidation.isValidEditText(et_contrasena, getString(R.string.field_is_required));

        if(usuario_b && contrasena_b){
            Cursor fila = db.rawQuery
                    ("select correo, contrasena from registro_control where correo = '"+usuario+"' and contrasena = '"+contrasena+"'" ,null);
            if(fila.moveToFirst()) {
                if(usuario.equals(fila.getString(0)) && contrasena.equals(fila.getString(1))){
                    Intent i = new Intent(Log_in.this, PrincipalMenuActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(this, "Usuario y/o Contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this, "No exite el registro", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }else {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}