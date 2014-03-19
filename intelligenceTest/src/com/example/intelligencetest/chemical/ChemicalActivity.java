package com.example.intelligencetest.chemical;



import com.example.intelligencetest.MainActivity;
import com.example.intelligencetest.R;
import com.example.intelligencetest.chemical.ui.ShowFirefightingData;
import com.example.intelligencetest.chemical.ui.ShowFirstaidData;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;

import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class ChemicalActivity extends FragmentActivity {
	
	String id;
	String name;
	String emergencyphone;
	String type;
	ChemicalDatasource chemData;
	
	Chemical chemical;
	public ChemicalDatasheet datasheet;
	
	ProgressDialog pDialog;
	
	
	//TextViews
	TextView tvName;
	
	public ChemicalActivity() {
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_checmical_layout);
		
		
		Intent intent = getIntent();
		id = intent.getStringExtra("id");
		chemData = new ChemicalDatasource();
		tvName = (TextView) findViewById(R.id.chemical_name);
		
		DatabaseOperation dbOper = new DatabaseOperation();
		dbOper.execute();

		
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.firstaid_measures_click);
		layout.setOnClickListener(new OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				ShowFirstaidData dialog = new ShowFirstaidData();
				dialog.setFirstaidInfo(datasheet.getFirstAidOnSkinContact(), 
						datasheet.getFirstAidOnEyeContact(), 
						datasheet.getFirstAidIfInhaled(), 
						datasheet.getFirstAidOnIngestion());
				
				dialog.show(getSupportFragmentManager(), "firstaid");
				
				
			}
		});
		
		RelativeLayout fflayout = (RelativeLayout) findViewById(R.id.firefighting_measures_click);
		fflayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ShowFirefightingData dialog = new ShowFirefightingData();
				dialog.setFireFightingInfo(datasheet.getFireFightingExtinguishingMedia(), 
						datasheet.getFireFightingSpecialHazards(), 
						datasheet.getFireFightingAdvice());
				dialog.show(getSupportFragmentManager(), "firefighting");
				
			}
		});
		
		createPdfLink();
		
	}
	
	
	private void createPdfLink() {
		TextView pdf = (TextView) findViewById(R.id.tvpdf);
		pdf.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 WebView webView = new WebView(ChemicalActivity.this);
                 String myPdfUrl = datasheet.getPdfAddress();
                 String url = "http://docs.google.com/gview?embedded=true&url=" + myPdfUrl;
                 webView.getSettings().setJavaScriptEnabled(true); 
                 webView.loadUrl(url);		
			}
		});
		
	}


	public class DatabaseOperation extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			chemical = chemData.getChemicalById(id);
			return "Executed";
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pDialog.dismiss();
			tvName.setText(chemical.getName());
			datasheet = chemData.getCurrentDatasheet();
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();			
		    pDialog = new ProgressDialog(ChemicalActivity.this);
            pDialog.setMessage("Oppdaterer..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
		}
		
		
	}
}

