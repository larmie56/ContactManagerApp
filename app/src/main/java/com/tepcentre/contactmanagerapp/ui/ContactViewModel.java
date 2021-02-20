package com.tepcentre.contactmanagerapp.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tepcentre.contactmanagerapp.database.Contact;
import com.tepcentre.contactmanagerapp.repo.ContactRepo;
import com.tepcentre.contactmanagerapp.repo.ContactRepoImpl;

import java.util.List;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class ContactViewModel extends AndroidViewModel {
    private final ContactRepo mContactRepo;

    private LiveData<List<Contact>> mContactListLiveData;
    private LiveData<Contact> mContactLiveData;
    private final MutableLiveData<Boolean> displayProgressBarLive = new MutableLiveData<>();
    private volatile boolean hasInsertedContact;
    private Contact mContactByNumber;

    public ContactViewModel(@NonNull Application application) {
        super(application);

        mContactRepo = new ContactRepoImpl(application);
    }

    public void insertContact(Contact contact) {
        mContactRepo.createContact(contact);
    }

    public void updateContact(Contact contact) {
        mContactRepo.updateContact(contact);
    }

    public void deleteContact(long contactId) {
        mContactRepo.deleteContact(contactId);
    }

    public void getAllContacts() {
        mContactListLiveData = mContactRepo.getAllContacts();
    }

    public void getContact(long contactId) {
        mContactLiveData = mContactRepo.getContact(contactId);
    }

    public void existOrInsertContact(long numberToGet, Contact contactToInsert) {
        displayProgressBarLive.setValue(true);
        mContactRepo.existOrInsertContact(numberToGet, contactToInsert)
                .doOnTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        displayProgressBarLive.setValue(false);
                    }
                }).subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean hasInsertedContact) throws Exception {
                        ContactViewModel.this.hasInsertedContact = hasInsertedContact;
                    }
                });
    }

    public void resetHasGottenContact() {
        //displayProgressBarLive.setValue(false);
    }

    public LiveData<Boolean> getDisplayProgressBarLive() { return displayProgressBarLive; }

    public LiveData<List<Contact>> getContactListLiveData() {
        return mContactListLiveData;
    }

    public LiveData<Contact> getContactLiveData() {
        return mContactLiveData;
    }

    public boolean getHasInsertedContact() { return hasInsertedContact; }

    @Override
    protected void onCleared() {
        super.onCleared();

        mContactRepo.cleanup();
    }
}
