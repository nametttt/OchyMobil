package com.ochy.ochy;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ochy.ochy.cod.ListView;
import com.ochy.ochy.cod.cardModel;
import com.ochy.ochy.cod.cardsDataList;
import com.ochy.ochy.cod.custom_spinner;
import com.ochy.ochy.cod.docDataList;
import com.ochy.ochy.cod.docsModel;
import com.ochy.ochy.cod.getSplittedPathChild;

import java.util.ArrayList;


public class BuyTicketFirstStepFragment extends Fragment {


    View pass_data1;
    custom_spinner passangers, newDocType;
    androidx.appcompat.widget.Toolbar mToolBar;
    EditText ps, citizen, number, surn, name, patron, birth;
    RadioGroup rd;
    LinearLayout ln;
    ArrayAdapter<String> docAdapter;
    String[] doc;
    View tick, docs_buy1;

    DatabaseReference db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_buy_ticket_first_step, container, false);
        init(v);
        return v;
    }

    private  void init (View v){
        docAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item);
        getSplittedPathChild getSplittedPathChild = new getSplittedPathChild();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseDatabase.getInstance().getReference("user").child( getSplittedPathChild.getSplittedPathChild(user.getEmail())).child("docs").getRef();
        tick = v.findViewById(R.id.tickets_view);

        Bundle args = getArguments();
        if (args != null) {
            String newText = args.getString("cost");
            String put = args.getString("marshr");
            String vr = args.getString("time");
            String pl = args.getString("free_places");
            String dur = args.getString("duration");

            updateTextView(newText, put, vr, pl, dur, tick);
        }
        pass_data1 = v.findViewById(R.id.docs_buy1);
        passangers = pass_data1.findViewById(R.id.passagers);
        ps = passangers.findViewById(R.id.spinnerEditText);
        ps.setHint("Пассажир");

        citizen = pass_data1.findViewById(R.id.docCitizen);
        newDocType = pass_data1.findViewById(R.id.custom_spinner);
        number = pass_data1.findViewById(R.id.docnumb);
        surn = pass_data1.findViewById(R.id.docSurn);
        name = pass_data1.findViewById(R.id.docName);
        patron = pass_data1.findViewById(R.id.docPatr);
        birth = pass_data1.findViewById(R.id.docBirth);
        rd = pass_data1.findViewById(R.id.radio);
        ln = pass_data1.findViewById(R.id.ln);


        ListView lst = passangers.findViewById(R.id.listview);
        mToolBar = v.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolBar);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Покупка авиабилета");
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable navIcon = mToolBar.getNavigationIcon();
        if (navIcon != null) {
            navIcon.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
            mToolBar.setNavigationIcon(navIcon);
        }
        setHasOptionsMenu(true);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.popBackStack();
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStack("flight",0);
                }
            }
        });
        addDataOnSpinner();
        spinnerSelectionNull();
    }

    public void updateTextView(String cost, String put, String vr, String pl, String dur, View v) {
        TextView textView = v.findViewById(R.id.cost);
        TextView marshr = v.findViewById(R.id.marshr);
        TextView time = v.findViewById(R.id.vremya);
        TextView free_places = v.findViewById(R.id.free_places);
        TextView duration = v.findViewById(R.id.durations);

        marshr.setText(put);
        textView.setText(cost);
        time.setText(vr);
        free_places.setText(pl);
        duration.setText(dur);
    }

    private void addDataOnSpinner(){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (docAdapter.getCount()>0) docAdapter.clear();
                docAdapter.add("Не выбрано");
                for (DataSnapshot ds: snapshot.getChildren()){
                    docsModel ps = ds.getValue(docsModel.class);
                    assert ps!=null;
                    String initials =ps.docSurname+" "+ ps.docName.charAt(0) + "." + ps.docPatronymic.charAt(0) + ".";
                    docAdapter.add(ps.docType+" "+ initials);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        db.addValueEventListener(valueEventListener);
        passangers.setAdapter(docAdapter);
    }


    private  void spinnerSelectionNull(){
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ps.getText().toString().isEmpty() || ps.getText().toString().equals("Не выбрано")){
                    citizen.setEnabled(true);
                    number.setEnabled(true);
                    surn.setEnabled(true);
                    name.setEnabled(true);
                    patron.setEnabled(true);
                    birth.setEnabled(true);
                    rd.setEnabled(true);
                    ln.setVisibility(View.VISIBLE);
                }
                else {
                    citizen.setEnabled(false);
                    number.setEnabled(false);
                    surn.setEnabled(false);
                    name.setEnabled(false);
                    patron.setEnabled(false);
                    birth.setEnabled(false);
                    rd.setEnabled(false);
                    ln.setVisibility(View.GONE);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        ps.addTextChangedListener(textWatcher);
    }

}