package com.gp2.omar.aqarcom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginPage extends Activity
{
	private EditText emailEditText, passEditText;
	private FirebaseAuth mAuth;
	private AdView       mAdView;
	private ProgressBar  progressBar;

	@Override
	protected void onCreate ( Bundle savedInstanceState )
	{
		super.onCreate ( savedInstanceState );
		this.requestWindowFeature ( Window.FEATURE_NO_TITLE );
		this.getWindow ( ).setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
		setContentView ( R.layout.activity_login_page );

		MobileAds.initialize ( getApplicationContext ( ), getString ( R.string.banner_ad_unit_id ) );
		mAdView = findViewById ( R.id.adView1 );
		AdRequest adRequest = new AdRequest.Builder ( ).build ( );
		mAdView.loadAd ( adRequest );

		mAuth = FirebaseAuth.getInstance ( );
		emailEditText = findViewById ( R.id.LoginEmail );
		passEditText = findViewById ( R.id.LoginPassword );
		progressBar = findViewById ( R.id.progressbasLogin );

	}


	public void LoginCheck ( View view )
	{
		progressBar.setVisibility ( View.VISIBLE );
		startSignIn ( );
	}

	public void startSignIn ( )
	{
		//To Get Permisission For Use Location.
		try
		{
			MapsActivity m = new MapsActivity ( );
			m.GetPermission ( );
		}
		catch ( Exception e )
		{

		}
		if ( TextUtils.isEmpty ( emailEditText.getText ( ).toString ( ) ) || TextUtils.isEmpty ( passEditText.getText ( ).toString ( ) ) )
		{
			emailEditText.setError ( getString ( R.string.Required ) );
			passEditText.setError ( getString ( R.string.Required ) );
			progressBar.setVisibility ( View.GONE );
		}
		else
		{
			mAuth.signInWithEmailAndPassword ( emailEditText.getText ( ).toString ( ), passEditText.getText ( ).toString ( ) ).addOnCompleteListener ( new OnCompleteListener < AuthResult > ( )
			{
				@Override
				public void onComplete ( @NonNull Task < AuthResult > task )
				{
					if ( ! task.isSuccessful ( ) )
					{
						emailEditText.setError ( getString ( R.string.check_correct ) );
						passEditText.setError ( getString ( R.string.check_correct ) );
						progressBar.setVisibility ( View.GONE );
					}
					else
					{
						progressBar.setVisibility ( View.GONE );
						startActivity ( new Intent ( LoginPage.this, MapsActivity.class ) );
					}
				}
			} );


		}
	}

	public void ResetPass ( View view )
	{
		startActivity ( new Intent ( this, Reset_Pass.class ) );
	}

	@Override
	public void onBackPressed ( )
	{
		startActivity ( new Intent ( this, MainActivity.class ) );
	}


}
