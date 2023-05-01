package com.ochy.ochy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ochy.ochy.cod.cardsDataList;

import java.util.ArrayList;

public class cardAdapterListView  extends ArrayAdapter<cardsDataList> {
    public cardAdapterListView(@NonNull Context context, ArrayList<cardsDataList> arrayList){
        super(context, 0, arrayList);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // convertView which is recyclable view
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.cards_view, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        cardsDataList currentNumberPosition = getItem(position);

        // then according to the position of the view assign the desired image for the same


        // then according to the position of the view assign the desired TextView 1 for the same
        TextView textView1 = currentItemView.findViewById(R.id.ed2);
        textView1.setText(currentNumberPosition.getCardName());


        // then return the recyclable view
        return currentItemView;
    }
}
