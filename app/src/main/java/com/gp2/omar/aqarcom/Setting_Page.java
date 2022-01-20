package com.gp2.omar.aqarcom;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;


public class Setting_Page extends AppCompatActivity
{
	private FirebaseAuth mAuth;
	private AdView       mAdView;

	@Override
	protected void onCreate ( Bundle savedInstanceState )
	{
		super.onCreate ( savedInstanceState );
		this.setTitle ( "Setting" );
		setContentView ( R.layout.activity_setting__page );
		mAuth = FirebaseAuth.getInstance ( );

		MobileAds.initialize ( getApplicationContext ( ), getString ( R.string.banner_ad_unit_id ) );
		mAdView = ( AdView ) findViewById ( R.id.adView5 );
		AdRequest adRequest = new AdRequest.Builder ( ).build ( );
		mAdView.loadAd ( adRequest );
	}

	public void ChangepassIntent ( View view )
	{
		startActivity ( new Intent ( this, Change_Password.class ) );
	}


	public void Deactivate ( View view )
	{
		startActivity ( new Intent ( this, Deactivate_Page.class ) );
	}

	public void deleteall ( View view )
	{
		startActivity ( new Intent ( this, Delete_All_ITem.class ) );

	}

	public void GoTOEdit ( View view )
	{
		startActivity ( new Intent ( this, Edit_Profile.class ) );
	}


	public void logout ( View view )
	{
		AlertDialog alertDialog = new AlertDialog.Builder ( Setting_Page.this ).create ( );
		alertDialog.setTitle ( getString ( R.string.alert ) );
		alertDialog.setMessage ( getString ( R.string.areyousuretologout ) );
		alertDialog.setButton ( AlertDialog.BUTTON_NEUTRAL, getString ( R.string.yes ), new DialogInterface.OnClickListener ( )
		{
			public void onClick ( DialogInterface dialog, int which )
			{
				FirebaseAuth.getInstance ( ).signOut ( );
				startActivity ( new Intent ( Setting_Page.this, LoginPage.class ) );
			}
		} );
		alertDialog.setButton ( DialogInterface.BUTTON_NEGATIVE, getString ( R.string.no ), new DialogInterface.OnClickListener ( )
		{
			@Override
			public void onClick ( DialogInterface dialog, int which )
			{
				// Do Nothing .
			}
		} );
		alertDialog.show ( );

	}

	public void ShowProfile ( View view )
	{
		Item_Profile k = new Item_Profile ( );
		Item_Profile.EMAIL_USER = mAuth.getCurrentUser ( ).getEmail ( );
		Intent intent = new Intent ( Setting_Page.this, Profile.class );
		intent.putExtra ( "activity", "Setting" );
		startActivity ( intent );
	}

	@Override
	public void onBackPressed ( )
	{
		startActivity ( new Intent ( this, MapsActivity.class ) );
	}
}
