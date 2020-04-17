package com.lpufoodie.lpufoodie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class Account extends Fragment implements LpuFoodie {
    public Account() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account, container, false);
        TextView name = v.findViewById(R.id.name), number_email = v.findViewById(R.id.email_number);
        FirebaseUser user = LF_User.get();
        if (user != null) {
            new Thread(() -> new Handler(Looper.getMainLooper()).post(() -> LF_DatabaseReference.apply("/Users/" + user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    UserData userData = dataSnapshot.getValue(UserData.class);
                    name.setText(Objects.requireNonNull(userData).user_name);
                    number_email.setText((userData.phone_number + "." + userData.email));

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            }))).start();
        } else
            startActivity(new Intent(getActivity(), LoginActivity.class));
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}

class UserData {
    public String user_name = " ", email = " ", photo_url = "", phone_number = "";

    UserData() {
    }
}
