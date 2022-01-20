package com.gp2.omar.aqarcom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

public class Add_Item_Page_Oldestate_4 extends Activity
{
	private EditText old, area;

	@Override
	protected void onCreate ( Bundle savedInstanceState )
	{
		super.onCreate ( savedInstanceState );
		this.requestWindowFeature ( Window.FEATURE_NO_TITLE );
		this.getWindow ( ).setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
		setContentView ( R.layout.activity_add__item__page__oldestate_4 );
		old = findViewById ( R.id.EntryOldState );
		area = findViewById ( R.id.EntryAreaEstate );
	}


	public void GoToPage5 ( View view )
	{
		if ( TextUtils.isEmpty ( old.getText ( ).toString ( ) ) || TextUtils.isEmpty ( area.getText ( ).toString ( ) ) )
		{
			if ( TextUtils.isEmpty ( old.getText ( ).toString ( ) ) )
			{
				old.setError ( getString ( R.string.Required ) );
			}
			if ( TextUtils.isEmpty ( area.getText ( ).toString ( ) ) )
			{
				area.setError ( getString ( R.string.Required ) );
			}
		}
		else
		{
			UserItem.olditem = old.getText ( ).toString ( );
			UserItem.areaitem = area.getText ( ).toString ( );
			startActivity ( new Intent ( this, Add_Item_Page_NumberEstate_5.class ) );
		}
	}
}
