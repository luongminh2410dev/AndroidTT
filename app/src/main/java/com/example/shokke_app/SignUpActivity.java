package com.example.shokke_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    private  FirebaseAuth mAuth;
    EditText edtUser, edtPass1, edtPass2;
    Button   btnRegist, btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        init();
        addEvent();
    }

    private void addEvent() {
        //    Sign up
        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUser.getText().toString();
                String password = edtPass1.getText().toString();
                if(username.isEmpty() || password.isEmpty() || edtPass2.getText().toString().isEmpty())
                {
                    Toast.makeText(SignUpActivity.this, "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                if(!password.equals(edtPass2.getText().toString()))
                {
                    Toast.makeText(SignUpActivity.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                }
                else {
                    handleRegister(username, password);
                }
            }
        });
        //    Back to login
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void handleRegister(String username, String password){
        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(SignUpActivity.this, "Account is created!", Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void init() {
        edtUser   = findViewById(R.id.edtNewUser);
        edtPass1  = findViewById(R.id.edtPassword1);
        edtPass2  = findViewById(R.id.edtPassword2);
        btnRegist = findViewById(R.id.btnRegist);
        btnBack   = findViewById(R.id.btnBackLogin);
    }
}