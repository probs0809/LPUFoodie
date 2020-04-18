package com.lpufoodie.lpufoodie;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements LpuFoodie {
    static Restaurant restaurant;
    static ViewPager vpPager;
    BottomNavigationView bottomNavigationView;
    Handler handler;

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


        LF_SetOrderBool.accept(LF_User.get(),MainActivity.this);



    }


    @Override
    public void onBackPressed() {
        vpPager.setCurrentItem(0, true);
    }

    public void locate(View view) {
       startActivity(new Intent(MainActivity.this, DeliveryStatus.class));
    }

    public void logout(View view) {
        LF_Auth.signOut();
        LF_GoogleSignInClient.apply(this).signOut().addOnCompleteListener(this, task -> vpPager.setCurrentItem(0, true));
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        MyPagerAdapter(@NonNull FragmentManager fm) {
            super(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @Override
        public int getCount() {
            return 4;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 1:
                    return new Search(MainActivity.this.getSupportFragmentManager());
                case 2:
                    return new Cart();
                case 3:
                    return new Account();
                default:

                    return new Home(MainActivity.this.getSupportFragmentManager());
            }
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Tab " + position;
        }
    }
}