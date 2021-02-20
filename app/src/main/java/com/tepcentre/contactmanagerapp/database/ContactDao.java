package com.tepcentre.contactmanagerapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Single;

@Dao
public abstract class ContactDao {

    @Insert
    public abstract void insertContact(Contact contact);
    
    @Update
    public abstract void updateContact(Contact contact);

    @Query("DELETE FROM contact_table WHERE contactId =:contactId")
    public abstract void deleteContact(long contactId);

    @Query("SELECT * FROM contact_table ORDER BY first_name ASC")
    public abstract LiveData<List<Contact>> getAllContacts();

    @Query("SELECT * FROM contact_table WHERE contactId =:contactId LIMIT 1")
    public abstract LiveData<Contact> getContact(long contactId);

    @Query("SELECT * FROM contact_table WHERE phone_number =:phoneNumber LIMIT 1")
    public abstract Contact getContactByNumber(long phoneNumber);

    @Transaction
    public boolean existOrInsertContact(long numberToGet, Contact contactToInsert) {
        Contact contact = getContactByNumber(numberToGet);
        if (contact == null) {
            //No contact was returned from the query, i.e. the contact does't exist in the db, and was inserted
            insertContact(contactToInsert);
            return true;
        } else {
            //A contact was gotten from the query, i.e. the contact already exist in the db and was not inserted
            return false;
        }
    }

    @Transaction
    public void getOrUpdateContact(long numberToGet, Contact contactToUpdate) {
        getContactByNumber(numberToGet);
        updateContact(contactToUpdate);
    }
}
