package com.gp2.omar.aqarcom;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Add_Item_Page_NameStreet_6 extends Activity
{
	private EditText street;
	private Spinner  City;


	@Override
	protected void onCreate ( Bundle savedInstanceState )
	{
		super.onCreate ( savedInstanceState );
		this.requestWindowFeature ( Window.FEATURE_NO_TITLE );
		this.getWindow ( ).setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
		setContentView ( R.layout.activity_add__item__page__name_street_6 );

		street = findViewById ( R.id.EntryStreetEstate );
		City = findViewById ( R.id.EntryCityEstate );

		Geocoder geocoder;
		List < Address > addresses;
		geocoder = new Geocoder ( this, Locale.getDefault ( ) );
		try
		{
			addresses = geocoder.getFromLocation ( UserItem.latitudeUSer, UserItem.longitudeUSer, 1 );
			street.setText ( addresses.get ( 0 ).getAddressLine ( 0 ) );
		}
		catch ( IOException e )
		{
			e.printStackTrace ( );
		}

	}

	public void GoToNextPage ( View view )
	{
		if ( TextUtils.isEmpty ( street.getText ( ).toString ( ) ) )
		{


				street.setError ( getString ( R.string.Required ) );


		}
		else
		{
			UserItem.streetItem = street.getText ( ).toString ( );
			UserItem.CityITem = City.getSelectedItem ( ).toString ( );
			startActivity ( new Intent ( this, Add_Item_Page_Images_7.class ) );
		}
	}
}