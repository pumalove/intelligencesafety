package com.example.intelligencetest.library;


import com.example.intelligencetest.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Library extends FragmentActivity{
	//Test new commit

	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		setContentView(R.layout.library_list_activity);
		

		   String products[] = {"Dell Inspiron", "HTC One X", "HTC Wildfire S", "HTC Sense", "HTC Sensation XE",
	               "iPhone 4S", "Samsung Galaxy Note 800",
	               "Samsung Galaxy S3", "MacBook Air", "Mac Mini", "MacBook Pro"};
		   
		ListView list = (ListView) findViewById(R.id.library_list);
		list.setAdapter(new ArrayAdapter<String>(getApplication(), R.layout.simple_list_only_text, R.id.product_name, products));
		
	}
}
