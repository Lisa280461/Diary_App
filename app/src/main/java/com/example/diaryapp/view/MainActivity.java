package com.example.diaryapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.diaryapp.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;



public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private FirebaseAuth mFirebase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mFirebase = FirebaseAuth.getInstance();

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new NewEntryFragment()).commit();
        navigationView.setCheckedItem(R.id.nav_home);



    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new NewEntryFragment()).commit();
                break;
            case R.id.nav_entries:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new EntriesFragment()).commit();
                break;
            case R.id.nav_questions:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new QuestionsFragment()).commit();
                break;
            case R.id.nav_logOut:
            {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intToMain);
            }
            return true;

        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new NewEntryFragment()).commit();
        }

    }


}
