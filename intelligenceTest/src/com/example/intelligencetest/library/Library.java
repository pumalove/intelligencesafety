package com.example.intelligencetest.library;


import java.util.List;

import com.example.intelligencetest.R;
import com.example.intelligencetest.chemical.Chemical;
import com.example.intelligencetest.chemical.ChemicalActivity;
import com.example.intelligencetest.chemical.data.ChemicalDatasource;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Library extends Activity{
	
	ProgressDialog pDialog;
	List<Chemical> chemList;
	ChemicalDatasource data;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		setContentView(R.layout.library_list_activity);
		data = new ChemicalDatasource();
		DatabaseOperation dbOper = new DatabaseOperation();
		dbOper.execute();
		
		   String products[] = {"Dell Inspiron", "HTC One X", "HTC Wildfire S", "HTC Sense", "HTC Sensation XE",
	               "iPhone 4S", "Samsung Galaxy Note 800",
	               "Samsung Galaxy S3", "MacBook Air", "Mac Mini", "MacBook Pro"};
		   
		ListView list = (ListView) findViewById(R.id.library_list);
		list.setAdapter(new ArrayAdapter<String>(getApplication(), R.layout.simple_list_only_text, R.id.product_name, products));
		
	}
	
	public class DatabaseOperation extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			chemList = data.getListOfChemicals();
			return "Executed";
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pDialog.dismiss();
;
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();			
		    pDialog = new ProgressDialog(Library.this);
            pDialog.setMessage("Oppdaterer..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
		}
		
		
	}
}
