package com.example.intelligencetest.persons;

import com.example.intelligencetest.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ShowPersonInfo extends DialogFragment{
	static Person p;
	View content;
	
	public static ShowPersonInfo newInstance(Person person) {
		ShowPersonInfo dialog = new ShowPersonInfo();
		p = person;
		return dialog;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(getDialog() != null) {
			getDialog().setCanceledOnTouchOutside(true);
		}
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = getActivity().getLayoutInflater();
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		content = inflater.inflate(R.layout.show_person_info, null);
		
		//reference and populate textviews
		TextView phone = (TextView) content.findViewById(R.id.tvShowpersonPhone);
		TextView name = (TextView) content.findViewById(R.id.tvShowPersonName);
		TextView email = (TextView) content.findViewById(R.id.tvShowPersonEmail);
		email.setText(p.getEmail());
		name.setText(p.getFirstname() + " " + p.getLastname());
		phone.setText(p.getPhone());
		
		//listener for buttons
		ImageButton callBtn = (ImageButton) content.findViewById(R.id.imgBtnCallPerson);
		callBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:" + p.getPhone()));
				startActivity(callIntent);
				
			}
		});
		
		ImageButton emailBtn = (ImageButton) content.findViewById(R.id.imgBtnSendEmail);
		emailBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("message/rfc822");
				i.putExtra(Intent.EXTRA_EMAIL  , new String[]{p.getEmail()});
				i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
				try {
				    startActivity(Intent.createChooser(i, "Send mail..."));
				} catch (android.content.ActivityNotFoundException ex) {
				    Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
		builder.setView(content).setCancelable(true);
		
		return builder.create();
	}
	
	public void recvData(Person person){
		
		Toast.makeText(getActivity(), p.getFirstname() + " " + p.getLastname(), Toast.LENGTH_LONG).show();
	}
}
