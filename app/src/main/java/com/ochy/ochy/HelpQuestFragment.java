package com.ochy.ochy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HelpQuestFragment extends Fragment {
    int variant=0;
    View first, second, third, fouth, fifth, sixth, seventh, eighth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_help_quest, container, false);
        init(v);
        return v;
    }

    private void init(View v){
        first = v.findViewById(R.id.first_help);
        second = v.findViewById(R.id.secon_help);
        third = v.findViewById(R.id.third_help);
        fouth = v.findViewById(R.id.foutrh_help);
        fifth = v.findViewById(R.id.fif);
        sixth = v.findViewById(R.id.six);
        seventh = v.findViewById(R.id.seven);
        Bundle args = new Bundle();

        switch (variant){
            case 0:
                first.setVisibility(View.VISIBLE);
                break;

            case 1:
                second.setVisibility(View.VISIBLE);
                break;

            case 2:
                third.setVisibility(View.VISIBLE);
                break;


            case 3:
                fouth.setVisibility(View.VISIBLE);
                break;

            case 4:
                fifth.setVisibility(View.VISIBLE);
                break;

            case 5:
                sixth.setVisibility(View.VISIBLE);
                break;

            case 6:
                seventh.setVisibility(View.VISIBLE);
                break;

            case 7:

                break;
        }
    }
}