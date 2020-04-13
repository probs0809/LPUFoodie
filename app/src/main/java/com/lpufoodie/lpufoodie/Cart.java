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

import androidx.fragment.app.Fragment;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;


public class Cart extends Fragment {
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
            MainActivity.LF_Orders.clear();
            setCart(view);
        });
        return view;
    }

    private void setCart(View view) {
        List<Food> cartList;
        if (!MainActivity.LF_Orders.isEmpty()) {
            cartList = new ArrayList<>(MainActivity.LF_Orders);
            CartItemAdapter cartItemAdapter = new CartItemAdapter(Objects.requireNonNull(getContext()), R.layout.cart_item, cartList);
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


