package com.tepcentre.contactmanagerapp.ui;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;
import com.tepcentre.contactmanagerapp.R;
import com.tepcentre.contactmanagerapp.database.Contact;

public class EditContactDetailsFragment extends Fragment {

    public static final long NEW_CONTACT_ID = -1L;
    private boolean isNewContact;
    private long contactId;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_contact_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        contactId = EditContactDetailsFragmentArgs.fromBundle(requireArguments()).getContactId();
        isNewContact = contactId == NEW_CONTACT_ID;

        ContactViewModel contactViewModel = new ViewModelProvider(getActivity()).get(ContactViewModel.class);

        mFirstNameEdit = view.findViewById(R.id.edit_first_name);
        mLastNameEdit = view.findViewById(R.id.edit_last_name);
        mPhoneNumberEdit = view.findViewById(R.id.edit_phone_number);
        mBirthdayImage = view.findViewById(R.id.image_birthday);
        mAddressEdit = view.findViewById(R.id.edit_address);
        mZipCodeEdit = view.findViewById(R.id.edit_zip_code);
        mSaveChangesButton = view.findViewById(R.id.button_save_changes);

        mSaveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserInput();
                Contact contact = new Contact(mFirstName,
                        mLastName,
                        Long.parseLong(mPhoneNumber),
                        mBirthday,
                        mAddress,
                        Integer.parseInt(mZipCode));
                contactViewModel.insertContact(contact);
                Toast.makeText(getContext(), "Contact Added Successfully", Toast.LENGTH_SHORT).show();
                NavController navController = Navigation.findNavController(view);
                navController.navigate(EditContactDetailsFragmentDirections.actionEditContactDetailsFragmentToAllContactsFragment());
            }
        });

    }

    private void getUserInput() {
        mFirstName = mFirstNameEdit.getText().toString();
        mLastName = mLastNameEdit.getText().toString();
        mPhoneNumber = mPhoneNumberEdit.getText().toString();
        mAddress = mAddressEdit.getText().toString();
        mZipCode = mZipCodeEdit.getText().toString();
    }
}
