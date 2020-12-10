package com.tepcentre.contactmanagerapp.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.tepcentre.contactmanagerapp.R;
import com.tepcentre.contactmanagerapp.database.Contact;

import java.util.Calendar;

public class EditContactDetailsFragment extends Fragment {

    public static final long NEW_CONTACT_ID = -1L;
    private static final CharSequence ERROR_MESSAGE = "Field cannot be empty";

    private long mContactId;

    private TextView mBirthdayText;
    private TextInputLayout mFirstNameTextInput;
    private TextInputLayout mLastNameTextInput;
    private TextInputLayout mPhoneNumberTextInput;
    private TextInputLayout mAddressTextInput;
    private TextInputLayout mZipCodeTextInput;
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
    private ContactViewModel mContactViewModel;

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
        mBirthdayText = view.findViewById(R.id.text_birthday);
        mBirthdayImage = view.findViewById(R.id.image_birthday);
        mAddressEdit = view.findViewById(R.id.edit_address);
        mZipCodeEdit = view.findViewById(R.id.edit_zip_code);
        mSaveChangesButton = view.findViewById(R.id.button_save_changes);
        mFirstNameTextInput = view.findViewById(R.id.text_input_first_name);
        mLastNameTextInput = view.findViewById(R.id.text_input_last_name);
        mPhoneNumberTextInput = view.findViewById(R.id.text_input_phone_number);
        mAddressTextInput = view.findViewById(R.id.text_input_address);
        mZipCodeTextInput = view.findViewById(R.id.text_input_zip_code);


        watchEditTextChanges(mFirstNameEdit, mFirstNameTextInput);
        watchEditTextChanges(mLastNameEdit, mLastNameTextInput);
        watchEditTextChanges(mPhoneNumberEdit, mPhoneNumberTextInput);
        watchEditTextChanges(mAddressEdit, mAddressTextInput);
        watchEditTextChanges(mZipCodeEdit, mZipCodeTextInput);

        mContactId = EditContactDetailsFragmentArgs.fromBundle(requireArguments()).getContactId();
        boolean isNewContact = mContactId == NEW_CONTACT_ID;

        mContactViewModel = new ViewModelProvider(getActivity()).get(ContactViewModel.class);

        if (!isNewContact) {
            //Updating a contact ~ fetch the contact from the database and display to the user
            mContactViewModel.getContact(mContactId);
            mContactViewModel.getContactLiveData().observe(getViewLifecycleOwner(), new Observer<Contact>() {
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
                if (!mFirstName.isEmpty() && !mLastName.isEmpty() && !mPhoneNumber.isEmpty()
                        && !(mBirthday != null && mBirthday.isEmpty()) && !mAddress.isEmpty() && !mZipCode.isEmpty()) {
                    if (isNewContact) {
                        handleCreateContact();
                    } else {
                        handleUpdateContact();
                    }
                    //Update/Insert completed, navigate back to the all contacts screen
                    NavController navController = Navigation.findNavController(view);
                    navController
                            .navigate(EditContactDetailsFragmentDirections
                                    .actionEditContactDetailsFragmentToAllContactsFragment());
                } else {
                    Toast.makeText(getContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mBirthdayImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                final Integer year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        int  year = i2;
                        int month = i1 + 1;
                        int day = i;
                        mBirthday = getActivity().getResources().getString(R.string.birthday_string, year, month, day);
                        mBirthdayText.setText(mBirthday);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    private void updateUi() {
        //Ensure a contact has been passed in
        if (mContact != null) {
            mFirstNameEdit.setText(mContact.getFirstName());
            mLastNameEdit.setText(mContact.getLastName());
            mPhoneNumberEdit.setText(String.valueOf(mContact.getPhoneNumber()));
            mBirthdayText.setText("Birthday: " + mContact.getBirthday());
            mAddressEdit.setText(mContact.getAddress());
            mZipCodeEdit.setText(String.valueOf(mContact.getZipCode()));
        }
    }

    private void watchEditTextChanges(TextInputEditText editText, TextInputLayout textInputLayout) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //Check if the user has filled an edit text field, if they haven't, show show an indication
                if (s.toString().isEmpty()) {
                    textInputLayout.setError(ERROR_MESSAGE);
                } else {
                    textInputLayout.setError(null);
                }
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

    private void handleCreateContact() {
        mContact = new Contact(mFirstName,
                mLastName,
                Long.parseLong(mPhoneNumber),
                mBirthday,
                mAddress,
                Integer.parseInt(mZipCode)
        );
        if (mContact != null) mContactViewModel.insertContact(mContact);
        Toast.makeText(getContext(), "Contact Added Successfully", Toast.LENGTH_SHORT).show();
    }

    private void handleUpdateContact() {
        mContact.setFirstName(mFirstName);
        mContact.setLastName(mLastName);
        mContact.setPhoneNumber(Long.parseLong(mPhoneNumber));
        mContact.setBirthday(mBirthday);
        mContact.setAddress(mAddress);
        mContact.setZipCode(Integer.parseInt(mZipCode));
        if (mContact != null) mContactViewModel.updateContact(mContact);
        Toast.makeText(getContext(), "Contact Updated Successfully", Toast.LENGTH_SHORT).show();
    }
}
