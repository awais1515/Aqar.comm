package com.gp2.omar.aqarcom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

public class Cont_SignUpPage_Email extends Activity
{
	private EditText EmailEditText, PhoneEditText;

	@Override
	protected void onCreate ( Bundle savedInstanceState )
	{
		super.onCreate ( savedInstanceState );
		this.requestWindowFeature ( Window.FEATURE_NO_TITLE );
		this.getWindow ( ).setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
		setContentView ( R.layout.activity_cont__sign_up_page__email );

		EmailEditText = findViewById ( R.id.EntryMail );
		PhoneEditText = findViewById ( R.id.EntryPhone );
	}

	public void NextToPass ( View view )
	{
		Boolean flagcontain = false;
		boolean flagGo = false;

		if ( EmailEditText.getText ( ).toString ( ).equals ( "" ) || PhoneEditText.getText ( ).toString ( ).equals ( "" ) || EmailEditText.getText ( ).toString ( ).contains ( "@" ) || PhoneEditText.getText ( ).toString ( ).length ( ) > 10 || PhoneEditText.getText ( ).toString ( ).length ( ) < 10 )
		{
			if ( EmailEditText.getText ( ).toString ( ).equals ( "" ) )
			{
				EmailEditText.setError ( getString ( R.string.Required ) );
				flagGo = false;
			}
			else
			{
				flagGo = true;
			}
			if ( PhoneEditText.getText ( ).toString ( ).equals ( "" ) )
			{
				PhoneEditText.setError ( getString ( R.string.Required ) );
				flagGo = false;
			}
			else
			{
				flagGo = true;
			}
			if ( EmailEditText.getText ( ).toString ( ).contains ( "@" ) )
			{

				flagcontain = true;
				flagGo = true;
			}

		}
		if ( flagcontain == false )
		{
			EmailEditText.setError ( getString ( R.string.email_not_correct ) );
			flagGo = false;
		}
		if ( ( PhoneEditText.getText ( ).toString ( ).length ( ) != 0 && ( PhoneEditText.getText ( ).toString ( ).length ( ) > 10 || PhoneEditText.getText ( ).toString ( ).length ( ) < 10 ) ) || PhoneEditText.getText ( ).toString ( ).length ( ) == 0 )
		{
			PhoneEditText.setError ( getString ( R.string.phone_not_correct ) );
			flagGo = false;
		}
		else
		{
			flagGo = true;
		}

		if ( flagGo == true )
		{
			UserReg.eemail = EmailEditText.getText ( ).toString ( );
			UserReg.phone = PhoneEditText.getText ( ).toString ( );
			startActivity ( new Intent ( this, Cont_SignUpPage_Pass.class ) );
		}
	}
}
