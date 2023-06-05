package com.ochy.ochy;

import static com.ochy.ochy.cod.flightDataList.convertToFlightDataList;
import static com.ochy.ochy.cod.flightModel.convertToFlightModel;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.filament.Colors;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
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
import java.util.List;
import java.util.Locale;

public class ticketFragment extends Fragment {

    Button date_picker, passajers_picker;
    Calendar calendar;
    boolean isStartDateSelected = false;
    AutoCompleteTextView fromCity, toCity;
    private int selectedAdultCount = 1;
    private int selectedTeenagerCount = 0;
    private int selectedInfantCount = 0;
    int quantity=1;
    RecyclerView recyclerView;

    DatabaseReference db;
    ArrayList<flightDataList> arrayList;
    private FlightAdapterRecyclerView adapter;

    private boolean isAdultMinusEnabled = true;
    private boolean isAdultPlusEnabled = true;
    private boolean isTeenagerMinusEnabled = true;
    private boolean isTeenagerPlusEnabled = true;
    private boolean isInfantMinusEnabled = true;
    private boolean isInfantPlusEnabled = true;

    private Button adultMinusButton;
    private Button adultPlusButton;
    private Button teenagerMinusButton;
    private Button teenagerPlusButton;
    private Button infantMinusButton;
    private Button infantPlusButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ticket, container, false);
        init(v);
        return v;
    }

    private void init(View v) {
        db = FirebaseDatabase.getInstance().getReference("flight").getRef();
        arrayList = new ArrayList<>();
        adapter = new FlightAdapterRecyclerView(getContext(), arrayList);
        recyclerView = v.findViewById(R.id.res);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        String[] cities = getResources().getStringArray(R.array.cities_array);
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, cities);

        date_picker = v.findViewById(R.id.date_picker);
        passajers_picker = v.findViewById(R.id.passajers_picker);
        passajers_picker.setText(quantity+" пассажиров");
        fromCity = v.findViewById(R.id.fromCity);
        toCity = v.findViewById(R.id.toCity);
        fromCity.setAdapter(cityAdapter);
        toCity.setAdapter(cityAdapter);


        passajers_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPassengersPickerDialog();
            }
        });

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
                    DatabaseReference flightRef = FirebaseDatabase.getInstance().getReference().child("flight");

                    flightRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot parentSnapshot : dataSnapshot.getChildren()) {
                                    flightModel firebaseItem = parentSnapshot.getValue(flightModel.class);
                                    if (convertedItem.equals(firebaseItem)) {
                                        String parentKey = parentSnapshot.getKey();
                                        BuyTicketFirstStepFragment buyTicketFirstStepFragment = new BuyTicketFirstStepFragment();
                                        FragmentManager fragmentManager = getParentFragmentManager();
                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        fragmentTransaction.replace(R.id.framelayout, buyTicketFirstStepFragment);
                                        fragmentTransaction.addToBackStack("flight"); // Добавляем в Back Stack

                                        flightDataList flightDataList = convertToFlightDataList(convertedItem);

                                        String cost = flightDataList.getCost();
                                        Bundle args = new Bundle();
                                        args.putString("cost", cost);
                                        args.putString("parentKey", parentKey);
                                        args.putString("marshr", flightDataList.getEzda());
                                        args.putString("time", flightDataList.getDate());
                                        args.putString("free_places", flightDataList.getPlaces());
                                        args.putString("duration",flightDataList.getDuration());
                                        args.putString("pas", passajers_picker.getText().toString());
                                        buyTicketFirstStepFragment.setArguments(args);


                                        fragmentTransaction.commit();
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
                updateRecyclerViewData(selectedDate);
            }
        }, year, month, dayOfMonth);

        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

        calendar.add(Calendar.MONTH, 1);

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
                    flightDataList fl = convertToFlightDataList(ps);
                    arrayList.add(fl);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        db.addValueEventListener(valueEventListener);
    }

    private void updateRecyclerViewData(String selectedDate) {
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

                    List<String> seats = ps.seats;
                    int emptyCount = 0;

                    for (String seat : seats) {
                        if (seat.equals("")) {
                            emptyCount++;
                        }
                    }
                    String difference;
                    String emptyCountString = String.valueOf(emptyCount);

                    if (days > 0) {
                        difference = String.format("%d дней", days);
                        if (hours > 0) {
                            if (minutes > 0) {
                                difference += String.format(", %d часов, %d минут", hours, minutes);
                            } else {
                                difference += String.format(", %d часов", hours);
                            }
                        }
                    } else if (hours > 0) {
                        if (minutes > 0) {
                            difference = String.format("%d часов, %d минут", hours, minutes);
                        } else {
                            difference = String.format("%d часов", hours);
                        }
                    } else if (minutes > 0) {
                        difference = String.format("%d минут", minutes);
                    } else {
                        difference = "0 минут";
                    }

                    arrayList.add(new flightDataList(ps.cost + " ₽", ps.otpr_city + " ➔ " + ps.prib_city, formattedDateTime1 + " ➔ " + formattedDateTime2, emptyCountString + " мест", difference));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Обработка ошибки при чтении данных из Firebase
            }
        };

        // Применение фильтрации по выбранной дате
        Query query = db.orderByChild("date_otpr").startAt(selectedDate).endAt(selectedDate + "\uf8ff");
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    private void showPassengersPickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Выберите количество пассажиров");
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.passengers_picker_dialog, null);
        builder.setView(dialogView);

        // Находим элементы внутри диалога
        TextView adultCountTextView = dialogView.findViewById(R.id.adultCountTextView);
        TextView teenagerCountTextView = dialogView.findViewById(R.id.teenagerCountTextView);
        TextView infantCountTextView = dialogView.findViewById(R.id.infantCountTextView);
        adultMinusButton = dialogView.findViewById(R.id.adultMinusButton);
        adultPlusButton = dialogView.findViewById(R.id.adultPlusButton);
        teenagerMinusButton = dialogView.findViewById(R.id.teenagerMinusButton);
        teenagerPlusButton = dialogView.findViewById(R.id.teenagerPlusButton);
        infantMinusButton = dialogView.findViewById(R.id.infantMinusButton);
        infantPlusButton = dialogView.findViewById(R.id.infantPlusButton);



        // Устанавливаем значения по умолчанию
        adultCountTextView.setText(String.valueOf(selectedAdultCount));
        teenagerCountTextView.setText(String.valueOf(selectedTeenagerCount));
        infantCountTextView.setText(String.valueOf(selectedInfantCount));

        adultPlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedAdultCount < 5) {
                    selectedAdultCount++;
                    adultCountTextView.setText(String.valueOf(selectedAdultCount));
                    updateButtonColors();
                }
                updateButtonColors();
                isAdultPlusEnabled = (selectedAdultCount < 5);
                isAdultMinusEnabled = (selectedAdultCount > 0);
            }
        });

        adultMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedAdultCount > 0) {
                    selectedAdultCount--;
                    adultCountTextView.setText(String.valueOf(selectedAdultCount));
                    updateButtonColors();
                }
                updateButtonColors();
                isAdultPlusEnabled = (selectedAdultCount < 5);
                isAdultMinusEnabled = (selectedAdultCount > 0);
            }
        });

        teenagerPlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTeenagerCount < 5) {
                    selectedTeenagerCount++;
                    teenagerCountTextView.setText(String.valueOf(selectedTeenagerCount));
                    updateButtonColors();
                }
                updateButtonColors();
                isTeenagerPlusEnabled = (selectedTeenagerCount < 5);
                isTeenagerMinusEnabled = (selectedTeenagerCount > 0);
            }
        });

        teenagerMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTeenagerCount > 0) {
                    selectedTeenagerCount--;
                    teenagerCountTextView.setText(String.valueOf(selectedTeenagerCount));
                    updateButtonColors();
                }
                updateButtonColors();

                isTeenagerPlusEnabled = (selectedTeenagerCount < 5);
                isTeenagerMinusEnabled = (selectedTeenagerCount > 0);
            }
        });

        infantPlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedInfantCount < selectedAdultCount && selectedInfantCount < 5) {
                    selectedInfantCount++;
                    infantCountTextView.setText(String.valueOf(selectedInfantCount));
                    updateButtonColors();
                }
                updateButtonColors();
                isInfantPlusEnabled = (selectedInfantCount < selectedAdultCount && selectedInfantCount < 5);
                isInfantMinusEnabled = (selectedInfantCount > 0);
            }
        });

        infantMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedInfantCount > 0) {
                    selectedInfantCount--;
                    infantCountTextView.setText(String.valueOf(selectedInfantCount));
                    updateButtonColors();
                }
                updateButtonColors();
                isInfantPlusEnabled = (selectedInfantCount < selectedAdultCount && selectedInfantCount < 5);
                isInfantMinusEnabled = (selectedInfantCount > 0);
            }
        });



        builder.setPositiveButton("Применить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                 quantity = selectedAdultCount + selectedInfantCount + selectedTeenagerCount;

                // Обновляем текст на кнопке
                String passengersText = String.format(Locale.getDefault(), "%d пассажиров", quantity);
                passajers_picker.setText(passengersText);
            }
        });

        builder.setNegativeButton("Отмена", null);

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(R.color.bezh); // Устанавливаем фоновый цвет диалога
        alertDialog.show();

        // Обновляем цвета кнопок
        updateButtonColors();
    }

    private void updateButtonColors() {
        // Обновляем цвета кнопок
        adultMinusButton.setEnabled(isAdultMinusEnabled);
        adultPlusButton.setEnabled(isAdultPlusEnabled);
        teenagerMinusButton.setEnabled(isTeenagerMinusEnabled);
        teenagerPlusButton.setEnabled(isTeenagerPlusEnabled);
        infantMinusButton.setEnabled(isInfantMinusEnabled);
        infantPlusButton.setEnabled(isInfantPlusEnabled);


        adultMinusButton.setBackgroundResource(isAdultMinusEnabled ? R.drawable.btn_exit : R.drawable.btn_notavailable);
        adultPlusButton.setBackgroundResource(isAdultPlusEnabled ? R.drawable.btn_exit : R.drawable.btn_notavailable);
        teenagerMinusButton.setBackgroundResource(isTeenagerMinusEnabled ? R.drawable.btn_exit : R.drawable.btn_notavailable);
        teenagerPlusButton.setBackgroundResource(isTeenagerPlusEnabled ? R.drawable.btn_exit : R.drawable.btn_notavailable);
        infantMinusButton.setBackgroundResource(isInfantMinusEnabled ? R.drawable.btn_exit : R.drawable.btn_notavailable);
        infantPlusButton.setBackgroundResource(isInfantPlusEnabled ? R.drawable.btn_exit : R.drawable.btn_notavailable);
    }



}
