package com.ochy.ochy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ochy.ochy.cod.custom_spinner;
import com.ochy.ochy.cod.docsModel;
import com.ochy.ochy.cod.getSplittedPathChild;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class EditDocsFragment extends Fragment {
    custom_spinner customSpinner;
    Button add, otkl;
    public String PATH="";
    AutoCompleteTextView docCitizen;
    EditText docSurn, docName, docPatr, docBirht,
             docNumber;
    RadioButton male, female;

    private String splittedPathChild;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_docs, container, false);
        init(v);
        return v;
    }

    private void init(View v){

        String[] countries = {
                "Австралия",
                "Австрия",
                "Албания",
                "Алжир",
                "Ангола",
                "Аргентина",
                "Армения",
                "Афганистан",
                "Бангладеш",
                "Беларусь",
                "Бельгия",
                "Болгария",
                "Боливия",
                "Бразилия",
                "Великобритания",
                "Венгрия",
                "Венесуэла",
                "Вьетнам",
                "Гаити",
                "Гана",
                "Германия",
                "Гондурас",
                "Греция",
                "Грузия",
                "Дания",
                "Доминиканская Республика",
                "Египет",
                "Израиль",
                "Индия",
                "Индонезия",
                "Иордания",
                "Иран",
                "Ирландия",
                "Исландия",
                "Испания",
                "Италия",
                "Казахстан",
                "Камерун",
                "Канада",
                "Кения",
                "Китай",
                "Колумбия",
                "Коста-Рика",
                "Кот-д'Ивуар",
                "Куба",
                "Россия",
                "Кувейт",
                "Латвия",
                "Ливан",
                "Ливия",
                "Литва",
                "Лихтенштейн",
                "Люксембург"
                // Добавьте остальные страны в массив
        };

        getSplittedPathChild pC = new getSplittedPathChild();
        add = v.findViewById(R.id.add);
        docSurn = v.findViewById(R.id.docSurn);
        docName = v.findViewById(R.id.docName);
        docPatr = v.findViewById(R.id.docPatron);
        docBirht = v.findViewById(R.id.docBirth);
        docCitizen =v.findViewById(R.id.docCitizen);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, countries);
        docCitizen.setAdapter(adapter);
        male = v.findViewById(R.id.male);
        female = v.findViewById(R.id.female);
        docNumber = v.findViewById(R.id.docNumber);
        otkl = v.findViewById(R.id.otkl);
        customSpinner = v.findViewById(R.id.custom_spinner);


        List<String> items = Arrays.asList("Паспорт", "Загран паспорт", "Свидетельство о рождении");
        ArrayAdapter<String> st = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, items);
        customSpinner.setAdapter(st);
        EditText spinnerEditText = customSpinner.findViewById(R.id.spinnerEditText);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("user").child(pC.getSplittedPathChild(FirebaseAuth.getInstance().getCurrentUser().getEmail())).child("docs").child(PATH).getRef();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                docsModel docsModel = snapshot.getValue(com.ochy.ochy.cod.docsModel.class);
                docSurn.setText(docsModel.docSurname);
                docName.setText(docsModel.docName);
                docPatr.setText(docsModel.docPatronymic);
                docBirht.setText(docsModel.docBirth);
                docCitizen.setText(docsModel.docCitizen);
                spinnerEditText.setText(docsModel.docType);

                if (Objects.equals(docsModel.sex, "Мужской")) {
                    male.setChecked(true);
                } else {
                    female.setChecked(true);
                }

                docNumber.setText(docsModel.docNumber);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference updateRef = FirebaseDatabase.getInstance().getReference().child("user").child(pC.getSplittedPathChild(FirebaseAuth.getInstance().getCurrentUser().getEmail())).child("docs").child(PATH).getRef();

                String surname = docSurn.getText().toString();
                String name = docName.getText().toString();
                String patron = docPatr.getText().toString();
                String birth = docBirht.getText().toString();
                String citizen = docCitizen.getText().toString();
                String sex = male.isChecked()? "Мужской": "Женский";
                String number =docNumber.getText().toString();

                if (surname.isEmpty()||name.isEmpty()|| patron.isEmpty()|| birth.isEmpty()||
                        citizen.isEmpty()||sex.isEmpty()|| number.isEmpty() || spinnerEditText.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Вы ввели не все данные", Toast.LENGTH_SHORT).show();
                    return;
                }

                updateRef.child("docSurname").setValue(docSurn.getText().toString());
                updateRef.child("docName").setValue(docName.getText().toString());
                updateRef.child("docPatronymic").setValue(docPatr.getText().toString());
                updateRef.child("docBirth").setValue(docBirht.getText().toString());
                updateRef.child("docCitizen").setValue(docCitizen.getText().toString());
                updateRef.child("docType").setValue(spinnerEditText.getText().toString());

                if (male.isChecked()) {
                    updateRef.child("sex").setValue("Мужской");
                } else {
                    updateRef.child("sex").setValue("Женский");
                }

                updateRef.child("docNumber").setValue(docNumber.getText().toString());

                Toast.makeText(getContext(), "Данные обновлены в базе данных", Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });

        otkl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSplittedPathChild ge = new getSplittedPathChild();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference updateRef = FirebaseDatabase.getInstance().getReference().child("user").child(pC.getSplittedPathChild(FirebaseAuth.getInstance().getCurrentUser().getEmail())).child("docs").child(PATH).getRef();
                FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                fragmentManager.popBackStack();
                updateRef.removeValue();
            }
        });


    }
}