package com.inconceptlabs.contactsapp.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inconceptlabs.contactsapp.R;
import com.inconceptlabs.contactsapp.adapter.ContactsAdapter;
import com.inconceptlabs.contactsapp.data.ContactData;
import com.inconceptlabs.contactsapp.manager.ContactsAppManager;
import com.inconceptlabs.contactsapp.util.OnContactClickListener;

/**
 * Created by heghine on 11/13/16.
 */

public class ContactsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String[] PROJECTION = {
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.LOOKUP_KEY,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.Contacts.PHOTO_THUMBNAIL_URI
    };

    private static final String SELECTION = ContactsContract.Contacts.HAS_PHONE_NUMBER + "=1";


    private ContactsAdapter contactsAdapter;

    public ContactsFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadContacts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contacts, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_contacts_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        contactsAdapter = new ContactsAdapter(ContactsAppManager.getInstance().contacts, (OnContactClickListener) getActivity());
        recyclerView.setAdapter(contactsAdapter);


        return rootView;
    }

    public void loadContacts() {
        if (ContactsAppManager.getInstance().isReadContactsPermissionGranted) {
            getLoaderManager().initLoader(0, null, this);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getActivity(),
                ContactsContract.Contacts.CONTENT_URI,
                PROJECTION,
                SELECTION,
                null,
                ContactsContract.Data.DISPLAY_NAME
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor.moveToFirst()) {
            do {
                ContactData contactData = new ContactData(cursor);
                ContactsAppManager.getInstance().contacts.add(contactData);
            } while (cursor.moveToNext());

            contactsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
