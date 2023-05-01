package com.ochy.ochy;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ochy.ochy.cod.cardModel;
import com.ochy.ochy.cod.getSplittedPathChild;

import java.util.UUID;

public class AddCardFragment extends Fragment {

    androidx.appcompat.widget.Toolbar mToolBar;
    androidx.appcompat.widget.AppCompatButton btn;
    private String splittedPathChild;
    private EditText name, numb, date, cvc, man;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_card, container, false);
        init(v);
        return v;
    }


    private void init (View v){
        name = v.findViewById(R.id.cardname);
        numb = v.findViewById(R.id.cardnum);
        date = v.findViewById(R.id.carddate);
        cvc = v.findViewById(R.id.cardcvc);
        man = v.findViewById(R.id.cardman);

        btn = v.findViewById(R.id.add);
        mToolBar = v.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolBar);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Добавить карту");
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.popBackStack();
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStack("card",0);
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                getSplittedPathChild pC = new getSplittedPathChild();
                splittedPathChild = pC.getSplittedPathChild(user.getEmail());
                String tableName = UUID.randomUUID().toString();

                DatabaseReference db = FirebaseDatabase.getInstance().getReference("user").child(splittedPathChild).child("cards").child(tableName).getRef();
                cardModel card = new cardModel(name.getText().toString(), numb.getText().toString(),
                        date.getText().toString(),
                        cvc.getText().toString(), man.getText().toString());
                db.setValue(card);
                Toast.makeText(getActivity(), "Карта успешно добавлена!", Toast.LENGTH_SHORT).show();

                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.popBackStack();
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStack("card",0);
                }
            }
        });
    }





}