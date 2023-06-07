package com.ochy.ochy;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ochy.ochy.cod.cardsDataList;
import com.ochy.ochy.cod.docDataList;
import com.ochy.ochy.cod.docsModel;
import com.ochy.ochy.cod.getSplittedPathChild;

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


        currentItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSplittedPathChild ge = new getSplittedPathChild();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("user").child(ge.getSplittedPathChild(user.getEmail())).child("docs");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int index = 0;
                        for (DataSnapshot ps: snapshot.getChildren()){
                           if (position == index){
                                String path = ps.getKey();
                               EditDocsFragment editDocsFragment = new EditDocsFragment();
                                editDocsFragment.PATH = path;
                               FragmentManager fragmentManager = ((MainActivity)getContext()).getSupportFragmentManager();
                               FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                               // Replace the current fragment with the new fragment
                               fragmentTransaction.replace(R.id.framelayout, editDocsFragment);
                               // Add the transaction to the back stack (optional)
                               fragmentTransaction.addToBackStack(null);
                               // Commit the transaction
                               fragmentTransaction.commit();

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



        // then return the recyclable view
        return currentItemView;
    }
}
