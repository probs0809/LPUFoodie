package com.lpufoodie.lpufoodie;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Handler handler ;
    Button cart;
    public static List<Fragment> mylist = new ArrayList<>();
    static Set<Food> orders = new HashSet<>();
    static Restaurant restaurant;
    static ViewPager vpPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler(getMainLooper());
        cart = findViewById(R.id.go_to_cart);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        vpPager = (ViewPager) findViewById(R.id.vpPager);
        MyPagerAdapter adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);

//        replace(Home.newInstance(MainActivity.this,MainActivity.this.getSupportFragmentManager()));
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
//                        replace(Home.newInstance(MainActivity.this,MainActivity.this.getSupportFragmentManager()));
                        vpPager.setCurrentItem(0,true);
                        return true;
                    case R.id.navigation_search:
                        vpPager.setCurrentItem(1,true);
                        cart.setVisibility(View.GONE);
                        return true;
                    case R.id.navigation_cart:
                        vpPager.setCurrentItem(2,true);
                        cart.setVisibility(View.GONE);
                        return true;
                    case R.id.navigation_account:
                        vpPager.setCurrentItem(3,true);
                        cart.setVisibility(View.GONE);
                        ;
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



    }

    int count = 0;
    @Override
    public void onBackPressed() {

        count++;
        vpPager.setCurrentItem(0,true);
        if(count > 2){
            System.exit(0);
        }
    }

    public void goToCart(View view) {
        vpPager.setCurrentItem(0,true);
        System.out.println(MainActivity.orders.size());
        cart.setVisibility(View.GONE);
    }



    public class MyPagerAdapter extends FragmentPagerAdapter {
        private int NUM_ITEMS = 100;


        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            mylist.add((Home.newInstance(MainActivity.this,MainActivity.this.getSupportFragmentManager())));
            mylist.add((Search.newInstance("","")));
            mylist.add((Cart.newInstance("","")));
            mylist.add((Account.newInstance("","")));
        }

        // Returns total number of pages.
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for a particular page.
        @Override
        public Fragment getItem(int position) {



            switch (position) {
                case 0:
                    return mylist.get(0);
                case 1:
                    cart.setVisibility(View.GONE);
                    return mylist.get(1);
                case 2:
                    cart.setVisibility(View.GONE);
                    return mylist.get(2);
                case 3:
                    cart.setVisibility(View.GONE);
                    return mylist.get(3);

                default:
                    cart.setVisibility(View.GONE);


            }
            return mylist.get(position);
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Tab " + position;
        }

    }
}


