package com.techdevs.practis;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class HelpPager extends FragmentStatePagerAdapter {

    int tabCount;
   // @NonNull FragmentManager fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;
    public HelpPager(FragmentManager fm, int tabCount){
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.tabCount=tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0 :
                Help1Fragment help1Fragment=new Help1Fragment();
                return help1Fragment;
            case 1 :
                Help2Fragment help2Fragment=new Help2Fragment();
                return help2Fragment;
            case 2 :
                Help3Fragment help3Fragment=new Help3Fragment();
                return help3Fragment;
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
