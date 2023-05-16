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

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ochy.ochy.cod.cardModel;
import com.ochy.ochy.cod.cardsDataList;
import com.ochy.ochy.cod.getSplittedPathChild;
import com.ochy.ochy.cod.post_Card;

import java.util.ArrayList;
import java.util.Collections;


public class CardFragment extends Fragment {

    androidx.appcompat.widget.Toolbar mToolBar;
    private com.ochy.ochy.cod.ListView listView;
    DatabaseReference db;
    ArrayList<cardsDataList> arrayList;
    private  cardAdapterListView list;
    MainActivity mainActivity;
    Button addCard;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_card, container, false);
        init(v);
        return v;
    }

    private  void init(View v){
        getSplittedPathChild getSplittedPathChild = new getSplittedPathChild();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseDatabase.getInstance().getReference("user").child( getSplittedPathChild.getSplittedPathChild(user.getEmail())).child("cards").getRef();
        mToolBar = v.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolBar);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Мои карты");
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable navIcon = mToolBar.getNavigationIcon();
        if (navIcon != null) {
            navIcon.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
            mToolBar.setNavigationIcon(navIcon);
        }
        setHasOptionsMenu(true);
        arrayList = new ArrayList<cardsDataList>();
        list = new cardAdapterListView(getActivity(), arrayList);
        mainActivity = (MainActivity)getActivity();
        listView = v.findViewById(R.id.listview);

        addDataOnListView();

        addCard = v.findViewById(R.id.addCard);
        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager  fragmentManager= getParentFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.ft, new AddCardFragment());
                fragmentTransaction.addToBackStack("card"); // Добавляем в Back Stack
                fragmentTransaction.commit();
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

    private void addDataOnListView(){
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (arrayList.size()>0) arrayList.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    cardModel ps = ds.getValue(cardModel.class);
                    assert ps!=null;
                    arrayList.add(new cardsDataList(ps.cardNumb));

                }
                list.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        db.addValueEventListener(vListener);
        listView.setAdapter(list);
    }
}