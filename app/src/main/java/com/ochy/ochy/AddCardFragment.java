package com.ochy.ochy;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ochy.ochy.cod.cardModel;
import com.ochy.ochy.cod.getSplittedPathChild;


import java.util.UUID;
import java.util.regex.Pattern;

import ru.tinkoff.decoro.MaskImpl;
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser;
import ru.tinkoff.decoro.slots.PredefinedSlots;
import ru.tinkoff.decoro.slots.Slot;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;

public class AddCardFragment extends Fragment {

    androidx.appcompat.widget.Toolbar mToolBar;
    androidx.appcompat.widget.AppCompatButton btn;
    private String splittedPathChild;
    private EditText name, numb, date, cvcv, man;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_card, container, false);
        init(v);
        return v;
    }


    private void init (View v){
        numb = v.findViewById(R.id.cardnum);
        date = v.findViewById(R.id.carddate);
        cvcv = v.findViewById(R.id.cardcvc);
        man = v.findViewById(R.id.cardman);

        btn = v.findViewById(R.id.add);
        mToolBar = v.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolBar);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Добавить карту");
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
                    fragmentManager.popBackStack("card",0);
                }
            }
        });




        int len = 5; // Максимальная длина даты карты (например, "MM/YY")

        InputFilter[] ft = new InputFilter[1];
        ft[0] = new InputFilter.LengthFilter(len);
        date.setFilters(ft);




        date.addTextChangedListener(new TextWatcher() {
            private boolean isFormatting;
            private String previousText = "";
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isFormatting) {
                    return;
                }

                String input = s.toString();
                String formattedDate = formatCardDate(input);

                if (!formattedDate.equals(input)) {
                    isFormatting = true;
                    date.setText(formattedDate);
                    date.setSelection(formattedDate.length());
                    isFormatting = false;
                }

                previousText = formattedDate;
            }

            private String formatCardDate(String input) {
                // Удаление всех нецифровых символов
                String digitsOnly = input.replaceAll("[^\\d]", "");

                // Ограничение значений дня и месяца
                if (digitsOnly.length() >= 2) {
                    int month = Integer.parseInt(digitsOnly.substring(0, 2));
                    if (month < 1 || month > 12) {
                        // Некорректный месяц, обрезаем до допустимых значений
                        digitsOnly = digitsOnly.substring(0, 1);
                    }
                }

                if (digitsOnly.length() >= 4) {
                    int day = Integer.parseInt(digitsOnly.substring(2, 4));
                    if (day < 1 || day > 31) {
                        // Некорректный день, обрезаем до допустимых значений
                        digitsOnly = digitsOnly.substring(0, 3);
                    }
                }

                if (digitsOnly.length() >= 5) {
                    int year = Integer.parseInt(digitsOnly.substring(3, 5));
                    if (year < 0 || year > 23) {
                        // Некорректный год, обрезаем до допустимых значений
                        digitsOnly = digitsOnly.substring(0, 4);
                    }
                }

                // Форматирование даты в формат "MM/YY"
                int length = digitsOnly.length();
                if (length > 2) {
                    String month = digitsOnly.substring(0, 2);
                    String year = digitsOnly.substring(2);
                    digitsOnly = month + "/" + year;
                }

                return digitsOnly;
            }
        });


        EditText cardNumberEditText = v.findViewById(R.id.cardnum);
        int maxLength = 19; // Максимальная длина номера карты (включая пробелы и разделители)

        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(maxLength);
        cardNumberEditText.setFilters(filters);

        cardNumberEditText.addTextChangedListener(new TextWatcher() {
            private boolean isFormatting;
            private String previousText = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Ничего не делаем
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Ничего не делаем
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isFormatting) {
                    return;
                }

                String input = s.toString();
                String formattedText = formatCardNumber(input);

                if (!formattedText.equals(input)) {
                    isFormatting = true;
                    cardNumberEditText.setText(formattedText);
                    cardNumberEditText.setSelection(formattedText.length());
                    isFormatting = false;
                }

                previousText = formattedText;
            }

            private String formatCardNumber(String input) {
                // Удаление всех нецифровых символов
                String digitsOnly = input.replaceAll("[^\\d]", "");

                // Применение форматирования для номера карты (например, разделение на группы по 4 цифры с пробелами)
                StringBuilder formatted = new StringBuilder();
                int groupSize = 4;
                int totalGroups = (int) Math.ceil((double) digitsOnly.length() / groupSize);

                for (int i = 0; i < totalGroups; i++) {
                    int startIndex = i * groupSize;
                    int endIndex = Math.min(startIndex + groupSize, digitsOnly.length());
                    String group = digitsOnly.substring(startIndex, endIndex);
                    formatted.append(group);

                    if (endIndex < digitsOnly.length()) {
                        formatted.append(" "); // Добавление пробела между группами
                    }
                }

                return formatted.toString();
            }
        });





        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number = numb.getText().toString();
                String expirationDate = date.getText().toString();
                String cvc = cvcv.getText().toString();
                String cardholderName = man.getText().toString();

                // Проверка на пустоту полей
                if (TextUtils.isEmpty(number)) {
                    Toast.makeText(getContext(), "Введите номер карты", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(expirationDate)) {
                    Toast.makeText(getContext(), "Введите срок действия карты", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(cvc)) {
                    Toast.makeText(getContext(), "Введите CVV/CVC код", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(cardholderName)) {
                    Toast.makeText(getContext(), "Введите имя держателя карты", Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                getSplittedPathChild pC = new getSplittedPathChild();
                splittedPathChild = pC.getSplittedPathChild(user.getEmail());
                String tableName = UUID.randomUUID().toString();

                DatabaseReference db = FirebaseDatabase.getInstance().getReference("user").child(splittedPathChild).child("cards").child(tableName).getRef();
                cardModel card = new cardModel(number, expirationDate, cvc, cardholderName);

                db.setValue(card);
                Toast.makeText(getActivity(), "Карта успешно добавлена!", Toast.LENGTH_SHORT).show();

                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.popBackStack();
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStack("card",0);
                }
            }
        });
    }





}