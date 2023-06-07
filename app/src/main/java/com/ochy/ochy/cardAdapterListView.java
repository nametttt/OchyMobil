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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ochy.ochy.cod.cardsDataList;
import com.ochy.ochy.cod.getSplittedPathChild;
import com.ochy.ochy.dialog.deleteCard;
import com.ochy.ochy.dialog.deleteDialog;

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

    public OnItemDeleteListener getOnItemDeleteListener() {
        return onItemDeleteListener;
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


        textView1.setClickable(true);

        currentItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSplittedPathChild getSplittedPathChild = new getSplittedPathChild();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("user").child(getSplittedPathChild.getSplittedPathChild(FirebaseAuth.getInstance().getCurrentUser().getEmail())).child("cards");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int index =0;
                        for(DataSnapshot ds: snapshot.getChildren()){
                            if (ds !=null){
                                if (index == position){
                                    deleteCard myDialogFragment = new deleteCard();
                                    myDialogFragment.path = ds.getKey();
                                    FragmentManager manager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                                    //myDialogFragment.show(manager, "dialog");

                                    FragmentTransaction transaction = manager.beginTransaction();
                                    myDialogFragment.show(transaction, "card");
                                }
                            }
                            index++;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        // Set long click listener on the text


        // then return the recyclable view
        return currentItemView;
    }
}
