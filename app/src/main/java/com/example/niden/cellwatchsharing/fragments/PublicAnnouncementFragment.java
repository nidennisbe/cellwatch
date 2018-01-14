//package com.example.niden.cellwatchsharing.fragments;
//
//import android.app.Activity;
//import android.app.Fragment;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.example.niden.cellwatchsharing.R;
//import com.example.niden.cellwatchsharing.database.PostEntityDatabase;
//import com.example.niden.cellwatchsharing.database.firebase;
//import com.firebase.ui.database.FirebaseListAdapter;
//import com.github.marlonlom.utilities.timeago.TimeAgo;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//
///**
// * Created by niden on 16-Nov-17.
// */
//
//public class PublicAnnouncementFragment extends Fragment {
//    View parentHolder;
//    private Activity referenceActivity;
//    private EditText txMessage;
//    private ListView listOfMessages;
//    private FirebaseListAdapter<PostEntityDatabase> mAdapter;
//    public TextView displayEmail;
//    public static FirebaseDatabase database;
//    firebase mFirebase = new firebase();
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        referenceActivity = getActivity();
//        parentHolder = inflater.inflate(R.layout.fragment_anouncement_layout, container, false);
//       // mFirebase.makeFirebaseWorkOffline();
//
//
//        txMessage = (EditText) parentHolder.findViewById(R.id.editTextMessage);
//        listOfMessages = (ListView) parentHolder.findViewById(R.id.list_of_messages);
//        final Button btnPost = (Button) parentHolder.findViewById(R.id.fragment_announcement_btn_post);
//        displayEmail = (TextView) parentHolder.findViewById(R.id.textViewEmail);
//
//        btnPost.setEnabled(false);
//        btnPost.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mFirebase.insertPostToFirebase(txMessage);
//                txMessage.setText("");
//            }
//        });
//        displayChatMessages();
//
//        //Listener for disable and enable button item_post depend on EditText
//        txMessage.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                if (charSequence.toString().trim().length() == 0) {
//                    btnPost.setEnabled(false);
//                    btnPost.setBackgroundResource(R.drawable.style_button_border_only);
//                    btnPost.setTextColor(getResources().getColor(R.color.colorPrimary));
//                } else {
//                    btnPost.setEnabled(true);
//                    btnPost.setBackgroundResource(R.color.colorPrimary);
//                    btnPost.setTextColor(getResources().getColor(R.color.colorTextLight));
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//            }
//        });
//        return parentHolder;
//
//    }
//
//    public void displayChatMessages() {
//        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("public_chat");
//        mAdapter = new FirebaseListAdapter<PostEntityDatabase>(getActivity(), PostEntityDatabase.class,
//                R.layout.item_post, mRef) {
//            @Override
//            protected void populateView(View v, PostEntityDatabase model, int position) {
//                // Get references to the DialogsUtils of item_message.xmle.xml
//                TextView messageText = (TextView) v.findViewById(R.id.message_text);
//                TextView messageUser = (TextView) v.findViewById(R.id.message_user);
//                TextView messageTime = (TextView) v.findViewById(R.id.message_time);
//                //setText
//                messageText.setText(model.getMessageText());
//                messageUser.setText(model.getMessageUser());
//                //Covert time to ago
//                long timeInMillis = System.currentTimeMillis();
//                String CovertMessageTime = TimeAgo.from(model.getMessageTime());
//                messageTime.setText(CovertMessageTime);
//                //listOfMessages.setStackFromBottom(true);
//            }
//        };
//        listOfMessages.setAdapter(mAdapter);
//    }
//
//}
