package com.example.intelligencetest.library;


import java.util.ArrayList;
import java.util.List;

import com.example.intelligencetest.R;
import com.example.intelligencetest.chemical.Chemical;
import com.example.intelligencetest.chemical.ChemicalActivity;
import com.example.intelligencetest.chemical.data.ChemicalDatasource;
import com.example.intelligencetest.chemical.data.ConnectionDetector;
import com.example.scanner.ScanActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData.Item;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Library extends Activity{
	
	ProgressDialog pDialog;
	List<Chemical> chemList;
	ChemicalDatasource data;
	ListView list;
	boolean succeeded = false;
	EditText inputSearch;
	LibraryAdapter adapter;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		setContentView(R.layout.library_list_activity);
		
		//variables
		list = (ListView) findViewById(R.id.library_list);
		list.setTextFilterEnabled(true);
		data = new ChemicalDatasource();
		
		//run the Async Task, for the DB connection
		DatabaseOperation dbOper = new DatabaseOperation();
		dbOper.execute();
		
		//initiate searchview
		//make sure its expanded by setting inconified to false
		//and set listener for when we type in text
		final SearchView inputSearch = (SearchView) findViewById(R.id.libraryInputSearch);
		inputSearch.setIconifiedByDefault(false);
		
		inputSearch.setOnQueryTextListener(new OnQueryTextListener() {
			
			public boolean onQueryTextSubmit(String query) {
				InputMethodManager imm=
					      (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

					  imm.hideSoftInputFromWindow(inputSearch.getWindowToken(), 0);
				return false;
			}
			
			public boolean onQueryTextChange(String newText) {
				Library.this.adapter.getFilter().filter(newText);
				return false;
			}
		});
		
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//Toast.makeText(Library.this, "ID clicked: " + chemList.get(arg2).getName(), Toast.LENGTH_LONG).show();
				Intent newChemical = new Intent(Library.this, ChemicalActivity.class);
                newChemical.putExtra("id", chemList.get(arg2).getChemicalId().toString());
                startActivity(newChemical);
			}
		});
		   
	}
	
	public class LibraryAdapter extends ArrayAdapter<Chemical> implements Filterable {
		
		private List<Chemical> fitems;
	    private List<Chemical> original;
		
		Context context;
		List<Chemical> objects;
	    
		public LibraryAdapter(Context context, int resource,
				int textViewResourceId, List<Chemical> objects) {
			super(context, resource, textViewResourceId, objects);
			this.fitems = new ArrayList<Chemical>(objects);
			this.original = new ArrayList<Chemical>(objects);
			this.context = context;
			
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			
			View v = convertView;
			if(v == null) {
				LayoutInflater inflater = LayoutInflater.from(getContext());
				v = inflater.inflate(R.layout.simple_list_only_text, null);
			}
			
			Chemical chem = fitems.get(position);
			
			if(chem != null) {
				TextView tv = (TextView) v.findViewById(R.id.product_name);
				if(tv != null) tv.setText(chem.getName());
			}
			
			return v;
		}
		
		
		
		@Override
		public Filter getFilter() {
           Filter filter = new Filter() {

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				final FilterResults results = new FilterResults();
				
                
                String prefix = constraint.toString().toLowerCase();

                if (prefix == null || prefix.length() == 0){
                    ArrayList<Chemical> list = new ArrayList<Chemical>(original);
                    results.values = list;
                    results.count = list.size();
                } else {
                	final ArrayList<Chemical> list = new ArrayList<Chemical>(original);
    	            final ArrayList<Chemical> nlist = new ArrayList<Chemical>();
                	for(int i = 0; i < list.size(); i++) {
                		Chemical chem = list.get(i);
                		if(chem.getName().toLowerCase().startsWith(prefix)) {
                			nlist.add(chem);
                		}
                	}
                	results.count = nlist.size();
                	results.values = nlist;                	                	
                }

				return results;
			}

			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint,
					FilterResults results) {
				
				fitems = (List<Chemical>) results.values;
				notifyDataSetChanged();
				clear();
				int count = fitems.size();
		        for(int i = 0; i<count; i++){
		            add(fitems.get(i));
		            notifyDataSetInvalidated();
		        }
				
			}
        	   
           };
           return filter;
		}
	}
	
	
	public class DatabaseOperation extends AsyncTask<String, Void, String> {

		
		@Override
		protected String doInBackground(String... params) {
			
			 ConnectionDetector cd = new ConnectionDetector(getApplicationContext());                    
             Boolean isInternetPresent = cd.isConnectingToInternet();                    
             if(isInternetPresent){
            	 chemList = data.getListOfChemicals("all");	
             }                    
             if(!isInternetPresent){

 				Library.this.runOnUiThread(new Runnable() {
 		            public void run() {				
 						 AlertDialog.Builder builder = new AlertDialog.Builder(Library.this);
 			                builder.setTitle("Network error");
 			                builder.setMessage("Check your internet connection.")  
 	                       .setCancelable(false)
 	                       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                         	   //alert.dismiss();
                            }
                        });                     
 			                AlertDialog alert = builder.create();
 			                alert.show(); 
 		            }
 			 });
 	 
             }
			return "Executed";
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pDialog.dismiss();
			 ConnectionDetector cd = new ConnectionDetector(getApplicationContext());                    
             Boolean isInternetPresent = cd.isConnectingToInternet();      	
			 if(isInternetPresent){
				 adapter = new LibraryAdapter(Library.this, R.layout.simple_list_only_text, R.id.product_name, chemList);
            	 list.setAdapter(adapter);
             }  
			
			
			
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
