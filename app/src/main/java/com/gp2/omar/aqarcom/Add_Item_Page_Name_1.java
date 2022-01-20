package com.gp2.omar.aqarcom;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


public class Add_Item_Page_Name_1 extends Activity
{
	private EditText          entryname;
	private AdView            mAdView;
	private FirebaseFirestore db;
	private Boolean foundName = false;

	@Override
	protected void onCreate ( Bundle savedInstanceState )
	{
		super.onCreate ( savedInstanceState );
		this.requestWindowFeature ( Window.FEATURE_NO_TITLE );
		this.getWindow ( ).setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
		setContentView ( R.layout.activity_add__item__page__name_1 );

		//Ad View
		MobileAds.initialize ( getApplicationContext ( ), getString ( R.string.banner_ad_unit_id ) );
		mAdView = findViewById ( R.id.adViewadd );
		AdRequest adRequest = new AdRequest.Builder ( ).build ( );
		mAdView.loadAd ( adRequest );

		db = FirebaseFirestore.getInstance ( );
		entryname = findViewById ( R.id.ItemEntryName );


	}

	public void NextClicked ( View view )
	{

		if ( TextUtils.isEmpty ( entryname.getText ( ).toString ( ) ) )
		{
			entryname.setError ( getString ( R.string.Required ) );
		}
		else
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
							if ( document.get ( "Title" ).toString ( ).equals ( entryname.getText ( ).toString ( ) ) )
							{
								foundName = true;
								entryname.setError ( "Please Try Another Name" );
							}
						}
					}
					else
					{
						Toast.makeText ( Add_Item_Page_Name_1.this, "Error Connection To Internet !", Toast.LENGTH_SHORT ).show ( );
					}

					if ( foundName != true )
					{
						UserItem.Title = entryname.getText ( ).toString ( );
						startActivity ( new Intent ( Add_Item_Page_Name_1.this, Add_Item_Page_TypeSale_2.class ) );
					}
					foundName = false;
				}
			} );

		}

	}
}
