package com.example.intelligencetest.persons;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.net.ParseException;
import android.util.Log;


public class PersonDatasource  {
	
	
	
	private static final String LOGTAG = "PersonDataSource";
	List<Person> personList = new ArrayList<Person>();
	char[] alphabet = "abcdefghijklmnopqrstuvwxyz¾¿Œ".toCharArray();
	List<Character> sections;
	
	public PersonDatasource() {
		
	}

	
	//see if the section allready exists
	public boolean hasSection(char letter) {	
		if(sections == null) return false;
		
		for(int i = 0; i < sections.size(); i++) {
			if(Character.toUpperCase(sections.get(i)) == Character.toUpperCase(letter)) return true;
		}
		return false;
	}
	

	public List<Character> getSections() {
		sections = new ArrayList<Character>();
		
		for(int a = 0; a < alphabet.length; a++) {
			for(Person p : personList) {
				if(String.valueOf(p.getLastname().charAt(0)).equalsIgnoreCase(String.valueOf(alphabet[a])) && 
						!hasSection(p.getLastname().charAt(0))) {
					sections.add(alphabet[a]);
				}
				
			}
		}
		
		return sections;
	}
	
	public int getLargestRowCount(char c) {
		int largestCount = 0;
		int counter = 0;
		
		for(Person p : personList) {
			if(String.valueOf(p.getLastname().charAt(0)).equalsIgnoreCase(String.valueOf(c))) {
				counter++;
			}
		}
		
		if(counter > largestCount) {
			largestCount = counter;
			counter = 0;
		} else {
			counter = 0;
		}

		Log.i(LOGTAG, "Larges row count: " + largestCount);
		return largestCount;
	}
	
	public Person[][] getPersonArray() {
		Person[][] personArray = new Person[sections.size()][];
		for(int section = 0; section < getSections().size(); section++) {
			int count = 0;
			personArray[section] = new Person[getLargestRowCount(sections.get(section))];
			for(Person p : personList) {
				if(String.valueOf(p.getLastname().charAt(0)).equalsIgnoreCase(String.valueOf(sections.get(section)))) {
					personArray[section][count] = p;
					count++;
				}
			}
		}
		
		return personArray;
	}
	
	public List<Person> getPersonList() {
		return personList;
	}
	
	public void getData() {
		JSONArray jArray = null;

		String result = null;

		StringBuilder sb = null;

		InputStream is = null;


		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		//http post
		try{
			 HttpClient httpclient = new DefaultHttpClient();
			

			 HttpPost httppost = new HttpPost("http://home.uia.no/jorgel11/ICA/getPersons.php");
			 httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			 HttpResponse response = httpclient.execute(httppost);
			 HttpEntity entity = response.getEntity();
			 is = entity.getContent();
		}catch(Exception e){
	         Log.e("log_tag", "Error in http connection"+e.toString());
		}
		//convert response to string
		try {
			
	       BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
	       sb = new StringBuilder();
	       sb.append(reader.readLine() + "\n");
	
	       String line="0";
	       while ((line = reader.readLine()) != null) {
	                      sb.append(line + "\n");
	       }
	       is.close();
	       result=sb.toString();
	       Log.i(LOGTAG, result);
       	} catch(Exception exception) {
            Log.e("log_tag", "Error converting result "+exception.toString());
        }
		
		try {
		JSONArray array = new JSONArray(result);
		JSONObject object = array.getJSONObject(0);
		jArray = object.getJSONArray("persons");
		JSONObject json_data = null;
		Log.i(LOGTAG, "Teller: " + jArray.toString());
	      for(int i=0;i<jArray.length();i++){
	    	  json_data = jArray.getJSONObject(i);
	           //person_id | person_fname | person_lname | person_phone | person_email
	            personList.add(new Person(Integer.parseInt(json_data.getString("person_id")), 
	            		json_data.getString("person_fname"), 
	            		json_data.getString("person_lname"), 
	            		json_data.getString("person_email"), 
	            		"img", 
	            		json_data.getString("person_phone")));
	         }
	      }
	      catch(JSONException JSONException){
	       Log.i(LOGTAG , "No data found: + " + JSONException.toString());
	      } catch (ParseException parseException) {
	    	  parseException.printStackTrace();
	      }
		for(Person p : personList) {
			Log.i(LOGTAG, "person added in list : " + p.getFirstname() + " " + p.getLastname());
		}
	}
	
}
