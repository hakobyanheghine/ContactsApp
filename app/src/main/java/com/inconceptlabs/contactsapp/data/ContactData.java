package com.inconceptlabs.contactsapp.data;

import android.os.Parcelable;

/**
 * Created by heghine on 11/13/16.
 */

public class ContactData {

    public String contactName;
    public String contactDescription;

    public ContactData() {

    }

    public ContactData(String name, String description) {
        contactName = name;
        contactDescription = description;
    }
}
