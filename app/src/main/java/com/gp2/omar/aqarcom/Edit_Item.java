package com.gp2.omar.aqarcom;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class Edit_Item extends AppCompatActivity
{

	private EditText Title, Place, Street, Area, OldItem, Floor, NumberItem, price, desc;
	private Spinner typeslae, typeitem;
	private ImageView         img;
	private FirebaseStorage   storage;
	private FirebaseFirestore db;
	private ProgressBar       load;
	private FirebaseAuth      mAuth;
	private Button            btnadmin, btnuser;
	private String id, title, Email, activity;
	private Boolean isAdmin = false;

	@Override
	protected void onStart ( )
	{
		super.onStart ( );


		db = FirebaseFirestore.getInstance ( );
		mAuth = FirebaseAuth.getInstance ( );
		btnadmin = ( Button ) findViewById ( R.id.AdminChangeButton );
		btnuser = ( Button ) findViewById ( R.id.UserChangeButton );
		db.collection ( "AdminUser" ).whereEqualTo ( "Email", mAuth.getCurrentUser ( ).getEmail ( ) ).get ( ).addOnCompleteListener ( new OnCompleteListener < QuerySnapshot > ( )
		{
			@Override
			public void onComplete ( @NonNull Task < QuerySnapshot > task )
			{
				if ( task.isSuccessful ( ) )
				{
					for ( DocumentSnapshot document : task.getResult ( ) )
					{
						btnuser.setVisibility ( View.GONE );
						btnadmin.setVisibility ( View.VISIBLE );
						isAdmin = true;
					}
				}
				else
				{
					btnuser.setVisibility ( View.VISIBLE );
					btnadmin.setVisibility ( View.GONE );
				}
			}
		} );
	}

	@Override
	protected void onCreate ( Bundle savedInstanceState )
	{
		super.onCreate ( savedInstanceState );
		this.setTitle ( "Edit Item" );
		setContentView ( R.layout.activity_edit__item );

		storage = FirebaseStorage.getInstance ( );
		db = FirebaseFirestore.getInstance ( );

		Intent intent = getIntent ( );
		activity = intent.getStringExtra ( "activity" );

		img = ( ImageView ) findViewById ( R.id.image_Item_Picture );
		load = ( ProgressBar ) findViewById ( R.id.wow1 );


		Title = ( EditText ) findViewById ( R.id.EntryNewTitle );
		Place = ( EditText ) findViewById ( R.id.EntryNewPlace );
		Street = ( EditText ) findViewById ( R.id.EntryNewStreet );
		Area = ( EditText ) findViewById ( R.id.EntryNewArea );
		OldItem = ( EditText ) findViewById ( R.id.EntryNewOld );
		Floor = ( EditText ) findViewById ( R.id.EntryNewFloor );
		NumberItem = ( EditText ) findViewById ( R.id.EntryNewNumber );
		price = ( EditText ) findViewById ( R.id.EntryNewPrice );
		desc = ( EditText ) findViewById ( R.id.EntryNewDesc );


		typeslae = ( Spinner ) findViewById ( R.id.EntryNewTypeSale );
		typeitem = ( Spinner ) findViewById ( R.id.EntryNewTypeItem );


		DocumentReference docRef = db.collection ( "ItemUser_Aqar.com" ).document ( Show_Item.title + " " + Show_Item.latitude );
		docRef.get ( ).addOnCompleteListener ( new OnCompleteListener < DocumentSnapshot > ( )
		{
			@Override
			public void onComplete ( @NonNull Task < DocumentSnapshot > task )
			{
				if ( task.isSuccessful ( ) )
				{
					DocumentSnapshot document = task.getResult ( );
					if ( document != null )
					{
						Picasso.with ( getApplicationContext ( ) ).load ( document.get ( "Link_Images" ).toString ( ) ).into ( img );
						Title.setText ( document.get ( "Title" ).toString ( ) );
						Place.setText ( document.get ( "placeITem" ).toString ( ) );
						Street.setText ( document.get ( "streetItem" ).toString ( ) );
						Area.setText ( document.get ( "areaitem" ).toString ( ) );
						OldItem.setText ( document.get ( "olditem" ).toString ( ) );
						Floor.setText ( document.get ( "flooritem" ).toString ( ) );
						NumberItem.setText ( document.get ( "numberItem" ).toString ( ) );
						price.setText ( document.get ( "priceitem" ).toString ( ) );
						if ( document.get ( "DescriptionUser" ) != null )
						{
							desc.setText ( document.get ( "DescriptionUser" ).toString ( ) );
						}
						else
						{
							desc.setText ( "Null" );
						}

						id = document.getId ( );
						title = document.get ( "Title" ).toString ( );
						Email = document.get ( "Email" ).toString ( );


						if ( ! typeslae.getSelectedItem ( ).toString ( ).equals ( document.get ( "typesale" ).toString ( ) ) )
						{
							typeslae.setSelection ( 1 );
						}
						else
						{
							typeslae.setSelection ( 0 );
						}
					}
					else
					{
						Title.setText ( getString ( R.string.errornodata ) );
						Place.setText ( getString ( R.string.errornodata ) );
						Street.setText ( getString ( R.string.errornodata ) );
						Area.setText ( getString ( R.string.errornodata ) );
						OldItem.setText ( getString ( R.string.errornodata ) );
						Floor.setText ( getString ( R.string.errornodata ) );
						NumberItem.setText ( getString ( R.string.errornodata ) );
						price.setText ( getString ( R.string.errornodata ) );
						desc.setText ( getString ( R.string.errornodata ) );

					}
				}
				else
				{
					Toast.makeText ( Edit_Item.this, "Error Get Data, Try Again Later", Toast.LENGTH_SHORT ).show ( );
					startActivity ( new Intent ( Edit_Item.this, Item_Information.class ) );
				}
			}
		} );
	}

	public void Updatedata ( View view )
	{
		if ( isAdmin )
		{
			db.collection ( "ItemUser_Aqar.com" ).document ( Show_Item.title + " " + Show_Item.latitude ).update ( "Title", Title.getText ( ).toString ( ), "placeITem", Place.getText ( ).toString ( ), "streetItem", Street.getText ( ).toString ( ), "areaitem", Area.getText ( ).toString ( ), "olditem", OldItem.getText ( ).toString ( ), "flooritem", Floor.getText ( ).toString ( ), "numberItem", NumberItem.getText ( ).toString ( ), "priceitem", price.getText ( ).toString ( ), "DescriptionUser", desc.getText ( ).toString ( ), "typesale", typeslae.getSelectedItem ( ).toString ( ), "typeitem", typeitem.getSelectedItem ( ).toString ( ) );
			Map < String, Object > data = new HashMap <> ( );
			data.put ( "Admin Email", mAuth.getCurrentUser ( ).getEmail ( ) );
			data.put ( "Couse Edited Data", "Item Data Not Accept." );
			data.put ( "Item", title );
			data.put ( "Item_Id", id );
			data.put ( "Email of item", Email );
			db.collection ( "Admin_Modify_ITem" ).document ( id ).set ( data );
			Toast.makeText ( this, "This ITem Is Edited.", Toast.LENGTH_SHORT ).show ( );
			startActivity ( new Intent ( Edit_Item.this, AdminSettingActivity.class ) );
		}
		else
		{
			db.collection ( "ItemUser_Aqar.com" ).document ( Show_Item.title + " " + Show_Item.latitude ).update ( "Title", Title.getText ( ).toString ( ), "placeITem", Place.getText ( ).toString ( ), "streetItem", Street.getText ( ).toString ( ), "areaitem", Area.getText ( ).toString ( ), "olditem", OldItem.getText ( ).toString ( ), "flooritem", Floor.getText ( ).toString ( ), "numberItem", NumberItem.getText ( ).toString ( ), "priceitem", price.getText ( ).toString ( ), "DescriptionUser", desc.getText ( ).toString ( ), "typesale", typeslae.getSelectedItem ( ).toString ( ), "typeitem", typeitem.getSelectedItem ( ).toString ( ) );
			Toast.makeText ( this, "Done, Thank you For Edit Your Information", Toast.LENGTH_SHORT ).show ( );
			startActivity ( new Intent ( Edit_Item.this, MapsActivity.class ) );
		}
	}

	public void changeimagesofitem ( View view )
	{
		Intent intent = new Intent ( Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI );
		startActivityForResult ( intent, 100 );
	}

	@Override
	protected void onActivityResult ( int requestCode, int resultCode, Intent data )
	{
		super.onActivityResult ( requestCode, resultCode, data );
		if ( requestCode == 100 && resultCode == RESULT_OK )
		{

			load.setVisibility ( View.VISIBLE );
			Toast.makeText ( this, getString ( R.string.wait ), Toast.LENGTH_SHORT ).show ( );
			final Uri uri = data.getData ( );
			img.setImageURI ( uri );

			storage.getReference ( ).child ( "Photos_Item/" + Show_Item.title + " " + Show_Item.latitude ).delete ( );


			StorageReference filepath = storage.getReference ( ).child ( "Photos_Item" ).child ( Show_Item.title + " " + Show_Item.latitude );
			filepath.putFile ( uri ).addOnSuccessListener ( new OnSuccessListener < UploadTask.TaskSnapshot > ( )
			{
				@Override
				public void onSuccess ( UploadTask.TaskSnapshot taskSnapshot )
				{
					db.collection ( "ItemUser_Aqar.com" ).document ( Show_Item.title + " " + Show_Item.latitude ).update ( "Link_Images", taskSnapshot.getDownloadUrl ( ).toString ( ) );
					Toast.makeText ( Edit_Item.this, getString ( R.string.doneupdateimage ), Toast.LENGTH_SHORT ).show ( );
					load.setVisibility ( View.GONE );
				}
			} ).addOnFailureListener ( new OnFailureListener ( )
			{
				@Override
				public void onFailure ( @NonNull Exception e )
				{
					load.setVisibility ( View.GONE );
					Toast.makeText ( Edit_Item.this, getString ( R.string.errorupdateimage ), Toast.LENGTH_SHORT ).show ( );
				}
			} );
		}
	}


	public void BlockImages ( View view )
	{
		db.collection ( "ItemUser_Aqar.com" ).document ( Show_Item.title + " " + Show_Item.latitude ).update ( "Link_Images", "https://firebasestorage.googleapis.com/v0/b/aqarcom-gp2.appspot.com/o/Icon%20Admin%2FExp_Icon.png?alt=media&token=298d391c-0db2-4d22-a029-96a5b9c5ecec" );
		Map < String, Object > data = new HashMap <> ( );
		data.put ( "Admin Email", mAuth.getCurrentUser ( ).getEmail ( ) );
		data.put ( "CouseDelete", "Images Not Accepted" );
		data.put ( "Item", title );
		data.put ( "Item_Id", id );
		data.put ( "Email of item", Email );
		db.collection ( "Admin_Modify_ITem" ).document ( id ).set ( data );
		Picasso.with ( getApplicationContext ( ) ).load ( "https://firebasestorage.googleapis.com/v0/b/aqarcom-gp2.appspot.com/o/Icon%20Admin%2FExp_Icon.png?alt=media&token=298d391c-0db2-4d22-a029-96a5b9c5ecec" ).into ( img );
		Toast.makeText ( Edit_Item.this, getString ( R.string.doneupdateimage ), Toast.LENGTH_SHORT ).show ( );


	}


	@Override
	public void onBackPressed ( )
	{
		if ( activity.equals ( "Item_Informatation" ) )
		{
			Intent intent = new Intent ( Edit_Item.this, Item_Information.class );
			intent.putExtra ( "activity", "Edit_Item" );
			startActivity ( intent );
		}

	}
}
