package com.tepcentre.contactmanagerapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface ContactDao {

    @Insert
    void insertContact(Contact contact);
    
    @Update
    void updateContact(Contact contact);
}
