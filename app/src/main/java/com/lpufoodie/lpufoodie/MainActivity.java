package com.lpufoodie.lpufoodie;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Handler handler ;
    static Set<Food> orders = new HashSet<>();
    static Restaurant restaurant;
    static ViewPager vpPager;


    static FloatingActionButton fab ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = new Intent();
        boolean bg = i.getBooleanExtra("Cart",false);

        handler = new Handler(getMainLooper());
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        fab = findViewById(R.id.fab);

        vpPager = findViewById(R.id.vpPager);
        MyPagerAdapter adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        vpPager.setCurrentItem(0,true);

                        return true;
                    case R.id.navigation_search:
                        vpPager.setCurrentItem(1,true);

                        return true;
                    case R.id.navigation_cart:
                        vpPager.setCurrentItem(2,true);

                        return true;
                    case R.id.navigation_account:
                        vpPager.setCurrentItem(3,true);

                        return true;
                    default:
                        break;
                }
                return false;
            }
        });

        YoYo.with(Techniques.BounceInUp)
                .duration(1000)
                .repeat(1)
                .playOn(bottomNavigationView);

        YoYo.with(Techniques.BounceInDown)
                .duration(1000)
                .repeat(1)
                .playOn(findViewById(R.id.header));


        vpPager.setCurrentItem(bg ? 2 : 0,true);

    }

    @Override
    public void onBackPressed() {
        vpPager.setCurrentItem(0,true);
    }



    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(@NonNull FragmentManager fm) {
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

            switch (position){
                case 1:
                    return Search.newInstance("","");
                case 2:
                    return Cart.newInstance("","");
                case 3:
                    return Account.newInstance("","");
                default:
                    return Home.newInstance(MainActivity.this,MainActivity.this.getSupportFragmentManager());
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


