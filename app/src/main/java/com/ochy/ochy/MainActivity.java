package com.ochy.ochy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ochy.ochy.cod.User;
import com.ochy.ochy.cod.docDataList;
import com.ochy.ochy.cod.docsModel;
import com.ochy.ochy.cod.getSplittedPathChild;
import com.ochy.ochy.dialog.deleteDialog;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sPref;

    TextView name, email;
    final String SAVED_DATA = "FIO_MAIL";
    ticketFragment ticketFragment = new ticketFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    MyCabFragment myCabFragment = new MyCabFragment();
    deleteDialog deleteDialog = new deleteDialog();
    CardFragment cardFragment = new CardFragment();
    MyTicketsFragment myTicketsFragment = new MyTicketsFragment();

    HelpFragment helpFragment = new HelpFragment();
    BottomNavigationView bnv;

    ArrayList<docDataList> arrayList;
    private  docAdapterListView list;
    private com.ochy.ochy.cod.ListView listView;
    private boolean isDataLoaded = false;
    MainActivity mainActivity;

    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            pushFragments("ticket", ticketFragment);
            pushFragments("myTicketsFragment", myTicketsFragment);
            pushFragments("profile", profileFragment);
            pushFragments("cab", myCabFragment);
            pushFragments("deleteDialog", deleteDialog);
            pushFragments("cardFragment", cardFragment);
            pushFragments("help", helpFragment);
        }
        setContentView(R.layout.activity_main);
        init();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, ticketFragment);
        fragmentTransaction.commit();
    }

    private void init(){
        getSplittedPathChild getSplittedPathChild = new getSplittedPathChild();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        sPref = getPreferences(Context.MODE_PRIVATE);
        bnv = findViewById(R.id.bottomNavigationView);
        bnv.setOnNavigationItemSelectedListener(getBottom());

    }


    @NonNull
    private BottomNavigationView.OnNavigationItemSelectedListener getBottom(){

        return (item ) ->{
            switch (item.getItemId()){
                case R.id.profile:
                    pushFragments("profile", profileFragment);
                    break;
                case R.id.search:
                    pushFragments("ticket", ticketFragment);
                    break;
                case R.id.dopomoga:
                    pushFragments("help", helpFragment);
                    break;
                case R.id.orders:
                    pushFragments("myTicketsFragment", myTicketsFragment);
                    break;
            }
            return true;
        };


    }


    public void pushFragments(String tag, Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        if (manager.findFragmentByTag(tag) == null) {
            ft.add(R.id.framelayout, fragment, tag);
        }

        Fragment profile = manager.findFragmentByTag("profile");
        Fragment ticket = manager.findFragmentByTag("ticket");
        Fragment cab = manager.findFragmentByTag("cab");
        Fragment deleteDialog = manager.findFragmentByTag("deleteDialog");
        Fragment cardFragment = manager.findFragmentByTag("cardFragment");
        Fragment helpFragment = manager.findFragmentByTag("help");
        Fragment myTicketsFragment = manager.findFragmentByTag("myTicketsFragment");



        if (profile != null)
            ft.hide(profile);
        if (ticket != null)
            ft.hide(ticket);
        if (cab !=null)
            ft.hide(cab);
        if(deleteDialog != null){
            ft.hide(deleteDialog);
        }
        if (cardFragment!= null){
            ft.hide(cardFragment);
        }
        if (helpFragment!= null){
            ft.hide(helpFragment);
        }

        if (myTicketsFragment !=null)
            ft.hide(myTicketsFragment);


        if (tag == "profile") {
            if (profile != null)
                ft.show(profile);
        }

        if (tag == "myTicketsFragment") {
            if (myTicketsFragment != null)
                ft.show(myTicketsFragment);
        }

        if (tag == "ticket") {
            if (ticket != null)
                ft.show(ticket);
        }

        if (tag == "cab") {
            if (cab != null)
                ft.show(cab);
        }

        if (tag == "deleteDialog") {
            if (deleteDialog != null)
                ft.show(deleteDialog);
        }

        if (tag == "cardFragment") {
            if (cardFragment != null)
                ft.show(cardFragment);
        }

        if (tag == "help") {
            if (helpFragment != null)
                ft.show(helpFragment);
        }
        ft.commitAllowingStateLoss();
    }






}