package com.tepcentre.contactmanagerapp.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.InvocationTargetException;

public class EditContactViewModelFactory implements ViewModelProvider.Factory {

    private final long mContactId;
    private final Application mApplication;

    public EditContactViewModelFactory(long contactId, Application application) {
        mContactId = contactId;
        mApplication = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isInstance(EditContactViewModel.class)) {
            try {
                return modelClass.getConstructor(Application.class, Long.class)
                        .newInstance(mApplication, mContactId);
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("Unknown view model class");
        }
        return null;
    }
}
