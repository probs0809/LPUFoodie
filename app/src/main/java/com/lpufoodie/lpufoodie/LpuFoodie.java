package com.lpufoodie.lpufoodie;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

interface LpuFoodie {
    int RC_SIGN_IN = 9001;

    FirebaseAuth LF_Auth = FirebaseAuth.getInstance();

    Set<Food> LF_Orders = new HashSet<>();

    List<Food> LF_CartList = new ArrayList<>();

    Map<String, Boolean> LF_Booleans = new HashMap<>();

    Supplier<Void> LF_ClearCart = () -> {
        LF_CartList.clear();
        LF_Orders.clear();
        return null;
    };

    Function<View, YoYo.YoYoString> LF_FadeInAnimation = YoYo.with(Techniques.FadeIn).delay(500).duration(700)::playOn;

    BiConsumer<String, ImageView> LF_LoadImage = (uri, imageView) -> Picasso.get().load(uri).fit().centerCrop().into(imageView);

    Function<Context, GoogleSignInClient> LF_GoogleSignInClient = (c) -> {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(c.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        return GoogleSignIn.getClient(c, gso);
    };

    Supplier<FirebaseUser> LF_User = () -> FirebaseAuth.getInstance().getCurrentUser();

    Function<String, DatabaseReference> LF_DatabaseReference = FirebaseDatabase.getInstance()::getReference;

    Function<List<Food>, Double> LF_FinalSumFood = foods -> foods.stream().mapToDouble(food -> food.getCount() * food.getCost()).sum();

    com.google.android.gms.common.util.BiConsumer<FirebaseUser, FloatingActionButton> setFab = (u, fab) -> {
        if (u != null)
            LF_DatabaseReference.apply("Users/" + u.getUid()).child("orders").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Boolean b = dataSnapshot.getValue(Boolean.class);
                    assert b != null;
                    if (b.equals(Boolean.FALSE)) {
                        LF_Booleans.put("orders", false);
                        fab.hide();
                    } else {
                        LF_Booleans.put("orders", true);
                        fab.show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
    };
}
