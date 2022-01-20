package com.gp2.omar.aqarcom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class Cont_SignUpPage_Pass extends Activity
{

	private EditText entryPassEditText, entryRePassEditText;
	private boolean flag;

	@Override
	protected void onCreate ( Bundle savedInstanceState )
	{
		super.onCreate ( savedInstanceState );
		this.requestWindowFeature ( Window.FEATURE_NO_TITLE );
		this.getWindow ( ).setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
		setContentView ( R.layout.activity_cont__sign_up_page__pass );

		entryPassEditText = findViewById ( R.id.EntryPassReg );
		entryRePassEditText = findViewById ( R.id.EntryRePassReg );
	}

	public void NextToAge ( View view )
	{
		flag = false;
		if ( TextUtils.isEmpty ( entryPassEditText.getText ( ).toString ( ) ) || TextUtils.isEmpty ( entryRePassEditText.getText ( ).toString ( ) ) )
		{
			if ( TextUtils.isEmpty ( entryPassEditText.getText ( ).toString ( ) ) )
			{
				entryPassEditText.setError ( getString ( R.string.Required ) );
			}
			if ( TextUtils.isEmpty ( entryRePassEditText.getText ( ).toString ( ) ) )
			{
				entryRePassEditText.setError ( getString ( R.string.Required ) );
			}
		}
		if ( entryPassEditText.getText ( ).toString ( ).length ( ) >= 5 || entryPassEditText.getText ( ).toString ( ).length ( ) < 5 )
		{
			if ( entryPassEditText.getText ( ).toString ( ).length ( ) >= 6 )
			{
				flag = true;
			}
			if ( entryPassEditText.getText ( ).toString ( ).length ( ) <= 5 )
			{
				flag = false;
				Toast.makeText ( this, getString ( R.string.length_pass ), Toast.LENGTH_LONG ).show ( );
			}
		}
		if ( flag )
		{
			if ( entryPassEditText.getText ( ).toString ( ).equals ( entryRePassEditText.getText ( ).toString ( ) ) )
			{
				UserReg.ppass = entryPassEditText.getText ( ).toString ( );
				startActivity ( new Intent ( Cont_SignUpPage_Pass.this, Cont_SignUpPage_Age.class ) );

			}
			else
			{
				flag = false;
				entryRePassEditText.setError ( getString ( R.string.pass_not_match ) );
			}
		}


	}


}

