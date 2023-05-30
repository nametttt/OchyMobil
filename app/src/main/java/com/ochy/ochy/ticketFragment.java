package com.ochy.ochy;

import static com.ochy.ochy.cod.flightModel.convertToFlightModel;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.filament.Colors;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.collection.LLRBNode;
import com.ochy.ochy.cod.FlightAdapterRecyclerView;
import com.ochy.ochy.cod.flightDataList;
import com.ochy.ochy.cod.flightModel;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ticketFragment extends Fragment {

    Button date_picker, passajers_picker;
    Calendar calendar;
    boolean isStartDateSelected = false;
    AutoCompleteTextView fromCity, toCity;
    RecyclerView recyclerView;

    DatabaseReference db;
    ArrayList<flightDataList> arrayList;
    private FlightAdapterRecyclerView adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ticket, container, false);
        init(v);
        return v;
    }

    private void init(View v) {
        db = FirebaseDatabase.getInstance().getReference(" flight").getRef();
        arrayList = new ArrayList<>();
        adapter = new FlightAdapterRecyclerView(getContext(), arrayList);
        recyclerView = v.findViewById(R.id.res);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        String[] cities = getResources().getStringArray(R.array.cities_array);
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, cities);

        date_picker = v.findViewById(R.id.date_picker);
        passajers_picker = v.findViewById(R.id.passajers_picker);
        fromCity = v.findViewById(R.id.fromCity);
        toCity = v.findViewById(R.id.toCity);
        fromCity.setAdapter(cityAdapter);
        toCity.setAdapter(cityAdapter);

        date_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        addDataOnRecyclerView();


        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child != null && e.getAction() == MotionEvent.ACTION_UP) {
                    int position = rv.getChildAdapterPosition(child);
                    flightDataList clickedItem = arrayList.get(position);
                    flightModel convertedItem = convertToFlightModel(clickedItem);
                    DatabaseReference flightRef = FirebaseDatabase.getInstance().getReference().child(" flight");

                    flightRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot parentSnapshot : dataSnapshot.getChildren()) {
                                    flightModel firebaseItem = parentSnapshot.getValue(flightModel.class);
                                    if (convertedItem.equals(firebaseItem)) {
                                        String parentKey = parentSnapshot.getKey();
                                        Toast.makeText(getContext(), parentKey, Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getContext(), "Что-то не так", Toast.LENGTH_SHORT).show();
                        }
                    });

                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

    }

    private void showDatePickerDialog() {
        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String selectedDate = DateFormat.format("dd.MM.yyyy", calendar).toString();
                date_picker.setText(selectedDate);
                date_picker.setTextColor(Color.BLACK);
            }
        }, year, month, dayOfMonth);

        datePickerDialog.show();
    }

    private void addDataOnRecyclerView() {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (arrayList.size() > 0) {
                    arrayList.clear();
                }
                for (DataSnapshot ds : snapshot.getChildren()) {
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

                    arrayList.add(new flightDataList(ps.cost+" ₽", ps.otpr_city + " ➔ " + ps.prib_city, formattedDateTime1 + " ➔ " + formattedDateTime2, "5", difference));

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
