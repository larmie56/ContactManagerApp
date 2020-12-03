package com.tepcentre.contactmanagerapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ContactDao {

    @Insert
    void insertContact(Contact contact);
    
    @Update
    void updateContact(Contact contact);

    @Query("DELETE FROM contact_table WHERE contactId =:contactId")
    void deleteContact(long contactId);

    @Query("SELECT first_name, last_name FROM contact_table")
    void getAllContacts();

    @Query("SELECT * FROM contact_table WHERE contactId =:contactId LIMIT 1")
    LiveData<Contact> getContact(long contactId);
}
