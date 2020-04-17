package com.lpufoodie.lpufoodie;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;


public class Cart extends Fragment implements LpuFoodie {
    static TextView cartValue;
    private ListView listView;
    private Handler handler = new Handler(Looper.getMainLooper());

    public Cart() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_cart, container, false);
        listView = view.findViewById(R.id.lv1);
        cartValue = view.findViewById(R.id.cart_value);
        final Thread set = new Thread(() -> handler.post(() -> setCart(view)));
        set.start();

        view.findViewById(R.id.clear_cart).setOnClickListener(v -> {
            LF_ClearCart.get();
            setCart(view);
        });

        view.findViewById(R.id.order).setOnClickListener(view1 -> {
            DatabaseReference dr = LF_DatabaseReference.apply("Users/"+LF_Auth.getUid());
            if(LF_CartList.size()>0 && LF_Booleans.get("orders") == false){
                dr.child("orders").setValue(true);
                LF_CartList.forEach((food)-> dr.child("deliver").child(Objects.requireNonNull(dr.child("deliver").push().getKey())).setValue(food));
                LF_ClearCart.get();
                setCart(view);
            }else{
                Snackbar.make(getActivity().findViewById(R.id.mainActivity),"Your deliveries are on the way",Snackbar.LENGTH_LONG).show();
            }
        });

        return view;
    }

    private void setCart(View view) {
        if (!LF_Orders.isEmpty()) {
            CartItemAdapter cartItemAdapter = new CartItemAdapter(Objects.requireNonNull(getContext()), R.layout.cart_item);
            listView.setAdapter(cartItemAdapter);
            changeVisibility
                    .accept(Boolean.TRUE,view);

        } else {
            changeVisibility
                    .accept(Boolean.FALSE,view);
            YoYo.with(Techniques.Shake)
                    .repeat(2)
                    .duration(500)
                    .playOn(view.findViewById(R.id.empty));
        }
    }

    private BiConsumer<Boolean,View> changeVisibility = (b, view) -> {
        listView.setVisibility(b ? View.VISIBLE : View.GONE);
        view.findViewById(R.id.empty).setVisibility(b ? View.GONE : View.VISIBLE);
        view.findViewById(R.id.bottom_button).setVisibility(b ? View.VISIBLE : View.GONE);
        cartValue.setVisibility(b ? View.VISIBLE : View.GONE);
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


}