package com.inconceptlabs.contactsapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.inconceptlabs.contactsapp.fragment.ContactDetailsFragment;
import com.inconceptlabs.contactsapp.fragment.ContactsFragment;
import com.inconceptlabs.contactsapp.manager.ContactsAppManager;
import com.inconceptlabs.contactsapp.util.OnContactClickListener;

public class MainActivity extends FragmentActivity implements OnContactClickListener {

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    private ContactsFragment contactsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
        } else {
            ContactsAppManager.getInstance().isReadContactsPermissionGranted = true;
        }

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            contactsFragment = new ContactsFragment();
            contactsFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, contactsFragment)
                    .commit();
        }
    }

    @Override
    public void onItemClick(int position) {
        ContactDetailsFragment contactDetailsFragment = (ContactDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.contact_details_fragment);

        if (contactDetailsFragment != null) {
            contactDetailsFragment.setContactDetails(position);
        } else {
            ContactDetailsFragment newContactDetailsFragment = new ContactDetailsFragment();
            Bundle args = new Bundle();

            args.putInt(ContactDetailsFragment.KEY_POSITION, position);
            newContactDetailsFragment.setArguments(args);

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, newContactDetailsFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ContactsAppManager.getInstance().isReadContactsPermissionGranted = true;
                    if (contactsFragment != null) {
                        contactsFragment.startLoadingContacts();
                    }
                } else {
                    Toast.makeText(this, "Please, grant this permission to continue", Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
                }
            }
        }
    }
}
