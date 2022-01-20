package com.gp2.omar.aqarcom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class item_Information_of_profile extends AppCompatActivity
{
	private ListView sampleList;


	@Override
	protected void onCreate ( Bundle savedInstanceState )
	{
		super.onCreate ( savedInstanceState );
		this.setTitle ( "List Of Item" );
		setContentView ( R.layout.activity_item__information_of_profile );
		sampleList = ( ListView ) findViewById ( R.id.item_of_profile );

		String[] array = new String[ Item_Profile.nameofitem.size ( ) ];
		Item_Profile.nameofitem.toArray ( array );
		ArrayAdapter < String > adapter = new ArrayAdapter < String > ( this, R.layout.activity_listview, array );
		sampleList.setAdapter ( adapter );
		sampleList.setOnItemClickListener ( new AdapterView.OnItemClickListener ( )
		{
			@Override
			public void onItemClick ( AdapterView < ? > parent, View view, int position, long id )
			{
				Show_Item.title = ( String ) sampleList.getItemAtPosition ( position );
				Show_Item.latitude = ( Double ) Item_Profile.latitudeofitem.get ( position );
				Intent intent = new Intent ( item_Information_of_profile.this, Item_Information.class );
				intent.putExtra ( "activity", "item_Information_of_profile" );
				startActivity ( intent );
			}
		} );
	}

	@Override
	public void onBackPressed ( )
	{
		Item_Profile.nameofitem = null;
		Intent intent = new Intent ( item_Information_of_profile.this, Profile.class );
		intent.putExtra ( "activity", "item_Information_of_profile" );
		startActivity ( intent );
	}
}
