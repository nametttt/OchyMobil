package com.ochy.ochy;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ochy.ochy.cod.flightModel;
import com.ochy.ochy.cod.getSplittedPathChild;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class add_flight extends Fragment {

    AutoCompleteTextView pribcity, otprcity;
    EditText dateotpr, dateprib, cost;

    Button add;
    private Calendar calendar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_flight, container, false);
        init(v);
        return v;
    }

    private void init(View v) {
        pribcity = v.findViewById(R.id.goroprib);
        add = v.findViewById(R.id.add);
        otprcity = v.findViewById(R.id.gorotpr);
        String[] cities = getResources().getStringArray(R.array.cities_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, cities);
        pribcity.setAdapter(adapter);
        otprcity.setAdapter(adapter);
        dateotpr = v.findViewById(R.id.dateotpr);
        dateotpr.setKeyListener(null);
        dateprib = v.findViewById(R.id.dateprib);
        dateprib.setKeyListener(null);
        cost = v.findViewById(R.id.cost);
        calendar = Calendar.getInstance();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                getSplittedPathChild pC = new getSplittedPathChild();
                String splittedPathChild = pC.getSplittedPathChild(user.getEmail());
                String tableName = UUID.randomUUID().toString();

                DatabaseReference db = FirebaseDatabase.getInstance().getReference("flight").child(tableName).getRef();
                List<String> seats = new ArrayList<>(Collections.nCopies(35, ""));
                flightModel flight = new flightModel(otprcity.getText().toString(),
                        dateotpr.getText().toString(),
                        pribcity.getText().toString(),
                        dateprib.getText().toString(),
                        cost.getText().toString(),
                        seats);
                db.setValue(flight);
                Toast.makeText(getActivity(), "Рейс успешно добавлен!", Toast.LENGTH_SHORT).show();

                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.popBackStack();
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStack("card", 0);
                }
            }
        });


        dateotpr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePickerDialog(dateotpr);
            }
        });

        dateprib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePickerDialog(dateprib);
            }
        });
    }

    private void showDateTimePickerDialog(EditText t) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                showTimePickerDialog(t);
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), dateSetListener,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

        // Ограничение выбора даты до текущей даты плюс один месяц
        calendar.add(Calendar.MONTH, 1);
        datePickerDialog.show();
    }

    private void showTimePickerDialog(EditText t) {
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                updateDateTimeEditText(t);
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    private void updateDateTimeEditText(EditText t) {
        String dateFormat = "dd.MM.yyyy HH:mm"; // Формат даты и времени, которым вы хотите воспользоваться
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
        String selectedDateTime = simpleDateFormat.format(calendar.getTime());
        t.setText(selectedDateTime);
    }
}
