package com.ochy.ochy;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ochy.ochy.cod.cardsDataList;
import com.ochy.ochy.cod.docDataList;

import java.util.ArrayList;
import java.util.List;

public class docAdapterListView  extends ArrayAdapter<docDataList> {


    private cardAdapterListView.OnItemDeleteListener onItemDeleteListener;

    public docAdapterListView(@NonNull Context context, ArrayList<docDataList> arrayList){
        super(context, 0, arrayList);
    }


    public interface OnItemDeleteListener {
        void onItemDelete(int position);
    }



    public void setOnItemDeleteListener(cardAdapterListView.OnItemDeleteListener onItemDeleteListener) {
        this.onItemDeleteListener = onItemDeleteListener;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // convertView which is recyclable view
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.docs_view, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        docDataList currentNumberPosition = getItem(position);

        // then according to the position of the view assign the desired image for the same


        // then according to the position of the view assign the desired TextView 1 for the same
        EditText textView1 = currentItemView.findViewById(R.id.doc_name);
        TextView text = currentItemView.findViewById(R.id.doc_type);
        text.setText(currentNumberPosition.getDocType());
        textView1.setText(currentNumberPosition.getDocDocFIO());




        // then return the recyclable view
        return currentItemView;
    }
}
