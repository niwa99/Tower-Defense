package de.dhbw.activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import de.dhbw.R;
import de.dhbw.game.Difficulty;
import de.dhbw.util.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class DifficultyFragment extends Fragment {

    private static final int FRAGMENT_EASY = 0;
    private static final int FRAGMENT_MEDIUM = 1;
    private static final int FRAGMENT_HARD = 2;

    private Difficulty difficulty;

    private TextView textDifficultyHeader, textDifficultyDescription, textValueMaxWave, textValueEnemiesKilled, textValueBuiltTowers, textValueUpgrades, textValueMoneySpent;
    private Button changeLeftButton, changeRightButton;

    public DifficultyFragment(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_difficulty, container, false);

        textDifficultyHeader = fragmentView.findViewById(R.id.difficulty_header);
        textDifficultyDescription = fragmentView.findViewById(R.id.difficulty_description_text);
        changeLeftButton = fragmentView.findViewById(R.id.difficulty_switch_left);
        changeRightButton = fragmentView.findViewById(R.id.difficulty_switch_right);

        textValueMaxWave = fragmentView.findViewById(R.id.stats_maxWave_value);
        textValueEnemiesKilled = fragmentView.findViewById(R.id.stats_enemiesKilled_value);
        textValueBuiltTowers = fragmentView.findViewById(R.id.stats_builtTowers_value);
        textValueUpgrades = fragmentView.findViewById(R.id.stats_upgrades_value);
        textValueMoneySpent = fragmentView.findViewById(R.id.stats_moneySpent_value);

        switch (difficulty) {
            case EASY:
                setEasyInformation();
                break;
            case MEDIUM:
                setMediumInformation();
                break;
            case HARD:
                setHardInformation();
                break;
        }
        return fragmentView;
    }

    private void setEasyInformation() {
        textDifficultyHeader.setText(R.string.difficulty_easy);
        textDifficultyDescription.setText(R.string.difficulty_easy_description);
        textValueMaxWave.setText(PreferenceManager.getStatisticsValue(PreferenceManager.Statistics.EASY_MAX_WAVE));
        textValueEnemiesKilled.setText(PreferenceManager.getStatisticsValue(PreferenceManager.Statistics.EASY_ENEMIES_KILLED));
        textValueBuiltTowers.setText(PreferenceManager.getStatisticsValue(PreferenceManager.Statistics.EASY_BUILT_TOWERS));
        textValueUpgrades.setText(PreferenceManager.getStatisticsValue(PreferenceManager.Statistics.EASY_UPGRADES));
        textValueMoneySpent.setText(PreferenceManager.getStatisticsValue(PreferenceManager.Statistics.EASY_MONEY_SPENT));

        changeLeftButton.setOnClickListener(view -> ((ViewPager) getActivity().findViewById(R.id.difficulty_pager)).setCurrentItem(FRAGMENT_HARD));
        changeRightButton.setOnClickListener(view -> ((ViewPager) getActivity().findViewById(R.id.difficulty_pager)).setCurrentItem(FRAGMENT_MEDIUM));
    }

    private void setMediumInformation() {
        textDifficultyHeader.setText(R.string.difficulty_medium);
        textDifficultyDescription.setText(R.string.difficulty_medium_description);
        textValueMaxWave.setText(PreferenceManager.getStatisticsValue(PreferenceManager.Statistics.MEDIUM_MAX_WAVE));
        textValueEnemiesKilled.setText(PreferenceManager.getStatisticsValue(PreferenceManager.Statistics.MEDIUM_ENEMIES_KILLED));
        textValueBuiltTowers.setText(PreferenceManager.getStatisticsValue(PreferenceManager.Statistics.MEDIUM_BUILT_TOWERS));
        textValueUpgrades.setText(PreferenceManager.getStatisticsValue(PreferenceManager.Statistics.MEDIUM_UPGRADES));
        textValueMoneySpent.setText(PreferenceManager.getStatisticsValue(PreferenceManager.Statistics.MEDIUM_MONEY_SPENT));

        changeLeftButton.setOnClickListener(view -> ((ViewPager) getActivity().findViewById(R.id.difficulty_pager)).setCurrentItem(FRAGMENT_EASY));
        changeRightButton.setOnClickListener(view -> ((ViewPager) getActivity().findViewById(R.id.difficulty_pager)).setCurrentItem(FRAGMENT_HARD));
    }

    private void setHardInformation() {
        textDifficultyHeader.setText(R.string.difficulty_hard);
        textDifficultyDescription.setText(R.string.difficulty_hard_description);
        textValueMaxWave.setText(PreferenceManager.getStatisticsValue(PreferenceManager.Statistics.HARD_MAX_WAVE));
        textValueEnemiesKilled.setText(PreferenceManager.getStatisticsValue(PreferenceManager.Statistics.HARD_ENEMIES_KILLED));
        textValueBuiltTowers.setText(PreferenceManager.getStatisticsValue(PreferenceManager.Statistics.HARD_BUILT_TOWERS));
        textValueUpgrades.setText(PreferenceManager.getStatisticsValue(PreferenceManager.Statistics.HARD_UPGRADES));
        textValueMoneySpent.setText(PreferenceManager.getStatisticsValue(PreferenceManager.Statistics.HARD_MONEY_SPENT));

        changeLeftButton.setOnClickListener(view -> ((ViewPager) getActivity().findViewById(R.id.difficulty_pager)).setCurrentItem(FRAGMENT_MEDIUM));
        changeRightButton.setOnClickListener(view -> ((ViewPager) getActivity().findViewById(R.id.difficulty_pager)).setCurrentItem(FRAGMENT_EASY));
    }
}
