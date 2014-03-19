package com.example.intelligencetest.chemical.ui;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.intelligencetest.R;
import com.example.intelligencetest.chemical.ChemicalActivity;
import com.example.intelligencetest.chemical.ChemicalDatasheet;

	public class ShowFirstaidData extends DialogFragment {

		
	   TextView onSkinContactTV;
	   TextView onEyeContactTV;
	   TextView onIngestionTV;
	   TextView ifInhaledTV;
	   
	   String onSkinContact;
	   String onEyeContact;
	   String ifInhaled;
	   String onIngestion;
	   
	   public void setFirstaidInfo(String onskin, String oneye, String inhaled, String ingestion) {
		   onSkinContact = onskin;
		   onEyeContact = oneye;
		   ifInhaled = inhaled;
		   onIngestion = ingestion;
		   
	   }
	   
	   public ShowFirstaidData() {
		   super();
	   }
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			LayoutInflater inflater = getActivity().getLayoutInflater();
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
			final View content = inflater.inflate(R.layout.show_firstaid_data, null);
			onSkinContactTV = (TextView) content.findViewById(R.id.firstaid_onSkinContact);
			onEyeContactTV = (TextView) content.findViewById(R.id.firstaid_onEyeContact);
			onIngestionTV = (TextView) content.findViewById(R.id.firstaid_oningestion);
			ifInhaledTV = (TextView) content.findViewById(R.id.firstaid_ifInhaled);
			fillTextViews();
			
			builder.setView(content)
			//adding buttons
				.setNegativeButton("Close", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						ShowFirstaidData.this.getDialog().cancel();
					}
				});
			
			return builder.create();
		}
		
		private void fillTextViews() {
			onSkinContactTV.setText(onSkinContact);
			onEyeContactTV.setText(onEyeContact);
			onIngestionTV.setText(onIngestion);
			ifInhaledTV.setText(ifInhaled);
			
		}
	}