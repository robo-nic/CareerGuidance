package paul.cipherresfeber.careerguidance.Registration;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import paul.cipherresfeber.careerguidance.Constants.Extra;
import paul.cipherresfeber.careerguidance.R;
import paul.cipherresfeber.careerguidance.Registration.Adapters.PageAdapter;
import paul.cipherresfeber.careerguidance.Student.StudentActivity;
import paul.cipherresfeber.careerguidance.Teacher.TeacherActivity;

public class RegistrationActivity extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // if user is already logged in, try opening the profile
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null){
            String name = firebaseUser.getDisplayName();
            if(name.split("@")[1].equals(Extra.USER_TYPE_TEACHER)){
                startActivity(new Intent(RegistrationActivity.this, TeacherActivity.class));
                RegistrationActivity.this.finish();
            } else{
                startActivity(new Intent(RegistrationActivity.this, StudentActivity.class));
                RegistrationActivity.this.finish();
            }

        }

        // referencing to the views
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        // label to be used in tab layout
        String[] labels = new String[]{
                "LOGIN",
                "SIGN UP"
        };

        // setting the icons to the tab layouts
        for(String label: labels){
            tabLayout.addTab(tabLayout.newTab().setText(label));
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
