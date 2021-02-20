package com.tepcentre.contactmanagerapp.repo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.tepcentre.contactmanagerapp.database.Contact;
import com.tepcentre.contactmanagerapp.database.ContactDao;
import com.tepcentre.contactmanagerapp.database.ContactDatabase;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ContactRepoImpl implements ContactRepo{

    private final ContactDao mContactDao;
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    private volatile boolean mHasInserted;

    public ContactRepoImpl(Application application) {
        ContactDatabase contactDatabase = ContactDatabase.getDatabase(application);
        mContactDao = contactDatabase.getContactDao();
    }

    @Override
    public void createContact(Contact contact) {
        mDisposable.add(Schedulers.io().scheduleDirect(new Runnable() {
            @Override
            public void run() {
                mContactDao.insertContact(contact);
            }
        }));
    }

    @Override
    public void updateContact(Contact contact) {
        mDisposable.add(Schedulers.io().scheduleDirect(new Runnable() {
            @Override
            public void run() {
                mContactDao.updateContact(contact);
            }
        }));
    }

    @Override
    public void deleteContact(long contactId) {
        mDisposable.add(Schedulers.io().scheduleDirect(new Runnable() {
            @Override
            public void run() {
                mContactDao.deleteContact(contactId);
            }
        }));
    }

    @Override
    public LiveData<List<Contact>> getAllContacts() {
        return mContactDao.getAllContacts();
    }

    @Override
    public LiveData<Contact> getContact(long contactId) {
        return mContactDao.getContact(contactId);
    }

    @Override
    public Single<Boolean> existOrInsertContact(long numberToGet, Contact contactToInsert) {
        synchronized (this) {
            mDisposable.add(Schedulers.io().scheduleDirect(new Runnable() {
                @Override
                public void run() {
                    mHasInserted = mContactDao.existOrInsertContact(numberToGet, contactToInsert);
                }
            }));
            return Single.just(mHasInserted);
        }
    }

    @Override
    public void cleanup() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
