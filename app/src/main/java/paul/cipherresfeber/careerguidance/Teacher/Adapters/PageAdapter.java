package paul.cipherresfeber.careerguidance.Teacher.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import paul.cipherresfeber.careerguidance.Teacher.MainFragments.Dashboard;
import paul.cipherresfeber.careerguidance.Teacher.MainFragments.NewQuestionPaper;

public class PageAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;


    // constructor
    public PageAdapter(FragmentManager fm, int numberOfTabs){
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new NewQuestionPaper();
            case 1:
                return new Dashboard();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
