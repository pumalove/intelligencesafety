package com.example.intelligencetest.library;


import java.util.List;

import com.example.intelligencetest.R;
import com.example.intelligencetest.chemical.Chemical;
import com.example.intelligencetest.chemical.ChemicalActivity;
import com.example.intelligencetest.chemical.data.ChemicalDatasource;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Library extends Activity{
	
	ProgressDialog pDialog;
	List<Chemical> chemList;
	ChemicalDatasource data;
	ListView list;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		
		setContentView(R.layout.library_list_activity);
		list = (ListView) findViewById(R.id.library_list);
		data = new ChemicalDatasource();
		DatabaseOperation dbOper = new DatabaseOperation();
		dbOper.execute();
		
		   String products[] = {"Dell Inspiron", "HTC One X", "HTC Wildfire S", "HTC Sense", "HTC Sensation XE",
	               "iPhone 4S", "Samsung Galaxy Note 800",
	               "Samsung Galaxy S3", "MacBook Air", "Mac Mini", "MacBook Pro"};
		
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(Library.this, "ID clicked: " + chemList.get(arg2).getName(), Toast.LENGTH_LONG).show();
				Intent newChemical = new Intent(Library.this, ChemicalActivity.class);
                newChemical.putExtra("id", chemList.get(arg2).getChemicalId().toString());
                startActivity(newChemical);
				
			}
		});
		   
	}
	
	public class LibraryAdapter extends ArrayAdapter<Chemical> {

		public LibraryAdapter(Context context, int resource,
				int textViewResourceId, List<Chemical> objects) {
			super(context, resource, textViewResourceId, objects);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			
			View v = convertView;
			if(v == null) {
				LayoutInflater inflater = LayoutInflater.from(getContext());
				v = inflater.inflate(R.layout.simple_list_only_text, null);
			}
			
			Chemical chem = getItem(position);
			
			if(chem != null) {
				TextView tv = (TextView) v.findViewById(R.id.product_name);
				if(tv != null) tv.setText(chem.getName());
			}
			
			return v;
		}
		
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
			list.setAdapter(new LibraryAdapter(Library.this, R.layout.simple_list_only_text, R.id.product_name, chemList));
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
