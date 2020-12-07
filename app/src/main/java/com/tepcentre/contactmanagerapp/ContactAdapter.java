package com.tepcentre.contactmanagerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tepcentre.contactmanagerapp.database.Contact;
import com.tepcentre.contactmanagerapp.util.Util;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private LayoutInflater mInflater;
    private List<Contact> mContacts;

    public ContactAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.contact_list_item, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public void setData(List<Contact> contacts) {
        mContacts = contacts;
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {
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

        public void bind(int position) {
            Contact contact = mContacts.get(position);
            Util util = new Util(contact);

            //On click of a contact, the contact details should be opened
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            //Set the text drawable to the first letter contact's first and last names
            util.displayTextDrawable(mTextDrawable);
            mContactNameText.setText(util.formatContactName());
            mPhoneImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            mTextImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
