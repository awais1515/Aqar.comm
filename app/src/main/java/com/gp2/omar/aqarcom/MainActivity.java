package com.gp2.omar.aqarcom;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends Activity
{

	private FirebaseAuth                   mAuth;
	private FirebaseAuth.AuthStateListener mAuthListener;
	private AdView                         mAdView;
	private Boolean exit = false;

	@Override
	protected void onStart ( )
	{
		super.onStart ( );
		mAuth.addAuthStateListener ( mAuthListener );

	}

	@Override
	protected void onCreate ( Bundle savedInstanceState )
	{

		super.onCreate ( savedInstanceState );
		this.requestWindowFeature ( Window.FEATURE_NO_TITLE );
		this.getWindow ( ).setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );

		setContentView ( R.layout.activity_main );
		MobileAds.initialize ( getApplicationContext ( ), getString ( R.string.banner_ad_unit_id ) );
		mAdView = findViewById ( R.id.adView );
		AdRequest adRequest = new AdRequest.Builder ( ).build ( );
		mAdView.loadAd ( adRequest );

		mAuth = FirebaseAuth.getInstance ( );
		mAuthListener = new FirebaseAuth.AuthStateListener ( )
		{
			@Override
			public void onAuthStateChanged ( @NonNull FirebaseAuth firebaseAuth )
			{
				if ( firebaseAuth.getCurrentUser ( ) != null )
				{
					startActivity ( new Intent ( MainActivity.this, MapsActivity.class ) );
				}

			}
		};
	}


	public void GoToLoginPage ( View view )
	{
		startActivity ( new Intent ( MainActivity.this, LoginPage.class ) );
	}

	public void GoToSignUpPage ( View view )
	{
		startActivity ( new Intent ( MainActivity.this, SignUpPage.class ) );
	}

	@Override
	public void onBackPressed ( )
	{
		if ( exit == true )
		{
			Intent intent = new Intent ( Intent.ACTION_MAIN );
			intent.addCategory ( Intent.CATEGORY_HOME );
			intent.setFlags ( Intent.FLAG_ACTIVITY_CLEAR_TOP );
			startActivity ( intent );
			finish ( );
			System.exit ( 0 );
		}
		exit = true;
		Toast.makeText ( this, getString ( R.string.Press_Back_Again ), Toast.LENGTH_SHORT ).show ( );
		new Handler ( ).postDelayed ( new Runnable ( )
		{
			@Override
			public void run ( )
			{
				exit = false;
			}
		}, 3000 );
	}
}
