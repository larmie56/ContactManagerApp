package com.tepcentre.contactmanagerapp.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.tepcentre.contactmanagerapp.R;
import com.tepcentre.contactmanagerapp.database.Contact;
import com.tepcentre.contactmanagerapp.util.Util;

public class ViewContactDetailsFragment extends Fragment {

    private long mContactId;

    private ImageView mTextDrawable;
    private TextView mContactName;
    private ImageView mEditContactImage;
    private ImageView mDeleteContactImage;
    private TextView mPhoneNumberText;
    private TextView mBirthdayText;
    private TextView mAddressText;
    private TextView mZipCodeText;

    private Contact mContact;
    private ContactViewModel mContactViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_contact_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTextDrawable = view.findViewById(R.id.image_text_drawable);
        mContactName = view.findViewById(R.id.text_contact_name);
        mEditContactImage = view.findViewById(R.id.image_edit_contact);
        mDeleteContactImage = view.findViewById(R.id.image_delete_contact);
        mPhoneNumberText = view.findViewById(R.id.text_phone_number);
        mBirthdayText = view.findViewById(R.id.text_birthday);
        mAddressText = view.findViewById(R.id.text_address);
        mZipCodeText = view.findViewById(R.id.text_zip_code);

        mContactId = ViewContactDetailsFragmentArgs.fromBundle(requireArguments()).getContactId();

        mContactViewModel = new ViewModelProvider(getActivity()).get(ContactViewModel.class);
        mContactViewModel.getContact(mContactId);

        mContactViewModel.getContactLiveData().observe(getViewLifecycleOwner(), new Observer<Contact>() {
            @Override
            public void onChanged(Contact contact) {
                mContact = contact;
                updateUi();
            }
        });
    }

    private void updateUi() {
        //Ensure a contact has been passed in
        if (mContact != null) {
            Util util = new Util(mContact);
            util.displayTextDrawable(mTextDrawable);
            mContactName.setText(util.formatContactName());
            mEditContactImage.setOnClickListener(new View.OnClickListener() {
                //Navigate to the edit contact screen so the user can edit the contact, passing in the contact id
                @Override
                public void onClick(View view) {
                    ViewContactDetailsFragmentDirections.ActionViewContactDetailsFragmentToEditContactDetailsFragment fragmentDirections
                            = ViewContactDetailsFragmentDirections.actionViewContactDetailsFragmentToEditContactDetailsFragment();
                    fragmentDirections.setContactId(mContact.getContactId());
                    NavController navController = Navigation.findNavController(view);
                    navController.navigate(fragmentDirections);
                }
            });
            //Confirm if the user wants to delete the contact, and take appropriate action
            mDeleteContactImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    handleConfirmationDialog();
                }
            });
            mPhoneNumberText.setText(String.valueOf(mContact.getPhoneNumber()));
            mBirthdayText.setText(mContact.getBirthday());
            mAddressText.setText(mContact.getAddress());
            mZipCodeText.setText(String.valueOf(mContact.getZipCode()));
        }
    }

    private void handleConfirmationDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        dialogBuilder.setTitle(R.string.delete_contact);
        dialogBuilder.setMessage(R.string.confirm_delete);
        dialogBuilder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mContactViewModel.deleteContact(mContactId);
                dialogInterface.dismiss();
                Toast.makeText(getContext(), R.string.contact_deleted, Toast.LENGTH_SHORT).show();
                NavController navController = NavHostFragment.findNavController(ViewContactDetailsFragment.this);
                navController.navigate(ViewContactDetailsFragmentDirections.actionViewContactDetailsFragmentToAllContactsFragment());
            }
        });
        dialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialogBuilder.create().show();
    }
}
