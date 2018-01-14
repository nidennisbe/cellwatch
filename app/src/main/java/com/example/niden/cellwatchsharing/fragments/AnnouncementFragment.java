package com.example.niden.cellwatchsharing.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.niden.cellwatchsharing.R;
import com.example.niden.cellwatchsharing.database.PostEntityDatabase;
import com.example.niden.cellwatchsharing.database.firebase;
import com.firebase.ui.database.FirebaseListAdapter;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by niden on 16-Nov-17.
 * Admin side for inserting task
 */

public class AnnouncementFragment extends Fragment {
    View parentHolder;
    private Activity referenceActivity;
    private EditText txTaskName,txDescription,txAddress,txSuburb,txClass;
    private ListView listOfMessages;
    private FirebaseListAdapter<PostEntityDatabase> mAdapter;
    public TextView displayEmail;
    public static FirebaseDatabase database;
    firebase mFirebase = new firebase();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        referenceActivity = getActivity();
        parentHolder = inflater.inflate(R.layout.fragment_anouncement_layout, container, false);
       // mFirebase.makeFirebaseWorkOffline();


        txTaskName = (EditText) parentHolder.findViewById(R.id.editTextTaskName);
        txAddress = (EditText) parentHolder.findViewById(R.id.editTextAddress);
        txDescription = (EditText) parentHolder.findViewById(R.id.editTextDescription);
        txSuburb = (EditText) parentHolder.findViewById(R.id.editTextSuburb);
        txClass = (EditText) parentHolder.findViewById(R.id.editTextClass);


        final Button btnPost = (Button) parentHolder.findViewById(R.id.fragment_announcement_btn_post);

        btnPost.setEnabled(false);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFirebase.insertPostToFirebase(txTaskName,txClass,txDescription,txAddress,txSuburb);
                txTaskName.setText("");
                txAddress.setText("");
                txDescription.setText("");
                txSuburb.setText("");
                txClass.setText("");
            }
        });

        //Listener for disable and enable button item_post depend on EditText
        txTaskName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.toString().trim().length() == 0) {
                    btnPost.setEnabled(false);
                    btnPost.setBackgroundResource(R.drawable.style_button_border_only);
                    btnPost.setTextColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    btnPost.setEnabled(true);
                    btnPost.setBackgroundResource(R.color.colorPrimary);
                    btnPost.setTextColor(getResources().getColor(R.color.colorTextLight));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        return parentHolder;

    }


}
