package com.tepcentre.contactmanagerapp.repo;

import com.tepcentre.contactmanagerapp.database.Contact;
import com.tepcentre.contactmanagerapp.database.ContactDao;

public class ContactRepoImpl implements ContactRepo{

    private ContactDao mContactDao;

    public ContactRepoImpl(ContactDao contactDao) {
        mContactDao = contactDao;
    }

    @Override
    public void createContact(Contact contact) {
        mContactDao.insertContact(contact);
    }

    @Override
    public void updateContact(Contact contact) {
        mContactDao.updateContact(contact);
    }

    @Override
    public void deleteContact(long contactId) {
        mContactDao.deleteContact(contactId);
    }
}
