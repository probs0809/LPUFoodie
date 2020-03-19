package com.lpufoodie.lpufoodie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewAdapter> {

    private List<Food> foodList;
    private Context context;
    private FragmentManager fragmentManager;
    Button cart;

    FoodAdapter(List<Food> foodList, Context context, FragmentManager fragmentManager, Button button){
        MainActivity.orders.clear();
        this.foodList = foodList;
        this.context = context;
        this.fragmentManager = fragmentManager;
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
        Picasso.get().load(food.getPictureUri()).into(holder.imageView);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.orders.add(food);
                cart.findViewById(R.id.go_to_cart).setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class ViewAdapter extends RecyclerView.ViewHolder {
        ImageView imageView ;
        TextView name,cost,category;
        ImageButton button;

        public ViewAdapter(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
            name = itemView.findViewById(R.id.name);
            cost = itemView.findViewById(R.id.cost);
            category = itemView.findViewById(R.id.category);
            button = itemView.findViewById(R.id.button);

        }
    }
}

