package com.tepcentre.contactmanagerapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

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
    }
}