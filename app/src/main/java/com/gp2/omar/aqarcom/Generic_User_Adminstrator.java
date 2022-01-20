package com.gp2.omar.aqarcom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Generic_User_Adminstrator extends AppCompatActivity
{

	@Override
	protected void onCreate ( Bundle savedInstanceState )
	{
		super.onCreate ( savedInstanceState );
		this.setTitle ( "User" );
		setContentView ( R.layout.activity_generic__user__adminstrator );

		userAsync userAsyncs = new userAsync ( );
		final ListView ls = ( ListView ) findViewById ( R.id.Admistrator_user );
		ls.setAdapter ( userAsyncs );
	}


	class userAsync extends BaseAdapter
	{

		@Override
		public int getCount ( )
		{
			return Generic_User_Data.Names.size ( );
		}

		@Override
		public Object getItem ( int position )
		{
			return Generic_User_Data.Names.get ( position );
		}

		@Override
		public long getItemId ( int position )
		{
			return position;
		}

		@Override
		public View getView ( int position, View convertView, ViewGroup parent )
		{
			LayoutInflater layoutInflater = getLayoutInflater ( );
			View view = layoutInflater.inflate ( R.layout.row_view_adminstrator_user, null );

			ImageView img = view.findViewById ( R.id.Admin_User_Profile_Picture );
			final TextView txtName = view.findViewById ( R.id.Admin_User_Name );
			final TextView txtEmail = view.findViewById ( R.id.Admin_User_Email );

			Picasso.with ( getApplicationContext ( ) ).load ( Generic_User_Data.ProfilePictire.get ( position ) ).into ( img );
			txtName.setText ( Generic_User_Data.Names.get ( position ) );
			txtEmail.setText ( Generic_User_Data.Email.get ( position ) );

			txtName.setOnClickListener ( new View.OnClickListener ( )
			{
				@Override
				public void onClick ( View v )
				{
					Item_Profile.EMAIL_USER = txtEmail.getText ( ).toString ( );
					Intent intent = new Intent ( Generic_User_Adminstrator.this, Profile.class );
					intent.putExtra ( "activity", "Setting_User_Admin" );
					startActivity ( intent );

				}
			} );


			return view;
		}


	}

	@Override
	public void onBackPressed ( )
	{
		Generic_User_Data.Names = null;
		Generic_User_Data.Email = null;
		Generic_User_Data.ProfilePictire = null;
		startActivity ( new Intent ( Generic_User_Adminstrator.this, AdminSettingActivity.class ) );

	}
}
