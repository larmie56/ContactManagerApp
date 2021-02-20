package com.tepcentre.contactmanagerapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.tepcentre.contactmanagerapp.R;
import com.tepcentre.contactmanagerapp.database.ContactDao;
import com.tepcentre.contactmanagerapp.database.ContactDatabase;
import com.tepcentre.contactmanagerapp.repo.ContactRepo;
import com.tepcentre.contactmanagerapp.repo.ContactRepoImpl;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //For up navigation support (back arrow on the action bar)
        NavController navController = Navigation.findNavController(this, R.id.nav_host);
        NavigationUI.setupActionBarWithNavController(this, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host);
        return navController.navigateUp()
                || super.onSupportNavigateUp();
    }
}