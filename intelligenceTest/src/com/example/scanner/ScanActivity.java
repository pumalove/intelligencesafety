package com.example.scanner;

import net.sourceforge.zbar.Symbol;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dm.zbar.android.scanner.ZBarConstants;
import com.dm.zbar.android.scanner.ZBarScannerActivity;
import com.example.intelligencetest.R;
import com.example.intelligencetest.chemical.ChemicalActivity;
import com.example.intelligencetest.chemical.data.ConnectionDetector;

public class ScanActivity extends Activity {

    private static final int ZBAR_SCANNER_REQUEST = 0;
    private static final int ZBAR_QR_SCANNER_REQUEST = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button b = (Button) findViewById(R.id.scan_btn);
 
    }

    public void launchScanner(View v) {
        if (isCameraAvailable()) {
            Intent intent = new Intent(this, ZBarScannerActivity.class);
            startActivityForResult(intent, ZBAR_SCANNER_REQUEST);
        } else {
            Toast.makeText(this, "Rear Facing Camera Unavailable", Toast.LENGTH_SHORT).show();
        }
    }

    public void launchQRScanner(View v) {
        if (isCameraAvailable()) {
            Intent intent = new Intent(this, ZBarScannerActivity.class);
            intent.putExtra(ZBarConstants.SCAN_MODES, new int[]{Symbol.QRCODE});
            startActivityForResult(intent, ZBAR_SCANNER_REQUEST);
        } else {
            Toast.makeText(this, "Rear Facing Camera Unavailable", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isCameraAvailable() {
        PackageManager pm = getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String result;
		switch (requestCode) {
            case ZBAR_SCANNER_REQUEST:
            case ZBAR_QR_SCANNER_REQUEST:
                if (resultCode == RESULT_OK) {
                	result = data.getStringExtra(ZBarConstants.SCAN_RESULT);
                    Toast.makeText(this, "Scan Result = " + result, Toast.LENGTH_SHORT).show();
                    
                    
                    
                    ConnectionDetector cd = new ConnectionDetector(getApplicationContext());                    
                    Boolean isInternetPresent = cd.isConnectingToInternet();                    
                    if(isInternetPresent){
                    	Intent newChemical = new Intent(this, ChemicalActivity.class);
                        newChemical.putExtra("id", result);
                        startActivity(newChemical);
                    }                    
                    if(!isInternetPresent){

        				ScanActivity.this.runOnUiThread(new Runnable() {
        		            public void run() {				
        						 AlertDialog.Builder builder = new AlertDialog.Builder(ScanActivity.this);
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
                    
                } else if(resultCode == RESULT_CANCELED && data != null) {
                    String error = data.getStringExtra(ZBarConstants.ERROR_INFO);
                    if(!TextUtils.isEmpty(error)) {
                        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                    }
                } else if(resultCode == RESULT_CANCELED) {	
                	Toast.makeText(this, "Back button was pressed", Toast.LENGTH_SHORT).show();
                	finish();
                }
                break;
        }
    }

}
