package id.co.myproject.talasstore_owner.view.detail;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.co.myproject.talasstore_owner.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailStaffFragment extends Fragment {


    public DetailStaffFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_staff, container, false);
    }

}
