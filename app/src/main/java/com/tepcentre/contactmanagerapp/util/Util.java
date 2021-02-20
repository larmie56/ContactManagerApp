package com.tepcentre.contactmanagerapp.util;

import android.widget.ImageView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.tepcentre.contactmanagerapp.database.Contact;

public class Util {

    private final Contact mContact;

    public Util(Contact contact) {
        mContact = contact;
    }

    //Setup and display the text drawable
    public void displayTextDrawable(ImageView imageView) {
        ColorGenerator colorGenerator = ColorGenerator.MATERIAL;
        int color = colorGenerator.getRandomColor();

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(getTextDrawableCharacters(), color);

        imageView.setImageDrawable(drawable);
    }

    //Get the first character of the first name and last name
    public String getTextDrawableCharacters() {
        if (!mContact.getFirstName().isEmpty() && !mContact.getLastName().isEmpty()) {
            return String.valueOf(mContact.getFirstName().charAt(0)).toUpperCase()
                    + String.valueOf(mContact.getLastName().charAt(0)).toUpperCase();
        }
        throw new IllegalArgumentException("Contact names cannot be empty");
    }

    public String formatContactName() {
        return mContact.getFirstName() + " " + mContact.getLastName();
    }
}
