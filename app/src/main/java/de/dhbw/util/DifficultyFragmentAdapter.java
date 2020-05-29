package de.dhbw.util;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import de.dhbw.activities.DifficultyFragment;
import de.dhbw.game.Difficulty;

public class DifficultyFragmentAdapter extends FragmentPagerAdapter {

    /**
     * Specifies the number of pages of the ViewPager.
     * This number should be equal to the amount of difficulties.
     */
    private static final int NUM_PAGES = 3;

    /**
     * Constructor
     * @param fm
     */
    public DifficultyFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * Access the fragments of the ViewPager by passing the position as int.
     * @param position
     * @return fragment
     */
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new DifficultyFragment(Difficulty.EASY);
            case 1: return new DifficultyFragment(Difficulty.MEDIUM);
            case 2: return new DifficultyFragment(Difficulty.HARD);
            default: return new DifficultyFragment(Difficulty.EASY);
        }
    }

    /**
     * Read the NUM_PAGES field.
     * @return number of pages
     */
    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
