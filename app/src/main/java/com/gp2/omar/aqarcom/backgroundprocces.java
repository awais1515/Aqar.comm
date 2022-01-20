package com.gp2.omar.aqarcom;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * Created by Omar on 12/25/2017.
 */

public class backgroundprocces extends Service
{

	private FirebaseFirestore db    = FirebaseFirestore.getInstance ( );
	private FirebaseAuth      mAuth = FirebaseAuth.getInstance ( );

	@Override
	public int onStartCommand ( final Intent intent, int flags, int startId )
	{
		Handler handler = new Handler ( );
		handler.postDelayed ( new Runnable ( )
		{
			@Override
			public void run ( )
			{
				onTaskRemoved ( intent );


				db.collection ( "NotificationService" ).get ( ).addOnCompleteListener ( new OnCompleteListener < QuerySnapshot > ( )
				{
					@Override
					public void onComplete ( @NonNull Task < QuerySnapshot > task )
					{
						if ( task.isSuccessful ( ) )
						{
							for ( DocumentSnapshot document1 : task.getResult ( ) )
							{
								if ( document1.getId ( ).toString ( ).equals ( mAuth.getCurrentUser ( ).getEmail ( ).toString ( ) ) )
								{
									Intent i = new Intent ( getApplicationContext ( ), MainActivity.class );
									i.addFlags ( Intent.FLAG_ACTIVITY_CLEAR_TOP );
									PendingIntent notification = PendingIntent.getActivity ( getApplicationContext ( ), 0, i, PendingIntent.FLAG_ONE_SHOT );
									NotificationCompat.Builder builder = new NotificationCompat.Builder ( getApplicationContext ( ) );
									builder.setSmallIcon ( R.mipmap.ic_launcher );
									builder.setContentTitle ( document1.get ( "Email Sender" ).toString ( ) );
									builder.setContentText ( document1.get ( "Item ID" ).toString ( ) );
									builder.setContentIntent ( notification );

									builder.setDefaults ( NotificationCompat.DEFAULT_SOUND );
									builder.setAutoCancel ( true );

									NotificationManager mm = ( NotificationManager ) getSystemService ( Context.NOTIFICATION_SERVICE );
									mm.cancel ( 1 );
									mm.notify ( 0, builder.build ( ) );


								}
							}
						}
					}
				} );
			}
		}, 15000 );


		return START_STICKY;
	}

	@Nullable
	@Override
	public IBinder onBind ( Intent intent )
	{
		return null;
	}

	@Override
	public void onTaskRemoved ( Intent rootIntent )
	{
		Intent restartServiceIntent = new Intent ( getApplicationContext ( ), this.getClass ( ) );
		restartServiceIntent.setPackage ( getPackageName ( ) );
		startService ( restartServiceIntent );
		super.onTaskRemoved ( rootIntent );
	}
}
