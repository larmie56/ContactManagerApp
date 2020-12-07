package com.tepcentre.contactmanagerapp.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.tepcentre.contactmanagerapp.repo.ContactRepo;

import java.lang.reflect.InvocationTargetException;

public class ContactViewModelFactory implements ViewModelProvider.Factory {

    private final ContactRepo mContactRepo;

    public ContactViewModelFactory(ContactRepo contactRepo) {
        mContactRepo = contactRepo;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isInstance(ContactViewModel.class)) {
            try {
                return modelClass.getConstructor(ContactRepo.class)
                        .newInstance(mContactRepo);
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
