package com.example.intelligencetest.persons;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.applidium.headerlistview.HeaderListView;
import com.applidium.headerlistview.SectionAdapter;
import com.example.intelligencetest.MainActivity;
import com.example.intelligencetest.R;
import com.example.intelligencetest.chemical.data.ConnectionDetector;


//J�rgen

public class PersonFragmentTest extends Fragment implements SendData {	
	public static String LOGTAG = "PersonFragment";
	
	MyAdapter adapter = new MyAdapter();
	Locale locale = Locale.getDefault();
	Boolean finished;
	List<Person> personList = new ArrayList<Person>();		
	List<Character> sections;
	PersonDatasource persondata;
	Person[][] personArray;
	ProgressDialog pDialog;
	HeaderListView list;
	
	public PersonFragmentTest() {
		//empty constructor
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		persondata = new PersonDatasource();
		list = new HeaderListView(getActivity());

		DatabaseOperation dbOper = new DatabaseOperation();
		dbOper.execute();
		setHasOptionsMenu(true);
		
		return list;
	}
	
	@Override
	public void onCreateOptionsMenu(
	      Menu menu, MenuInflater inflater) {	
	   inflater.inflate(R.menu.person_items, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	   // handle item selection
	   switch (item.getItemId()) {
	      case R.id.action_update:
	         // do s.th.
	    	  System.out.println("Au!");
	    	  persondata.clearList();
	    	  DatabaseOperation dbOper = new DatabaseOperation();
	  		  dbOper.execute();
	  		  adapter.notifyDataSetChanged();
	  		
	         return true;
	      default:
	         return super.onOptionsItemSelected(item);
	   }
	}
	
	public class Refresh extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			ConnectionDetector cd = new ConnectionDetector(getActivity());                    
            Boolean isInternetPresent = cd.isConnectingToInternet();                    
            if(isInternetPresent){
            	persondata.getData();
            	
            }                    
            if(!isInternetPresent){

				getActivity().runOnUiThread(new Runnable() {
		            public void run() {				
						 AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
			try{
				pDialog.dismiss();			
			}
			catch(Exception e){
				
			}
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();			
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Oppdaterer personlist...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
		}
		
		
	}

	
	public class DatabaseOperation extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			ConnectionDetector cd = new ConnectionDetector(getActivity());                    
            Boolean isInternetPresent = cd.isConnectingToInternet();                    
            if(isInternetPresent){
            	persondata.getData();
            	
            }                    
            if(!isInternetPresent){

				getActivity().runOnUiThread(new Runnable() {
		            public void run() {				
						 AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
			try{
				sections = persondata.getSections();
				personArray = persondata.getPersonArray();	
				list.setAdapter(adapter);
				pDialog.dismiss();
				
				
			}
			catch(Exception e){
				
			}
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();			
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Oppdaterer personlist...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
		}
		
		
	}
	
	public class MyAdapter extends SectionAdapter {
		
		
		@Override
        public int numberOfSections() {   
        	return sections.size();
        }

        
        @Override
        public int numberOfRows(int section) {
	        return personArray[section].length;
        }

        @Override
        public Object getRowItem(int section, int row) {
            return null;
        }

        @Override
        public boolean hasSectionHeaderView(int section) {
            return true;
        }

        @Override
        public View getRowView(int section, int row, View convertView, ViewGroup parent) {
    
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.simple_list_item_1, null);
            }
            TextView tv = (TextView) convertView.findViewById(R.id.personName);
            if(personArray[section][row] != null) {
            	tv.setText("Name: " + personArray[section][row].getLastname() + ", " + personArray[section][row].getFirstname());
            }
            
            return convertView;
        }

        @Override
        public int getSectionHeaderViewTypeCount() {
            return 2;
        }

        @Override
        public int getSectionHeaderItemViewType(int section) {
            return section % 2;
        }

        
        
        @Override
		public void onRowItemClick(AdapterView<?> parent, View view,
				int section, int row, long id) {
        	Person p = personArray[section][row];
			ShowPersonInfo personDialog = ShowPersonInfo.newInstance(p);
			personDialog.show(getFragmentManager(), "personinfo");
			super.onRowItemClick(parent, view, section, row, id);
		}


		@Override
        public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        	Log.i(LOGTAG, "getSectionHeaderView");
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.person_list_section_divider, null);
            }

            TextView text = (TextView) convertView.findViewById(R.id.dividerText);
            text.setText(String.valueOf(String.valueOf(sections.get(section))).toUpperCase(locale));
            
            return convertView;
        }
	}

	@Override
	public void sendPersonData(Person p) {
		Fragment frag = getFragmentManager().findFragmentByTag("personinfo");
		Log.i(LOGTAG, frag.toString());
	}
}
