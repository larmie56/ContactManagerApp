package com.tepcentre.contactmanagerapp.util;

import android.widget.ImageView;

import androidx.room.util.StringUtil;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.tepcentre.contactmanagerapp.database.Contact;

public class Util {

    private Contact mContact;

    public Util(Contact contact) {
        mContact = contact;
    }

    //Setup and display the text drawable
    public void displayTextDrawable(ImageView imageView) {
        float density = imageView.getContext().getResources().getDisplayMetrics().density;

        ColorGenerator colorGenerator = ColorGenerator.MATERIAL;
        int color = colorGenerator.getRandomColor();

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(getTextDrawableChar(), color);

        imageView.setImageDrawable(drawable);
    }

    //Get the first character of the first name and last name
    public String getTextDrawableChar() {
        if (!(mContact.getFirstName().isEmpty() && mContact.getLastName().isEmpty())) {
            return String.valueOf(mContact.getFirstName().charAt(0)) + mContact.getLastName().charAt(0);
        }
        throw new IllegalArgumentException("Contact names cannot be empty");
    }

    public String formatContactName() {
        return mContact.getFirstName() + " " + mContact.getLastName();
    }
}
