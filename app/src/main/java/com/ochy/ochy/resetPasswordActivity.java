package com.ochy.ochy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.ochy.ochy.cod.getEmail;

public class resetPasswordActivity extends AppCompatActivity {

    EditText email;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        init();
    }
    private void init(){
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
    }

    public void onClick(View v){
        if (!getEmail.isValidEmail(email.getText())){
            Toast.makeText(this, "Пожалуйста, введите корректную почту", Toast.LENGTH_SHORT).show();
            return;
        }
        if (email.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Пожалуйста, введите  почту", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(resetPasswordActivity.this, "Письмо с инструкцией отправлено на почту!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(resetPasswordActivity.this, loginActivity.class);
                    startActivity(intent);
                    resetPasswordActivity.this.finish();
                }
                else{
                    switch (task.getException().toString()){
                        case "FirebaseAuthInvalidUserException":
                            Toast.makeText(resetPasswordActivity.this, "Аккаунта с такой почтой не существует", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }
        });
    }
}