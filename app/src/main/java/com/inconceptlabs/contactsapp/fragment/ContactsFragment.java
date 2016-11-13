package com.inconceptlabs.contactsapp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inconceptlabs.contactsapp.R;
import com.inconceptlabs.contactsapp.adapter.ContactsAdapter;
import com.inconceptlabs.contactsapp.data.ContactData;
import com.inconceptlabs.contactsapp.util.OnContactClickListener;

import java.util.ArrayList;

/**
 * Created by heghine on 11/13/16.
 */

public class ContactsFragment extends Fragment {

    private ArrayList<ContactData> contactDatas = new ArrayList<>();

    public ContactsFragment() {
        contactDatas.add(new ContactData("Heghine Hakobyan", "Android Developer"));
        contactDatas.add(new ContactData("Helen Safarian", "Software Engineer"));
        contactDatas.add(new ContactData("Davit Petrosyan", "Java Engineer"));
        contactDatas.add(new ContactData("Jacob Strace", "Project Manager"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contacts, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_contacts_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ContactsAdapter contactsAdapter = new ContactsAdapter(contactDatas, (OnContactClickListener) getActivity());
        recyclerView.setAdapter(contactsAdapter);

        return rootView;
    }
}
