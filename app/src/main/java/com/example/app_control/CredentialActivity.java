package com.example.app_control;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app_control.utils.InputValidation;

public class CredentialActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonCompose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credential);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonCompose = findViewById(R.id.buttonCompose);

        buttonCompose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (InputValidation.isValidEditText(editTextEmail, getString(R.string.field_is_required))
                        && InputValidation.isValidEditText(editTextPassword, getString(R.string.field_is_required))) {
                    redirectToMainActivity();
                }
            }
        });
    }

    private void redirectToMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("recipientEmail", editTextEmail.getText().toString());
        intent.putExtra("recipientPassword", editTextPassword.getText().toString());
        startActivity(intent);
    }
}