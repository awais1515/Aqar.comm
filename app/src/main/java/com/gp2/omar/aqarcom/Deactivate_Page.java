package com.gp2.omar.aqarcom;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class Deactivate_Page extends AppCompatActivity
{
	private FirebaseUser      user;
	private FirebaseFirestore db;
	private ProgressBar       bar;


	@Override
	protected void onCreate ( Bundle savedInstanceState )
	{
		super.onCreate ( savedInstanceState );
		setContentView ( R.layout.activity_deactivate__page );
		this.setTitle ( getString ( R.string.deactivate ) );
		user = FirebaseAuth.getInstance ( ).getCurrentUser ( );
		db = FirebaseFirestore.getInstance ( );
		bar = ( ProgressBar ) findViewById ( R.id.progressBarDeactivate );
	}

	public void DeactivateNow ( View view )
	{
		AlertDialog.Builder builder = new AlertDialog.Builder ( this );
		builder.setTitle ( getString ( R.string.enter_pass ) );


		final EditText input = new EditText ( this );

		input.setInputType ( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
		builder.setView ( input );

		builder.setPositiveButton ( getString ( R.string.ok ), new DialogInterface.OnClickListener ( )
		{
			@Override
			public void onClick ( DialogInterface dialog, int which )
			{

				if ( ! TextUtils.isEmpty ( input.getText ( ).toString ( ) ) )
				{


					AuthCredential credential = EmailAuthProvider.getCredential ( FirebaseAuth.getInstance ( ).getCurrentUser ( ).getEmail ( ), input.getText ( ).toString ( ) );

					user.reauthenticate ( credential ).addOnCompleteListener ( new OnCompleteListener < Void > ( )
					{
						@Override
						public void onComplete ( @NonNull Task < Void > task )
						{
							bar.setVisibility ( View.VISIBLE );

							user.delete ( ).addOnCompleteListener ( new OnCompleteListener < Void > ( )
							{
								@Override
								public void onComplete ( @NonNull Task < Void > task )
								{
									if ( task.isSuccessful ( ) )
									{
										deleteAllItemOFCurrentUser ( );
										deletefavoritelistofmyItem ( );
										deleteFavoriteItemOfMe ( );
										deleteRequestItemofMe ( );
										deleteUserInfo ( );

										Toast.makeText ( Deactivate_Page.this, getString ( R.string.Deactivated ), Toast.LENGTH_SHORT ).show ( );
										bar.setVisibility ( View.GONE );
										FirebaseAuth.getInstance ( ).signOut ( );
										startActivity ( new Intent ( getApplicationContext ( ), LoginPage.class ) );
									}
									else
									{
										bar.setVisibility ( View.GONE );
										Toast.makeText ( getApplicationContext ( ), getString ( R.string.not_correct ), Toast.LENGTH_SHORT ).show ( );
									}
								}
							} ).addOnFailureListener ( new OnFailureListener ( )
							{
								@Override
								public void onFailure ( @NonNull Exception e )
								{
									bar.setVisibility ( View.GONE );
									Toast.makeText ( getApplicationContext ( ), getString ( R.string.not_correct ), Toast.LENGTH_SHORT ).show ( );
								}
							} );


						}
					} );
				}
				else
				{
					Toast.makeText ( getApplicationContext ( ), getString ( R.string.passReq ), Toast.LENGTH_SHORT ).show ( );
				}
			}
		} );
		builder.setNegativeButton ( getString ( R.string.cancel ), new DialogInterface.OnClickListener ( )
		{
			@Override
			public void onClick ( DialogInterface dialog, int which )
			{
				dialog.cancel ( );
			}
		} );

		builder.show ( );


	}

	private void deleteAllItemOFCurrentUser ( )
	{
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
					}
				}
			}
		} );

	}

	private void deletefavoritelistofmyItem ( )
	{
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
	}

	private void deleteFavoriteItemOfMe ( )
	{
		db.collection ( "FavoriteItem" ).whereEqualTo ( "Email liked", user.getEmail ( ) ).get ( ).addOnCompleteListener ( new OnCompleteListener < QuerySnapshot > ( )
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
	}

	private void deleteRequestItemofMe ( )
	{
		db.collection ( "NotificationService" ).whereEqualTo ( "Email Sender", user.getEmail ( ) ).get ( ).addOnCompleteListener ( new OnCompleteListener < QuerySnapshot > ( )
		{
			@Override
			public void onComplete ( @NonNull Task < QuerySnapshot > task )
			{
				if ( task.isSuccessful ( ) )
				{
					for ( DocumentSnapshot document : task.getResult ( ) )
					{
						db.collection ( "NotificationService" ).document ( document.getId ( ) ).delete ( );
					}
				}
			}
		} );
		db.collection ( "NotificationService" ).whereEqualTo ( "Email Reciever", user.getEmail ( ) ).get ( ).addOnCompleteListener ( new OnCompleteListener < QuerySnapshot > ( )
		{
			@Override
			public void onComplete ( @NonNull Task < QuerySnapshot > task )
			{
				if ( task.isSuccessful ( ) )
				{
					for ( DocumentSnapshot document : task.getResult ( ) )
					{
						db.collection ( "NotificationService" ).document ( document.getId ( ) ).delete ( );
					}
				}
			}
		} );
	}

	private void deleteUserInfo ( )
	{
		db.collection ( "UserInfo" ).whereEqualTo ( "Email", user.getEmail ( ) ).get ( ).addOnCompleteListener ( new OnCompleteListener < QuerySnapshot > ( )
		{
			@Override
			public void onComplete ( @NonNull Task < QuerySnapshot > task )
			{
				if ( task.isSuccessful ( ) )
				{
					for ( DocumentSnapshot document : task.getResult ( ) )
					{
						db.collection ( "UserInfo" ).document ( document.getId ( ) ).delete ( );
					}
				}
			}
		} );
	}


	@Override
	public void onBackPressed ( )
	{
		startActivity ( new Intent ( Deactivate_Page.this, Setting_Page.class ) );
	}
}


