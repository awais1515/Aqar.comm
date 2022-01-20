package com.gp2.omar.aqarcom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

public class Add_Item_Page_NumberEstate_5 extends Activity
{
	private EditText number, floor;

	@Override
	protected void onCreate ( Bundle savedInstanceState )
	{
		super.onCreate ( savedInstanceState );
		this.requestWindowFeature ( Window.FEATURE_NO_TITLE );
		this.getWindow ( ).setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
		setContentView ( R.layout.activity_add__item__page__number_estate_5 );

		number = findViewById ( R.id.EntrynumberEstate );
		floor = findViewById ( R.id.EntryFloarEstate );

	}

	public void GoToNextPage ( View view )
	{
		if ( TextUtils.isEmpty ( number.getText ( ).toString ( ) ) || TextUtils.isEmpty ( floor.getText ( ).toString ( ) ) )
		{
			if ( TextUtils.isEmpty ( number.getText ( ).toString ( ) ) )
			{
				number.setError ( getString ( R.string.Required ) );
			}
			if ( TextUtils.isEmpty ( floor.getText ( ).toString ( ) ) )
			{
				floor.setError ( getString ( R.string.Required ) );
			}
		}
		else
		{
			UserItem.numberItem = number.getText ( ).toString ( );
			UserItem.flooritem = floor.getText ( ).toString ( );
			startActivity ( new Intent ( this, Add_Item_Page_NameStreet_6.class ) );
		}
	}
}
