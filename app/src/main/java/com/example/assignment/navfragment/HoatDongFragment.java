package com.example.assignment.navfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.assignment.HdBuocChan.ViewBuocChan;
import com.example.assignment.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class HoatDongFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hoat_dong, container, false);
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.view_pager);

        FragmentActivity activity = getActivity();
        if (activity != null) {
            ViewBuocChan viewAdapter = new ViewBuocChan(activity);
            viewPager.setAdapter(viewAdapter);

            new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
                // Set tab names here
                switch (position) {
                    case 0:
                        tab.setText("Hôm nay");
                        break;
                    case 1:
                        tab.setText("Tháng này");
                        break;
                }
            }).attach();
        }

        return view;
    }
}
