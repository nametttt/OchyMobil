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

import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ochy.ochy.cod.AirlineTicket;
import com.ochy.ochy.cod.QRCodeGenerator;
import com.ochy.ochy.cod.cardModel;
import com.ochy.ochy.cod.cardsDataList;
import com.ochy.ochy.cod.docsModel;
import com.ochy.ochy.cod.flightDataList;
import com.ochy.ochy.cod.flightModel;
import com.ochy.ochy.cod.getSplittedPathChild;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class BuyTicketSeconStepFragment extends Fragment {


    androidx.appcompat.widget.Toolbar mToolBar;
    View seats_select, payment;
    ArrayList<cardsDataList> arrayList;
    ArrayList<docsModel> docInstance =new ArrayList<docsModel>();;

    DatabaseReference db;
    LinearLayout ln;
    private EditText  numb, date, cvcv, man;
    Button addpass;
    ArrayList<CheckBox> checkBoxList = new ArrayList<>();




    CheckBox ch_1, ch_2,ch_3, ch_4, ch_5, ch_6, ch_7, ch_8,ch_9, ch_10, ch_11, ch_12,
            ch_13, ch_14,ch_15, ch_16, ch_17, ch_18, ch_19, ch_20,ch_21, ch_22, ch_23, ch_24,
            ch_25, ch_26,ch_27, ch_28, ch_29, ch_30, ch_31, ch_32,ch_33, ch_34, ch_35, ch_36;

    int[] selected_seats;
    ArrayList<Integer> listOfSeats = new ArrayList<Integer>();
    int allSeats=1;
    ArrayAdapter<String> docAdapter;

    com.ochy.ochy.cod.custom_spinner spinner;
    private String parentKey;
    EditText ed;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_buy_ticket_secon_step, container, false);

        init(v);

        return v;
    }

    private  void init (View v){
        addpass = v.findViewById(R.id.addpass);
        getSplittedPathChild getSplittedPathChild = new getSplittedPathChild();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        payment = v.findViewById(R.id.payment);
        numb = payment.findViewById(R.id.cardnumb);
        date = payment.findViewById(R.id.carddate);
        cvcv = payment.findViewById(R.id.cvc);
        man = payment.findViewById(R.id.cardname);

        ln = payment.findViewById(R.id.ln);
        docAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item);
        spinner = payment.findViewById(R.id.cards);
         ed = spinner.findViewById(R.id.spinnerEditText);
        ed.setHint("Выберите карту");
        db = FirebaseDatabase.getInstance().getReference("user").child( getSplittedPathChild.getSplittedPathChild(user.getEmail())).child("cards").getRef();

        Bundle args = getArguments();
        if (args != null) {
            allSeats = args.getInt("pasesr");
            parentKey = args.getString("parentKey");
            docInstance  = args.getParcelableArrayList("dssss");
        }
        seats_select = v.findViewById(R.id.seats_select);
        ch_1 = seats_select.findViewById(R.id.chc_1);
        ch_2 = seats_select.findViewById(R.id.chc_2);
        ch_3 = seats_select.findViewById(R.id.chc_3);
        ch_4 = seats_select.findViewById(R.id.chc_4);
        ch_5 = seats_select.findViewById(R.id.chc_5);
        ch_6 = seats_select.findViewById(R.id.chc_6);
        ch_7 = seats_select.findViewById(R.id.chc_7);
        ch_8 = seats_select.findViewById(R.id.chc_8);
        ch_9 = seats_select.findViewById(R.id.chc_9);
        ch_10 = seats_select.findViewById(R.id.chc_10);
        ch_11 = seats_select.findViewById(R.id.chc_11);
        ch_12 = seats_select.findViewById(R.id.chc_12);

        ch_13 = seats_select.findViewById(R.id.chc_13);
        ch_14 = seats_select.findViewById(R.id.chc_14);
        ch_15 = seats_select.findViewById(R.id.chc_15);
        ch_16 = seats_select.findViewById(R.id.chc_16);
        ch_17 = seats_select.findViewById(R.id.chc_17);
        ch_18 = seats_select.findViewById(R.id.chc_18);
        ch_19 = seats_select.findViewById(R.id.chc_19);
        ch_20 = seats_select.findViewById(R.id.chc_20);
        ch_21 = seats_select.findViewById(R.id.chc_21);
        ch_22 = seats_select.findViewById(R.id.chc_22);
        ch_23 = seats_select.findViewById(R.id.chc_23);
        ch_24 = seats_select.findViewById(R.id.chc_24);

        ch_25 = seats_select.findViewById(R.id.chc_25);
        ch_26 = seats_select.findViewById(R.id.chc_26);
        ch_27 = seats_select.findViewById(R.id.chc_27);
        ch_28 = seats_select.findViewById(R.id.chc_28);
        ch_29 = seats_select.findViewById(R.id.chc_29);
        ch_30 = seats_select.findViewById(R.id.chc_30);
        ch_31 = seats_select.findViewById(R.id.chc_31);
        ch_32 = seats_select.findViewById(R.id.chc_32);
        ch_33 = seats_select.findViewById(R.id.chc_33);
        ch_34 = seats_select.findViewById(R.id.chc_34);
        ch_35 = seats_select.findViewById(R.id.chc_35);
        ch_36 = seats_select.findViewById(R.id.chc_36);
        setArrayList();






        selected_seats = new int[allSeats];

        mToolBar = v.findViewById(R.id.toolbar);
        mToolBar = v.findViewById(R.id.toolbar);

        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolBar);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Покупка авиабилета");
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
                    fragmentManager.popBackStack("fl",0);
                }
            }
        });

        addpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cardNum = numb.getText().toString();
                String cardDate = date.getText().toString();
                String cardCVC = cvcv.getText().toString();
                String cardName = man.getText().toString();

                if (listOfSeats.size()<allSeats){
                    Toast.makeText(getContext(), "Вы не выбрали места для всех пассажиров", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (ed.getText().toString().isEmpty() || ed.getText().toString().equals("Не выбрано")) {
                    if (cardNum.isEmpty() || cardDate.isEmpty()|| cardCVC.isEmpty() || cardName.isEmpty()){
                        Toast.makeText(getContext(), "Вы ввели не все данные", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }


                else {
                    int selectedPosition = spinner.getSelectedItemPosition();
                    if (selectedPosition > 0) {
                        String selectedCardNumber = docAdapter.getItem(selectedPosition);
                        getSelectedCardInstance(selectedPosition);
                    } else {
                        Toast.makeText(getActivity(), "Выберите карту", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                DatabaseReference flights = FirebaseDatabase.getInstance().getReference().child("flight").child(parentKey).getRef();
                flights.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        getSplittedPathChild pC = new getSplittedPathChild();
                        String splittedPathChild = pC.getSplittedPathChild(user.getEmail());
                        String tableName = UUID.randomUUID().toString();
                        DatabaseReference flightRef = FirebaseDatabase.getInstance().getReference().child("flight").child(parentKey).child("seats");
                        flightDataList flightDataListss = flightDataList.convertToFlightDataList(snapshot.getValue(flightModel.class));


                        DatabaseReference db = FirebaseDatabase.getInstance().getReference("user").child(splittedPathChild).child("tickets").child(tableName).getRef();

                        for (int i = 0; i < allSeats; i++) {
                            docsModel dc = docInstance.get(i);
                            int seatNumber = listOfSeats.get(i);

                            AirlineTicket airlineTicket = new AirlineTicket(flightDataListss.getEzda(), flightDataListss.getDate(),dc.docSurname +" " +dc.docName,dc.docType, dc.docNumber, parentKey.substring(0, 5), String.valueOf(seatNumber), "");
                            QRCodeGenerator.generateQRCodeAndUpload(airlineTicket, splittedPathChild);

                            String newValue = splittedPathChild; // Замените на нужное вам значение

                            // Создание объекта для обновления значения места
                            Map<String, Object> update = new HashMap<>();
                            update.put(String.valueOf(seatNumber), splittedPathChild);

                            flightRef.updateChildren(update);
                        }

                        Toast.makeText(getActivity(), "Билет успешно куплен!", Toast.LENGTH_SHORT).show();
                        FragmentManager fragmentManager = getParentFragmentManager();
                        fragmentManager.popBackStack();
                        if (fragmentManager.getBackStackEntryCount() > 0) {
                            fragmentManager.popBackStack("buyTicket",0);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }

        });

        int maxLength = 19; // Максимальная длина номера карты (включая пробелы и разделители)

        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(maxLength);
        numb.setFilters(filters);

        numb.addTextChangedListener(new TextWatcher() {
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
                    numb.setText(formattedText);
                    numb.setSelection(formattedText.length());
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


        DatabaseReference seatsRef = FirebaseDatabase.getInstance().getReference().child("flight").child(parentKey).child("seats");

        seatsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Проход по всем дочерним элементам узла "seats"
                for (DataSnapshot seatSnapshot : dataSnapshot.getChildren()) {
                    assert  seatSnapshot!=null;
                    String seatNumber = seatSnapshot.getKey();
                    String seatStatus = seatSnapshot.getValue(String.class);
                    int seatIndex = Integer.parseInt(seatNumber) ;
                    CheckBox checkBox = checkBoxList.get(seatIndex);
                    checkBox.setEnabled(seatStatus.equals(""));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        selectedCheckBox();
        addDataOnListView();
        spinnerSelectionNull();


    }


    private  void selectedCheckBox(){
        CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String chId = getResources().getResourceEntryName(buttonView.getId());
                int numb = Integer.parseInt(chId.split("_")[1])-1;
                Object o = numb;

                if(!buttonView.isChecked()){
                    listOfSeats.remove(o);
                }

                if (listOfSeats.size()< allSeats){
                    if (buttonView.isChecked()){
                        listOfSeats.add(numb);
                    }
                    else if(!buttonView.isChecked()){
                        listOfSeats.remove(o);
                    }
                }

                else{
                    Toast.makeText(getContext(), "Предельное количество мест", Toast.LENGTH_SHORT).show();
                    buttonView.setChecked(false);
                }


            }
        };

        ch_1.setOnCheckedChangeListener(checkedChangeListener);
        ch_2.setOnCheckedChangeListener(checkedChangeListener);
        ch_3.setOnCheckedChangeListener(checkedChangeListener);
        ch_4.setOnCheckedChangeListener(checkedChangeListener);
        ch_5.setOnCheckedChangeListener(checkedChangeListener);
        ch_6.setOnCheckedChangeListener(checkedChangeListener);
        ch_7.setOnCheckedChangeListener(checkedChangeListener);
        ch_8.setOnCheckedChangeListener(checkedChangeListener);
        ch_9.setOnCheckedChangeListener(checkedChangeListener);
        ch_10.setOnCheckedChangeListener(checkedChangeListener);
        ch_11.setOnCheckedChangeListener(checkedChangeListener);
        ch_12.setOnCheckedChangeListener(checkedChangeListener);
        ch_13.setOnCheckedChangeListener(checkedChangeListener);
        ch_14.setOnCheckedChangeListener(checkedChangeListener);
        ch_15.setOnCheckedChangeListener(checkedChangeListener);
        ch_16.setOnCheckedChangeListener(checkedChangeListener);
        ch_17.setOnCheckedChangeListener(checkedChangeListener);
        ch_18.setOnCheckedChangeListener(checkedChangeListener);
        ch_19.setOnCheckedChangeListener(checkedChangeListener);
        ch_20.setOnCheckedChangeListener(checkedChangeListener);
        ch_21.setOnCheckedChangeListener(checkedChangeListener);
        ch_22.setOnCheckedChangeListener(checkedChangeListener);
        ch_23.setOnCheckedChangeListener(checkedChangeListener);
        ch_24.setOnCheckedChangeListener(checkedChangeListener);
        ch_25.setOnCheckedChangeListener(checkedChangeListener);
        ch_26.setOnCheckedChangeListener(checkedChangeListener);
        ch_27.setOnCheckedChangeListener(checkedChangeListener);
        ch_28.setOnCheckedChangeListener(checkedChangeListener);
        ch_29.setOnCheckedChangeListener(checkedChangeListener);
        ch_30.setOnCheckedChangeListener(checkedChangeListener);
        ch_31.setOnCheckedChangeListener(checkedChangeListener);
        ch_32.setOnCheckedChangeListener(checkedChangeListener);
        ch_33.setOnCheckedChangeListener(checkedChangeListener);
        ch_34.setOnCheckedChangeListener(checkedChangeListener);
        ch_35.setOnCheckedChangeListener(checkedChangeListener);
        ch_36.setOnCheckedChangeListener(checkedChangeListener);
    }

    private void addDataOnListView() {
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (docAdapter.getCount() > 0) docAdapter.clear();
                docAdapter.add("Не выбрано");
                for (DataSnapshot ds : snapshot.getChildren()) {
                    cardModel ps = ds.getValue(cardModel.class);
                    assert ps != null;
                    String maskedCardNumber = maskCardNumber(ps.cardNumb);
                    docAdapter.add(maskedCardNumber);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Обработка ошибки
            }
        };
        db.addValueEventListener(vListener);
        spinner.setAdapter(docAdapter);
    }

    private String maskCardNumber(String cardNumber) {
        int numDigitsToMask = cardNumber.length() - 4;
        StringBuilder maskedNumber = new StringBuilder();

        for (int i = 0; i < numDigitsToMask; i++) {
            maskedNumber.append("*");
        }

        maskedNumber.append(cardNumber.substring(numDigitsToMask));

        return maskedNumber.toString();
    }


    private  void spinnerSelectionNull() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ed.getText().toString().isEmpty() || ed.getText().toString().equals("Не выбрано")) {
                    ln.setVisibility(View.VISIBLE);
                } else {
                    ln.setVisibility(View.GONE);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        ed.addTextChangedListener( textWatcher);
    }

    private void getSelectedCardInstance(int selectedPosition) {
        DatabaseReference selectedCardRef = db;

        selectedCardRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count =1;
                for (DataSnapshot ds : snapshot.getChildren()) {

                    cardModel selectedCard = ds.getValue(cardModel.class);
                    if (selectedCard != null && count ==selectedPosition) {
                        // Используйте экземпляр класса выбранной карты по вашему усмотрению
                        // Например, вы можете отобразить данные карты на экране
                        String cardNumber = selectedCard.cardNumb;
                        String expirationDate = selectedCard.cardDate;
                        String cvc = selectedCard.cardCVC;
                        String cardholderName = selectedCard.cardMan;
                        // Далее выполните нужные вам действия с данными карты
                    }
                    count++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Обработка ошибки
            }
        });
    }

    private void setArrayList(){
        checkBoxList.add(ch_1);
        checkBoxList.add(ch_2);
        checkBoxList.add(ch_3);
        checkBoxList.add(ch_4);
        checkBoxList.add(ch_5);
        checkBoxList.add(ch_6);
        checkBoxList.add(ch_7);
        checkBoxList.add(ch_8);
        checkBoxList.add(ch_9);
        checkBoxList.add(ch_10);
        checkBoxList.add(ch_11);
        checkBoxList.add(ch_12);
        checkBoxList.add(ch_13);
        checkBoxList.add(ch_14);
        checkBoxList.add(ch_15);
        checkBoxList.add(ch_16);
        checkBoxList.add(ch_17);
        checkBoxList.add(ch_18);
        checkBoxList.add(ch_19);
        checkBoxList.add(ch_20);
        checkBoxList.add(ch_21);
        checkBoxList.add(ch_22);
        checkBoxList.add(ch_23);
        checkBoxList.add(ch_24);
        checkBoxList.add(ch_25);
        checkBoxList.add(ch_26);
        checkBoxList.add(ch_27);
        checkBoxList.add(ch_28);
        checkBoxList.add(ch_29);
        checkBoxList.add(ch_30);
        checkBoxList.add(ch_31);
        checkBoxList.add(ch_32);
        checkBoxList.add(ch_33);
        checkBoxList.add(ch_34);
        checkBoxList.add(ch_35);
        checkBoxList.add(ch_36);





    }


}