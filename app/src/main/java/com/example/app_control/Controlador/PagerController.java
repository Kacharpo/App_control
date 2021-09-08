package com.example.app_control.Controlador;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.app_control.Blank;

public class PagerController extends FragmentPagerAdapter {
    int numOfTabs;

    public PagerController(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.numOfTabs = behavior;
    }

    @NonNull

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Inicio();
            case 1:
                return new Final();
            case 2:
                return new Tiempo();
            case 3:
                return new Disponibilidad();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
