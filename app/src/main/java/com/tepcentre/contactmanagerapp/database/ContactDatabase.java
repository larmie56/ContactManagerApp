package com.tepcentre.contactmanagerapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Contact.class, version = 1, exportSchema = true)
public abstract class ContactDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "contact_manager_database";

    private static ContactDatabase INSTANCE;

    public static ContactDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (ContactDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        ContactDatabase.class,
                        DATABASE_NAME).build();
            }
        }
        return INSTANCE;
    }

    public abstract ContactDao getContactDao();
}
