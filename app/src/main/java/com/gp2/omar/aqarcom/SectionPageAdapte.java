package com.gp2.omar.aqarcom;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omar on 10/28/2017.
 */

class SectionPageAdapte extends FragmentPagerAdapter
{
	private final List < Fragment > fragmentslist      = new ArrayList <> ( );
	private final List < String >   mfragmentTitleList = new ArrayList <> ( );

	public void addFragment ( Fragment fragment, String title )
	{
		fragmentslist.add ( fragment );
		mfragmentTitleList.add ( title );
	}


	public SectionPageAdapte ( FragmentManager fm )
	{
		super ( fm );
	}

	@Override
	public CharSequence getPageTitle ( int position )
	{
		return mfragmentTitleList.get ( position );
	}

	@Override
	public Fragment getItem ( int position )
	{
		return fragmentslist.get ( position );
	}

	@Override
	public int getCount ( )
	{
		return fragmentslist.size ( );
	}
}
