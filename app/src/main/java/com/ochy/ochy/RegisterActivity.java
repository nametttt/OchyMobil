package com.ochy.ochy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ochy.ochy.cod.CustomprogressBar;
import com.ochy.ochy.cod.User;
import com.ochy.ochy.cod.getEmail;
import com.ochy.ochy.cod.getSplittedPathChild;


public class RegisterActivity extends AppCompatActivity {

    private EditText fio, email, pass, reppass;
    private ProgressBar progressBar;
    ProgressDialog pd;
    private CheckBox checkBox;
    private Button button;
    private FirebaseAuth mAuth;
    private String splittedPathChild;
    ViewGroup progressView;
    private TextView txt;
    protected boolean isProgressShowing = false;
    CustomprogressBar pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    public void onRegister(View v){
        //hide keyboard
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        FirebaseDatabase mDb = FirebaseDatabase.getInstance();
        DatabaseReference ref = mDb.getReference("user");

        if (fio.getText().toString().isEmpty() || email.getText().toString().isEmpty()
                || pass.getText().toString().isEmpty() ||reppass.getText().toString().isEmpty()){
            Toast.makeText(this, "Пожалуйста, заполните все поля!", Toast.LENGTH_SHORT).show();
        }
        else if (fio.getText().length()<3){
            Toast.makeText(this, "Пожалуйста, введите корректное ФИО", Toast.LENGTH_SHORT).show();
        }
        else if (!getEmail.isValidEmail(email.getText())){
            Toast.makeText(this, "Пожалуйста, введите корректную почту", Toast.LENGTH_SHORT).show();
        }
        else if (pass.getText().toString().length() <6){
            Toast.makeText(this, "Пароль слишком короткий", Toast.LENGTH_SHORT).show();
        }
        else if (!pass.getText().toString().equals(reppass.getText().toString())){
            Toast.makeText(this, "Введённые пароль не совпадают", Toast.LENGTH_SHORT).show();
        }

        else{

                    showProgressingView();
                    mAuth.createUserWithEmailAndPassword(email.getText().toString() , pass.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    hideProgressingView();
                                    if (task.isSuccessful()){
                                        getSplittedPathChild pC = new getSplittedPathChild();
                                        splittedPathChild = pC.getSplittedPathChild(email.getText().toString());
                                        String emails = email.getText().toString();
                                        String passww = pass.getText().toString();
                                        String fios = fio.getText().toString();
                                        String surn = fios.split(" ")[0];
                                        String name = fios.split(" ")[1];
                                        String patron = fios.split(" ")[2];
                                        User user = new User(emails, passww, surn, name, patron, "");
                                        ref.child(splittedPathChild).setValue(user);
                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                RegisterActivity.this.finish();
                                    }
                                    else{
                                        try {
                                            throw task.getException();
                                        } catch(FirebaseAuthWeakPasswordException e) {
                                            Toast.makeText(RegisterActivity.this, "Пароль слишком прост", Toast.LENGTH_SHORT).show();

                                        } catch(FirebaseAuthInvalidCredentialsException e) {
                                            Toast.makeText(RegisterActivity.this, "Неверный email", Toast.LENGTH_SHORT).show();

                                        } catch(FirebaseAuthUserCollisionException e) {
                                            Toast.makeText(RegisterActivity.this, "Пользователь существует", Toast.LENGTH_SHORT).show();

                                        } catch(Exception e) {

                                        }
                                    }

                                }
                            });


//
        }
    }

    public void tanya(View v){
        if (!checkBox.isChecked()){
            button.setClickable(false);
            button.setBackgroundColor(getColor(R.color.gray));
        }
        else{
            button.setBackground(getDrawable(R.drawable.gradient_button));
            button.setClickable(true);
        }

    }

    public void  init(){
        fio = findViewById(R.id.fio);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        reppass = findViewById(R.id.reppass);
        button = findViewById(R.id.button);
        checkBox = findViewById(R.id.checkbox);
        mAuth =  FirebaseAuth.getInstance();
        txt = findViewById(R.id.textView6);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, loginActivity.class);
                startActivity(intent);
                RegisterActivity.this.finish();
            }
        });
       pb = new CustomprogressBar(RegisterActivity.this);
    }

    public void showProgressingView() {
            isProgressShowing = true;
            progressView = (ViewGroup) getLayoutInflater().inflate(R.layout.progrssbar_layout, null);
           View v = this.findViewById(android.R.id.content).getRootView();
            ViewGroup viewGroup = (ViewGroup) v;
            viewGroup.addView(progressView);
    }
    public void hideProgressingView() {

        View v =  getWindow().getDecorView().getRootView();
        v.requestFocus();
        ViewGroup viewGroup = (ViewGroup) v;
        viewGroup.removeView(progressView);
       viewGroup.requestFocus();
    }
}