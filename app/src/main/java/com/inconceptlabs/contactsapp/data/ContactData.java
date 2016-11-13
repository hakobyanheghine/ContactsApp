package com.inconceptlabs.contactsapp.data;

import android.database.Cursor;
import android.provider.ContactsContract;

/**
 * Created by heghine on 11/13/16.
 */

public class ContactData {

    public String contactId;
    public String contactLookUpKey;
    public String contactName;
    public String contactPhotoUri;

    public String contactPhoneNumber;

    public ContactData() {

    }

    public ContactData(Cursor cursor) {
        contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
        contactLookUpKey = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
        contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
        contactPhotoUri = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI));
    }
}
