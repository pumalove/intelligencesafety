package com.example.intelligencetest.chemical;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.ParseException;
import android.util.Log;

public class ChemicalDatasource {
	
	private static final String LOGTAG = "ChemicalDatasource";
	
	
	Chemical currentChemical;
	ChemicalDatasheet currentDatasheet;
	
	public ChemicalDatasource() {
		//chemical = new Chemical("Formidor", "Liquid", "+47 815 49 300");
	}
	
	
	public Chemical getChemicalById(String id) {
		JSONArray jArray = null;

		String result = null;

		StringBuilder sb = null;

		InputStream is = null;


		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		try{
			 nameValuePairs.add(new BasicNameValuePair("chemid", id.trim()));
			 HttpClient httpclient = new DefaultHttpClient();
			 
			 HttpPost httppost = new HttpPost("http://home.uia.no/jorgel11/ICA/getAllChemInfo.php");
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
			jArray = object.getJSONArray("chem");
			JSONObject json_data = null;
	      for(int i=0;i<jArray.length();i++){
	    	  json_data = jArray.getJSONObject(i);
	          	createChemicalFromJSON(json_data);
	          	createDatasheetFromJSON(json_data);
	          	
	         }
	      }
	      catch(JSONException JSONException){
	       Log.i(LOGTAG , "No data found: + " + JSONException.toString());
	      } catch (ParseException parseException) {
	    	  parseException.printStackTrace();
	      }
		
		return currentChemical;
	}
	
	
	public ChemicalDatasheet getCurrentDatasheet() {
		return currentDatasheet;
	}
	
	private void createDatasheetFromJSON(JSONObject json_data) {
		currentDatasheet = new ChemicalDatasheet();
		
		try {
			currentDatasheet.setChemicalId(json_data.getString("chem_id"));
			currentDatasheet.setRevisionDate(json_data.getString("revision_date"));
			currentDatasheet.setPdfAddress(json_data.getString("pdf"));
			currentDatasheet.setProducerName(json_data.getString("producer_name"));
			currentDatasheet.setProducerEmail(json_data.getString("producer_email"));
			currentDatasheet.setProducerLocation(json_data.getString("producer_location"));
			currentDatasheet.setProducerPhone(json_data.getString("producer_phone"));
			currentDatasheet.setContaintmentAndCleaning(json_data.getString("containment_and_cleaning"));
			currentDatasheet.setEnvironmentalPrecatuions(json_data.getString("environmental_precatuions"));	
			currentDatasheet.setFireFightingExtinguishingMedia(json_data.getString("extinguishing_media"));
			currentDatasheet.setFireFightingSpecialHazards(json_data.getString("special_hazards"));
			currentDatasheet.setFireFightingAdvice(json_data.getString("firefighting_advice"));
			currentDatasheet.setFirstAidIfInhaled(json_data.getString("ifinhaled"));
			currentDatasheet.setFirstAidOnSkinContact(json_data.getString("onskincontact"));
			currentDatasheet.setFirstAidOnEyeContact(json_data.getString("oneyecontact"));
			currentDatasheet.setFirstAidOnIngestion(json_data.getString("oningestion"));
			Log.i(LOGTAG, "Datasheet created for chemical: " + currentChemical.getName());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void createChemicalFromJSON(JSONObject json_data) {
		currentChemical = new Chemical();
		try {
			currentChemical.setName(json_data.getString("chem_name"));
			currentChemical.setType(json_data.getString("chem_type"));
			currentChemical.setChemicalId(json_data.getString("chem_id"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
