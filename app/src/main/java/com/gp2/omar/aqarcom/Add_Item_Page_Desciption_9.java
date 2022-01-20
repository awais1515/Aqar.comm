package com.gp2.omar.aqarcom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class Add_Item_Page_Desciption_9 extends Activity
{
	private EditText          desc;
	private FirebaseFirestore db;
	private FirebaseAuth      mAuth;
	private ProgressBar       bar;


	@Override
	protected void onCreate ( Bundle savedInstanceState )
	{
		super.onCreate ( savedInstanceState );
		this.requestWindowFeature ( Window.FEATURE_NO_TITLE );
		this.getWindow ( ).setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
		setContentView ( R.layout.activity_add__item__page__desciption_9 );

		db = FirebaseFirestore.getInstance ( );
		mAuth = FirebaseAuth.getInstance ( );
		desc = findViewById ( R.id.EntryDesc );
		bar = findViewById ( R.id.ProgressUploadNewItem );
	}

	public void UploadData ( View view )
	{
		bar.setVisibility ( View.VISIBLE );

		SimpleDateFormat df = new SimpleDateFormat ( "dd-MMM-yyyy" );
		String formattedDate = df.format ( Calendar.getInstance ( ).getTime ( ) );

		// Create a new Item.
		final Map < String, Object > Item = new HashMap <> ( );
		Item.put ( "latitudeUSer", UserItem.latitudeUSer );
		Item.put ( "longitudeUSer", UserItem.longitudeUSer );
		Item.put ( "Title", UserItem.Title );
		Item.put ( "Email", mAuth.getCurrentUser ( ).getEmail ( ) );
		Item.put ( "typesale", UserItem.typesale );
		Item.put ( "typeitem", UserItem.typeitem );
		Item.put ( "olditem", UserItem.olditem );
		Item.put ( "areaitem", UserItem.areaitem );
		Item.put ( "numberItem", UserItem.numberItem );
		Item.put ( "flooritem", UserItem.flooritem );
		Item.put ( "streetItem", UserItem.streetItem );
		Item.put ( "placeITem", UserItem.CityITem );
		Item.put ( "priceitem", UserItem.priceitem );
		Item.put ( "Link_Images", UserItem.imageitem );
		Item.put ( "DescriptionUser", desc.getText ( ).toString ( ) );
		Item.put ( "View", 0 );
		Item.put ( "Date", formattedDate );


		// Add a new document with a title&latitude ID
		db.collection ( "ItemUser_Aqar.com" ).document ( UserItem.Title + " " + UserItem.latitudeUSer.toString ( ) ).set ( Item ).addOnSuccessListener ( new OnSuccessListener < Void > ( )
		{
			@Override
			public void onSuccess ( Void aVoid )
			{
				bar.setVisibility ( View.GONE );
				startActivity ( new Intent ( Add_Item_Page_Desciption_9.this, MapsActivity.class ) );
			}
		} ).addOnFailureListener ( new OnFailureListener ( )
		{
			@Override
			public void onFailure ( @NonNull Exception e )
			{
				bar.setVisibility ( View.GONE );
				Toast.makeText ( Add_Item_Page_Desciption_9.this, "Error To Add Item , try again later", Toast.LENGTH_SHORT ).show ( );
			}
		} );
	}


}
