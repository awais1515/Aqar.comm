package com.gp2.omar.aqarcom;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

public class Delete_All_ITem extends AppCompatActivity
{
	private FirebaseFirestore db;
	private FirebaseUser      user;


	@Override
	protected void onCreate ( Bundle savedInstanceState )
	{
		super.onCreate ( savedInstanceState );
		this.setTitle ( "Delete All Item" );
		setContentView ( R.layout.activity_delete__all__item );
		user = FirebaseAuth.getInstance ( ).getCurrentUser ( );
		db = FirebaseFirestore.getInstance ( );

	}

	public void DeleteNow ( View view )
	{

		// Delete Item.
		db.collection ( "ItemUser_Aqar.com" ).whereEqualTo ( "Email", user.getEmail ( ) ).get ( ).addOnCompleteListener ( new OnCompleteListener < QuerySnapshot > ( )
		{
			@Override
			public void onComplete ( @NonNull Task < QuerySnapshot > task )
			{
				if ( task.isSuccessful ( ) )
				{
					for ( DocumentSnapshot document : task.getResult ( ) )
					{
						db.collection ( "ItemUser_Aqar.com" ).document ( document.getId ( ) ).delete ( );
						Toast.makeText ( Delete_All_ITem.this, getString( R.string.deletedi), Toast.LENGTH_SHORT ).show ( );
					}
				}
				else
				{
					Toast.makeText ( Delete_All_ITem.this, getString( R.string.deletenotsucces), Toast.LENGTH_SHORT ).show ( );
				}
			}
		} );

		// Delete Favorite.
		db.collection ( "FavoriteItem" ).whereEqualTo ( "Email of Item", user.getEmail ( ) ).get ( ).addOnCompleteListener ( new OnCompleteListener < QuerySnapshot > ( )
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


		startActivity ( new Intent ( Delete_All_ITem.this, Setting_Page.class ) );
	}


	@Override
	public void onBackPressed ( )
	{
		startActivity ( new Intent ( Delete_All_ITem.this, Setting_Page.class ) );
	}
}
