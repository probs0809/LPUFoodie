package com.lpufoodie.lpufoodie;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

class CartItemAdapter extends ArrayAdapter<Food> implements LpuFoodie {
    private Context context;

    public CartItemAdapter(@NonNull Context context, int resource) {
        super(context, resource, new ArrayList<>(LF_Orders));
        this.context = context;
        System.out.println(LF_Orders.size());
        LF_CartList.addAll(LF_Orders);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final Food food = LF_CartList.get(position);
        View rowView = inflater.inflate(R.layout.cart_item, null);
        ImageView image = rowView.findViewById(R.id.image);
        TextView restaurant_name = rowView.findViewById(R.id.restaurant_name);
        TextView item_name = rowView.findViewById(R.id.item_name);
        TextView item_cost = rowView.findViewById(R.id.item_cost);
        Spinner spinner = rowView.findViewById(R.id.item_quantity);
        final Integer[] integer = new Integer[]{1, 2, 3, 4, 5};
        ArrayAdapter<Integer> aa = new ArrayAdapter<>(this.context, R.layout.support_simple_spinner_dropdown_item, integer);
        aa.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(aa);

        LF_LoadImage.accept(food.getPictureUri(), image);
        item_name.setText(food.getName());
        restaurant_name.setText(MainActivity.restaurant.getName());
        item_cost.setText(("Rs." + food.getCost()));
        LF_FadeInAnimation.apply(rowView);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                item_cost.setText(("Rs." + (food.getCost() * integer[i])));
                LF_CartList.get(position).setCount(integer[i]);
                Cart.cartValue.setText(("Final Value : \t Rs. " + LF_FinalSumFood.apply(LF_CartList)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return rowView;
    }
}

class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewAdapter> implements LpuFoodie {

    private Button cart;
    private List<Food> foodList;

    FoodAdapter(List<Food> foodList, Button button) {
        LF_Orders.clear();
        this.foodList = foodList;
        this.cart = button;
    }

    @NonNull
    @Override
    public FoodAdapter.ViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.food, parent, false);
        return new ViewAdapter(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final FoodAdapter.ViewAdapter holder, int position) {
        final Food food = foodList.get(position);
        holder.name.setText(food.getName());
        holder.cost.setText(String.valueOf(food.getCost()));
        holder.category.setText(food.getCategory());
        LF_LoadImage.accept(food.getPictureUri(), holder.imageView);
        holder.button.setOnClickListener(view -> {
            MainActivity.LF_Orders.add(food);
            cart.findViewById(R.id.go_to_cart).setVisibility(View.VISIBLE);
        });
        YoYo.with(Techniques.FadeInUp)
                .duration(700)
                .playOn(holder.itemView);

    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    static class ViewAdapter extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, cost, category;
        ImageButton button;

        ViewAdapter(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
            name = itemView.findViewById(R.id.name);
            cost = itemView.findViewById(R.id.cost);
            category = itemView.findViewById(R.id.category);
            button = itemView.findViewById(R.id.button);

        }
    }
}

class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.ViewAdapter> implements LpuFoodie {
    private Context context;
    private FragmentManager fragmentManager;
    protected View view;
    private List<Restaurant> restaurants = new ArrayList<>();

    RestaurantsAdapter(Context context, FragmentManager fragmentManager,View view) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.view = view;
        RestaurantDataLoader rd = new RestaurantDataLoader();
        rd.execute();
    }

    RestaurantsAdapter(Context context, FragmentManager fragmentManager, View view,String keywords) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.view = view;
        restaurants = LF_Restaurants.stream()
                                    .filter(r -> r.getKeywords().toLowerCase().contains(keywords.toLowerCase()) )
                                    .collect(Collectors.toList());
    }

    @NonNull
    @Override
    public RestaurantsAdapter.ViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.restaurant, parent, false);
        return new ViewAdapter(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAdapter holder, final int position) {
        final Restaurant res = restaurants.get(position);
        LF_LoadImage.accept(res.getPictureUri(), holder.imageView);
        int rate = (int) res.getRating();
        holder.name.setText(res.getName());
        holder.subTitle.setText(res.getSubTitle());
        holder.rating.setText(String.valueOf(res.getRating()));
        for (int i = 0; i < rate; i++) {
            holder.st[i].setVisibility(View.VISIBLE);
        }
        holder.offer.setText(res.getOffer());
        holder.itemView.setVisibility(View.VISIBLE);
        holder.itemView.setOnClickListener(view -> {
            FoodMenu.newInstance(res, context, fragmentManager);
            Intent i = new Intent(context, FoodMenu.class);
            context.startActivity(i);
        });
        LF_FadeInAnimation.apply(holder.itemView);
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    static class ViewAdapter extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageView[] st = new ImageView[5];
        TextView name, subTitle, offer, rating;

        ViewAdapter(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
            name = itemView.findViewById(R.id.name);
            subTitle = itemView.findViewById(R.id.subtitle);
            rating = itemView.findViewById(R.id.rating);
            offer = itemView.findViewById(R.id.offer);

            st[0] = itemView.findViewById(R.id.st1);
            st[1] = itemView.findViewById(R.id.st2);
            st[2] = itemView.findViewById(R.id.st3);
            st[3] = itemView.findViewById(R.id.st4);
            st[4] = itemView.findViewById(R.id.st5);
        }
    }

    class RestaurantDataLoader extends AsyncTask<Object, Integer, Void> {
        @Override
        protected Void doInBackground(Object... o) {
            LF_DatabaseReference.apply("Restaurant").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    view.setVisibility(View.GONE);
                    LF_Restaurants.clear();
                    for (DataSnapshot rDataSnap : dataSnapshot.getChildren()) {
                        Restaurant restaurant = rDataSnap.getValue(Restaurant.class);
                        LF_Restaurants.add(restaurant);
                    }
                    restaurants = LF_Restaurants;
                    RestaurantsAdapter.this.notifyDataSetChanged();
                    view.setVisibility(View.VISIBLE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            return null;
        }
    }
}