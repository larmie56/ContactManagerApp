package com.tepcentre.contactmanagerapp.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "contact_table", indices = {@Index(value = "phone_number", unique = true)})
public class Contact {

    @PrimaryKey
    private long contactId;
    @ColumnInfo(name = "first_name")
    private String firstName;
    @ColumnInfo(name = "last_name")
    private String lastName;
    @ColumnInfo(name = "phone_number")
    private long phoneNumber;
    @ColumnInfo(name = "birthday")
    private String birthday;
    @ColumnInfo(name = "address")
    private String address;
    @ColumnInfo(name = "zip_code")
    private int zipCode;
}
