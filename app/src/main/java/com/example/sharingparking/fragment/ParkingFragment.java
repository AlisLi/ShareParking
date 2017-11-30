package com.example.sharingparking.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sharingparking.R;


/**
 * Created by Lizhiguo on 2017/4/2.
 */

public class ParkingFragment extends Fragment {
    private View mView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_parking,container,false);
        return mView;
    }

    @Nullable
    @Override
    public View getView() {
        return mView;
    }
}
