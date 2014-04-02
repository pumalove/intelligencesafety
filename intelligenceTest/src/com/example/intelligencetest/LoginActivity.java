package com.example.intelligencetest;

import java.util.ArrayList;
import java.util.zip.Inflater;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	TextView errorMsg;
	EditText etPassword;
	EditText etUsername;
	Button b;
	DBOperation dbOper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		b = (Button) findViewById(R.id.signinButton);
		etPassword = (EditText) findViewById(R.id.etPassword);
		etUsername = (EditText) findViewById(R.id.etUsername);
		errorMsg = (TextView) findViewById(R.id.loginError);
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dbOper = new DBOperation();
				dbOper.execute();
			}
		});
		
	}
	
	public void validateResult(String result) {
		
		Log.i("LoginActivity", result);
		
		if(result.equals("NO_USER_FOUND")) {
			Log.i("LoginActivity", "Bruker ble ikke funnet");
			errorMsg.setText("Username/Password Incorrect");
		} else if (result.equals("USER_FOUND")) {
      	  	Intent intent = new Intent();
      	  	intent.putExtra("username", etPassword.getText().toString().trim());
      	  	intent.putExtra("password", etPassword.getText().toString().trim());
      	  	setResult(RESULT_OK, intent);
      	  	finish();
		}
		
	}
	
	private class DBOperation extends AsyncTask<String, Void, String> {

		String responseString;
		
		@Override
		protected String doInBackground(String... params) {
			CheckUserInDB();
			return null;
		}
		
		private void CheckUserInDB() {
			DefaultHttpClient httpclient;
			HttpPost httppost;
			ArrayList<NameValuePair> nameValuePairs;
			HttpResponse response;
			
			try{ 
	        	//Sending values to the PHP-script
	            httpclient=new DefaultHttpClient();
	            httppost= new HttpPost("http://home.uia.no/jorgel11/ICA/ica.php"); // 
	            //creating arraylist
	            nameValuePairs = new ArrayList<NameValuePair>(3);
	            nameValuePairs.add(new BasicNameValuePair("action", "login"));
	            // Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar, 
	            nameValuePairs.add(new BasicNameValuePair("username",etUsername.getText().toString().trim()));  // $Edittext_value = $_POST['Edittext_value'];
	            nameValuePairs.add(new BasicNameValuePair("password",etPassword.getText().toString().trim())); 
	            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	           
	            //Execute HTTP Post Request
	            response=httpclient.execute(httppost);
	            response.getEntity().consumeContent();
	            ResponseHandler<String> responseHandler = new BasicResponseHandler();
	            responseString = httpclient.execute(httppost, responseHandler);

	            
	 
			} catch (Exception exception) {
				//do something
			}
			

		}
			
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		
		@Override
		protected void onPostExecute(String result) {			
			super.onPostExecute(result);
			validateResult(responseString.trim());
		}
		
	}
	
}
