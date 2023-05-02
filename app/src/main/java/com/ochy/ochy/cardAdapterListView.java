package com.ochy.ochy;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ochy.ochy.cod.cardsDataList;

import java.util.ArrayList;

public class cardAdapterListView  extends ArrayAdapter<cardsDataList> {

    private OnItemDeleteListener onItemDeleteListener;

    public cardAdapterListView(@NonNull Context context, ArrayList<cardsDataList> arrayList){
        super(context, 0, arrayList);
    }


    public interface OnItemDeleteListener {
        void onItemDelete(int position);
    }

    public void setOnItemDeleteListener(OnItemDeleteListener onItemDeleteListener) {
        this.onItemDeleteListener = onItemDeleteListener;
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
        EditText textView1 = currentItemView.findViewById(R.id.ed2);
        textView1.setText(currentNumberPosition.getCardNumber());
        Drawable[] drawables = textView1.getCompoundDrawables();
        Drawable drawableRight = drawables[2].mutate();
        drawableRight.setCallback(new Drawable.Callback() {
            @Override
            public void invalidateDrawable(Drawable who) {
                textView1.invalidate();
            }
            @Override
            public void scheduleDrawable(Drawable who, Runnable what, long when) {
                textView1.postDelayed(what, when);
            }
            @Override
            public void unscheduleDrawable(Drawable who, Runnable what) {
                textView1.removeCallbacks(what);
            }
        });
        textView1.setCompoundDrawables(drawables[0], drawables[1], drawableRight, drawables[3]);
        textView1.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(getContext(), "sssss", Toast.LENGTH_SHORT).show();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (textView1.getRight() - drawableRight.getBounds().width())) {
                        // Обработка нажатия на Drawable Right
                        if (onItemDeleteListener != null) {
                            Toast.makeText(getContext(), "sssss", Toast.LENGTH_SHORT).show();
                            onItemDeleteListener.onItemDelete(position);
                        }
                        return true;
                    }
                }
                return false;
            }
        });


        // then return the recyclable view
        return currentItemView;
    }
}
