package com.ochy.ochy;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Arrays;
import java.util.List;


public class BuyTicketFirstStepFragment extends Fragment {

    View docs_buy1, docs_buy2, docs_buy3, docs_buy4, docs_buy5;
    custom_spinner passangers, newDocType, spin2, newDocType2, spin3, newDocType3, spin4,newDocType4, spin5, newDocType5;
    androidx.appcompat.widget.Toolbar mToolBar;
    EditText ps, citizen, number, surn, name, patron, birth;
    ArrayList<docsModel> docInstance = new ArrayList<docsModel>();
    int callCount = 0; // Счетчик вызовов getSelectedDocInstance()


    EditText ps2, citizen2, number2, surn2, name2, patron2, birth2;
    EditText ps3, citizen3, number3, surn3, name3, patron3, birth3;
    EditText ps4, citizen4, number4, surn4, name4, patron4, birth4;
    EditText ps5, citizen5, number5, surn5, name5, patron5, birth5;

    TextView passData1, passData2, passData3, passData4, passData5;

    RadioGroup rd , rd2, rd3, rd4, rd5;
    LinearLayout ln, ln2, ln3, ln4, ln5;

    String pass_numb, parentKey;
    ArrayAdapter<String> docAdapter;

    Button btn;
    int need=1;
    View tick;

    DatabaseReference db;

