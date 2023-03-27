package com.ochy.ochy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ochy.ochy.cod.User;
import com.ochy.ochy.cod.getSplittedPathChild;


public class ProfileFragment extends Fragment {

    TextView name, email;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    com.ochy.ochy.cod.getSplittedPathChild getSplittedPathChild = new getSplittedPathChild();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        init(view);
        viewData();
        return view;
    }

    private void init(View view){
        name = view.findViewById(R.id.fio);
        email = view.findViewById(R.id.email);
    }

    private void viewData(){
        FirebaseDatabase mDb = FirebaseDatabase.getInstance();
        DatabaseReference mRef = mDb.getReference("user");

        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                setText(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setText(DataSnapshot snapshot){
        getSplittedPathChild getSplittedPathChild = new getSplittedPathChild();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        User u =  snapshot.child(getSplittedPathChild.getSplittedPathChild(user.getEmail())).getValue(User.class);
        name.setText(u.name + " "+ u.surn);
        email.setText(u.email);
    }
}