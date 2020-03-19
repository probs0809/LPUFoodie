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
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Handler handler ;
    Button cart;
    static Set<Food> orders = new HashSet<>();
    static Restaurant restaurant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler(getMainLooper());
        cart = findViewById(R.id.go_to_cart);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        replace(Home.newInstance(MainActivity.this,MainActivity.this.getSupportFragmentManager()));
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        replace(Home.newInstance(MainActivity.this,MainActivity.this.getSupportFragmentManager()));
                        return true;
                    case R.id.navigation_search:
                        replace(Search.newInstance("",""));
                        cart.setVisibility(View.GONE);
                        return true;
                    case R.id.navigation_cart:
                        replace(Cart.newInstance("",""));
                        cart.setVisibility(View.GONE);
                        return true;
                    case R.id.navigation_account:
                        replace(Account.newInstance("",""));
                        cart.setVisibility(View.GONE);
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });



    }
    void replace(Fragment fragment){
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.parent,fragment);
        fragmentTransaction.commit();

    }

    int count = 0;
    @Override
    public void onBackPressed() {

        count++;
        replace(Home.newInstance(MainActivity.this,MainActivity.this.getSupportFragmentManager()));
        if(count > 2){
            System.exit(0);
        }
    }

    public void goToCart(View view) {
        replace(Cart.newInstance("",""));
        System.out.println(MainActivity.orders.size());
        cart.setVisibility(View.GONE);
    }
}
