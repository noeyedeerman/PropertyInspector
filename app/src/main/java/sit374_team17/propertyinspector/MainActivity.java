package sit374_team17.propertyinspector;

import android.animation.ObjectAnimator;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.Calendar;

import static sit374_team17.propertyinspector.Fragment_CreateProperty.newInstance;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Listener {

    Property mProperty;
    //Calendar required variables
    int mYear, mMonth, mDay;
    FloatingActionButton mFabProperty, mFabNote;
    SearchView mSearchView;
    Listener mListener;
Context context;

    EditText editText_note;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        mFabProperty = (FloatingActionButton) findViewById(R.id.fab_addProperty);
        mFabProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProperty = new Property();
                goTo_CreatePropertyFragment(getCurrentFocus(), new Property(-1));
                mFabProperty.hide();
            }
        });

        mFabNote = (FloatingActionButton) findViewById(R.id.fab_addNote);

        mFabNote.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            @Override
            public void onClick(View view) {

                ScrollView myScroller = (ScrollView) findViewById(R.id.scrollView_property);
int[] scrollLocation = {0,0};
              //  ObjectAnimator.ofInt(myScroller, "scrollY",  myScroller.getChildAt(0).getBottom()).setDuration(600).start();
                ObjectAnimator.ofInt(myScroller, "scrollY",  myScroller.findViewById(R.id.commentView).getBottom()+1600).setDuration(600).start();
                //ObjectAnimator.ofInt(myScroller, "scrollY",  200).setDuration(600).start();
            }
        });
        mFabNote.hide();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        gotTo_HomeFragment(getCurrentFocus());

    }


    public void gotTo_HomeFragment(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragmentManager.getBackStackEntryCount() < 1) {
            Fragment_Home fragment = Fragment_Home.newInstance("param1", "param2");
            replaceFragment(fragment, "Fragment_Home");
        }
    }

    public void goTo_CreatePropertyFragment(View view, Property property) {
        Fragment_CreateProperty fragment = newInstance(property);
        replaceFragment(fragment, "Fragment_CreateProperty");
    }

    public void goTo_PropertyFragment(Property property) {

        Fragment_Property fragment = Fragment_Property.newInstance(property);
        replaceFragment(fragment, "Fragment_Property");
        mFabNote.show();


    }

    public void goTo_AddNoteFragment() {

        Fragment_CreateNote fragment = Fragment_CreateNote.newInstance();
        replaceFragment(fragment, "Fragment_CreateNote");
       // mFabNote.hide();


    }

    private void replaceFragment(Fragment fragment, String tag) {
        try {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();


            if (tag == "Fragment_CreateProperty" || tag == "Fragment_CreateNote") {
                fragmentTransaction.setCustomAnimations(R.anim.enter_up, R.anim.exit_down, R.anim.exit_up, R.anim.enter_down);
                fragmentTransaction.addToBackStack(null);
            } else if (tag == "Fragment_Property") {
                fragmentTransaction.setCustomAnimations(R.anim.enter_left, R.anim.exit_right, R.anim.exit_left, R.anim.enter_right);
                fragmentTransaction.addToBackStack(null);
            }

            fragmentTransaction.replace(R.id.content_main, fragment, tag);
            fragmentTransaction.commit();

        } catch (Exception e) {
            Log.d(tag, e.toString());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        } else if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        if (fm.getBackStackEntryCount() == 1) {
            mFabProperty.show();
            mFabNote.hide();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//      //  int id = item.getItemId();
//
//       // return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_calendar) {
            // Handle the camera action
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            Toast.makeText(getApplicationContext(),year+"/"+(monthOfYear + 1)+"/"+dayOfMonth,Toast.LENGTH_LONG).show();
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        } else if (id == R.id.nav_privacySettings) {

        } else if (id == R.id.nav_alert) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_help) {

        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(MainActivity.this, Activity_Login.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSaveProperty() {
        onBackPressed();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    public void onSaveComment() {
//        FragmentManager manager = getSupportFragmentManager();
//        Fragment toggleFragment = (ToggleFragment) manager.findFragmentById(R.id.content_main);
//        toggleFragment.deleteToggle(id);
    }


    @Override
    public void onHomeInteraction() {

    }

    @Override
    public void onItemClicked(Property property) {
        goTo_PropertyFragment(property);

        mFabProperty.hide();

    }




    public SearchView getSearchView() {
        return mSearchView;
    }

}