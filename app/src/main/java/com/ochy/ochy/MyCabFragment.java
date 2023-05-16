package com.ochy.ochy;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ochy.ochy.cod.User;
import com.ochy.ochy.cod.getSplittedPathChild;
import com.ochy.ochy.dialog.deleteDialog;

import java.util.Map;

public class MyCabFragment extends Fragment {
    androidx.appcompat.widget.Toolbar mToolBar;
    androidx.appcompat.widget.AppCompatButton btn, btnDel;
    FirebaseUser user;
    private String splittedPathChild;
    private EditText ed1, ed2, patr, email, tel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_cab, container, false);
        init(v);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = db.getReference("user");
        getSplittedPathChild pC = new getSplittedPathChild();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user1 = snapshot.child(pC.getSplittedPathChild(user.getEmail())).getValue(User.class);
                ed1.setText(user1.surn);
                ed2.setText(user1.name);
                patr.setText(user1.patronomyc);
                email.setText(user1.email);
                if (user1.tel == ""){
                    return;
                }
                tel.setText(user1.tel);
                //ref.child(pC.getSplittedPathChild(user.getEmail())).child("acc").updateChildren(map);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference ref = db.getReference("user");
                getSplittedPathChild pC = new getSplittedPathChild();

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user1 = snapshot.child(pC.getSplittedPathChild(user.getEmail())).getValue(User.class);
                        Map<String, Object> map = user1.toMap();
                        map.put("surn",ed1.getText().toString());
                        map.put("name",ed2.getText().toString());
                        map.put("patronomyc",patr.getText().toString());
                        map.put("tel", tel.getText().toString());
                        Toast.makeText(getActivity(), "Данные изменены", Toast.LENGTH_SHORT).show();
                        ref.child(pC.getSplittedPathChild(user.getEmail())).updateChildren(map);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog myDialogFragment = new deleteDialog();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                //myDialogFragment.show(manager, "dialog");

                FragmentTransaction transaction = manager.beginTransaction();
                myDialogFragment.show(transaction, "dialog");
            }
        });
        return v;
    }

    private void init (View v){
        ed1 = v.findViewById(R.id.surn);
        ed2 = v.findViewById(R.id.name);
        patr = v.findViewById(R.id.patr);
        email = v.findViewById(R.id.poch);
        tel = v.findViewById(R.id.tel);
        btn = v.findViewById(R.id.saveData);
        btnDel = v.findViewById(R.id.tanusha);
        mToolBar = v.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolBar);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Личный кабинет");
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable navIcon = mToolBar.getNavigationIcon();
        if (navIcon != null) {
            navIcon.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
            mToolBar.setNavigationIcon(navIcon);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            ProfileFragment pf = new ProfileFragment();
            ((MainActivity) getActivity()).pushFragments("profile", pf);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

}