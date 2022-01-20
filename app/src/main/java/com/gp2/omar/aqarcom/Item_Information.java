package com.gp2.omar.aqarcom;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class Item_Information extends Activity
{
	private TextView infoArea, infoOld, infoStreet, infoSale, infoPrice, infoFloor, infoNumber, intoTitle, infoplace, infoTypeItem, infoDesc;
	private FirebaseFirestore db;
	private int               ViewCounter;

	private ImageView            img;
	private FirebaseAuth         mAuth;
	private FloatingActionButton btndelete, btnmodefy, btnreq, btncall, btnprofile, btnfav;
	private FloatingTextButton btnView;
	private String             Phone, Email, idTitele, Title, Link_Images, activity;
	private Double Latitude;


	@Override
	protected void onCreate ( Bundle savedInstanceState )
	{
		super.onCreate ( savedInstanceState );
		this.requestWindowFeature ( Window.FEATURE_NO_TITLE );
		this.getWindow ( ).setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );

		setContentView ( R.layout.activity_item__information );
		Intent intent = getIntent ( );
		activity = intent.getStringExtra ( "activity" );
		mAuth = FirebaseAuth.getInstance ( );

		btncall = findViewById ( R.id.RequestCall );
		btndelete = findViewById ( R.id.FloatButtonDelete );
		btnmodefy = findViewById ( R.id.FloatButtonModify );
		btnreq = findViewById ( R.id.RequestButton );
		btnView = findViewById ( R.id.action_button_View );
		btnprofile = findViewById ( R.id.FloatButtonprofile );
		btnfav = findViewById ( R.id.FloatButtonFavorite );

		img = findViewById ( R.id.ImagesOfItem );
		intoTitle = findViewById ( R.id.InfoTitle );
		infoStreet = findViewById ( R.id.InfoSteet );
		infoplace = findViewById ( R.id.InfoPlace );
		infoTypeItem = findViewById ( R.id.InfoTypeItem );
		infoSale = findViewById ( R.id.InfoSale );
		infoArea = findViewById ( R.id.InfoArea );
		infoOld = findViewById ( R.id.InfoOld );
		infoFloor = findViewById ( R.id.InfFloor );
		infoNumber = findViewById ( R.id.InfNumber );
		infoPrice = findViewById ( R.id.Infprice );
		infoDesc = findViewById ( R.id.InfDesc );


		db = FirebaseFirestore.getInstance ( );
		try
		{
			if ( activity.equals ( "Generic_Item_Admistrator" ) )
			{
				btnreq.setVisibility ( View.GONE );
				btncall.setVisibility ( View.GONE );
				btnfav.setVisibility ( View.GONE );
				btnprofile.setVisibility ( View.GONE );
				btndelete.setVisibility ( View.GONE );
				btnmodefy.setVisibility ( View.VISIBLE );

				btnmodefy.setColorFilter ( Color.WHITE );
			}
		}
		catch ( Exception e )
		{
			btnreq.setVisibility ( View.VISIBLE );
			btncall.setVisibility ( View.VISIBLE );
			btnfav.setVisibility ( View.VISIBLE );
			btnprofile.setVisibility ( View.VISIBLE );
			btndelete.setVisibility ( View.GONE );
			btnmodefy.setVisibility ( View.GONE );
		}

		final DocumentReference docRef = db.collection ( "ItemUser_Aqar.com" ).document ( Show_Item.title + " " + Show_Item.latitude );
		docRef.get ( ).

				addOnCompleteListener ( new OnCompleteListener < DocumentSnapshot > ( )
				{
					@Override
					public void onComplete ( @NonNull Task < DocumentSnapshot > task )
					{
						if ( task.isSuccessful ( ) )
						{

							DocumentSnapshot document = task.getResult ( );

							if ( document != null )
							{
								Title = task.getResult ( ).get ( "Title" ).toString ( );
								Email = task.getResult ( ).get ( "Email" ).toString ( );
								idTitele = task.getResult ( ).getId ( );
								Latitude = ( Double ) task.getResult ( ).get ( "latitudeUSer" );
								Link_Images = task.getResult ( ).get ( "Link_Images" ).toString ( );

								Picasso.with ( getApplicationContext ( ) ).load ( Link_Images ).into ( img );

								if ( mAuth.getCurrentUser ( ).getEmail ( ).equals ( Email ) )
								{
									btnreq.setVisibility ( View.GONE );
									btncall.setVisibility ( View.GONE );
									btnfav.setVisibility ( View.GONE );
									btnprofile.setVisibility ( View.GONE );
									btndelete.setVisibility ( View.VISIBLE );
									btnmodefy.setVisibility ( View.VISIBLE );
								}


								intoTitle.setText ( getString ( R.string.titleitem ) + " " + Title );
								infoStreet.setText ( getString ( R.string.streets ) + " " + task.getResult ( ).get ( "streetItem" ).toString ( ) );
								infoplace.setText ( getString ( R.string.placeitem ) + " " + task.getResult ( ).get ( "placeITem" ).toString ( ) );
								infoTypeItem.setText ( getString ( R.string.TypeItem ) + " " + task.getResult ( ).get ( "typeitem" ).toString ( ) );
								infoSale.setText ( getString ( R.string.sales ) + " " + task.getResult ( ).get ( "typesale" ).toString ( ) );
								infoArea.setText ( getString ( R.string.area ) + " " + task.getResult ( ).get ( "areaitem" ).toString ( ) + "²" );
								infoOld.setText ( getString ( R.string.age ) + task.getResult ( ).get ( "olditem" ).toString ( ) + getString ( R.string.years ) );
								infoFloor.setText ( getString ( R.string.numberItem ) + " " + task.getResult ( ).get ( "numberItem" ).toString ( ) );
								infoNumber.setText ( getString ( R.string.Floor ) + " " + task.getResult ( ).get ( "flooritem" ).toString ( ) );
								infoPrice.setText ( getString ( R.string.price ) + " " + task.getResult ( ).get ( "priceitem" ).toString ( ) + getString ( R.string.jd ) );
								btnView.setTitle ( task.getResult ( ).get ( "View" ).toString ( ) );
								if ( task.getResult ( ).get ( "DescriptionUser" ) == null )
								{
									infoDesc.setText ( getString ( R.string.descnull ) );
								}
								else
								{
									infoDesc.setText ( getString ( R.string.descItem ) + " " + task.getResult ( ).get ( "DescriptionUser" ).toString ( ) );
								}


								if ( ! mAuth.getCurrentUser ( ).getEmail ( ).equals ( Email ) )
								{
									// Add View Count + 1
									int ViewCount = Integer.parseInt ( task.getResult ( ).get ( "View" ).toString ( ) );
									db.collection ( "ItemUser_Aqar.com" ).document ( task.getResult ( ).getId ( ) ).update ( "View", ++ ViewCount );
									ViewCounter = ViewCount;
								}


							}
							else
							{
								Toast.makeText ( Item_Information.this, getString ( R.string.Error_101 ), Toast.LENGTH_SHORT ).show ( );
								startActivity ( new Intent ( Item_Information.this, MapsActivity.class ) );
							}

						}
						else
						{
							Toast.makeText ( Item_Information.this, getString ( R.string.check_internet ), Toast.LENGTH_SHORT ).show ( );
						}
					}
				} );

		btnView.setOnClickListener ( new View.OnClickListener ( )

		{
			@Override
			public void onClick ( View v )
			{
				Toast.makeText ( Item_Information.this, getString ( R.string.thisITem ) + " " + ( ViewCounter - 1 ) + " " + getString ( R.string.views ), Toast.LENGTH_SHORT ).show ( );
			}
		} );
	}

	public void Call_Saller ( View view )
	{
		DocumentReference documentReference = db.collection ( "UserInfo" ).document ( Email );
		documentReference.get ( ).addOnCompleteListener ( new OnCompleteListener < DocumentSnapshot > ( )
		{
			@Override
			public void onComplete ( @NonNull Task < DocumentSnapshot > task )
			{
				if ( task.isSuccessful ( ) )
				{
					DocumentSnapshot document = task.getResult ( );
					if ( document != null )
					{
						Phone = task.getResult ( ).get ( "Phone" ).toString ( );
						Intent intent = new Intent ( Intent.ACTION_DIAL );
						intent.setData ( Uri.parse ( "tel:" + Phone ) );
						startActivity ( intent );
					}
				}
			}
		} );
	}

	public void Request_Item ( View view )
	{
		Map < String, Object > city = new HashMap <> ( );
		city.put ( "Email Reciever", Email );
		city.put ( "Email Sender", mAuth.getCurrentUser ( ).getEmail ( ) );
		city.put ( "Message", "I want this Item" );
		city.put ( "Time", Calendar.AM );
		city.put ( "Item ID", idTitele );
		db.collection ( "NotificationService" ).document ( Email ).set ( city ).addOnSuccessListener ( 	new OnSuccessListener < Void > ( )
		{
			@Override
			public void onSuccess ( Void aVoid )
			{
				Toast.makeText ( Item_Information.this, "Request Sended", Toast.LENGTH_SHORT ).show ( );
			}
		} ).addOnFailureListener ( new OnFailureListener ( )
		{
			@Override
			public void onFailure ( @NonNull Exception e )
			{
				Toast.makeText ( Item_Information.this, "Request Not Send , Error 404", Toast.LENGTH_SHORT ).show ( );
			}
		} );
	}

	public void Favorite_ITem ( View view )
	{
		Map < String, Object > fav = new HashMap <> ( );
		fav.put ( "Email liked", mAuth.getCurrentUser ( ).getEmail ( ) );
		fav.put ( "idTitle", idTitele );
		fav.put ( "Title To Show", Title );
		fav.put ( "latitude", Latitude );
		fav.put ( "ImageLink", Link_Images );
		fav.put ( "Email of Item", Email );

		db.collection ( "FavoriteItem" ).document ( mAuth.getCurrentUser ( ).getEmail ( ) + " " + Title ).set ( fav ).addOnSuccessListener ( new OnSuccessListener < Void > ( )
		{
			@Override
			public void onSuccess ( Void aVoid )
			{
				Toast.makeText ( Item_Information.this, "Favorite it", Toast.LENGTH_SHORT ).show ( );
			}
		} ).addOnFailureListener ( new OnFailureListener ( )
		{
			@Override
			public void onFailure ( @NonNull Exception e )
			{
				Toast.makeText ( Item_Information.this, "Error , please try again.", Toast.LENGTH_SHORT ).show ( );
			}
		} );
	}

	public void Show_Profile_Of_ITem ( View view )
	{
		Item_Profile.EMAIL_USER = Email;
		Intent intent = new Intent ( Item_Information.this, Profile.class );
		intent.putExtra ( "activity", "Item_Information" );
		startActivity ( intent );
	}

	public void DeleteITemNow ( View view )
	{
		AlertDialog alertDialog = new AlertDialog.Builder ( Item_Information.this ).create ( );
		alertDialog.setTitle ( getString ( R.string.alert ) );
		alertDialog.setMessage ( getString ( R.string.areyousuretodelete ) + Show_Item.title + " ?" );
		alertDialog.setButton ( AlertDialog.BUTTON_NEUTRAL, getString ( R.string.yes ), new DialogInterface.OnClickListener ( )
		{
			public void onClick ( DialogInterface dialog, int which )
			{
				db.collection ( "ItemUser_Aqar.com" ).document ( Show_Item.title + " " + Show_Item.latitude ).delete ( ).addOnSuccessListener ( new OnSuccessListener < Void > ( )
				{
					@Override
					public void onSuccess ( Void aVoid )
					{
						Toast.makeText ( Item_Information.this, getString ( R.string.deleted_ ), Toast.LENGTH_LONG ).show ( );
					}
				} ).addOnFailureListener ( new OnFailureListener ( )
				{
					@Override
					public void onFailure ( @NonNull Exception e )
					{
						Toast.makeText ( Item_Information.this, getString ( R.string.errordeleteitem ), Toast.LENGTH_SHORT ).show ( );
					}
				} );

				// Delete Favorite.
				db.collection ( "FavoriteItem" ).whereEqualTo ( "idTitle", idTitele ).get ( ).addOnCompleteListener ( new OnCompleteListener < QuerySnapshot > ( )
				{
					@Override
					public void onComplete ( @NonNull Task < QuerySnapshot > task )
					{
						if ( task.isSuccessful ( ) )
						{
							for ( DocumentSnapshot document : task.getResult ( ) )
							{
								db.collection ( "FavoriteItem" ).document ( document.getId ( ) ).delete ( );
							}

						}
					}
				} );

				startActivity ( new Intent ( Item_Information.this, MapsActivity.class ) );

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

	public void ModifyItem ( View view )
	{
		Intent intent = new Intent ( Item_Information.this, Edit_Item.class );
		intent.putExtra ( "activity", "Item_Informatation" );
		startActivity ( intent );

	}

	@Override
	public void onBackPressed ( )
	{
		try
		{
			switch ( activity )
			{
				case "MapsActivity":
				case "Profile":
				case "Item_Informatation":
					startActivity ( new Intent ( Item_Information.this, MapsActivity.class ) );
					break;
				case "Favorite_ListView":
					startActivity ( new Intent ( Item_Information.this, Favorite_ListView.class ) );
					break;
				case "item_Information_of_profile":
					startActivity ( new Intent ( Item_Information.this, item_Information_of_profile.class ) );
					break;
				case "Generic_Item_Admistrator":
					startActivity ( new Intent ( Item_Information.this, Generic_Item_Admistrator.class ) );
					break;
				case "Filter_Search":
					startActivity ( new Intent ( Item_Information.this, Filter_Result.class ) );
				default:
					startActivity ( new Intent ( Item_Information.this, MapsActivity.class ) );
			}
		}
		catch ( Exception e )
		{
			Toast.makeText ( this, e.getMessage ( ), Toast.LENGTH_SHORT ).show ( );
		}
	}
}
