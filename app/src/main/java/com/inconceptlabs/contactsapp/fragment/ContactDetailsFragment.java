package com.inconceptlabs.contactsapp.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inconceptlabs.contactsapp.R;
import com.inconceptlabs.contactsapp.data.ContactData;
import com.inconceptlabs.contactsapp.manager.ContactsAppManager;

/**
 * Created by heghine on 11/13/16.
 */

public class ContactDetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    final public static String KEY_POSITION = "position";

    private static final String[] PROJECTION = {
            ContactsContract.CommonDataKinds.Phone._ID,
            ContactsContract.CommonDataKinds.Phone.NUMBER
    };

    private static final String SELECTION = ContactsContract.Data.LOOKUP_KEY + " = ?";
    private String[] mSelectionArgs = { "" };

    private int currentPosition = -1;
    private ContactData currentContact;

    private TextView contactNameTxt;
    private TextView contactPhoneTxt;

    public ContactDetailsFragment() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            currentPosition = args.getInt(KEY_POSITION);
        }

        loadContactDetails();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(KEY_POSITION);
        }

        View rootView = inflater.inflate(R.layout.fragment_contact_details, container, false);
        contactNameTxt = (TextView) rootView.findViewById(R.id.fragment_contact_details_name_txt);
        contactPhoneTxt = (TextView) rootView.findViewById(R.id.fragment_contact_details_phone_txt);

        return rootView;
    }

    public void setSelectedContactPosition(int position) {
        currentPosition = position;
        loadContactDetails();
    }

    public void loadContactDetails() {
        if (currentPosition != -1) {
            currentContact = ContactsAppManager.getInstance().contacts.get(currentPosition);

            if (currentContact.contactPhoneNumber != null) {
                updateContactDetailsView();
            } else {
                getLoaderManager().initLoader(Integer.valueOf(currentContact.contactId), null, this);
            }
        }
    }

    private void updateContactDetailsView() {
        contactNameTxt.setText(String.format(getString(R.string.contact_name), currentContact.contactName));
        contactPhoneTxt.setText(String.format(getString(R.string.contact_phone), currentContact.contactPhoneNumber));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current selected contact position, in case we need to recreate the fragment
        outState.putInt(KEY_POSITION, currentPosition);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        mSelectionArgs[0] = currentContact != null ? currentContact.contactLookUpKey : "";
        return new CursorLoader(
                getActivity(),
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                PROJECTION,
                SELECTION,
                mSelectionArgs,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor.moveToFirst()) {
            currentContact.contactPhoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            updateContactDetailsView();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
