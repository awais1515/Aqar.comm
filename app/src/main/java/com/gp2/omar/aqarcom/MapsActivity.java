package com.gp2.omar.aqarcom;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;

import static android.Manifest.*;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{

	private GoogleMap            mMap;
	private FloatingActionButton addItemFloatingButton, OpenFloatFloatingButton, HelpFloatingActionButton, MapChangerFloatingActionButton, settingFloatingActionButton, filterFloatingActionButton, favoriteFloatingActionButton, MedianFloatingActionButton, TaxiFloatingActionButton;
	private Animation fabopen, fabclose, rotateforward, rotatebackword;
	private boolean isopen = false, exit = false;
	private Context mContext;
	private FirebaseAuth      mAuth = FirebaseAuth.getInstance ( );
	private FirebaseFirestore db    = FirebaseFirestore.getInstance ( );
	private ArrayList < String > title, img;
	private ArrayList < Double > latitude;

	@Override
	protected void onStart ( )
	{
		super.onStart ( );

		startService ( new Intent ( this, backgroundprocces.class ) );
		db.collection ( "AdminUser" ).whereEqualTo ( "Email", mAuth.getCurrentUser ( ).getEmail ( ) ).get ( ).addOnCompleteListener ( new OnCompleteListener < QuerySnapshot > ( )
		{
			@Override
			public void onComplete ( @NonNull Task < QuerySnapshot > task )
			{
				if ( task.isSuccessful ( ) )
				{
					for ( DocumentSnapshot document : task.getResult ( ) )
					{
						startActivity ( new Intent ( MapsActivity.this, AdminSettingActivity.class ) );
					}
				}
				else
				{
					Toast.makeText ( MapsActivity.this, "Nice To See You !", Toast.LENGTH_LONG ).show ( );
				}
			}
		} );
	}

	@Override
	protected void onCreate ( Bundle savedInstanceState )
	{
		super.onCreate ( savedInstanceState );
		this.requestWindowFeature ( Window.FEATURE_NO_TITLE );
		setContentView ( R.layout.activity_maps );
		SupportMapFragment mapFragment = ( SupportMapFragment ) getSupportFragmentManager ( ).findFragmentById ( R.id.map );
		mapFragment.getMapAsync ( this );


		addItemFloatingButton = findViewById ( R.id.floatingActionButtonAddItem );
		OpenFloatFloatingButton = findViewById ( R.id.floatingActionButtonopentype );
		HelpFloatingActionButton = findViewById ( R.id.floatingActionButtonhelp );
		filterFloatingActionButton = findViewById ( R.id.floatingActionButtonfilter );
		MapChangerFloatingActionButton = findViewById ( R.id.floatingActionButtonchangetypemap );
		settingFloatingActionButton = findViewById ( R.id.floatingActionButtonSetting );
		favoriteFloatingActionButton = findViewById ( R.id.floatingActionButtonFavorites );
		MedianFloatingActionButton = findViewById ( R.id.floatingActionButtonMedian );
		TaxiFloatingActionButton = findViewById ( R.id.floatingActionButtonTaxi );

		fabopen = AnimationUtils.loadAnimation ( this, R.anim.fab_open );
		fabclose = AnimationUtils.loadAnimation ( this, R.anim.fab_close );
		rotatebackword = AnimationUtils.loadAnimation ( this, R.anim.rotate_backword );
		rotateforward = AnimationUtils.loadAnimation ( this, R.anim.rotate_forward );

		CheckGpsISOn ( );
		resetAllData ( );
	}

	private void resetAllData ( )
	{
		UserItem.areaitem = null;
		UserItem.flooritem = null;
		UserItem.imageitem = null;
		UserItem.latitudeUSer = null;
		UserItem.longitudeUSer = null;
		UserItem.numberItem = null;
		UserItem.olditem = null;
		UserItem.CityITem = null;
		UserItem.priceitem = null;
		UserItem.streetItem = null;
		UserItem.Title = null;
		UserItem.typeitem = null;
		//------------------------------------------
		UserReg.age = null;
		UserReg.eemail = null;
		UserReg.fname = null;
		UserReg.lname = null;
		UserReg.phone = null;
		UserReg.ppass = null;
		UserReg.gender = null;
		UserReg.profilepicture = null;
	}


	@Override
	public void onMapReady ( final GoogleMap googleMap )
	{

		mMap = googleMap;
		GetMarker ( mMap );
		mMap.setMapType ( GoogleMap.MAP_TYPE_NORMAL );
		LatLng LocationJordan = new LatLng ( 31.963158, 35.930359 );
		mMap.moveCamera ( CameraUpdateFactory.newLatLng ( LocationJordan ) );
		mMap.setBuildingsEnabled ( true );
		mMap.getUiSettings ( ).setZoomControlsEnabled ( true );
		mMap.setIndoorEnabled ( true );
		mMap.setTrafficEnabled ( true );
		CameraUpdate MyLocation = CameraUpdateFactory.newLatLngZoom ( LocationJordan, 7 );
		mMap.animateCamera ( MyLocation );
		mMap.setOnMarkerClickListener ( new GoogleMap.OnMarkerClickListener ( )
		{
			@Override
			public boolean onMarkerClick ( Marker marker )
			{
				if ( marker.getTitle ( ) != null )
				{
					LatLng position = marker.getPosition ( );
					Show_Item.title = marker.getTitle ( );
					Show_Item.latitude = marker.getPosition ( ).latitude;
					Intent intent = new Intent ( MapsActivity.this, Item_Information.class );
					intent.putExtra ( "activity", "MapsActivity" );
					startActivity ( intent );
				}
				else
				{
					Toast.makeText ( MapsActivity.this, getString ( R.string.donttuchyoumarkerwithouttitle ), Toast.LENGTH_LONG ).show ( );
				}
				return true;
			}
		} );

		GetPermission ( );
		AddMarker ( );
		LongPress ( );
		OpenFloatFloatingButton.setOnClickListener ( new View.OnClickListener ( )
		{
			@Override
			public void onClick ( View v )
			{
				animatefeb ( );
			}
		} );
	}

	private void GetMarker ( final GoogleMap mMap )
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
						if ( ! mAuth.getCurrentUser ( ).getEmail ( ).equals ( document.get ( "Email" ) ) )
						{
							mMap.addMarker ( new MarkerOptions ( ).position ( new LatLng ( ( double ) document.get ( "latitudeUSer" ), ( double ) document.get ( "longitudeUSer" ) ) ).title ( document.get ( "Title" ).toString ( ) ).icon ( BitmapDescriptorFactory.defaultMarker ( BitmapDescriptorFactory.HUE_GREEN ) ) );
						}
						else if ( mAuth.getCurrentUser ( ).getEmail ( ).equals ( document.get ( "Email" ) ) )
						{
							mMap.addMarker ( new MarkerOptions ( ).position ( new LatLng ( ( double ) document.get ( "latitudeUSer" ), ( double ) document.get ( "longitudeUSer" ) ) ).title ( document.get ( "Title" ).toString ( ) ).icon ( BitmapDescriptorFactory.defaultMarker ( BitmapDescriptorFactory.HUE_MAGENTA ) ) );
						}
					}
				}
				else
				{
					Toast.makeText ( MapsActivity.this, getString ( R.string.error_andwewillnotfixitbecousewearegraduate ), Toast.LENGTH_SHORT ).show ( );
					FirebaseCrash.report ( task.getException ( ) );
				}
			}
		} );
	}


	public void animatefeb ( )
	{
		if ( isopen )
		{
			OpenFloatFloatingButton.startAnimation ( rotateforward );

			MapChangerFloatingActionButton.startAnimation ( fabclose );
			HelpFloatingActionButton.startAnimation ( fabclose );
			filterFloatingActionButton.startAnimation ( fabclose );
			settingFloatingActionButton.startAnimation ( fabclose );
			favoriteFloatingActionButton.startAnimation ( fabclose );
			MedianFloatingActionButton.setAnimation ( fabclose );
			TaxiFloatingActionButton.setAnimation ( fabclose );

			HelpFloatingActionButton.setClickable ( false );
			filterFloatingActionButton.setClickable ( false );
			favoriteFloatingActionButton.setClickable ( false );
			settingFloatingActionButton.setClickable ( false );
			MedianFloatingActionButton.setClickable ( false );
			TaxiFloatingActionButton.setClickable ( false );

			MapChangerFloatingActionButton.setEnabled ( false );
			settingFloatingActionButton.setEnabled ( false );
			HelpFloatingActionButton.setEnabled ( false );
			filterFloatingActionButton.setEnabled ( false );
			favoriteFloatingActionButton.setEnabled ( false );
			MedianFloatingActionButton.setEnabled ( false );
			TaxiFloatingActionButton.setEnabled ( false );
			isopen = false;
		}
		else
		{
			OpenFloatFloatingButton.startAnimation ( rotatebackword );

			MapChangerFloatingActionButton.startAnimation ( fabopen );
			HelpFloatingActionButton.startAnimation ( fabopen );
			filterFloatingActionButton.startAnimation ( fabopen );
			favoriteFloatingActionButton.startAnimation ( fabopen );
			settingFloatingActionButton.startAnimation ( fabopen );
			MedianFloatingActionButton.setAnimation ( fabopen );
			TaxiFloatingActionButton.setAnimation ( fabopen );

			HelpFloatingActionButton.setClickable ( true );
			filterFloatingActionButton.setClickable ( true );
			favoriteFloatingActionButton.setClickable ( true );
			settingFloatingActionButton.setClickable ( true );
			MedianFloatingActionButton.setClickable ( true );
			TaxiFloatingActionButton.setClickable ( true );

			MapChangerFloatingActionButton.setEnabled ( true );
			HelpFloatingActionButton.setEnabled ( true );
			settingFloatingActionButton.setEnabled ( true );
			filterFloatingActionButton.setEnabled ( true );
			favoriteFloatingActionButton.setEnabled ( true );
			MedianFloatingActionButton.setEnabled ( true );
			TaxiFloatingActionButton.setEnabled ( true );

			isopen = true;
		}
	}


	private void AddMarker ( )
	{
		final Marker[] marker = new Marker[ 1 ];
		mMap.setOnMapLongClickListener ( new GoogleMap.OnMapLongClickListener ( )
		{
			@Override
			public void onMapLongClick ( LatLng latLng )
			{
				if ( marker[ 0 ] != null )
				{
					marker[ 0 ].remove ( );
				}
				marker[ 0 ] = mMap.addMarker ( new MarkerOptions ( ).position ( new LatLng ( latLng.latitude, latLng.longitude ) ).draggable ( true ).visible ( true ) );
				UserItem.latitudeUSer = latLng.latitude;
				UserItem.longitudeUSer = latLng.longitude;
				addItemFloatingButton.setVisibility ( View.VISIBLE );


			}
		} );

		mMap.setOnMapClickListener ( new GoogleMap.OnMapClickListener ( )
		{
			@Override
			public void onMapClick ( LatLng latLng )
			{
				try
				{
					marker[ 0 ].remove ( );
					addItemFloatingButton.setVisibility ( View.GONE );
				}
				catch ( Exception e )
				{
					mMap.resetMinMaxZoomPreference ( );
				}
			}
		} );
	}

	// Check IF The Location Turn on or not , if turn on continue , if not alert dialog
	private void CheckGpsISOn ( )
	{
		try
		{
			LocationManager locationManager = ( LocationManager ) getSystemService ( LOCATION_SERVICE

			);
			if ( ! locationManager.isProviderEnabled ( LocationManager.GPS_PROVIDER ) )
			{
				showGPSDisabledAlertToUser ( );
			}
		}
		catch ( Exception e )
		{
			Toast.makeText ( this, e.getMessage ( ), Toast.LENGTH_LONG ).show ( );
		}
	}

	// show alert message to turn on it via setting , or not.
	private void showGPSDisabledAlertToUser ( )
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder ( this );
		alertDialogBuilder.setMessage ( getString ( R.string.GpsDisabledAlert ) ).setCancelable ( false ).setPositiveButton ( getString ( R.string.Enable ), new DialogInterface.OnClickListener ( )
		{
			public void onClick ( DialogInterface dialog, int id )
			{
				Intent callGPSSettingIntent = new Intent ( android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS );
				startActivity ( callGPSSettingIntent );
			}
		} );
		alertDialogBuilder.setNegativeButton ( getString ( R.string.Cancle ), new DialogInterface.OnClickListener ( )
		{
			public void onClick ( DialogInterface dialog, int id )
			{
				dialog.cancel ( );
			}
		} );
		AlertDialog alert = alertDialogBuilder.create ( );
		alert.show ( );
	}

	public void GetPermission ( )
	{
		try
		{
			if ( ActivityCompat.checkSelfPermission ( this, permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission ( this, permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED )
			{
				// TODO: Consider calling
				ActivityCompat.requestPermissions ( MapsActivity.this, new String[] { permission.ACCESS_FINE_LOCATION }, 1 );
				return;
			}
			mMap.setMyLocationEnabled ( true );
		}
		catch ( Exception e )
		{
			Toast.makeText ( this, e.getMessage ( ), Toast.LENGTH_LONG ).show ( );
		}
	}

	@Override
	public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults )
	{
		if ( requestCode == 1 )
		{
			if ( grantResults[ 0 ] == PackageManager.PERMISSION_GRANTED )
			{
				Toast.makeText ( this, getString ( R.string.Can_Use_Location ), Toast.LENGTH_LONG ).show ( );
				GetPermission ( );
			}
			else
			{
				Toast.makeText ( this, getString ( R.string.Cant_Use_Location ), Toast.LENGTH_LONG ).show ( );
			}
		}
	}

	public void ChangeMapType ( View view )
	{
		if ( mMap.getMapType ( ) == GoogleMap.MAP_TYPE_NORMAL )
		{
			mMap.setMapType ( GoogleMap.MAP_TYPE_HYBRID );
		}
		else
		{
			mMap.setMapType ( GoogleMap.MAP_TYPE_NORMAL );
		}
	}

	public void Help_Page ( View view )
	{
		startActivity ( new Intent ( MapsActivity.this, Help_Page.class ) );
	}

	public void Filter_Page ( View view )
	{
		startActivity ( new Intent ( MapsActivity.this, Filter_Page.class ) );
	}

	public void Favorite_page ( View view )
	{

		db.collection ( "FavoriteItem" ).get ( ).addOnCompleteListener ( new OnCompleteListener < QuerySnapshot > ( )
		{
			@Override
			public void onComplete ( @NonNull Task < QuerySnapshot > task )
			{
				if ( task.isSuccessful ( ) )
				{

					title = null;
					img = null;
					latitude = null;
					title = new ArrayList < String > ( );
					img = new ArrayList < String > ( );
					latitude = new ArrayList < Double > ( );
					for ( DocumentSnapshot document : task.getResult ( ) )
					{
						if ( document.getData ( ).get ( "Email liked" ).toString ( ).equals ( mAuth.getCurrentUser ( ).getEmail ( ) ) )
						{
							title.add ( document.get ( "Title To Show" ).toString ( ) );
							img.add ( document.get ( "ImageLink" ).toString ( ) );
							latitude.add ( ( Double ) document.get ( "latitude" ) );
						}
					}
					FavoriteItem.Title = title;
					FavoriteItem.Image = img;
					FavoriteItem.latitude = latitude;
					// for bug press twice its show list view Twice or more.

					startActivity ( new Intent ( MapsActivity.this, Favorite_ListView.class ) );
				}
				else
				{
					Toast.makeText ( MapsActivity.this, getString ( R.string.itemnotfound ), Toast.LENGTH_SHORT ).show ( );
				}
			}
		} );
	}

	public void Setting_Page ( View view )
	{
		startActivity ( new Intent ( MapsActivity.this, Setting_Page.class ) );
	}

	public void addItem ( View view )
	{
		startActivity ( new Intent ( MapsActivity.this, Add_Item_Page_Name_1.class ) );
	}

	private void LongPress ( )
	{
		filterFloatingActionButton.setOnLongClickListener ( new View.OnLongClickListener ( )
		{
			@Override
			public boolean onLongClick ( View v )
			{
				Toast.makeText ( getApplicationContext ( ), getString ( R.string.filter ), Toast.LENGTH_SHORT ).show ( );
				return false;
			}
		} );

		HelpFloatingActionButton.setOnLongClickListener ( new View.OnLongClickListener ( )
		{
			@Override
			public boolean onLongClick ( View v )
			{
				Toast.makeText ( getApplicationContext ( ), getString ( R.string.Help_Page ), Toast.LENGTH_SHORT ).show ( );
				return false;
			}
		} );

		MapChangerFloatingActionButton.setOnLongClickListener ( new View.OnLongClickListener ( )
		{
			@Override
			public boolean onLongClick ( View v )
			{
				Toast.makeText ( getApplicationContext ( ), getString ( R.string.changemap ), Toast.LENGTH_SHORT ).show ( );
				return false;
			}
		} );

		favoriteFloatingActionButton.setOnLongClickListener ( new View.OnLongClickListener ( )
		{
			@Override
			public boolean onLongClick ( View v )
			{
				Toast.makeText ( getApplicationContext ( ), getString ( R.string.favoitem ), Toast.LENGTH_SHORT ).show ( );
				return false;
			}
		} );
		MedianFloatingActionButton.setOnLongClickListener ( new View.OnLongClickListener ( )
		{
			@Override
			public boolean onLongClick ( View v )
			{
				Toast.makeText ( getApplicationContext ( ), getString ( R.string.comingSoon ), Toast.LENGTH_SHORT ).show ( );
				return false;
			}
		} );

		TaxiFloatingActionButton.setOnLongClickListener ( new View.OnLongClickListener ( )
		{
			@Override
			public boolean onLongClick ( View v )
			{
				Toast.makeText ( getApplicationContext ( ), getString ( R.string.comingSoon ), Toast.LENGTH_SHORT ).show ( );
				return false;
			}
		} );

		settingFloatingActionButton.setOnLongClickListener ( new View.OnLongClickListener ( )
		{
			@Override
			public boolean onLongClick ( View v )
			{
				Toast.makeText ( getApplicationContext ( ), getString ( R.string.settingapplicationandacount ), Toast.LENGTH_SHORT ).show ( );
				return false;
			}
		} );
	}

	public void Reload ( View view )
	{
		Intent intent = getIntent ( );
		finish ( );
		startActivity ( intent );
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


	public void ShowMEssageFeatureMedian ( View view )
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder ( this );
		alertDialogBuilder.setTitle ( "New Feature .. !!" );
		alertDialogBuilder.setIcon ( R.drawable.ic_action_new );
		alertDialogBuilder.setMessage ( "Median man is for you , and to make all transaction for you.\n\nWe will Notify you when activate it.\nThank You ♥." );
		alertDialogBuilder.setPositiveButton ( getString ( R.string.comingSoon ), new DialogInterface.OnClickListener ( )
		{

			@Override
			public void onClick ( DialogInterface arg0, int arg1 )
			{
				//Toast.makeText ( getApplicationContext ( ), "", Toast.LENGTH_SHORT ).show ( );
			}
		} );
		AlertDialog alertDialog = alertDialogBuilder.create ( );
		alertDialog.show ( );
	}

	public void ShowMEssageFeatureTaxi ( View view )
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder ( this );
		alertDialogBuilder.setTitle ( "New Feature .. !!" );
		alertDialogBuilder.setIcon ( R.drawable.ic_action_new );
		alertDialogBuilder.setMessage ( "Taxi service is for you, When you want to go to place of item, We provide to you Taxi.\nWe will notify you when activate it.\nThank You ♥." );
		alertDialogBuilder.setPositiveButton ( getString ( R.string.comingSoon ), new DialogInterface.OnClickListener ( )
		{

			@Override
			public void onClick ( DialogInterface arg0, int arg1 )
			{
				//Toast.makeText ( getApplicationContext ( ), "", Toast.LENGTH_SHORT ).show ( );
			}
		} );
		AlertDialog alertDialog = alertDialogBuilder.create ( );
		alertDialog.show ( );
	}
}




