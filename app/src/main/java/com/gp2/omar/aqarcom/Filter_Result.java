package com.gp2.omar.aqarcom;

import android.annotation.SuppressLint;
import android.content.Intent;
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

import com.squareup.picasso.Picasso;

public class Filter_Result extends AppCompatActivity
{

	private TextView textnotfound;


	@Override
	protected void onCreate ( Bundle savedInstanceState )
	{
		super.onCreate ( savedInstanceState );
		setContentView ( R.layout.activity_filter__result );

		textnotfound = ( TextView ) findViewById ( R.id.textNotFound );


		filteradapter filteradapter = new filteradapter ( );
		final ListView listView = ( ListView ) findViewById ( R.id.filter_list_view );
		listView.setAdapter ( filteradapter );



	}

	class filteradapter extends BaseAdapter
	{

		@Override
		public int getCount ( )
		{
			return Filter_Search_Data.title.size ( );
		}

		@Override
		public Object getItem ( int position )
		{
			return Filter_Search_Data.title.get ( position );
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
			View view = layoutInflater.inflate ( R.layout.row_filter_item, null );

			final TextView title = view.findViewById ( R.id.FilterTitle );
			TextView street = view.findViewById ( R.id.FilterStreet );
			TextView area = view.findViewById ( R.id.FilterArea );
			TextView price = view.findViewById ( R.id.FilterPrice );

			ImageView img = ( ImageView ) view.findViewById ( R.id.filterImage );

			Button btn = ( Button ) view.findViewById ( R.id.ShowItemButton );

			Picasso.with ( getApplicationContext ( ) ).load ( Filter_Search_Data.picture.get ( position ) ).into ( img );
			title.setText ( Filter_Search_Data.title.get ( position ) );
			street.setText ( getString( R.string.street) + Filter_Search_Data.street.get ( position ) );
			area.setText ( getString( R.string.area_) + Filter_Search_Data.area.get ( position ) );
			price.setText ( getString( R.string.price_) + Filter_Search_Data.price.get ( position ) + " JD." );


			btn.setOnClickListener ( new View.OnClickListener ( )
			{
				@Override
				public void onClick ( View v )
				{
					Show_Item.title = title.getText ( ).toString ( );
					Show_Item.latitude = Filter_Search_Data.latitude.get ( Filter_Search_Data.title.indexOf ( title.getText ( ).toString ( ) ) );
					Intent intent = new Intent ( Filter_Result.this, Item_Information.class );
					intent.putExtra ( "activity", "Filter_Search" );
					startActivity ( intent );
				}
			} );

			return view;
		}
	}

	@Override
	public void onBackPressed ( )
	{
		Filter_Search_Data.title = null;
		Filter_Search_Data.area = null;
		Filter_Search_Data.street = null;
		Filter_Search_Data.picture = null;
		Filter_Search_Data.price = null;

		startActivity ( new Intent ( Filter_Result.this, Filter_Page.class ) );
	}
}
