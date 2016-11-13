package com.inconceptlabs.contactsapp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inconceptlabs.contactsapp.R;

/**
 * Created by heghine on 11/13/16.
 */

public class ContactDetailsFragment extends Fragment {

    final public static String KEY_POSITION = "position";

    private int currentPosition = -1;

    TextView mVersionDescriptionTextView;

    public ContactDetailsFragment() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // maybe initialize some data for this fragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(KEY_POSITION);
        }

        View rootView = inflater.inflate(R.layout.fragment_contact_details, container, false);

        // TODO init the contact details view
        mVersionDescriptionTextView = (TextView) rootView.findViewById(R.id.fragment_contact_details_desc);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle args = getArguments();
        if (args != null) {
            // Set description based on argument passed in
            setContactDetails(args.getInt(KEY_POSITION));
        } else if (currentPosition != -1) {
            // Set description based on savedInstanceState defined during onCreateView()
            setContactDetails(currentPosition);
        }
    }

    public void setContactDetails(int position) {
        mVersionDescriptionTextView.setText("position = " + position);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current description selection in case we need to recreate the fragment
        outState.putInt(KEY_POSITION, currentPosition);
    }
}
