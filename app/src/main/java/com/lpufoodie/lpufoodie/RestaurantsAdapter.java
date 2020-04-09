package com.lpufoodie.lpufoodie;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;
import java.util.List;

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.ViewAdapter> {
    private List<Restaurant> restaurants;
    private Context context;
    private FragmentManager fragmentManager;


    RestaurantsAdapter(List<Restaurant> restaurants, Context context, FragmentManager fragmentManager) {
        this.restaurants = restaurants;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }



    class ViewAdapter extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, subTitle, offer,rating;
        ViewAdapter(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
            name = itemView.findViewById(R.id.name);
            subTitle = itemView.findViewById(R.id.subtitle);
            rating = itemView.findViewById(R.id.rating);
            offer = itemView.findViewById(R.id.offer);
        }
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
        Picasso.get()
                .load(res.getPictureUri())
                .fit()
                .into(holder.imageView);

        holder.name.setText(res.getName());
        holder.subTitle.setText(res.getSubTitle());
        holder.rating.setText(String.valueOf(res.getRating()));
        holder.offer.setText(res.getOffer());
        holder.itemView.setVisibility(View.VISIBLE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FoodMenu.newInstance(res,context,fragmentManager);
                Intent i = new Intent(context,FoodMenu.class);
                context.startActivity(i);

            }
        });
        YoYo.with(Techniques.FadeIn)
                .duration(700)
                .playOn(holder.itemView);
    }


    @Override
    public int getItemCount() {
        return restaurants.size();
    }

}


