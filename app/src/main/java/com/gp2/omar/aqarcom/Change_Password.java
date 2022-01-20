package com.gp2.omar.aqarcom;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Change_Password extends AppCompatActivity
{
	private EditText pass11, pass22, Oldpass;
	private Boolean flag;

	@Override
	protected void onCreate ( Bundle savedInstanceState )
	{
		super.onCreate ( savedInstanceState );
		this.setTitle ( "Change Pass" );
		setContentView ( R.layout.activity_change__password );
		this.getWindow ( ).setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );

		pass11 = ( EditText ) findViewById ( R.id.EntryNewPassReg );
		pass22 = ( EditText ) findViewById ( R.id.EntryNewRePassReg );
		Oldpass = ( EditText ) findViewById ( R.id.EntryPass );
	}

	public void Change ( View view )
	{
		flag = false;

		if ( TextUtils.isEmpty ( pass11.getText ( ).toString ( ) ) || TextUtils.isEmpty ( pass22.getText ( ).toString ( ) ) )
		{
			if ( TextUtils.isEmpty ( pass11.getText ( ).toString ( ) ) )
			{
				pass11.setError ( getString ( R.string.Required ) );
			}
			if ( TextUtils.isEmpty ( pass22.getText ( ).toString ( ) ) )
			{
				pass22.setError ( getString ( R.string.Required ) );
			}
		}
		if ( pass11.getText ( ).toString ( ).length ( ) >= 5 || pass11.getText ( ).toString ( ).length ( ) < 5 )
		{
			if ( pass11.getText ( ).toString ( ).length ( ) >= 6 )
			{
				flag = true;
			}
			if ( pass11.getText ( ).toString ( ).length ( ) <= 5 )
			{
				flag = false;
				Toast.makeText ( this, getString ( R.string.length_pass ), Toast.LENGTH_LONG ).show ( );
			}
		}
		if ( flag )
		{
			if ( pass11.getText ( ).toString ( ).equals ( pass22.getText ( ).toString ( ) ) )
			{
				final FirebaseUser user = FirebaseAuth.getInstance ( ).getCurrentUser ( );


				AuthCredential credential = EmailAuthProvider.getCredential ( FirebaseAuth.getInstance ( ).getCurrentUser ( ).getEmail ( ), Oldpass.getText ( ).toString ( ) );


				user.reauthenticate ( credential ).addOnCompleteListener ( new OnCompleteListener < Void > ( )
				{
					@Override
					public void onComplete ( @NonNull Task < Void > task )
					{
						if ( task.isSuccessful ( ) )
						{
							user.updatePassword ( pass11.getText ( ).toString ( ) ).addOnCompleteListener ( new OnCompleteListener < Void > ( )
							{
								@Override
								public void onComplete ( @NonNull Task < Void > task )
								{
									if ( task.isSuccessful ( ) )
									{
										Toast.makeText ( Change_Password.this, "Change Password is Successful", Toast.LENGTH_SHORT ).show ( );
										startActivity ( new Intent ( Change_Password.this, Setting_Page.class ) );
										overridePendingTransition ( R.anim.slide_in_right, R.anim.slide_out_left );
									}
									else
									{
										Toast.makeText ( Change_Password.this, "Change Password is Not Successful", Toast.LENGTH_SHORT ).show ( );
									}
								}
							} );
						}
						else
						{
							Toast.makeText ( Change_Password.this, "Error Auth Field", Toast.LENGTH_SHORT ).show ( );
						}
					}
				} );


			}
			else
			{
				flag = false;
				pass22.setError ( getString ( R.string.pass_not_match ) );
			}
		}


	}
}
