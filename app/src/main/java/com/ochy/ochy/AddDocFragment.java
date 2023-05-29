package com.ochy.ochy;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ochy.ochy.cod.cardModel;
import com.ochy.ochy.cod.custom_spinner;
import com.ochy.ochy.cod.docsModel;
import com.ochy.ochy.cod.getSplittedPathChild;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;


public class AddDocFragment extends Fragment {
    androidx.appcompat.widget.Toolbar mToolBar;
    custom_spinner customSpinner;
    Button add;
    EditText docSurn, docName, docPatr, docBirht,
        docCitizen, docNumber;
    RadioButton male, female;

    private String splittedPathChild;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_doc, container, false);
        init(v);
        return v;
    }

    private  void init (View v){
        add = v.findViewById(R.id.add);
        docSurn = v.findViewById(R.id.docSurn);
        docName = v.findViewById(R.id.docName);
        docPatr = v.findViewById(R.id.docPatron);
        docBirht = v.findViewById(R.id.docBirth);
        docCitizen =v.findViewById(R.id.docCitizen);
        male = v.findViewById(R.id.male);
        female = v.findViewById(R.id.female);
        docNumber = v.findViewById(R.id.docNumber);

        mToolBar = v.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolBar);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Добавить документ");
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable navIcon = mToolBar.getNavigationIcon();
        customSpinner = v.findViewById(R.id.custom_spinner);
        List<String> items = Arrays.asList("Паспорт", "Загран паспорт", "Свидетельство о рождении");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, items);
        customSpinner.setAdapter(adapter);
        EditText spinnerEditText = customSpinner.findViewById(R.id.spinnerEditText);

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
                    fragmentManager.popBackStack("profile",0);
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!male.isChecked() && !female.isChecked()){
                    Toast.makeText(getActivity(), "Вы не выбрали пол", Toast.LENGTH_SHORT).show();
                    return;
                }

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
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                getSplittedPathChild pC = new getSplittedPathChild();
                splittedPathChild = pC.getSplittedPathChild(user.getEmail());
                String tableName = UUID.randomUUID().toString();


                DatabaseReference db = FirebaseDatabase.getInstance().getReference("user").child(splittedPathChild).child("docs").child(tableName).getRef();
                docsModel docs = new docsModel(surname, name, patron, birth, citizen, number, sex, spinnerEditText.getText().toString());
                db.setValue(docs);
                Toast.makeText(getActivity(), "Документ успешно добавлен!", Toast.LENGTH_SHORT).show();

                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.popBackStack();

            }
        });
    }



}