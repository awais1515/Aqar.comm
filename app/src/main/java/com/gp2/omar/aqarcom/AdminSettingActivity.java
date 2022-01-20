package com.gp2.omar.aqarcom;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AdminSettingActivity extends AppCompatActivity
{
	private boolean exit = false;
	private FirebaseFirestore    db;
	private ArrayList < String > TitleITem, DateITem, ImagesItem, Emailuser, Name, ProfilePicture;
	private ArrayList < Double > latitude;


	@Override
	protected void onCreate ( Bundle savedInstanceState )
	{
		super.onCreate ( savedInstanceState );
		this.setTitle ( "Adminstrator" );
		setContentView ( R.layout.admin_setting );

		db = FirebaseFirestore.getInstance ( );


		TitleITem = new ArrayList < String > ( );
		DateITem = new ArrayList < String > ( );
		ImagesItem = new ArrayList < String > ( );
		Emailuser = new ArrayList < String > ( );
		Name = new ArrayList < String > ( );
		ProfilePicture = new ArrayList < String > ( );

		latitude = new ArrayList < Double > ( );

	}

	public void GenerateItem ( View view )

	{
		db.collection ( "ItemUser_Aqar.com" ).get ( ).addOnCompleteListener ( new OnCompleteListener < QuerySnapshot > ( )
		{
			@Override
			public void onComplete ( @NonNull Task < QuerySnapshot > task )
			{
				if ( task.isSuccessful ( ) )
				{
					for ( DocumentSnapshot document : task.getResult ( ) )
					{
						ImagesItem.add ( document.get ( "Link_Images" ).toString ( ) );
						TitleITem.add ( document.get ( "Title" ).toString ( ) );
						DateITem.add ( document.get ( "Date" ).toString ( ) );
						latitude.add ( ( Double ) document.get ( "latitudeUSer" ) );

					}
					Generic_Item_Admin.Title = TitleITem;
					Generic_Item_Admin.Image = ImagesItem;
					Generic_Item_Admin.Date = DateITem;
					Generic_Item_Admin.latitude = latitude;
					if ( TitleITem.size ( ) == Generic_Item_Admin.Title.size ( ) )
						startActivity ( new Intent ( AdminSettingActivity.this, Generic_Item_Admistrator.class ) );
				}
				else
				{
					Toast.makeText ( AdminSettingActivity.this, "Error get Item, Please Inform The Technical Support", Toast.LENGTH_SHORT ).show ( );
				}
			}
		} );
	}

	public void GenerateUser ( View view )
	{
		db.collection ( "UserInfo" ).get ( ).addOnCompleteListener ( new OnCompleteListener < QuerySnapshot > ( )
		{
			@Override
			public void onComplete ( @NonNull Task < QuerySnapshot > task )
			{
				if ( task.isSuccessful ( ) )
				{
					for ( DocumentSnapshot document : task.getResult ( ) )
					{
						Emailuser.add ( document.get ( "Email" ).toString ( ) );
						Name.add ( document.get ( "Fname" ).toString ( ) + " " + document.get ( "Lname" ).toString ( ) );
						ProfilePicture.add ( document.get ( "Profile_Picture" ).toString ( ) );
					}
					Generic_User_Data.Email = Emailuser;
					Generic_User_Data.Names = Name;
					Generic_User_Data.ProfilePictire = ProfilePicture;
					startActivity ( new Intent ( AdminSettingActivity.this, Generic_User_Adminstrator.class ) );
				}
				else
				{
					Toast.makeText ( AdminSettingActivity.this, "Error get Item, Please Inform The Technical Support", Toast.LENGTH_SHORT ).show ( );
				}
			}
		} );
	}

	public void LogOut ( View view )
	{
		AlertDialog alertDialog = new AlertDialog.Builder ( AdminSettingActivity.this ).create ( );
		alertDialog.setTitle ( "Alert" );
		alertDialog.setMessage ( "Are You Sure You Want To Log Out From Aqar.com ?" );
		alertDialog.setButton ( AlertDialog.BUTTON_NEUTRAL, "Yes", new DialogInterface.OnClickListener ( )
		{
			public void onClick ( DialogInterface dialog, int which )
			{
				FirebaseAuth.getInstance ( ).signOut ( );
				startActivity ( new Intent ( AdminSettingActivity.this, LoginPage.class ) );
			}
		} );
		alertDialog.setButton ( DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener ( )
		{
			@Override
			public void onClick ( DialogInterface dialog, int which )
			{
				// Do Nothing .
			}
		} );
		alertDialog.show ( );

	}

	@Override
	public void onBackPressed ( )
	{
		if ( exit == true )
		{
			Intent intent = new Intent ( Intent.ACTION_MAIN );
			intent.addCategory ( Intent.CATEGORY_HOME );
			intent.setFlags ( Intent.FLAG_ACTIVITY_CLEAR_TOP );
			startActivity ( intent );
			finish ( );
			System.exit ( 0 );

		}
		exit = true;
		Toast.makeText ( this, getString ( R.string.Press_Back_Again ), Toast.LENGTH_SHORT ).show ( );
		new Handler ( ).postDelayed ( new Runnable ( )
		{
			@Override
			public void run ( )
			{
				exit = false;
			}
		}, 3000 );

	}
}
