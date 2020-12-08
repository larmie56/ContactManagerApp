package com.tepcentre.contactmanagerapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;
import com.tepcentre.contactmanagerapp.R;
import com.tepcentre.contactmanagerapp.database.Contact;

public class EditContactDetailsFragment extends Fragment {

    public static final long NEW_CONTACT_ID = -1L;
    private long mContactId;
    private TextInputEditText mFirstNameEdit;
    private TextInputEditText mLastNameEdit;
    private TextInputEditText mPhoneNumberEdit;
    private ImageView mBirthdayImage;
    private TextInputEditText mAddressEdit;
    private TextInputEditText mZipCodeEdit;
    private FrameLayout mSaveChangesButton;

    private String mFirstName;
    private String mLastName;
    private String mPhoneNumber;
    private String mBirthday;
    private String mAddress;
    private String mZipCode;

    private Contact mContact;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_contact_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFirstNameEdit = view.findViewById(R.id.edit_first_name);
        mLastNameEdit = view.findViewById(R.id.edit_last_name);
        mPhoneNumberEdit = view.findViewById(R.id.edit_phone_number);
        mBirthdayImage = view.findViewById(R.id.image_birthday);
        mAddressEdit = view.findViewById(R.id.edit_address);
        mZipCodeEdit = view.findViewById(R.id.edit_zip_code);
        mSaveChangesButton = view.findViewById(R.id.button_save_changes);

        mContactId = EditContactDetailsFragmentArgs.fromBundle(requireArguments()).getContactId();
        boolean isNewContact = mContactId == NEW_CONTACT_ID;

        ContactViewModel contactViewModel = new ViewModelProvider(getActivity()).get(ContactViewModel.class);

        if (!isNewContact) {
            //Updating a contact ~ fetch the contact from the database and display to the user
            contactViewModel.getContact(mContactId);
            contactViewModel.getContactLiveData().observe(getViewLifecycleOwner(), new Observer<Contact>() {
                @Override
                public void onChanged(Contact contact) {
                    mContact = contact;
                    updateUi();
                }
            });
        }

        mSaveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserInput();
                if (!(mFirstName.isEmpty() && mLastName.isEmpty() && mPhoneNumber.isEmpty() && mZipCode.isEmpty())) {
                    if (isNewContact) {
                        mContact = new Contact(mFirstName,
                                mLastName,
                                Long.parseLong(mPhoneNumber),
                                mBirthday,
                                mAddress,
                                Integer.parseInt(mZipCode)
                        );
                        if (mContact != null) contactViewModel.insertContact(mContact);
                        Toast.makeText(getContext(), "Contact Added Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        mContact.setFirstName(mFirstName);
                        mContact.setLastName(mLastName);
                        mContact.setPhoneNumber(Long.parseLong(mPhoneNumber));
                        mContact.setBirthday(mBirthday);
                        mContact.setAddress(mAddress);
                        mContact.setZipCode(Integer.parseInt(mZipCode));
                        if (mContact != null) contactViewModel.updateContact(mContact);
                        Toast.makeText(getContext(), "Contact Updated Successfully", Toast.LENGTH_SHORT).show();
                    }
                    //Update/Insert completed, navigate back to the all contacts screen
                    NavController navController = Navigation.findNavController(view);
                    navController
                            .navigate(EditContactDetailsFragmentDirections
                                    .actionEditContactDetailsFragmentToAllContactsFragment());
                }
            }
        });
    }

    private void updateUi() {
        //Ensure a contact has been passed in
        if (mContact != null) {
            mFirstNameEdit.setText(mContact.getFirstName());
            mLastNameEdit.setText(mContact.getLastName());
            mPhoneNumberEdit.setText(String.valueOf(mContact.getPhoneNumber()));
            //mBirthdayEdit.setText(mContact.getBirthday());
            mAddressEdit.setText(mContact.getAddress());
            mZipCodeEdit.setText(String.valueOf(mContact.getZipCode()));
        }
    }

    private void getUserInput() {
        mFirstName = mFirstNameEdit.getText().toString();
        mLastName = mLastNameEdit.getText().toString();
        mPhoneNumber = mPhoneNumberEdit.getText().toString();
        mAddress = mAddressEdit.getText().toString();
        mZipCode = mZipCodeEdit.getText().toString();
    }
}
