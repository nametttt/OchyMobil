package com.ochy.ochy;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ochy.ochy.cod.User;
import com.ochy.ochy.cod.cardModel;
import com.ochy.ochy.cod.cardsDataList;
import com.ochy.ochy.cod.docDataList;
import com.ochy.ochy.cod.docsModel;
import com.ochy.ochy.cod.getSplittedPathChild;

import java.util.ArrayList;


public class ProfileFragment extends Fragment {
    SharedPreferences sPref;
    MyCabFragment myCabFragment = new MyCabFragment();
    final String SAVED_DATA = "FIO_MAIL";
    TextView name, email, lk, card, reset;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    Button btn;
    private boolean isDataLoaded = false;

    DatabaseReference db;
    ArrayList<docDataList> arrayList;
    private  docAdapterListView list;
    private com.ochy.ochy.cod.ListView listView;
    MainActivity mainActivity;
    TextView text;

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


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager= getChildFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.ft, new reset_password());
                fragmentTransaction.addToBackStack("profile");
                fragmentTransaction.commit();
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
        getSplittedPathChild getSplittedPathChild = new getSplittedPathChild();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        card = view.findViewById(R.id.card);
        sPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        name = view.findViewById(R.id.fio);
        email = view.findViewById(R.id.email);
        lk = view.findViewById(R.id.lk);
        text =  view.findViewById(R.id.text);
        reset = view.findViewById(R.id.reset);
        btn = view.findViewById(R.id.addpass);
        db = FirebaseDatabase.getInstance().getReference("user").child( getSplittedPathChild.getSplittedPathChild(user.getEmail())).child("docs").getRef();
        arrayList = new ArrayList<docDataList>();
        list = new docAdapterListView(getActivity(), arrayList);
        mainActivity = (MainActivity)getActivity();
        listView = view.findViewById(R.id.listview);
        addDataOnListView();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager  fragmentManager= getParentFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.ft, new AddDocFragment());
                fragmentTransaction.addToBackStack("prof"); // Добавляем в Back Stack
                fragmentTransaction.commit();
            }
        });
        lk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tanusha( "cab", myCabFragment);
            }
        });

        list.setOnItemDeleteListener(new cardAdapterListView.OnItemDeleteListener() {
            @Override
            public void onItemDelete(int position) {
                arrayList.remove(position);
                // Обновление списка
                list.notifyDataSetChanged();
            }
        });
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


    public void addDataOnListView(){
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (arrayList.size()>0) arrayList.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    docsModel ps = ds.getValue(docsModel.class);
                    assert ps!=null;
                    arrayList.add(new docDataList(ps.docType, ps.docSurname+" "+ ps.docName+" "+ ps.docPatronymic));
                    checkDataLoaded(listView, text);

                }
                list.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        db.addValueEventListener(vListener);
        listView.setAdapter(list);

        isDataLoaded = true; // Устанавливаем значение в true после успешной загрузки данных

        // Проверяем, нужно ли скрывать текст и отображать ListView
    }


    public void checkDataLoaded(com.ochy.ochy.cod.ListView listView, TextView text) {
        if (isDataLoaded) {
            if (list.isEmpty()) {
                listView.setVisibility(View.GONE); // Скрываем ListView
                text.setVisibility(View.VISIBLE); // Отображаем текстовый элемент
            } else {
                listView.setVisibility(View.VISIBLE); // Отображаем ListView
                text.setVisibility(View.GONE); // Скрываем текстовый элемент
            }
        }
    }




}