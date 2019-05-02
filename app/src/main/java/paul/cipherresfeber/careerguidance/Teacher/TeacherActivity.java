package paul.cipherresfeber.careerguidance.Teacher;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import paul.cipherresfeber.careerguidance.R;
import paul.cipherresfeber.careerguidance.Teacher.Adapters.PageAdapter;

public class TeacherActivity extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        // referencing to the views
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        // array of icons to be used in tab layout
        int[] icons = new int[]{
                R.drawable.ic_dashboard,
                R.drawable.ic_new_question_paper
        };

        // setting the icons to the tab layouts
        for(int icon: icons){
            tabLayout.addTab(tabLayout.newTab().setIcon(icon));
        }

        // page adapter as controller
        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);

        // changing page using swipe in view pager
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab){}
            @Override
            public void onTabReselected(TabLayout.Tab tab){}
        });

        // the teacher dashboard is set as default
        viewPager.setCurrentItem(0);

    }

}
