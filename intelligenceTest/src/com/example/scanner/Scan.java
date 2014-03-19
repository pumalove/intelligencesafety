package com.example.scanner;
// Scan klassen kan fjernes når menyen kommer på plass. Slett Scan.java og gå rett til ScanActivity fra menyen. 
import com.example.intelligencetest.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class Scan extends Fragment implements OnClickListener {
	Button btn;

    @Override
    public View onCreateView(LayoutInflater inflater,
    ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(
            R.layout.scan, container, false);
        
        btn = (Button) view.findViewById(R.id.button1);
        btn.setOnClickListener(this);       

        return view;
    }

    @Override
    public void onClick(View v) {
    	Intent i = new Intent(getActivity(), ScanActivity.class);
		startActivity(i);

    }
    
}

