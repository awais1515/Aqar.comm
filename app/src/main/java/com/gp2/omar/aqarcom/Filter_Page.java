package com.gp2.omar.aqarcom;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Filter_Page extends AppCompatActivity
{
	private AdView   mAdView;
	private EditText minarea, maxarea, minprice, maxprice;
	private Spinner typesale, typeitem, ciryitem;
	private ProgressBar bar;

	private FirebaseFirestore db;
	private FirebaseAuth      mAuth;

	private ArrayList < String > Title, Area, picture, price, street;
	private ArrayList < Double > latitude;
	private Boolean check = false;


	@Override
	protected void onCreate ( Bundle savedInstanceState )
	{
		super.onCreate ( savedInstanceState );
		this.setTitle ( "Filter" );
		setContentView ( R.layout.activity_filter__page );

		MobileAds.initialize ( getApplicationContext ( ), getString ( R.string.banner_ad_unit_id ) );
		mAdView = ( AdView ) findViewById ( R.id.adView6 );
		AdRequest adRequest = new AdRequest.Builder ( ).build ( );
		mAdView.loadAd ( adRequest );

		db = FirebaseFirestore.getInstance ( );
		mAuth = FirebaseAuth.getInstance ( );


		bar = ( ProgressBar ) findViewById ( R.id.progressBarToGetItemFilter );

		minarea = ( EditText ) findViewById ( R.id.filter_getMinArea );
		maxarea = ( EditText ) findViewById ( R.id.filter_getMaxArea );
		minprice = ( EditText ) findViewById ( R.id.filter_getMinprice );
		maxprice = ( EditText ) findViewById ( R.id.filter_getMaxprice );
		typesale = ( Spinner ) findViewById ( R.id.filter_getsale );
		typeitem = ( Spinner ) findViewById ( R.id.filter_getType );
		ciryitem = ( Spinner ) findViewById ( R.id.filter_getCityItem );
	}

	public void getITemFiltered ( View view )
	{
		bar.setVisibility ( View.VISIBLE );
		if ( TextUtils.isEmpty ( minprice.getText ( ).toString ( ) ) )
		{
			minprice.setText ( "0" );
		}
		if ( TextUtils.isEmpty ( minarea.getText ( ).toString ( ) ) )
		{
			minarea.setText ( "0" );
		}
		if ( TextUtils.isEmpty ( maxarea.getText ( ).toString ( ) ) )
		{
			maxarea.setError ( getString ( R.string.Required ) );
			check = false;
			bar.setVisibility ( View.INVISIBLE );
		}
		else
		{
			check = true;
		}
		if ( TextUtils.isEmpty ( maxprice.getText ( ).toString ( ) ) )
		{
			maxprice.setError ( getString ( R.string.Required ) );
			check = false;
			bar.setVisibility ( View.INVISIBLE );
		}
		else
		{
			check = true;
		}

		if ( check == true )
		{
			//.whereLessThan ( "areaitem", maxarea.getText ( ).toString ( ) )
			try
			{
				Title = null;
				Area = null;
				Title = null;
				picture = null;
				price = null;
				street = null;
				latitude = null;

				Title = new ArrayList < String > ( );
				Area = new ArrayList < String > ( );
				Title = new ArrayList < String > ( );
				picture = new ArrayList < String > ( );
				price = new ArrayList < String > ( );
				street = new ArrayList < String > ( );
				latitude = new ArrayList < Double > ( );
				db.collection ( "ItemUser_Aqar.com" ).whereEqualTo ( "typeitem", typeitem.getSelectedItem ( ).toString ( ) ).whereEqualTo ( "typesale", typesale.getSelectedItem ( ).toString ( ) ).whereEqualTo ( "placeITem", ciryitem.getSelectedItem ( ).toString ( ) ).get ( ).addOnCompleteListener ( new OnCompleteListener < QuerySnapshot > ( )
				{
					@Override
					public void onComplete ( @NonNull Task < QuerySnapshot > task )
					{
						if ( task.isSuccessful ( ) )
						{
							for ( DocumentSnapshot document : task.getResult ( ) )
							{
								Double priceitemdatabase = Double.parseDouble ( document.getData ( ).get ( "priceitem" ).toString ( ) );
								Double areaitemdatabase = Double.parseDouble ( document.getData ( ).get ( "areaitem" ).toString ( ) );

								if ( ! mAuth.getCurrentUser ( ).getEmail ( ).equals ( document.getData ( ).get ( "Email" ).toString ( ) ) )
								{
									if ( ( priceitemdatabase ) <= ( Double.parseDouble ( maxprice.getText ( ).toString ( ) ) ) )
									{
										if ( ( areaitemdatabase ) <= ( Double.parseDouble ( maxarea.getText ( ).toString ( ) ) ) )
										{
											Title.add ( document.getData ( ).get ( "Title" ).toString ( ) );
											picture.add ( document.getData ( ).get ( "Link_Images" ).toString ( ) );
											price.add ( document.getData ( ).get ( "priceitem" ).toString ( ) );
											street.add ( document.getData ( ).get ( "streetItem" ).toString ( ) );
											Area.add ( document.getData ( ).get ( "areaitem" ).toString ( ) );
											latitude.add ( ( Double ) document.getData ( ).get ( "latitudeUSer" ) );
										}
									}
								}

							}

							check = false;
						}
						else
						{
							Toast.makeText ( Filter_Page.this, getString ( R.string.sorry ), Toast.LENGTH_LONG ).show ( );
						}
					}
				} );
				Filter_Search_Data.title = Title;
				Filter_Search_Data.area = Area;
				Filter_Search_Data.picture = picture;
				Filter_Search_Data.price = price;
				Filter_Search_Data.street = street;
				Filter_Search_Data.latitude = latitude;


				Handler handler = new Handler ( );
				handler.postDelayed ( new Runnable ( )
				{
					@Override
					public void run ( )
					{
						bar.setVisibility ( View.INVISIBLE );
						startActivity ( new Intent ( Filter_Page.this, Filter_Result.class ) );
					}
				}, 3000 );


			}
			catch ( Exception e )
			{
				Toast.makeText ( this, e.getMessage ( ), Toast.LENGTH_SHORT ).show ( );
			}
		}


	}

	@Override
	public void onBackPressed ( )
	{
		super.onBackPressed ( );
		startActivity ( new Intent ( Filter_Page.this, MapsActivity.class ) );
	}
}
