package com.example.intelligencetest.chemical.ui;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.intelligencetest.R;

	public class ShowFirefightingData extends DialogFragment {

		
	   TextView extinguishingMediaTV;
	   TextView specialHazardsTV;
	   TextView adviceTV;
	   
	   String extMedia;
	   String specialHazards;
	   String advice;
	   
	   public void setFireFightingInfo(String media, String hazards, String adv) {
		   extMedia = media;
		   specialHazards = hazards;
		   advice = adv;
		   
	   }
	   
	   public ShowFirefightingData() {
		   super();
	   }
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			LayoutInflater inflater = getActivity().getLayoutInflater();
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
			final View content = inflater.inflate(R.layout.show_firefighting_data, null);
			extinguishingMediaTV = (TextView) content.findViewById(R.id.firefighting_extMedia);
			specialHazardsTV = (TextView) content.findViewById(R.id.firefighting_specialHazards);
			adviceTV = (TextView) content.findViewById(R.id.firefighting_advice);
			fillTextViews();
			
			builder.setView(content)
			//adding buttons
				.setNegativeButton("Close", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						ShowFirefightingData.this.getDialog().cancel();
					}
				});
			
			return builder.create();
		}
		
		private void fillTextViews() {
			extinguishingMediaTV.setText(extMedia);
			specialHazardsTV.setText(specialHazards);
			adviceTV.setText(advice);
			
		}
	}