    Bundle args = new Bundle();




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_buy_ticket_first_step, container, false);
        init(v);
        return v;
    }

    private  void init (View v){
        btn = v.findViewById(R.id.addpass);
        docAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item);
        getSplittedPathChild getSplittedPathChild = new getSplittedPathChild();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseDatabase.getInstance().getReference("user").child( getSplittedPathChild.getSplittedPathChild(user.getEmail())).child("docs").getRef();
        tick = v.findViewById(R.id.tickets_view);
        int count =1;

        Bundle args = getArguments();
        if (args != null) {
            String newText = args.getString("cost");
            String put = args.getString("marshr");
            String vr = args.getString("time");
            String pl = args.getString("free_places");
            String dur = args.getString("duration");
            pass_numb = args.getString("pas");
            parentKey = args.getString("parentKey");
            count = Integer.parseInt(pass_numb.split(" ")[0]);

            updateTextView(newText, put, vr, pl, dur, tick);
        }
        docs_buy1 = v.findViewById(R.id.docs_buy1);
        docs_buy2 = v.findViewById(R.id.docs_buy2);
        passData1 = docs_buy1.findViewById(R.id.passData);
        docs_buy3 = v.findViewById(R.id.docs_buy3);
        docs_buy4 = v.findViewById(R.id.docs_buy4);
        docs_buy5 = v.findViewById(R.id.docs_buy5);

        passangers = docs_buy1.findViewById(R.id.passagers);
        ps = passangers.findViewById(R.id.spinnerEditText);
        ps.setHint("Пассажир");

        citizen = docs_buy1.findViewById(R.id.docCitizen);
        newDocType = docs_buy1.findViewById(R.id.custom_spinner);

        List<String> items = Arrays.asList("Паспорт", "Загран паспорт", "Свидетельство о рождении");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, items);
        newDocType.setAdapter(adapter);

        number = docs_buy1.findViewById(R.id.docnumb);
        surn = docs_buy1.findViewById(R.id.docSurn);
        name = docs_buy1.findViewById(R.id.docName);
        patron = docs_buy1.findViewById(R.id.docPatr);
        birth = docs_buy1.findViewById(R.id.docBirth);
        rd = docs_buy1.findViewById(R.id.radio);
        ln = docs_buy1.findViewById(R.id.ln);




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
        passTickets(count);
        addDataOnSpinner();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (need){
                    case 1:
                        if (!ps.getText().toString().isEmpty()|| !ps.getText().toString().equals("Не выбрано")){
                            getSelectedDocInstance(passangers.getSelectedItemPosition());
                            callCount++;
                        }
                        break;
                    case 2:
                        if (!ps.getText().toString().isEmpty()|| !ps.getText().toString().equals("Не выбрано")){
                            getSelectedDocInstance(passangers.getSelectedItemPosition());
                            callCount++;
                        }

                        if (!ps2.getText().toString().isEmpty()|| !ps2.getText().toString().equals("Не выбрано")){
                            getSelectedDocInstance(spin2.getSelectedItemPosition());
                            callCount++;
                        }
                        break;
                    case 3:
                        if (!ps.getText().toString().isEmpty()|| !ps.getText().toString().equals("Не выбрано")){
                            getSelectedDocInstance(passangers.getSelectedItemPosition());
                            callCount++;
                        }

                        if (!ps2.getText().toString().isEmpty()|| !ps2.getText().toString().equals("Не выбрано")){
                            getSelectedDocInstance(spin2.getSelectedItemPosition());
                            callCount++;
                        }

                        if (!ps3.getText().toString().isEmpty()|| !ps3.getText().toString().equals("Не выбрано")){
                            getSelectedDocInstance(spin3.getSelectedItemPosition());
                            callCount++;
                        }
                        break;
                    case 4:
                        if (!ps.getText().toString().isEmpty()|| !ps.getText().toString().equals("Не выбрано")){
                            getSelectedDocInstance(passangers.getSelectedItemPosition());
                            callCount++;
                        }

                        if (!ps2.getText().toString().isEmpty()|| !ps2.getText().toString().equals("Не выбрано")){
                            getSelectedDocInstance(spin2.getSelectedItemPosition());
                            callCount++;
                        }

                        if (!ps3.getText().toString().isEmpty()|| !ps3.getText().toString().equals("Не выбрано")){
                            getSelectedDocInstance(spin3.getSelectedItemPosition());
                            callCount++;
                        }

                        if (!ps4.getText().toString().isEmpty()|| !ps4.getText().toString().equals("Не выбрано")){
                            getSelectedDocInstance(spin4.getSelectedItemPosition());
                            callCount++;
                        }
                        break;
                    case 5:
                        if (!ps.getText().toString().isEmpty()|| !ps.getText().toString().equals("Не выбрано")){
                            getSelectedDocInstance(passangers.getSelectedItemPosition());
                            callCount++;
                        }

                        if (!ps2.getText().toString().isEmpty()|| !ps2.getText().toString().equals("Не выбрано")){
                            getSelectedDocInstance(spin2.getSelectedItemPosition());
                            callCount++;
                        }

                        if (!ps3.getText().toString().isEmpty()|| !ps3.getText().toString().equals("Не выбрано")){
                            getSelectedDocInstance(spin3.getSelectedItemPosition());
                            callCount++;
                        }

                        if (!ps4.getText().toString().isEmpty()|| !ps4.getText().toString().equals("Не выбрано")){
                            getSelectedDocInstance(spin4.getSelectedItemPosition());
                            callCount++;
                        }

                        if (!ps5.getText().toString().isEmpty()|| !ps5.getText().toString().equals("Не выбрано")){
                            getSelectedDocInstance(spin5.getSelectedItemPosition());
                            callCount++;
                        }
                        break;
                }



            }
        });
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
        if (spin2!=null){
            spin2.setAdapter(docAdapter);

        }

        if (spin3!=null){
            spin3.setAdapter(docAdapter);

        }

        if (spin4!=null){
            spin4.setAdapter(docAdapter);

        }

        if (spin5!=null){
            spin5.setAdapter(docAdapter);

        }
    }


    private  void spinnerSelectionNull(EditText edit, EditText cit, EditText nmb, EditText sur, EditText nam, EditText patro
        , EditText birt, RadioGroup rad, LinearLayout layout ){
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edit.getText().toString().isEmpty() || edit.getText().toString().equals("Не выбрано")){
                    cit.setEnabled(true);
                    nmb.setEnabled(true);
                    sur.setEnabled(true);
                    nam.setEnabled(true);
                    patro.setEnabled(true);
                    birt.setEnabled(true);
                    rad.setEnabled(true);
                    layout.setVisibility(View.VISIBLE);
                }
                else {
                    cit.setEnabled(false);
                    nmb.setEnabled(false);
                    sur.setEnabled(false);
                    nam.setEnabled(false);
                    patro.setEnabled(false);
                    birt.setEnabled(false);
                    rad.setEnabled(false);
                    layout.setVisibility(View.GONE);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        ps.addTextChangedListener(textWatcher);
        if (ps2!=null){
            ps2.addTextChangedListener(textWatcher);
        }

        if (ps3!=null){
            ps3.addTextChangedListener(textWatcher);
        }

        if (ps4!=null){
            ps4.addTextChangedListener(textWatcher);
        }

        if (ps5!=null){
            ps5.addTextChangedListener(textWatcher);
        }
    }


    private void passTickets(int count){

        List<String>         items =  Arrays.asList("Паспорт", "Загран паспорт", "Свидетельство о рождении");

        ArrayAdapter<String>         adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, items);
        switch (count){

            case 1:
                spinnerSelectionNull(ps, citizen, number, surn, name, patron, birth, rd , ln);
                need =1;
                break;
            case 2:
                docs_buy2.setVisibility(View.VISIBLE);
                spin2 = docs_buy2.findViewById(R.id.passagers);
                need =2;


                ps2 = spin2.findViewById(R.id.spinnerEditText);
                ps2.setHint("Пассажир");

                citizen2 = docs_buy2.findViewById(R.id.docCitizen);
                newDocType = docs_buy2.findViewById(R.id.custom_spinner);
                passData2 = docs_buy2.findViewById(R.id.passData);
                passData1.setText("Данные пассажира 1");
                passData2.setText("Данные пассажира 2");
                number2 = docs_buy2.findViewById(R.id.docnumb);
                surn2 = docs_buy2.findViewById(R.id.docSurn);
                name2 = docs_buy2.findViewById(R.id.docName);
                patron2 = docs_buy2.findViewById(R.id.docPatr);
                birth2 = docs_buy2.findViewById(R.id.docBirth);
                rd2 = docs_buy2.findViewById(R.id.radio);
                ln2 = docs_buy2.findViewById(R.id.ln);


                newDocType2 = docs_buy2.findViewById(R.id.custom_spinner);
                newDocType2.setAdapter(adapter);

                spinnerSelectionNull(ps, citizen, number, surn, name, patron, birth, rd , ln);
                spinnerSelectionNull(ps2 , citizen2 ,number2, surn2, name2, patron2, birth2, rd2, ln2 );

                break;
            case 3:
                need =3;

                docs_buy2.setVisibility(View.VISIBLE);
                docs_buy3.setVisibility(View.VISIBLE);

                spin2 = docs_buy2.findViewById(R.id.passagers);
                ps2 = spin2.findViewById(R.id.spinnerEditText);
                ps2.setHint("Пассажир");



                spin3 = docs_buy3.findViewById(R.id.passagers);
                ps3 = spin3.findViewById(R.id.spinnerEditText);
                ps3.setHint("Пассажир");

                citizen2 = docs_buy2.findViewById(R.id.docCitizen);
                citizen3 = docs_buy3.findViewById(R.id.docCitizen);

                passData2 = docs_buy2.findViewById(R.id.passData);
                passData3 = docs_buy3.findViewById(R.id.passData);

                passData1.setText("Данные пассажира 1");
                passData2.setText("Данные пассажира 2");
                passData3.setText("Данные пассажира 3");

                number2 = docs_buy2.findViewById(R.id.docnumb);
                number3 = docs_buy3.findViewById(R.id.docnumb);

                surn2 = docs_buy2.findViewById(R.id.docSurn);
                surn3 = docs_buy3.findViewById(R.id.docSurn);

                name2 = docs_buy2.findViewById(R.id.docName);
                name3 = docs_buy3.findViewById(R.id.docName);

                patron2 = docs_buy2.findViewById(R.id.docPatr);
                patron3 = docs_buy3.findViewById(R.id.docPatr);

                birth2 = docs_buy2.findViewById(R.id.docBirth);
                birth3 = docs_buy3.findViewById(R.id.docBirth);

                rd2 = docs_buy2.findViewById(R.id.radio);
                rd3 = docs_buy3.findViewById(R.id.radio);

                ln2 = docs_buy2.findViewById(R.id.ln);
                ln3 = docs_buy3.findViewById(R.id.ln);



                newDocType2 = docs_buy2.findViewById(R.id.custom_spinner);
                newDocType2.setAdapter(adapter);
                newDocType3 = docs_buy3.findViewById(R.id.custom_spinner);
                newDocType3.setAdapter(adapter);

                spinnerSelectionNull(ps, citizen, number, surn, name, patron, birth, rd , ln);
                spinnerSelectionNull(ps2 , citizen2 ,number2, surn2, name2, patron2, birth2, rd2, ln2 );
                spinnerSelectionNull(ps3, citizen3, number3, surn3, name3, patron3, birth3, rd3 , ln3);

                break;

            case 4:
                need =4;
                docs_buy2.setVisibility(View.VISIBLE);
                docs_buy3.setVisibility(View.VISIBLE);
                docs_buy4.setVisibility(View.VISIBLE);

                spin2 = docs_buy2.findViewById(R.id.passagers);
                ps2 = spin2.findViewById(R.id.spinnerEditText);
                ps2.setHint("Пассажир");

                spin3 = docs_buy3.findViewById(R.id.passagers);
                ps3 = spin3.findViewById(R.id.spinnerEditText);
                ps3.setHint("Пассажир");

                spin4 = docs_buy4.findViewById(R.id.passagers);
                ps4 = spin4.findViewById(R.id.spinnerEditText);
                ps4.setHint("Пассажир");

                citizen2 = docs_buy2.findViewById(R.id.docCitizen);
                citizen3 = docs_buy3.findViewById(R.id.docCitizen);
                citizen4 = docs_buy4.findViewById(R.id.docCitizen);

                passData2 = docs_buy2.findViewById(R.id.passData);
                passData3 = docs_buy3.findViewById(R.id.passData);
                passData4 = docs_buy4.findViewById(R.id.passData);

                passData1.setText("Данные пассажира 1");
                passData2.setText("Данные пассажира 2");
                passData3.setText("Данные пассажира 3");
                passData4.setText("Данные пассажира 4");

                number2 = docs_buy2.findViewById(R.id.docnumb);
                number3 = docs_buy3.findViewById(R.id.docnumb);
                number4 = docs_buy4.findViewById(R.id.docnumb);

                surn2 = docs_buy2.findViewById(R.id.docSurn);
                surn3 = docs_buy3.findViewById(R.id.docSurn);
                surn4 = docs_buy4.findViewById(R.id.docSurn);

                name2 = docs_buy2.findViewById(R.id.docName);
                name3 = docs_buy3.findViewById(R.id.docName);
                name4 = docs_buy4.findViewById(R.id.docName);

                patron2 = docs_buy2.findViewById(R.id.docPatr);
                patron3 = docs_buy3.findViewById(R.id.docPatr);
                patron4 = docs_buy4.findViewById(R.id.docPatr);

                birth2 = docs_buy2.findViewById(R.id.docBirth);
                birth3 = docs_buy3.findViewById(R.id.docBirth);
                birth4 = docs_buy4.findViewById(R.id.docBirth);

                rd2 = docs_buy2.findViewById(R.id.radio);
                rd3 = docs_buy3.findViewById(R.id.radio);
                rd4 = docs_buy4.findViewById(R.id.radio);

                ln2 = docs_buy2.findViewById(R.id.ln);
                ln3 = docs_buy3.findViewById(R.id.ln);
                ln4 = docs_buy4.findViewById(R.id.ln);

                newDocType2 = docs_buy2.findViewById(R.id.custom_spinner);
                newDocType2.setAdapter(adapter);
                newDocType3 = docs_buy3.findViewById(R.id.custom_spinner);
                newDocType3.setAdapter(adapter);
                newDocType4 = docs_buy4.findViewById(R.id.custom_spinner);
                newDocType4.setAdapter(adapter);

                spinnerSelectionNull(ps, citizen, number, surn, name, patron, birth, rd , ln);
                spinnerSelectionNull(ps2 , citizen2 ,number2, surn2, name2, patron2, birth2, rd2, ln2 );
                spinnerSelectionNull(ps3, citizen3, number3, surn3, name3, patron3, birth3, rd3 , ln3);
                spinnerSelectionNull(ps4, citizen4, number4, surn4, name4, patron4, birth4, rd4 , ln4);

                break;
            case 5:
                need =5;
                docs_buy2.setVisibility(View.VISIBLE);
                docs_buy3.setVisibility(View.VISIBLE);
                docs_buy4.setVisibility(View.VISIBLE);
                docs_buy5.setVisibility(View.VISIBLE);

                spin2 = docs_buy2.findViewById(R.id.passagers);
                ps2 = spin2.findViewById(R.id.spinnerEditText);
                ps2.setHint("Пассажир");

                spin3 = docs_buy3.findViewById(R.id.passagers);
                ps3 = spin3.findViewById(R.id.spinnerEditText);
                ps3.setHint("Пассажир");

                spin4 = docs_buy4.findViewById(R.id.passagers);
                ps4 = spin4.findViewById(R.id.spinnerEditText);
                ps4.setHint("Пассажир");

                spin5 = docs_buy5.findViewById(R.id.passagers);
                ps5 = spin5.findViewById(R.id.spinnerEditText);
                ps5.setHint("Пассажир");

                citizen2 = docs_buy2.findViewById(R.id.docCitizen);
                citizen3 = docs_buy3.findViewById(R.id.docCitizen);
                citizen4 = docs_buy4.findViewById(R.id.docCitizen);
                citizen5 = docs_buy5.findViewById(R.id.docCitizen);

                passData2 = docs_buy2.findViewById(R.id.passData);
                passData3 = docs_buy3.findViewById(R.id.passData);
                passData4 = docs_buy4.findViewById(R.id.passData);
                passData5 = docs_buy5.findViewById(R.id.passData);

                passData1.setText("Данные пассажира 1");
                passData2.setText("Данные пассажира 2");
                passData3.setText("Данные пассажира 3");
                passData4.setText("Данные пассажира 4");
                passData5.setText("Данные пассажира 5");

                number2 = docs_buy2.findViewById(R.id.docnumb);
                number3 = docs_buy3.findViewById(R.id.docnumb);
                number4 = docs_buy4.findViewById(R.id.docnumb);
                number5 = docs_buy5.findViewById(R.id.docnumb);

                surn2 = docs_buy2.findViewById(R.id.docSurn);
                surn3 = docs_buy3.findViewById(R.id.docSurn);
                surn4 = docs_buy4.findViewById(R.id.docSurn);
                surn5 = docs_buy5.findViewById(R.id.docSurn);

                name2 = docs_buy2.findViewById(R.id.docName);
                name3 = docs_buy3.findViewById(R.id.docName);
                name4 = docs_buy4.findViewById(R.id.docName);
                name5 = docs_buy5.findViewById(R.id.docName);

                patron2 = docs_buy2.findViewById(R.id.docPatr);
                patron3 = docs_buy3.findViewById(R.id.docPatr);
                patron4 = docs_buy4.findViewById(R.id.docPatr);
                patron5 = docs_buy5.findViewById(R.id.docPatr);

                birth2 = docs_buy2.findViewById(R.id.docBirth);
                birth3 = docs_buy3.findViewById(R.id.docBirth);
                birth4 = docs_buy4.findViewById(R.id.docBirth);
                birth5 = docs_buy5.findViewById(R.id.docBirth);

                rd2 = docs_buy2.findViewById(R.id.radio);
                rd3 = docs_buy3.findViewById(R.id.radio);
                rd4 = docs_buy4.findViewById(R.id.radio);
                rd5 = docs_buy5.findViewById(R.id.radio);

                ln2 = docs_buy2.findViewById(R.id.ln);
                ln3 = docs_buy3.findViewById(R.id.ln);
                ln4 = docs_buy4.findViewById(R.id.ln);
                ln5 = docs_buy5.findViewById(R.id.ln);

                spinnerSelectionNull(ps, citizen, number, surn, name, patron, birth, rd , ln);
                spinnerSelectionNull(ps2 , citizen2 ,number2, surn2, name2, patron2, birth2, rd2, ln2 );
                spinnerSelectionNull(ps3, citizen3, number3, surn3, name3, patron3, birth3, rd3 , ln3);
                spinnerSelectionNull(ps4, citizen4, number4, surn4, name4, patron4, birth4, rd4 , ln4);
                spinnerSelectionNull(ps5, citizen5, number5, surn5, name5, patron5, birth5, rd5 , ln5);

                newDocType2 = docs_buy2.findViewById(R.id.custom_spinner);
                newDocType2.setAdapter(adapter);
                newDocType3 = docs_buy3.findViewById(R.id.custom_spinner);
                newDocType3.setAdapter(adapter);
                newDocType4 = docs_buy4.findViewById(R.id.custom_spinner);
                newDocType4.setAdapter(adapter);
                newDocType5 = docs_buy4.findViewById(R.id.custom_spinner);
                newDocType5.setAdapter(adapter);
                break;
        }
    }

    private  void getSelectedDocInstance(int selectedPosition) {
        DatabaseReference selectedCardRef = db;
        ArrayList<docsModel> currentDocInstance = new ArrayList<>(); // Список для текущего вызова
        selectedCardRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count =1;
                for (DataSnapshot ds : snapshot.getChildren()) {

                    docsModel selectedDoc = ds.getValue(docsModel.class);
                    if (selectedDoc != null && count ==selectedPosition) {
                        currentDocInstance.add(selectedDoc);
                    }
                    count++;
                }
                docInstance.addAll(currentDocInstance);

                if (callCount == docInstance.size()) {
                    // Добавление docInstance в args
                    if (!docInstance.isEmpty()) {


                        args.putParcelableArrayList("dssss", docInstance);

                        BuyTicketSeconStepFragment buyTicketSeconStepFragment = new BuyTicketSeconStepFragment();
                        FragmentManager fragmentManager = getParentFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.framelayout, buyTicketSeconStepFragment);
                        fragmentTransaction.addToBackStack("fl"); // Добавляем в Back Stack

                        args.putString("parentKey", parentKey);
                        args.putInt("pasesr", Integer.parseInt(pass_numb.split(" ")[0]));
                        buyTicketSeconStepFragment.setArguments(args);

                        fragmentTransaction.commit();
                        callCount =0;
                    }

                    // Здесь вы можете продолжить выполнение дополнительной логики после полного выполнения всех вызовов getSelectedDocInstance()
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }


}