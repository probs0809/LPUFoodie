package com.lpufoodie.lpufoodie;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.crystal.crystalpreloaders.widgets.CrystalPreloader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

import static com.lpufoodie.lpufoodie.MainActivity.fab;


public class Home extends Fragment {
    @SuppressLint("StaticFieldLeak")
    private static Context ARG_PARAM1;
    private static FragmentManager ARG_PARAM2;
    private Context context;
    private FragmentManager fragmentManager;
    private RecyclerView rv;
    private RecyclerView.LayoutManager layoutManager;
    private RestaurantsAdapter ra;
    private Button cart;
    static CrystalPreloader crystalPreloader;

    Handler handler = new Handler(Looper.getMainLooper());
    public Home() {
        // Required empty public constructor
    }

    public static Home newInstance(Context param1, FragmentManager param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        ARG_PARAM1 = param1;
        ARG_PARAM2 = param2;
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            context = ARG_PARAM1;
            fragmentManager = ARG_PARAM2;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        crystalPreloader = getActivity().findViewById(R.id.loader);
        view.setVisibility(View.INVISIBLE);
        rv = view.findViewById(R.id.rv1);
        rv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                if(i3 < 0){
                    fab.hide();
                }else{
                    fab.show();
                }
            }
        });
        final List<Restaurant> restaurants = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Restaurant");
                        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                crystalPreloader.setVisibility(View.VISIBLE);
                                for (DataSnapshot rDataSnap: dataSnapshot.getChildren()) {
                                    restaurants.add(rDataSnap.getValue(Restaurant.class));

                                }

                                ra = new RestaurantsAdapter(restaurants,getContext(),fragmentManager);

                                rv.setAdapter(ra);
                                view.setVisibility(View.VISIBLE);

                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });
            }
        }).start();


        return view;
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
