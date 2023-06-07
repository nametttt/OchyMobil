package com.ochy.ochy;

import static android.content.ContentValues.TAG;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ochy.ochy.cod.User;
import com.ochy.ochy.cod.cardsDataList;
import com.ochy.ochy.cod.getSplittedPathChild;
import com.ochy.ochy.dialog.deleteDialog;

import java.util.ArrayList;
import java.util.Map;


public class reset_password extends Fragment {
    androidx.appcompat.widget.Toolbar mToolBar;
    MainActivity mainActivity;
    Button btn;
    EditText oldPass,newPass,repPass;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reset_password, container, false);
        init(v);
        return v;
    }

    private void init (View v){
        oldPass = v.findViewById(R.id.ed1);
        newPass = v.findViewById(R.id.ed3);
        repPass = v.findViewById(R.id.ed4);

        mToolBar = v.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolBar);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Изменение пароля");
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable navIcon = mToolBar.getNavigationIcon();
        if (navIcon != null) {
            navIcon.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
            mToolBar.setNavigationIcon(navIcon);
        }
        setHasOptionsMenu(true);
        mainActivity = (MainActivity)getActivity();
        btn = v.findViewById(R.id.saveData);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (newPass.length()<=6){
                    Toast.makeText(mainActivity, "Длина пароля должна быть более 6 символов!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!newPass.getText().toString().equals(repPass.getText().toString())){
                    Toast.makeText(mainActivity, "Пароли не совпадают!", Toast.LENGTH_SHORT).show();
                    return;
                }
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser user = auth.getCurrentUser();

                String newPassword = newPass.getText().toString();

                if (user != null) {
                    // Запрашиваем повторную аутентификацию пользователя
                    AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), oldPass.getText().toString());
                    user.reauthenticate(credential)
                            .addOnCompleteListener(reauthTask -> {
                                if (reauthTask.isSuccessful()) {
                                    // Пользователь успешно повторно аутентифицирован, теперь можно изменить пароль
                                    user.updatePassword(newPassword)
                                            .addOnCompleteListener(updateTask -> {
                                                if (updateTask.isSuccessful()) {

                                                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                                                    DatabaseReference ref = db.getReference("user");
                                                    getSplittedPathChild pC = new getSplittedPathChild();
                                                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                            User user1 = snapshot.child(pC.getSplittedPathChild(user.getEmail())).getValue(User.class);
                                                            Map<String, Object> map = user1.toMap();
                                                            map.put("password",newPassword);
                                                            Toast.makeText(getActivity(), "Данные изменены", Toast.LENGTH_SHORT).show();
                                                            ref.child(pC.getSplittedPathChild(user.getEmail())).updateChildren(map);
                                                            //ref.child(pC.getSplittedPathChild(user.getEmail())).child("acc").updateChildren(map);
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                        }
                                                    });
                                                    // Пароль пользователя успешно изменен
                                                    Log.d(TAG, "Пароль пользователя успешно изменен.");
                                                    Toast.makeText(mainActivity, "Пароль успешно изменён!", Toast.LENGTH_SHORT).show();
                                                    oldPass.getText().clear();
                                                    newPass.getText().clear();
                                                    repPass.getText().clear();
                                                } else {
                                                    // Произошла ошибка при изменении пароля
                                                    Toast.makeText(mainActivity, "Произошла непредвиденная ошибка!", Toast.LENGTH_SHORT).show();

                                                    Log.e(TAG, "Ошибка при изменении пароля пользователя: " + updateTask.getException().getMessage());
                                                }
                                            });
                                } else {
                                    // Произошла ошибка при повторной аутентификации пользователя
                                    Log.e(TAG, "Ошибка при повторной аутентификации пользователя: " + reauthTask.getException().getMessage());
                                }
                            });
                }



            }
        });
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.popBackStack();
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStack("profile",0);
                }
            }
        });
    }




}