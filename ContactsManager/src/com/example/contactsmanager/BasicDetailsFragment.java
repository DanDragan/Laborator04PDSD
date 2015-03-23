package com.example.contactsmanager;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BasicDetailsFragment extends Fragment {
	private Button showhide;
	private Button save;
	private Button cancel;
	private EditText name;
	private EditText phone;
	private EditText email;
	
	@Override
	public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle state) {
		return layoutInflater.inflate(R.layout.fragment_basic_details, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		showhide = (Button) getActivity().findViewById(R.id.button1);
		save = (Button) getActivity().findViewById(R.id.button2);
		cancel = (Button) getActivity().findViewById(R.id.button3);
		name = (EditText) getActivity().findViewById(R.id.editText1);
		phone = (EditText) getActivity().findViewById(R.id.editText2);
		email = (EditText) getActivity().findViewById(R.id.editText3);
		
		save.setOnClickListener(new ButtonListener());
		showhide.setOnClickListener(new ButtonListener());
		cancel.setOnClickListener(new ButtonListener());
		
	}
	
	private class ButtonListener implements View.OnClickListener {

		@Override
		public void onClick(View view) {
			if((Button)view == showhide) {
				android.app.FragmentManager fragmentManager = getActivity().getFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				AdditionalDetailsFragment additionalDetailsFragment = (AdditionalDetailsFragment)fragmentManager.findFragmentById(R.id.containerBottom);
				if (additionalDetailsFragment == null) {
				  fragmentTransaction.add(R.id.containerBottom, new AdditionalDetailsFragment());
				  ((Button)view).setText("Show Additional Fields");
				  fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
				} else {
				  fragmentTransaction.remove(additionalDetailsFragment);
				  ((Button)view).setText("Hide Additional Fields");
				  fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_EXIT_MASK);
				}
				fragmentTransaction.commit();
			}
			
			else if((Button)view == save) {
				android.app.FragmentManager fragmentManager = getActivity().getFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				AdditionalDetailsFragment additionalDetailsFragment = (AdditionalDetailsFragment)fragmentManager.findFragmentById(R.id.containerBottom);
				Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
				intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
				
				EditText jobTitle = (EditText) getActivity().findViewById(R.id.editText4);
				EditText company = (EditText) getActivity().findViewById(R.id.editText5);
				EditText website = (EditText) getActivity().findViewById(R.id.editText6);
				EditText im = (EditText) getActivity().findViewById(R.id.editText7);
				
				if (name != null) {
				  intent.putExtra(ContactsContract.Intents.Insert.NAME, name.getText());
				}
				if (phone != null) {
				  intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone.getText());
				}
				if (email != null) {
				  intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email.getText());
				}
				if (jobTitle != null) {
				  intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitle.getText());
				}
				if (company != null) {
				  intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company.getText());
				}
				ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
				if (website != null) {
				  ContentValues websiteRow = new ContentValues();
				  websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
				  websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website.getText().toString());
				  contactData.add(websiteRow);
				}
				if (im != null) {
				  ContentValues imRow = new ContentValues();
				  imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
				  imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im.getText().toString());
				  contactData.add(imRow);
				}
				intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
				getActivity().startActivity(intent);
			}
			else if((Button)view == cancel) {
				getActivity().finish();
			}
		}
		
	}
}