package com.example.intelligencetest.persons;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import android.app.ProgressDialog;
import android.os.AsyncTask;


import com.applidium.headerlistview.HeaderListView;
import com.applidium.headerlistview.SectionAdapter;
import com.example.intelligencetest.R;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


//Jørgen

public class PersonFragmentTest extends Fragment {	
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
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		persondata = new PersonDatasource();
		list = new HeaderListView(getActivity());

		DatabaseOperation dbOper = new DatabaseOperation();
		dbOper.execute();
				
		return list;
	}
	
	public class DatabaseOperation extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			persondata.getData();
			return "Executed";
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			sections = persondata.getSections();
			personArray = persondata.getPersonArray();		
			pDialog.dismiss();
			list.setAdapter(adapter);
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
	

}
