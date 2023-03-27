package com.ochy.ochy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    ticketFragment ticketFragment = new ticketFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    BottomNavigationView bnv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            pushFragments("ticket", ticketFragment);
            pushFragments("profile", profileFragment);


        }
        setContentView(R.layout.activity_main);
        init();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, ticketFragment);
        fragmentTransaction.commit();
    }

    private void init(){
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


        if (profile != null)
            ft.hide(profile);
        if (ticket != null)
            ft.hide(ticket);

        if (tag == "profile") {
            if (profile != null)
                ft.show(profile);
        }

        if (tag == "ticket") {
            if (ticket != null)
                ft.show(ticket);
        }
        ft.commitAllowingStateLoss();
    }
}