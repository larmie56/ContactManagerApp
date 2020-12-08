package com.tepcentre.contactmanagerapp.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "contact_table", indices = {@Index(value = "phone_number", unique = true)})
public class Contact {

    public Contact() {}

    public Contact(String firstName,
                   String lastName,
                   long phoneNumber,
                   String birthday,
                   String address,
                   int zipCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.address = address;
        this.zipCode = zipCode;
    }

    @PrimaryKey(autoGenerate = true)
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

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public long getContactId() {
        return contactId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getAddress() {
        return address;
    }

    public int getZipCode() {
        return zipCode;
    }
}
