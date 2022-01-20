package com.gp2.omar.aqarcom;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Favorite_ListView extends AppCompatActivity
{

	private FirebaseFirestore db;
	private FirebaseAuth      mAuth;

	@Override
	protected void onCreate ( Bundle savedInstanceState )
	{
		super.onCreate ( savedInstanceState );
		setContentView ( R.layout.activity_favorite__list_view );
		this.setTitle ( "Favorite Item" );
		db = FirebaseFirestore.getInstance ( );
		mAuth = FirebaseAuth.getInstance ( );

		MyCustomAdapter myCustomAdapter = new MyCustomAdapter ( );
		final ListView ls = ( ListView ) findViewById ( R.id.favorite_list_view );
		ls.setAdapter ( myCustomAdapter );


	}

	class MyCustomAdapter extends BaseAdapter
	{


		@Override
		public int getCount ( )
		{
			return FavoriteItem.Title.size ( );
		}

		@Override
		public Object getItem ( int position )
		{
			return FavoriteItem.Title.get ( position );
		}

		@Override
		public long getItemId ( int position )
		{
			return position;
		}

		@Override
		public View getView ( final int position, View View, ViewGroup parent )
		{
			LayoutInflater layoutInflater = getLayoutInflater ( );
			View view = layoutInflater.inflate ( R.layout.row_view_favorite, null );

			ImageView img = view.findViewById ( R.id.ImageITemFavorite );
			final TextView txt = view.findViewById ( R.id.TitleItemFavorites );
			Button btn = view.findViewById ( R.id.btnDelteITemFavorite );

			Picasso.with ( getApplicationContext ( ) ).load ( FavoriteItem.Image.get ( position ) ).into ( img );
			txt.setText ( FavoriteItem.Title.get ( position ) );

			txt.setOnClickListener ( new View.OnClickListener ( )
			{
				@Override
				public void onClick ( View v )
				{
					Show_Item.title = txt.getText ( ).toString ( );
					Show_Item.latitude = FavoriteItem.latitude.get ( FavoriteItem.Title.indexOf ( txt.getText ( ).toString ( ) ) );
					Intent intent = new Intent ( Favorite_ListView.this, Item_Information.class );
					intent.putExtra ( "activity", "Favorite_ListView" );
					startActivity ( intent );
				}
			} );


			btn.setOnClickListener ( new View.OnClickListener ( )
			{
				@Override
				public void onClick ( View v )
				{

					db.collection ( "FavoriteItem" ).document ( mAuth.getCurrentUser ( ).getEmail ( ) + " " + FavoriteItem.Title.get ( position ) ).delete ( ).addOnSuccessListener ( new OnSuccessListener < Void > ( )
					{
						@Override
						public void onSuccess ( Void aVoid )
						{
							Toast.makeText ( Favorite_ListView.this, FavoriteItem.Title.get ( position ) + getString( R.string.deletd), Toast.LENGTH_SHORT ).show ( );
							startActivity ( new Intent ( Favorite_ListView.this, MapsActivity.class ) );
						}
					} ).addOnFailureListener ( new OnFailureListener ( )
					{
						@Override
						public void onFailure ( @NonNull Exception e )
						{
							Toast.makeText ( Favorite_ListView.this, getString( R.string.error_tryagian), Toast.LENGTH_SHORT ).show ( );
						}
					} );
				}

			} );
			return view;
		}
	}

	@Override
	public void onBackPressed ( )
	{
		super.onBackPressed ( );
		FavoriteItem.Title = null;
		FavoriteItem.Image = null;
		FavoriteItem.latitude = null;
		startActivity ( new Intent ( Favorite_ListView.this, MapsActivity.class ) );
	}
}
