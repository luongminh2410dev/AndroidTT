package com.example.shokke_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private  FirebaseAuth mAuth;
    EditText edtUser, edtPass;
    Button   btnLogin, btnSignUp;
    CheckBox cbSave;
    String   saveInfo = "log-in";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        init();
        addEvent();
    }

    private void addEvent() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edtUser.getText().toString();
                String pass = edtPass.getText().toString();
                checkLogin(user, pass);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        edtUser   = findViewById(R.id.edtUserName);
        edtPass   = findViewById(R.id.edtPassword);
        btnLogin  = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);
        cbSave    = findViewById(R.id.checkBox);
    }

    private void checkLogin(String user, String pass){
        mAuth.signInWithEmailAndPassword(user, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("USERNAME", user.getEmail());
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences preferences = getSharedPreferences(saveInfo, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("Save", cbSave.isChecked());
        editor.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        // If checkbox is checked, save current account
        SharedPreferences preferences = getSharedPreferences(saveInfo, MODE_PRIVATE);
        Boolean save = preferences.getBoolean("Save", false);
        if(save)
        {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if(currentUser != null){
                Log.e("Nofiti", "Da dang nhap");
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("USERNAME", currentUser.getEmail());
                startActivity(intent);
            }
        }
    }

}