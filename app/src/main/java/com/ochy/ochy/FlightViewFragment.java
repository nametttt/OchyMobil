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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ochy.ochy.cod.cardModel;
import com.ochy.ochy.cod.cardsDataList;
import com.ochy.ochy.cod.flightAdapterListView;
import com.ochy.ochy.cod.flightDataList;
import com.ochy.ochy.cod.flightModel;
import com.ochy.ochy.cod.getSplittedPathChild;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;


public class FlightViewFragment extends Fragment {

    private com.ochy.ochy.cod.ListView listView;
    DatabaseReference db;
    ArrayList<flightDataList> arrayList;
    private flightAdapterListView list;
    Button addCard;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_flight_view, container, false);
        init(v);
        return v;
    }

    private void init(View v){
        db = FirebaseDatabase.getInstance().getReference("flight").getRef();
        arrayList = new ArrayList<flightDataList>();
        list = new flightAdapterListView(getActivity(), arrayList);
        listView = v.findViewById(R.id.listview);
        addDataOnListView();

        addCard = v.findViewById(R.id.addCard);
        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_flight add_flight = new add_flight();

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout, add_flight); // Замените R.id.fragmentContainer на идентификатор контейнера фрагментов в вашей макете
                fragmentTransaction.commit();
            }
        });
    }


    private void addDataOnListView(){
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (arrayList.size()>0) arrayList.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    flightModel ps = ds.getValue(flightModel.class);
                    assert ps != null;

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                    LocalDateTime dateTime1 = LocalDateTime.parse(ps.date_otpr, formatter);
                    LocalDateTime dateTime2 = LocalDateTime.parse(ps.date_prib, formatter);



                    DateTimeFormatter f = DateTimeFormatter.ofPattern("d MMMM H:mm", new Locale("ru"));
                    String formattedDateTime1 = dateTime1.format(f);

                    String formattedDateTime2 = dateTime2.format(f);

                    Duration duration = Duration.between(dateTime1, dateTime2);

                    long days = duration.toDays();
                    long hours = duration.toHours() % 24;
                    long minutes = duration.toMinutes() % 60;

                    String difference;

                    if (days > 0) {
                        difference = String.format("%d дней, %d часов", days, hours);
                    } else {
                        difference = String.format("%d часов, %d минут", hours, minutes);
                    }

                    arrayList.add(new flightDataList(ps.cost, ps.otpr_city + " ➔ " + ps.prib_city, formattedDateTime1 + " ➔ " + formattedDateTime2, "5", difference));

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