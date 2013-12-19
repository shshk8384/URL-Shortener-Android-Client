package com.example.urlshortener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	TextView txtView;
	EditText txturl;
	Button btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		txtView = (TextView) findViewById(R.id.txtView);
		txturl = (EditText) findViewById(R.id.txturl);
		btn = (Button) findViewById(R.id.btn);
		
		// We change the font to the Android Roboto font
		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
		txtView.setTypeface(tf);
		txturl.setTypeface(tf);
		btn.setTypeface(tf);
	}
	
	// Touch the button to call the AsyncTask class
	public void shorten(View v){
		ConnectApi thread = new ConnectApi();
		thread.execute(txturl.getText().toString());
	}
	
	// Method called when AsyncTask finish the communication with the server
	// This method handles the response of the server
	void handleApiResult(String result){
		if(result!=null){
			Intent intent = new Intent(this, ShowResult.class);
			Bundle bundle = new Bundle();
			bundle.putString("URL", result);
			intent.putExtras(bundle);
			startActivity(intent);
		}
		else{
			txturl.setText(getResources().getString(R.string.error));
			txturl.setTextColor(getResources().getColor(R.color.error_red));
		}
	}
	
	// Inner class to connect to the url shortener web server in a different thread
	private class ConnectApi extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			String result = null;
			
			try{
				URL url = new URL("http://192.168.1.33/urlshortener/api/api.php?url="+params[0]);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();

				if(connection.getResponseCode()==HttpURLConnection.HTTP_OK){
					BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					result = reader.readLine();	
				}
			} catch(Exception e){
				Log.e("URL Shortener", "Error with http request: "+e.getMessage());
			}
			
			return result;
		}
		
		@Override
		protected void onPostExecute(String result) {
			handleApiResult(result);
		}
	}
}
