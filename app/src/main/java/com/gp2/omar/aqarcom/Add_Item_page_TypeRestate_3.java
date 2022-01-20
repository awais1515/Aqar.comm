package com.gp2.omar.aqarcom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class Add_Item_page_TypeRestate_3 extends Activity
{
	private Spinner sp;

	@Override
	protected void onCreate ( Bundle savedInstanceState )
	{
		super.onCreate ( savedInstanceState );
		this.requestWindowFeature ( Window.FEATURE_NO_TITLE );
		this.getWindow ( ).setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
		setContentView ( R.layout.activity_add__item_page__type_restate_3 );


		sp = findViewById ( R.id.SpinnerEstate );

		List < String > lst = new ArrayList < String > ( );
		lst.add ( getString ( R.string.a ) );
		lst.add ( getString ( R.string.Villa ) );
		lst.add ( getString ( R.string.floor ) );
		lst.add ( getString ( R.string.farm ) );
		lst.add ( getString ( R.string.office ) );
		lst.add ( getString ( R.string.shops ) );
		lst.add ( getString ( R.string.stores ) );
		lst.add ( getString ( R.string.land ) );

		//Convert From Arraylist To ArrayAdapter because Spinner can't Connect with Arraylist.
		ArrayAdapter < String > adaptermail = new ArrayAdapter < String > ( this, android.R.layout.simple_spinner_item, lst );
		adaptermail.setDropDownViewResource ( android.R.layout.simple_spinner_dropdown_item );
		sp.setAdapter ( adaptermail );
		sp.setSelection ( 0 );
	}

	public void GoToNextPage ( View view )
	{
		UserItem.typeitem = sp.getSelectedItem ( ).toString ( );
		startActivity ( new Intent ( this, Add_Item_Page_Oldestate_4.class ) );
	}
}
