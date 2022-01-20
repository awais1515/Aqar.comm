package com.gp2.omar.aqarcom;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class Add_Item_Page_Images_7 extends Activity
{

	private ImageView img;
	private Button    btnF;
	private StorageReference mstorage;
	private ProgressBar      progressBar;

	@Override
	protected void onCreate ( Bundle savedInstanceState )
	{
		super.onCreate ( savedInstanceState );
		this.requestWindowFeature ( Window.FEATURE_NO_TITLE );
		this.getWindow ( ).setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
		setContentView ( R.layout.activity_add__item__page__images_7 );

		mstorage = FirebaseStorage.getInstance ( ).getReference ( );
		progressBar = findViewById ( R.id.progressBar2 );
		progressBar.setVisibility ( View.GONE );
		img = findViewById ( R.id.ImageViewJ );
		btnF = findViewById ( R.id.btnDone );

	}

	public void ImportImages ( View view )
	{
		Intent intent = new Intent ( Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI );

		startActivityForResult ( intent, 100 );

	}

	@Override
	protected void onActivityResult ( int requestCode, final int resultCode, Intent data )
	{
		super.onActivityResult ( requestCode, resultCode, data );

		if ( requestCode == 100 && resultCode == RESULT_OK )
		{
			final Uri uri = data.getData ( );
			img.setImageURI ( uri );
			btnF.setOnClickListener ( new View.OnClickListener ( )
			{
				@Override
				public void onClick ( View v )
				{
					if ( resultCode == RESULT_OK )
					{
						progressBar.setVisibility ( View.VISIBLE );
						StorageReference filepath = mstorage.child ( "Photos_Item" ).child ( UserItem.Title + " " + UserItem.latitudeUSer.toString ( ) );
						filepath.putFile ( uri ).addOnSuccessListener ( new OnSuccessListener < UploadTask.TaskSnapshot > ( )
						{

							@Override
							public void onSuccess ( UploadTask.TaskSnapshot taskSnapshot )
							{


								Toast.makeText ( Add_Item_Page_Images_7.this, "Uploaded Images.", Toast.LENGTH_SHORT ).show ( );
								Uri imguploaded = taskSnapshot.getDownloadUrl ( );
								UserItem.imageitem = imguploaded.toString ( );
								progressBar.setVisibility ( View.GONE );
								startActivity ( new Intent ( Add_Item_Page_Images_7.this, Add_Item_Page_Price_8.class ) );
							}
						} ).addOnFailureListener ( new OnFailureListener ( )
						{
							@Override
							public void onFailure ( @NonNull Exception e )
							{
								progressBar.setVisibility ( View.GONE );
								Toast.makeText ( Add_Item_Page_Images_7.this, "Error .. Images Dont Uploaded, Check your internet Setting.", Toast.LENGTH_SHORT ).show ( );
							}
						} );


					}
				}
			} );
		}
		if ( resultCode == RESULT_CANCELED )
		{
			btnF.setOnClickListener ( new View.OnClickListener ( )
			{
				@Override
				public void onClick ( View v )
				{
					Toast.makeText ( Add_Item_Page_Images_7.this, getString ( R.string.Alert_Null_Images ), Toast.LENGTH_SHORT ).show ( );
				}
			} );
		}


	}

	public void GoToPagePrice ( View view )
	{
		Toast.makeText ( Add_Item_Page_Images_7.this, getString ( R.string.Alert_Null_Images ), Toast.LENGTH_SHORT ).show ( );
	}


}



