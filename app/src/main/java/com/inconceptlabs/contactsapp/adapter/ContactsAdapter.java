package com.inconceptlabs.contactsapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.inconceptlabs.contactsapp.R;
import com.inconceptlabs.contactsapp.data.ContactData;
import com.inconceptlabs.contactsapp.util.OnContactClickListener;

import java.util.ArrayList;

/**
 * Created by heghine on 11/13/16.
 */

public class ContactsAdapter  extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {

    private ArrayList<ContactData> contacts;

    private OnContactClickListener onContactClickListener;

    public ContactsAdapter(ArrayList<ContactData> contacts, OnContactClickListener listener) {
        this.contacts = contacts;
        this.onContactClickListener = listener;
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        ContactsViewHolder contactsViewHolder = new ContactsViewHolder(v);

        return contactsViewHolder;
    }

    @Override
    public void onBindViewHolder(final ContactsViewHolder holder, int position) {
        ContactData contactData = contacts.get(position);

        holder.contactNameTxt.setText(contactData.contactName);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                onContactClickListener.onItemClick(position);
            }
        };

        holder.contactImg.setOnClickListener(listener);
        holder.contactNameTxt.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return contacts != null ? contacts.size() : 0;
    }

    public class ContactsViewHolder extends RecyclerView.ViewHolder {

        public ImageView contactImg;
        public TextView contactNameTxt;

        public ContactsViewHolder(View itemView) {
            super(itemView);

            contactImg = (ImageView) itemView.findViewById(R.id.item_contact_img);
            contactNameTxt = (TextView) itemView.findViewById(R.id.item_contact_name_txt);
        }
    }
}
