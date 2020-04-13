package com.lpufoodie.lpufoodie;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.common.util.BiConsumer;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements LpuFoodie {
    static Restaurant restaurant;
    static ViewPager vpPager;
    BottomNavigationView bottomNavigationView;
    Handler handler;
    float dX;
    float dY;
    int lastAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = new Intent();
        boolean bg = i.getBooleanExtra("Cart", false);
        handler = new Handler(getMainLooper());
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        vpPager = findViewById(R.id.vpPager);
        MyPagerAdapter adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    vpPager.setCurrentItem(0, true);
                    return true;

                case R.id.navigation_search:
                    vpPager.setCurrentItem(1, true);
                    return true;

                case R.id.navigation_cart:
                    vpPager.setCurrentItem(2, true);
                    return true;

                case R.id.navigation_account:
                    vpPager.setCurrentItem(3, true);
                    return true;

                default:
                    break;
            }
            return false;
        });

        YoYo.with(Techniques.BounceInUp)
                .duration(1000)
                .repeat(1)
                .playOn(bottomNavigationView);

        YoYo.with(Techniques.BounceInDown)
                .duration(1000)
                .repeat(1)
                .playOn(findViewById(R.id.header));

        vpPager.setCurrentItem(bg ? 2 : 0, true);

        FloatingActionButton fab = findViewById(R.id.fab);
        setFab.accept(LF_User.get(),fab);


        fab.setOnTouchListener((view, event) -> {
            view.performClick();
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    dX = view.getX() - event.getRawX();
                    dY = view.getY() - event.getRawY();

                    lastAction = MotionEvent.ACTION_DOWN;
                    break;

                case MotionEvent.ACTION_MOVE:
                    view.setY(event.getRawY() + dY);
//                    view.setX(event.getRawX() + dX);
                    lastAction = MotionEvent.ACTION_MOVE;
                    break;

                case MotionEvent.ACTION_UP:
                    if (lastAction == MotionEvent.ACTION_DOWN)
                        locate(view);
                    break;

                default:
                    return false;
            }

            return true;
        });
    }



    BiConsumer<FirebaseUser,FloatingActionButton> setFab = (u,fab) -> {
        if(u != null)
            LF_DatabaseReference.apply("Users/"+u.getUid()).child("orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Boolean b = dataSnapshot.getValue(Boolean.class);
                assert b != null;
                if (b.equals(Boolean.FALSE)){
                    fab.hide();
                }else{
                    fab.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    };
    @Override
    public void onBackPressed() {
        vpPager.setCurrentItem(0, true);
    }

    public void locate(View view) {
        Snackbar.make(findViewById(R.id.mainActivity), "Feature Not Yet Available", Snackbar.LENGTH_SHORT).setAction("Retry", view1 -> { }).show();
    }

    public void logout(View view) {
        LF_Auth.signOut();
        LF_GoogleSignInClient.apply(this).signOut().addOnCompleteListener(this, task -> vpPager.setCurrentItem(0,true));
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        MyPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        // Returns total number of pages.
        @Override
        public int getCount() {
            return 4;
        }

        // Returns the fragment to display for a particular page.
        @NonNull
        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 1:
                    return Search.newInstance("", "");
                case 2:
                    return new Cart();
                case 3:
                    return new Account();
                default:
                    return Home.newInstance(MainActivity.this.getSupportFragmentManager());
            }


        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Tab " + position;
        }


    }
}