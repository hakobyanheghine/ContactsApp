package com.inconceptlabs.contactsapp;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.inconceptlabs.contactsapp.data.ContactData;
import com.inconceptlabs.contactsapp.fragment.ContactDetailsFragment;
import com.inconceptlabs.contactsapp.fragment.ContactsFragment;
import com.inconceptlabs.contactsapp.util.OnContactClickListener;

public class MainActivity extends Activity implements OnContactClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            ContactsFragment contactsFragment = new ContactsFragment();
            contactsFragment.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, contactsFragment)
                    .commit();
        }
    }

    @Override
    public void onItemClick(int position) {
        ContactDetailsFragment contactDetailsFragment = (ContactDetailsFragment) getFragmentManager().findFragmentById(R.id.contact_details_fragment);

        if (contactDetailsFragment != null) {
            contactDetailsFragment.setContactDetails(position);
        } else {
            ContactDetailsFragment newContactDetailsFragment = new ContactDetailsFragment();
            Bundle args = new Bundle();

            args.putInt(ContactDetailsFragment.KEY_POSITION, position);
            newContactDetailsFragment.setArguments(args);

            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, newContactDetailsFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
