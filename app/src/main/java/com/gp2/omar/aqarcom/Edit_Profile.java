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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Edit_Profile extends AppCompatActivity
{
	private EditText fname, lname, phone, age;
	private Spinner           gender;
	private FirebaseFirestore db;
	private FirebaseAuth      mAuth;
	private ImageView         img;
	private FirebaseStorage   storage;
	private ProgressBar       load;

	@Override
	protected void onCreate ( Bundle savedInstanceState )
	{
		super.onCreate ( savedInstanceState );
		this.setTitle ( "Edit Profile" );
		setContentView ( R.layout.activity_edit__profile );


		storage = FirebaseStorage.getInstance ( );
		db = FirebaseFirestore.getInstance ( );
		mAuth = FirebaseAuth.getInstance ( );
		fname = ( EditText ) findViewById ( R.id.EntryNewFName );
		lname = ( EditText ) findViewById ( R.id.EntryNewLName );
		phone = ( EditText ) findViewById ( R.id.EntryNewPhone );
		age = ( EditText ) findViewById ( R.id.EntryNewAge );
		gender = ( Spinner ) findViewById ( R.id.EntryNewGender );
		img = ( ImageView ) findViewById ( R.id.image_profile_picture );
		load = ( ProgressBar ) findViewById ( R.id.wow );


		DocumentReference docRef = db.collection ( "UserInfo" ).document ( mAuth.getCurrentUser ( ).getEmail ( ) );
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
						Picasso.with ( getApplicationContext ( ) ).load ( task.getResult ( ).get ( "Profile_Picture" ).toString ( ) ).into ( img );
						fname.setText ( document.get ( "Fname" ).toString ( ) );
						lname.setText ( document.get ( "Lname" ).toString ( ) );
						phone.setText ( document.get ( "Phone" ).toString ( ) );
						age.setText ( document.get ( "Age" ).toString ( ) );


					}
					else
					{
						fname.setText ( getString ( R.string.errornodata ) );
						lname.setText ( getString ( R.string.errornodata ) );
						phone.setText ( getString ( R.string.errornodata ) );
						age.setText ( getString ( R.string.errornodata ) );
					}
				}
				else
				{
					Toast.makeText ( Edit_Profile.this, getString( R.string.errror_get_data), Toast.LENGTH_SHORT ).show ( );
					startActivity ( new Intent ( Edit_Profile.this, Setting_Page.class ) );
				}
			}
		} );

	}

	public void EditProfile ( View view )
	{
		db.collection ( "UserInfo" ).document ( mAuth.getCurrentUser ( ).getEmail ( ) ).update ( "Fname", fname.getText ( ).toString ( ), "Lname", lname.getText ( ).toString ( ), "Phone", phone.getText ( ).toString ( ), "Age", age.getText ( ).toString ( ), "Gender", gender.getSelectedItem ( ).toString ( ) );
		Toast.makeText ( this, getString( R.string.doneupdateinformation), Toast.LENGTH_SHORT ).show ( );
		startActivity ( new Intent ( Edit_Profile.this, Setting_Page.class ) );
	}


	public void ChangeImages ( View view )
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
			Toast.makeText ( this, "Wait To Upload Profile Picture.", Toast.LENGTH_SHORT ).show ( );
			final Uri uri = data.getData ( );
			img.setImageURI ( uri );

			storage.getReference ( ).child ( "Profile_Picture/" + mAuth.getCurrentUser ( ).getEmail ( ) ).delete ( );


			StorageReference filepath = storage.getReference ( ).child ( "Profile_Picture" ).child ( mAuth.getCurrentUser ( ).getEmail ( ) );
			filepath.putFile ( uri ).addOnSuccessListener ( new OnSuccessListener < UploadTask.TaskSnapshot > ( )
			{

				@Override
				public void onSuccess ( UploadTask.TaskSnapshot taskSnapshot )
				{

					db.collection ( "UserInfo" ).document ( mAuth.getCurrentUser ( ).getEmail ( ) ).update ( "Profile_Picture", taskSnapshot.getDownloadUrl ( ).toString ( ) );
					Toast.makeText ( Edit_Profile.this, getString ( R.string.doneupdateimage ), Toast.LENGTH_SHORT ).show ( );
					load.setVisibility ( View.GONE );
				}
			} ).addOnFailureListener ( new OnFailureListener ( )
			{
				@Override
				public void onFailure ( @NonNull Exception e )
				{
					load.setVisibility ( View.GONE );
					Toast.makeText ( Edit_Profile.this, getString ( R.string.errorupdateimage ), Toast.LENGTH_SHORT ).show ( );
				}
			} );


		}


	}

	@Override
	public void onBackPressed ( )
	{
		startActivity ( new Intent ( Edit_Profile.this, Setting_Page.class ) );
	}

}

