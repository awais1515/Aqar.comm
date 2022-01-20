package com.gp2.omar.aqarcom;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

public class Generic_Item_Admistrator extends AppCompatActivity
{
	private ListView list;

	@Override
	protected void onCreate ( Bundle savedInstanceState )
	{
		super.onCreate ( savedInstanceState );
		this.setTitle ( "Item" );
		setContentView ( R.layout.activity_generic__item__admistrator );

		MyCustomerAdapter myCustomerAdapter = new MyCustomerAdapter ( );
		final ListView ls = ( ListView ) findViewById ( R.id.Adminstrator_ListView_Item );
		ls.setAdapter ( myCustomerAdapter );

	}

	class MyCustomerAdapter extends BaseAdapter
	{

		@Override
		public int getCount ( )
		{
			return Generic_Item_Admin.Title.size ( );
		}

		@Override
		public Object getItem ( int position )
		{
			return Generic_Item_Admin.Title.get ( position );
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
			View view = layoutInflater.inflate ( R.layout.row_view_item_adminstrator, null );

			ImageView img = view.findViewById ( R.id.Admin_Item_Images );
			final TextView txttitle = view.findViewById ( R.id.Admin_Item_Title );
			TextView txtdate = view.findViewById ( R.id.Admin_Item_Date );

			Picasso.with ( getApplicationContext ( ) ).load ( Generic_Item_Admin.Image.get ( position ) ).into ( img );
			txttitle.setText ( Generic_Item_Admin.Title.get ( position ) );
			txtdate.setText ( Generic_Item_Admin.Date.get ( position ) );

			txttitle.setOnClickListener ( new View.OnClickListener ( )
			{
				@Override
				public void onClick ( View v )
				{
					Show_Item.title = txttitle.getText ( ).toString ( );
					Show_Item.latitude = Generic_Item_Admin.latitude.get ( Generic_Item_Admin.Title.indexOf ( txttitle.getText ( ).toString ( ) ) );
					Intent intent = new Intent ( Generic_Item_Admistrator.this, Item_Information.class );
					intent.putExtra ( "activity", "Generic_Item_Admistrator" );
					startActivity ( intent );
				}
			} );
			return view;
		}
	}


	@Override
	public void onBackPressed ( )
	{


		Handler handler = new Handler ( );
		handler.postDelayed ( new Runnable ( )
		{
			public void run ( )
			{
				Generic_Item_Admin.Title = null;
				Generic_Item_Admin.Image = null;
				Generic_Item_Admin.latitude = null;
				Generic_Item_Admin.Date = null;
				startActivity ( new Intent ( Generic_Item_Admistrator.this, AdminSettingActivity.class ) );
			}
		}, 1000 );   //1 seconds

	}
}
