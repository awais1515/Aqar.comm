package com.gp2.omar.aqarcom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


public class SignUpPage extends Activity
{
	private AdView   mAdView;
	private EditText fnameEditText, lnameEditText;

	@Override
	protected void onCreate ( Bundle savedInstanceState )
	{
		super.onCreate ( savedInstanceState );
		this.requestWindowFeature ( Window.FEATURE_NO_TITLE );
		this.getWindow ( ).setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
		setContentView ( R.layout.activity_sign_up_page );

		fnameEditText = findViewById ( R.id.EntryFName );
		lnameEditText = findViewById ( R.id.EntryLName );

		MobileAds.initialize ( getApplicationContext ( ), getString ( R.string.banner_ad_unit_id ) );
		mAdView = findViewById ( R.id.adView2 );
		AdRequest adRequest = new AdRequest.Builder ( ).build ( );
		mAdView.loadAd ( adRequest );

	}

	public void NextToCont ( View view )
	{
		if ( TextUtils.isEmpty ( fnameEditText.getText ( ).toString ( ) ) || TextUtils.isEmpty ( lnameEditText.getText ( ).toString ( ) ) )
		{
			if ( TextUtils.isEmpty ( fnameEditText.getText ( ).toString ( ) ) )
			{
				fnameEditText.setError ( getString ( R.string.Required ) );
			}
			if ( TextUtils.isEmpty ( lnameEditText.getText ( ).toString ( ) ) )
			{
				lnameEditText.setError ( getString ( R.string.Required ) );
			}
		}
		else
		{
			UserReg.fname = fnameEditText.getText ( ).toString ( );
			UserReg.lname = lnameEditText.getText ( ).toString ( );
			startActivity ( new Intent ( SignUpPage.this, Cont_SignUpPage_Email.class ) );
		}
	}
}
