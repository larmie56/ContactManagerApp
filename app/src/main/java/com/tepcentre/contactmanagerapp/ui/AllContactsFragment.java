package com.tepcentre.contactmanagerapp.ui;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tepcentre.contactmanagerapp.ContactAdapter;
import com.tepcentre.contactmanagerapp.R;
import com.tepcentre.contactmanagerapp.database.Contact;

import java.util.List;

public class AllContactsFragment extends Fragment implements ContactAdapter.IAllContactsFragment {

    private RecyclerView mRecyclerView;
    private FloatingActionButton mFloatingActionButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all_contacts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = view.findViewById(R.id.recycler_contacts);
        mFloatingActionButton = view.findViewById(R.id.fab_create_contact);

        //Navigate to the edit contact screen for the user to create a new account
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(AllContactsFragmentDirections.actionAllContactsFragmentToEditContactDetailsFragment());
            }
        });

        ContactAdapter contactAdapter = new ContactAdapter(getContext(), this);

        ContactViewModel contactViewModel = new ViewModelProvider(getActivity()).get(ContactViewModel.class);
        contactViewModel.getAllContacts();

        contactViewModel.getContactListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
                //If there's no data in the database, animate the floatingActionButton(fab) ~
                // to serve as a notifier to the user, to click on the fab to create a new contact
                if (contacts.isEmpty()) animateFab();
                contactAdapter.setData(contacts);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        mRecyclerView.setAdapter(contactAdapter);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void animateFab() {
        Handler animationHandler = new Handler(getActivity().getMainLooper());
        //Delay the animation for about 1 secs
        animationHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                displayFabAnimation();
            }
        }, 1000);
        //Run the animation for a second time, this time after about 3 secs
        animationHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                displayFabAnimation();
            }
        }, 3000);
    }

    private void displayFabAnimation() {
        Animator fabAnimation = AnimatorInflater.loadAnimator(getContext(), R.animator.fab_animation);
        fabAnimation.setTarget(mFloatingActionButton);
        fabAnimation.start();
        //fabAnimation.addListener(this);
    }

    /**
     * Handles the intent to make a call, when the user clicks to call a particular contact
     * This method is overridden from IAllContactsFragment
     * @param contact - The contact to call
     */
    @Override
    public void handleCallIntent(@NonNull Contact contact) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + contact.getPhoneNumber()));

        if (callIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(callIntent);
        } else {
            Toast.makeText(getContext(), "No application to make the call", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Handles the intent to send a message, when the user clicks to send a contact a text message
     * This method is overridden from IAllContactsFragment
     * @param contact - The contact to send a message
     */
    @Override
    public void handleMessageIntent(@NonNull Contact contact) {
        Intent messageIntent = new Intent(Intent.ACTION_VIEW);

        messageIntent.setData(Uri.parse("smsto:"));
        messageIntent.setType("vnd.android-dir/mms-sms");
        messageIntent.putExtra("address"  , String.valueOf(contact.getPhoneNumber()));

        if (messageIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(messageIntent);
        } else {
            Toast.makeText(getContext(), "No application to send the text", Toast.LENGTH_SHORT).show();
        }
    }
}
