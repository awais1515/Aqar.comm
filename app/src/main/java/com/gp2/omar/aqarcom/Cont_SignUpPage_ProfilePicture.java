package com.gp2.omar.aqarcom;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Cont_SignUpPage_ProfilePicture extends AppCompatActivity
{
	private ImageView        img;
	private Button           btnFinishUpload;
	private StorageReference mstorage;
	private ProgressBar      progressBar;

	@Override
	protected void onCreate ( Bundle savedInstanceState )
	{
		super.onCreate ( savedInstanceState );
		setContentView ( R.layout.activity_cont__sign_up_page__profile_picture );

		mstorage = FirebaseStorage.getInstance ( ).getReference ( );
		progressBar = ( ProgressBar ) findViewById ( R.id.progressBarUploadIMage );
		progressBar.setVisibility ( View.GONE );
	}

	public void ImportImages ( View view )
	{

		Intent intent = new Intent ( Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI );

		startActivityForResult ( intent, 100 );
		img = ( ImageView ) findViewById ( R.id.ImageViewProfile );
		btnFinishUpload = ( Button ) findViewById ( R.id.btnDoneChoice );
	}

	@Override
	protected void onActivityResult ( int requestCode, final int resultCode, Intent data )
	{
		super.onActivityResult ( requestCode, resultCode, data );

		if ( requestCode == 100 && resultCode == RESULT_OK )
		{
			final Uri uri = data.getData ( );
			img.setImageURI ( uri );
			btnFinishUpload.setOnClickListener ( new View.OnClickListener ( )
			{
				@Override
				public void onClick ( View v )
				{
					if ( resultCode == RESULT_OK )
					{
						progressBar.setVisibility ( View.VISIBLE );

						StorageReference filepath = mstorage.child ( "Profile_Picture" ).child ( UserReg.eemail );
						filepath.putFile ( uri ).addOnSuccessListener ( new OnSuccessListener < UploadTask.TaskSnapshot > ( )
						{

							@Override
							public void onSuccess ( UploadTask.TaskSnapshot taskSnapshot )
							{


								Toast.makeText ( Cont_SignUpPage_ProfilePicture.this, "Uploaded Images.", Toast.LENGTH_SHORT ).show ( );
								Uri imguploaded = taskSnapshot.getDownloadUrl ( );
								UserReg.profilepicture = imguploaded.toString ( );
								progressBar.setVisibility ( View.GONE );
								startActivity ( new Intent ( Cont_SignUpPage_ProfilePicture.this, Cont_SignUpPage_Gender.class ) );
							}
						} ).addOnFailureListener ( new OnFailureListener ( )
						{
							@Override
							public void onFailure ( @NonNull Exception e )
							{
								progressBar.setVisibility ( View.GONE );
								Toast.makeText ( Cont_SignUpPage_ProfilePicture.this, e.getMessage ( ), Toast.LENGTH_SHORT ).show ( );
							}
						} );


					}
				}
			} );
		}
		if ( resultCode == RESULT_CANCELED )
		{
			btnFinishUpload.setOnClickListener ( new View.OnClickListener ( )
			{
				@Override
				public void onClick ( View v )
				{
					Toast.makeText ( Cont_SignUpPage_ProfilePicture.this, getString ( R.string.Alert_Null_Images ), Toast.LENGTH_SHORT ).show ( );
				}
			} );
		}


	}

	public void GoToPagePrice ( View view )
	{
		Toast.makeText ( Cont_SignUpPage_ProfilePicture.this, getString ( R.string.Alert_Null_Images ), Toast.LENGTH_SHORT ).show ( );
	}
}

