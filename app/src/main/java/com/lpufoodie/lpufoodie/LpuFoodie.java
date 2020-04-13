package com.lpufoodie.lpufoodie;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.arch.core.util.Function;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

interface LpuFoodie {
    int RC_SIGN_IN = 9001;
    FirebaseAuth LF_Auth = FirebaseAuth.getInstance();

    Set<Food> LF_Orders = new HashSet<>();

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

    java.util.function.Function<List<Food>,Double> LF_FinalSumFood = foods -> foods.stream().mapToDouble(food -> food.getCount()*food.getCost()).sum();

}
