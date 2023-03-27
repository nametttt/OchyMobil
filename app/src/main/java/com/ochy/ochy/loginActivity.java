package com.ochy.ochy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ochy.ochy.cod.getEmail;

public class loginActivity extends AppCompatActivity {

    TextView txt;
    EditText email, password;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init(){
        txt = findViewById(R.id.textView6);
        email = findViewById(R.id.email);
        password = findViewById(R.id.pass);
        mAuth = FirebaseAuth.getInstance();

    }

    public void clickedLogin(View v){
        if (!getEmail.isValidEmail(email.getText())){
            Toast.makeText(this, "Пожалуйста, введите корректную почту", Toast.LENGTH_SHORT).show();
            return;
        }
        //hide keyboard
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

        if (email.getText().toString().isEmpty() ||
                password.getText().toString().isEmpty()){
            Toast.makeText(this, "Вы ввели не все данные", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
                enterUser();
        }
    }

    public void txtclicked(View view){
        Intent x = new Intent(loginActivity.this, resetPasswordActivity.class);
        startActivity(x);
        finish();
    }

    public void enterUser(){
            mAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (task.isSuccessful()) {
                                assert user != null;
                                Intent x = new Intent(loginActivity.this, MainActivity.class);
                                startActivity(x);
                                finish();
                            }
                            else
                                Toast.makeText(loginActivity.this, "Что-то пошло не так!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }


}