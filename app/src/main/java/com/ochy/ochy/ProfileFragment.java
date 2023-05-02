package com.ochy.ochy;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
    SharedPreferences sPref;

    MyCabFragment myCabFragment = new MyCabFragment();
    CardFragment cardFragment = new CardFragment();
    final String SAVED_DATA = "FIO_MAIL";
    String fio, myMail;
    TextView name, email, lk, card;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

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
        String data = sPref.getString(SAVED_DATA, "");
        if (data != ""){
            String[] tanushaYakovleva = data.split(" ");
            name.setText(tanushaYakovleva[0] + " "+ tanushaYakovleva[1]);
            email.setText(tanushaYakovleva[2]);
        }
        else {
            viewData();
        }
        lk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tanusha( "cab", myCabFragment);
            }
        });
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager= getChildFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.ft, new CardFragment());
                fragmentTransaction.addToBackStack("profile");
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    private void init(View view){
        card = view.findViewById(R.id.card);
        sPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        name = view.findViewById(R.id.fio);
        email = view.findViewById(R.id.email);
        lk = view.findViewById(R.id.lk);
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
        SharedPreferences.Editor ed = sPref.edit();

        User u =  snapshot.child(getSplittedPathChild.getSplittedPathChild(user.getEmail())).getValue(User.class);
        ed.putString(SAVED_DATA, u.name + " "+ u.surn + " " + u.email);
        name.setText(u.name + " "+ u.surn);
        email.setText(u.email);
    }

    public  void tanusha(String tag, Fragment fr){
        ((MainActivity)getActivity()).pushFragments(tag, fr);
    }
}