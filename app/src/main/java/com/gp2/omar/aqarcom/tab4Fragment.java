package com.gp2.omar.aqarcom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Omar on 10/28/2017.
 */

public class tab4Fragment extends Fragment
{
	private static final String TAG = "Tab4Fragment";

	@Nullable
	@Override
	public View onCreateView ( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState )
	{
		View view = inflater.inflate ( R.layout.tab4_fragment, container, false );
		return view;
	}
}
