package com.gp2.omar.aqarcom;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

public class Help_Page extends AppCompatActivity
{
	private static final String TAG = "MainTabPage";

	private SectionPageAdapte sectionPageAdapte;

	private ViewPager mViewPager;

	@Override
	protected void onCreate ( Bundle savedInstanceState )
	{
		super.onCreate ( savedInstanceState );
		setContentView ( R.layout.activity_help__page );

		sectionPageAdapte = new SectionPageAdapte ( getSupportFragmentManager ( ) );

		mViewPager = ( ViewPager ) findViewById ( R.id.container );
		setupViewPager ( mViewPager );

		TabLayout tabLayout = ( TabLayout ) findViewById ( R.id.tabs );
		tabLayout.setupWithViewPager ( mViewPager );
	}

	private void setupViewPager ( ViewPager viewPager )
	{
		SectionPageAdapte adapte = new SectionPageAdapte ( getSupportFragmentManager ( ) );
		adapte.addFragment ( new tab1Fragment ( ), "Icon" );
		adapte.addFragment ( new tab2Fragment ( ), "Marker" );
		adapte.addFragment ( new tab3Fragment ( ), "Add Item" );
		adapte.addFragment ( new tab4Fragment ( ), "Delete Item" );
		adapte.addFragment ( new tab5Fragment ( ), "Request Item" );

		viewPager.setAdapter ( adapte );

	}
}

