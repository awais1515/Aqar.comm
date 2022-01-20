package com.gp2.omar.aqarcom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;

import java.util.Calendar;

public class Cont_SignUpPage_Age extends Activity
{
	private DatePicker datePicker;

	@Override
	protected void onCreate ( Bundle savedInstanceState )
	{
		super.onCreate ( savedInstanceState );
		this.requestWindowFeature ( Window.FEATURE_NO_TITLE );
		this.getWindow ( ).setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
		setContentView ( R.layout.activity_cont__sign_up_page__age );

		datePicker = findViewById ( R.id.datePicker );
	}

	public void NextToGender ( View view )
	{
		UserReg.age = Integer.toString ( Calendar.getInstance ( ).get ( Calendar.YEAR ) - datePicker.getYear ( ) );
		startActivity ( new Intent ( this, Cont_SignUpPage_ProfilePicture.class ) );
	}
}
