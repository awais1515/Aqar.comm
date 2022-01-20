package com.gp2.omar.aqarcom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

public class Add_Item_Page_Price_8 extends Activity
{
	private EditText price;

	@Override
	protected void onCreate ( Bundle savedInstanceState )
	{
		super.onCreate ( savedInstanceState );
		this.requestWindowFeature ( Window.FEATURE_NO_TITLE );
		this.getWindow ( ).setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
		setContentView ( R.layout.activity_add__item__page__price_8 );
		price = findViewById ( R.id.ItemPrice );
	}

	public void GoToDesc ( View view )
	{
		if ( TextUtils.isEmpty (  price.getText ( ).toString ( ) ) )
		{
			price.setError ( getString ( R.string.Required ) );
		}
		else
		{
			UserItem.priceitem =  price.getText ( ).toString ( );
			startActivity ( new Intent ( this, Add_Item_Page_Desciption_9.class ) );
		}
	}
}
