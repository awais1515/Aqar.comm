package com.gp2.omar.aqarcom;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Profile extends Activity
{

	private FirebaseFirestore db;
	private ImageView         img;
	private TextView          fullname, userage, gender, email, Phone, Itemlabel;
	private String Caller, mail, activity;
	private AdView               mAdView;
	private ProgressBar          bar;
	private ArrayList < String > nameofitem;
	private ArrayList < Double > latitudeofitem;
	private FloatingActionButton editprofileforadmin;

	@Override
	protected void onCreate ( final Bundle savedInstanceState )
	{
		super.onCreate ( savedInstanceState );
		this.requestWindowFeature ( Window.FEATURE_NO_TITLE );
		this.getWindow ( ).setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
		setContentView ( R.layout.activity_profile );

		MobileAds.initialize ( getApplicationContext ( ), getString ( R.string.banner_ad_unit_id ) );
		mAdView = findViewById ( R.id.adView4 );
		AdRequest adRequest = new AdRequest.Builder ( ).build ( );
		mAdView.loadAd ( adRequest );

		Intent intent = getIntent ( );
		activity = intent.getStringExtra ( "activity" );

		db = FirebaseFirestore.getInstance ( );
		img = findViewById ( R.id.ImagesOfprofile );
		fullname = findViewById ( R.id.fullname );
		userage = findViewById ( R.id.age );
		gender = findViewById ( R.id.Gender );
		email = findViewById ( R.id.EmailProfile );
		Phone = findViewById ( R.id.PhoneUser );
		Itemlabel = findViewById ( R.id.itemlabel );
		editprofileforadmin = findViewById ( R.id.Edit_Profile_For_Admin );

		bar = findViewById ( R.id.progressBarToGetItemName );

		nameofitem = new ArrayList < String > ( );
		latitudeofitem = new ArrayList < Double > ( );


		db.collection ( "UserInfo" ).document ( Item_Profile.EMAIL_USER ).get ( ).addOnCompleteListener ( new OnCompleteListener < DocumentSnapshot > ( )
		{
			@Override
			public void onComplete ( @NonNull Task < DocumentSnapshot > task )
			{
				Picasso.with ( getApplicationContext ( ) ).load ( task.getResult ( ).get ( "Profile_Picture" ).toString ( ) ).into ( img );
				mail = task.getResult ( ).get ( "Email" ).toString ( );
				fullname.setText ( getString ( R.string.name ) + task.getResult ( ).get ( "Fname" ).toString ( ) + " " + task.getResult ( ).get ( "Lname" ) );
				userage.setText ( getString ( R.string.age ) + task.getResult ( ).get ( "Age" ).toString ( ) );
				gender.setText ( getString ( R.string.gender ) + task.getResult ( ).get ( "Gender" ).toString ( ) );
				email.setText ( getString ( R.string.email ) + mail );
				Phone.setText ( getString ( R.string.phone ) + task.getResult ( ).get ( "Phone" ).toString ( ) );
				Caller = ( task.getResult ( ).get ( "Phone" ).toString ( ) );
				Itemlabel.setText ( getString ( R.string.itemof ) + task.getResult ( ).get ( "Fname" ).toString ( ) );
			}
		} );

		try
		{
			if ( activity.equals ( "Setting_User_Admin" ) )
			{
				editprofileforadmin.setVisibility ( View.VISIBLE );
			}
		}
		catch ( Exception e )
		{
			editprofileforadmin.setVisibility ( View.GONE );
		}

		editprofileforadmin.setOnClickListener ( new View.OnClickListener ( )
		{
			@Override
			public void onClick ( View v )
			{
				db.collection ( "UserInfo" ).document ( Item_Profile.EMAIL_USER ).update ( "Profile_Picture", "https://firebasestorage.googleapis.com/v0/b/aqarcom-gp2.appspot.com/o/Icon%20Admin%2FExp_Icon.png?alt=media&token=298d391c-0db2-4d22-a029-96a5b9c5ecec" );
				Toast.makeText ( Profile.this, "Done.", Toast.LENGTH_SHORT ).show ( );
			}
		} );


	}

	public void callphone ( View view )
	{
		Intent intent = new Intent ( Intent.ACTION_DIAL );
		intent.setData ( Uri.parse ( "tel:" + Caller ) );
		startActivity ( intent );
	}

	public void saveEmail ( View view )
	{
		ClipboardManager clipboard = ( ClipboardManager ) getSystemService ( Context.CLIPBOARD_SERVICE );
		ClipData clip = ClipData.newPlainText ( getString ( R.string.copied ), mail );
		clipboard.setPrimaryClip ( clip );
		Toast.makeText ( getApplicationContext ( ), getString ( R.string.copied ), Toast.LENGTH_LONG ).show ( );
	}

	public void ShowItemActivity ( View view )
	{
		bar.setVisibility ( View.VISIBLE );

		db.collection ( "ItemUser_Aqar.com" ).get ( ).addOnCompleteListener ( new OnCompleteListener < QuerySnapshot > ( )
		{
			@Override
			public void onComplete ( @NonNull Task < QuerySnapshot > task )
			{
				if ( task.isSuccessful ( ) )
				{

					for ( DocumentSnapshot document : task.getResult ( ) )
					{
						if ( document.getData ( ).get ( "Email" ).toString ( ).equals ( mail ) )
						{
							nameofitem.add ( document.getData ( ).get ( "Title" ).toString ( ) );
							latitudeofitem.add ( ( Double ) document.getData ( ).get ( "latitudeUSer" ) );
						}
					}
					Item_Profile.nameofitem = nameofitem;
					Item_Profile.EMAIL_USER = mail;
					Item_Profile.latitudeofitem = latitudeofitem;
					bar.setVisibility ( View.GONE );
					startActivity ( new Intent ( Profile.this, item_Information_of_profile.class ) );
				}
				else
				{
					bar.setVisibility ( View.GONE );
					Toast.makeText ( Profile.this, "No Item Found.", Toast.LENGTH_SHORT ).show ( );
				}
			}
		} );

	}

	@Override
	public void onBackPressed ( )
	{

		if ( activity.equals ( "Setting" ) || activity.equals ( "item_Information_of_profile" ) )
		{
			startActivity ( new Intent ( Profile.this, Setting_Page.class ) );

		}
		else if ( activity.equals ( "Setting_User_Admin" ) )
		{
			startActivity ( new Intent ( Profile.this, Generic_User_Adminstrator.class ) );
		}
		else if ( activity.equals ( "Item_Information" ) )
		{
			Intent intent = new Intent ( Profile.this, Item_Information.class );
			intent.putExtra ( "activity", "Profile" );
			startActivity ( intent );
		}

	}
}
