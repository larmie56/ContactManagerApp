package com.tepcentre.contactmanagerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.tepcentre.contactmanagerapp.database.Contact;
import com.tepcentre.contactmanagerapp.ui.AllContactsFragmentDirections;
import com.tepcentre.contactmanagerapp.util.Util;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private final LayoutInflater mInflater;
    private List<Contact> mContacts;
    private IAllContactsFragment mIAllContactsFragment;


    /**
     * This interface will be used to communicate between this adapter class and AllContactsFragment
     * AllContactsFragment must implement this interface and provide the implementation for the methods declared here
     */
    public interface IAllContactsFragment {
        void handleCallIntent(Contact contact);
        void handleMessageIntent(Contact contact);
    }


    public ContactAdapter(Context context, IAllContactsFragment iAllContactsFragment) {
        mInflater = LayoutInflater.from(context);
        mIAllContactsFragment = iAllContactsFragment;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.contact_list_item, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.bind(mIAllContactsFragment, mContacts, position);
    }

    @Override
    public int getItemCount() {
        return mContacts == null ? 0 : mContacts.size();
    }

    public void setData(List<Contact> contacts) {
        mContacts = contacts;
        notifyDataSetChanged();
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {
        private ImageView mTextDrawable;
        private TextView mContactNameText;
        private ImageView mPhoneImage;
        private ImageView mTextImage;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);

            mTextDrawable = itemView.findViewById(R.id.image_text_drawable);
            mContactNameText = itemView.findViewById(R.id.text_contact_name);
            mPhoneImage = itemView.findViewById(R.id.image_phone);
            mTextImage = itemView.findViewById(R.id.image_text);
        }

        public void bind(IAllContactsFragment iAllContactsFragment, List<Contact> contacts, int position) {
            Contact contact = contacts.get(position);
            Util util = new Util(contact);

            //Navigate to the edit contact screen for the user to edit the contact, passing in the contact id
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavController navController = Navigation.findNavController(view);
                    navController.navigate(
                            AllContactsFragmentDirections.
                                    actionAllContactsFragmentToViewContactDetailsFragment(contact.getContactId()));
                }
            });
            //Set the text drawable to the first letter contact's first and last names
            util.displayTextDrawable(mTextDrawable);
            mContactNameText.setText(util.formatContactName());
            mPhoneImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iAllContactsFragment.handleCallIntent(contact);
                }
            });
            mTextImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iAllContactsFragment.handleMessageIntent(contact);
                }
            });
        }
    }
}
