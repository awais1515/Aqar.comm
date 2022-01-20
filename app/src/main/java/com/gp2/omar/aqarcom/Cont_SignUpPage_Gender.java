package com.gp2.omar.aqarcom;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Cont_SignUpPage_Gender extends Activity
{
	private ProgressBar  progressBar;
	private FirebaseAuth auth;
	private RadioButton  femaleRadioButoon, maleRadioButton;
	FirebaseFirestore db = FirebaseFirestore.getInstance ( );

	@Override
	protected void onCreate ( Bundle savedInstanceState )
	{
		super.onCreate ( savedInstanceState );
		this.requestWindowFeature ( Window.FEATURE_NO_TITLE );
		this.getWindow ( ).setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );

		setContentView ( R.layout.activity_cont__sign_up_page__gender );

		progressBar = findViewById ( R.id.progressBarSignUp );
		auth = FirebaseAuth.getInstance ( );

		femaleRadioButoon = findViewById ( R.id.radioButton_Female );
		maleRadioButton = findViewById ( R.id.radioButton_Male );


	}


	// Set Radio That Choice From Customer And Uncheck For Another Radio.
	public void SelectorGender ( View view )
	{

		boolean Checked = ( ( RadioButton ) view ).isChecked ( );

		switch ( view.getId ( ) )
		{

			case R.id.radioButton_Male:
				if ( Checked )
				{
					femaleRadioButoon.setChecked ( false );
					UserReg.gender = "Male";

				}
				break;
			case R.id.radioButton_Female:
				if ( Checked )
				{
					maleRadioButton.setChecked ( false );
					UserReg.gender = "Female";
				}
				break;
		}

	}

	public void NextToPass ( View view )
	{
		SendEmaiAndPassToAutho ( );
	}

	private void SendEmaiAndPassToAutho ( )
	{
		try
		{
			progressBar.setVisibility ( View.VISIBLE );
			auth.createUserWithEmailAndPassword ( UserReg.eemail, UserReg.ppass ).addOnCompleteListener ( Cont_SignUpPage_Gender.this, new OnCompleteListener < AuthResult > ( )
			{
				@Override
				public void onComplete ( @NonNull Task < AuthResult > task )
				{
					Toast.makeText ( Cont_SignUpPage_Gender.this, "createUserWithEmail:onComplete:" + task.isSuccessful ( ), Toast.LENGTH_SHORT ).show ( );
					progressBar.setVisibility ( View.GONE );
					if ( ! task.isSuccessful ( ) )
					{
						Toast.makeText ( Cont_SignUpPage_Gender.this, "Authentication failed." + task.getException ( ), Toast.LENGTH_SHORT ).show ( );
					}
					else
					{
						Map < String, Object > Item = new HashMap <> ( );
						Item.put ( "Fname", UserReg.fname );
						Item.put ( "Lname", UserReg.lname );
						Item.put ( "Email", UserReg.eemail );
						Item.put ( "Password", UserReg.ppass );
						Item.put ( "Age", UserReg.age );
						Item.put ( "Phone", UserReg.phone );
						Item.put ( "Gender", UserReg.gender );
						Item.put ( "Profile_Picture", UserReg.profilepicture );

						// Add a new document with a Email ID
						db.collection ( "UserInfo" ).document ( UserReg.eemail ).set ( Item ).addOnSuccessListener ( new OnSuccessListener < Void > ( )
						{
							@Override
							public void onSuccess ( Void aVoid )
							{
								Toast.makeText ( Cont_SignUpPage_Gender.this, "Welcome mr." + UserReg.fname, Toast.LENGTH_SHORT ).show ( );
								progressBar.setVisibility ( View.GONE );
								startActivity ( new Intent ( Cont_SignUpPage_Gender.this, MapsActivity.class ) );
							}
						} ).addOnFailureListener ( new OnFailureListener ( )
						{
							@Override
							public void onFailure ( @NonNull Exception e )
							{
								progressBar.setVisibility ( View.GONE );
								Toast.makeText ( Cont_SignUpPage_Gender.this, "Error Registration, Try Again Later!", Toast.LENGTH_SHORT ).show ( );
							}
						} );
						finish ( );
					}
				}
			} );

		}
		catch ( Exception e )
		{
			progressBar.setVisibility ( View.GONE );
			Toast.makeText ( this, e.getMessage ( ), Toast.LENGTH_LONG ).show ( );
		}
	}

	@Override
	protected void onResume ( )
	{
		super.onResume ( );
		progressBar.setVisibility ( View.GONE );
	}
}


