package com.example.urlshortener;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class ShowResult extends Activity {
	TextView txtView;
	EditText txturl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_result);
		
		txtView = (TextView) findViewById(R.id.txtView);
		txturl = (EditText) findViewById(R.id.txturl);
		
		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
		txtView.setTypeface(tf);
		txturl.setTypeface(tf);
		
		txturl.setText(getIntent().getExtras().getString("URL"));
	}
}
