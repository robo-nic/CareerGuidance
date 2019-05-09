package paul.cipherresfeber.careerguidance.Student;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import paul.cipherresfeber.careerguidance.R;
import paul.cipherresfeber.careerguidance.Registration.RegistrationActivity;
import paul.cipherresfeber.careerguidance.Student.MainFragments.AttemptTest;
import paul.cipherresfeber.careerguidance.Student.MainFragments.Dashboard;
import paul.cipherresfeber.careerguidance.Student.MainFragments.Profile;

public class StudentActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // first time set the Dashboard as the default fragment
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer,new Dashboard()).commit();
            getSupportActionBar().setTitle("Dashboard");
            navigationView.setCheckedItem(R.id.navDashboard);
        }

        // set the user name, profile picture and contact detail
        View headerView = navigationView.getHeaderView(0);
        TextView textViewUserProfilePicture = headerView.findViewById(R.id.txvUserProfilePicture);
        TextView textViewUserName = headerView.findViewById(R.id.txvUserName);
        TextView textViewUserEmail = headerView.findViewById(R.id.txvUserEmail);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        textViewUserEmail.setText(user.getEmail());
        textViewUserName.setText(user.getDisplayName().split("@")[0]);
        textViewUserProfilePicture.setText(String.valueOf(user.getDisplayName().split("@")[0].charAt(0)));

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.navDashboard:
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.fade_in_frag, R.anim.fade_out_frag)
                        .replace(R.id.fragmentContainer, new Dashboard()).commit();
                getSupportActionBar().setTitle("Dashboard");
                break;
            case R.id.navAttemptTest:
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.fade_in_frag, R.anim.fade_out_frag)
                        .replace(R.id.fragmentContainer, new AttemptTest()).commit();
                getSupportActionBar().setTitle("Attempt Test");
                break;
            case R.id.navProfile:
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.fade_in_frag, R.anim.fade_out_frag)
                        .replace(R.id.fragmentContainer, new Profile()).commit();
                getSupportActionBar().setTitle("Profile");
                break;
            case R.id.navLogout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(StudentActivity.this, RegistrationActivity.class));
                StudentActivity.this.finish();
                break;
            case R.id.navEmail:
                // call an intent to gmail app
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + "cipherresfeber@gmail.com"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Career Guidance Report");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(emailIntent, "Chooser Title"));
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



}
