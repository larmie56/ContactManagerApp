package com.tepcentre.contactmanagerapp.database;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface ContactDao {

    @Insert
    void insertContact(Contact contact);
}
