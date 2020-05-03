package de.dhbw.util;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import de.dhbw.activities.DifficultyFragment;
import de.dhbw.game.Difficulty;

public class DifficultyFragmentAdapter extends FragmentPagerAdapter {

    private static final int NUM_PAGES = 3;

    public DifficultyFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new DifficultyFragment(Difficulty.EASY);
            case 1: return new DifficultyFragment(Difficulty.MEDIUM);
            case 2: return new DifficultyFragment(Difficulty.HARD);
            default: return new DifficultyFragment(Difficulty.EASY);
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
