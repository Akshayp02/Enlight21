package com.example.enlight21.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewpagerAdapter extends FragmentStateAdapter {

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();

    public ViewpagerAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);

//        // Add your fragments and titles to the lists
//        fragmentList.add(YourFragment.newInstance(0));
//        titleList.add("Title 1");
//
//        fragmentList.add(YourFragment.newInstance(1));
//        titleList.add("Title 2");
//
//        fragmentList.add(YourFragment.newInstance(2));
//        titleList.add("Title 3");

    }

    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        titleList.add(title);
    }

    public String getTitle(int position) {
        return titleList.get(position);
    }
}
