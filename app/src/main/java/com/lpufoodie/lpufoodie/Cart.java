package com.lpufoodie.lpufoodie;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Cart extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static TextView cartValue;
    ListView listView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Handler handler = new Handler(Looper.getMainLooper());
    public Cart() {
        // Required empty public constructor
    }


    public static Cart newInstance(String param1, String param2) {
        Cart fragment = new Cart();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_cart, container, false);
        listView = view.findViewById(R.id.lv1);
        cartValue = view.findViewById(R.id.cart_value);
        final Thread set =  new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        setCart(view);
                    }
                });
            }
        });
        set.start();

        view.findViewById(R.id.clear_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.orders.clear();
                setCart(view);
            }
        });



        return view;
    }

    private void setCart(View view){
        List<Food> cartList = new ArrayList<>();
        if(!MainActivity.orders.isEmpty()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                cartList = MainActivity.orders.stream().collect(Collectors.<Food>toList());
            }else {
                cartList.addAll(MainActivity.orders);
            }
            CartItemAdapter cartItemAdapter = new CartItemAdapter(getContext(),R.layout.cart_item,cartList);
            listView.setAdapter(cartItemAdapter);
            view.findViewById(R.id.empty).setVisibility(View.GONE);
            view.findViewById(R.id.bottom_button).setVisibility(View.VISIBLE);
            cartValue.setVisibility(View.VISIBLE);
            listView.setVisibility(View.VISIBLE);

        }else{
            listView.setVisibility(View.GONE);
            view.findViewById(R.id.empty).setVisibility(View.VISIBLE);
            view.findViewById(R.id.bottom_button).setVisibility(View.GONE);
            cartValue.setVisibility(View.GONE);
            YoYo.with(Techniques.Shake)
                    .repeat(2)
                    .duration(500)
                    .playOn(view.findViewById(R.id.empty));
        }




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


class CartItemAdapter extends ArrayAdapter<Food>{
    double finalCost = 0;
    private List<Food> cartList;
    List<Integer> lastCount = new ArrayList<>();


    Context context;
    public CartItemAdapter(@NonNull Context context, int resource, List<Food> cartList) {
        super(context, resource, cartList);
        this.cartList = cartList;
        this.context = context;
        System.out.println(cartList.size());
        for (Food c:cartList) {
            lastCount.add(0);
        }

    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final Food food = cartList.get(position);
        View rowView = inflater.inflate(R.layout.cart_item,null);
        ImageView image = rowView.findViewById(R.id.image);
        TextView restaurant_name = rowView.findViewById(R.id.restaurant_name);
        TextView item_name = rowView.findViewById(R.id.item_name);
        TextView item_cost = rowView.findViewById(R.id.item_cost);
        Spinner spinner = rowView.findViewById(R.id.item_quantity);
        final Integer integer[] = new Integer[]{1,2,3,4,5};
        ArrayAdapter<Integer> aa = new ArrayAdapter<>(this.context,R.layout.support_simple_spinner_dropdown_item,integer);
        aa.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(aa);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(lastCount.get(position) != 0) {
                    finalCost -= lastCount.get(position) * food.getCost();
                }
                finalCost += food.getCost()*integer[i];
                lastCount.add(position,integer[i]);
                Toast.makeText(context,""+finalCost,Toast.LENGTH_LONG).show();
                Cart.cartValue.setText("Final Value : \t "+finalCost);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Picasso.get().load(food.getPictureUri())
                .fit()
                .centerCrop()
                .into(image);
        item_name.setText(food.getName());
        restaurant_name.setText(MainActivity.restaurant.getName());
        item_cost.setText("Rs."+food.getCost());
        YoYo.with(Techniques.FadeIn)
                .duration(700)
                .playOn(rowView);

        return rowView;
    }
}
