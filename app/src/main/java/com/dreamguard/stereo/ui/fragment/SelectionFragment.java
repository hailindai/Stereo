package com.dreamguard.stereo.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamguard.stereo.R;

/**
 * Created by hailin on 17-7-7.
 */

public class SelectionFragment extends Fragment {

    public SelectionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selection, container, false);
        return view;
    }



}
