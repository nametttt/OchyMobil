package com.ochy.ochy;

import static com.ochy.ochy.cod.flightDataList.convertToFlightDataList;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ochy.ochy.R;
import com.ochy.ochy.cod.AirlineTicket;
import com.ochy.ochy.cod.FlightAdapterRecyclerView;
import com.ochy.ochy.cod.TicketsAdapterRecyclerView;
import com.ochy.ochy.cod.flightDataList;
import com.ochy.ochy.cod.flightModel;
import com.ochy.ochy.cod.getSplittedPathChild;

import java.util.ArrayList;


public class MyTicketsFragment extends Fragment {
    RecyclerView recyclerView;
    DatabaseReference db;
    ArrayList<AirlineTicket> arrayList;
    private TicketsAdapterRecyclerView adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_tickets, container, false);
        init(v);
        return v;
    }

    private void init(View v){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        getSplittedPathChild pC = new getSplittedPathChild();

        String splittedPathChild = pC.getSplittedPathChild(user.getEmail());
        db = FirebaseDatabase.getInstance().getReference("user").child(splittedPathChild).child("tickets").getRef();
        arrayList = new ArrayList<>();
        adapter = new TicketsAdapterRecyclerView(getContext(), arrayList);
        recyclerView = v.findViewById(R.id.res);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager.setReverseLayout(false);
        recyclerView.setLayoutManager(layoutManager);
        addDataOnRecyclerView();
    }

    private void addDataOnRecyclerView() {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (arrayList.size() > 0) {
                    arrayList.clear();
                }
                for (DataSnapshot ds : snapshot.getChildren()) {
                    AirlineTicket ps = ds.getValue(AirlineTicket.class);
                    assert ps != null;
                    arrayList.add(ps);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        db.addValueEventListener(valueEventListener);
    }
}