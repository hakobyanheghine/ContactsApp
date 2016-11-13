package com.inconceptlabs.contactsapp.manager;

import com.inconceptlabs.contactsapp.data.ContactData;

import java.util.ArrayList;

/**
 * Created by heghine on 11/13/16.
 */

public class ContactsAppManager {

    private static ContactsAppManager instance;

    private ContactsAppManager() {

    }

    public static ContactsAppManager getInstance() {
        if (instance == null) {
            instance = new ContactsAppManager();
        }

        return instance;
    }

    public boolean isReadContactsPermissionGranted;
    public ArrayList<ContactData> contactDatas = new ArrayList<>();
}
