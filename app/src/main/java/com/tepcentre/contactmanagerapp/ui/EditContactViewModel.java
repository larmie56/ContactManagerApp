package com.tepcentre.contactmanagerapp.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.tepcentre.contactmanagerapp.repo.ContactRepo;
import com.tepcentre.contactmanagerapp.repo.ContactRepoImpl;

public class EditContactViewModel extends AndroidViewModel {

    private final long mContactId;
    private final ContactRepo mContactRepo;

    public EditContactViewModel(@NonNull Application application, long contactId) {
        super(application);

        mContactId = contactId;
        mContactRepo = new ContactRepoImpl(application);
    }

    public void saveButttonClickHandle() {

    }
}
