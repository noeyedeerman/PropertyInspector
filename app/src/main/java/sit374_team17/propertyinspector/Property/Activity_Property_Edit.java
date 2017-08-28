package sit374_team17.propertyinspector.Property;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import sit374_team17.propertyinspector.R;

public class Activity_Property_Edit extends AppCompatActivity {
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final FragmentManager fm = getSupportFragmentManager();
        // Initialises fragment
        Button button_continue = (Button) findViewById(R.id.button_continue);
        button_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mListener.onContinue1();

                switch (fm.getBackStackEntryCount()) {
                    case 0:
                        goTo_PropertyEditFragment2();
                        break;
                    case 1:
                        goTo_PropertyEditFragment3();
                        break;

                }

                //  fm.getBackStackEntryCount()
                // goTo_CreatePropertyFragment2();
            }
        });

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        goTo_PropertyEditFragment1();
    }


    // Passes "CreateGroupFragment" fragment to the "replaceFragment" method.
    public void goTo_PropertyEditFragment1() {
        Fragment_Property_Edit_1 fragment = new Fragment_Property_Edit_1();
        replaceFragment(fragment, "Fragment_Property_Edit_1");
    }

    // Passes "CreateGroupFragment" fragment to the "replaceFragment" method.
    public void goTo_PropertyEditFragment2() {
        Fragment_Property_Edit_2 fragment = new Fragment_Property_Edit_2();
        replaceFragment(fragment, "Fragment_Property_Edit_2");
    }

    // Passes "CreateGroupFragment" fragment to the "replaceFragment" method.
    public void goTo_PropertyEditFragment3() {
        Fragment_Property_Edit_3 fragment = new Fragment_Property_Edit_3();
        replaceFragment(fragment, "Fragment_Property_Edit_3");
    }
//
//    // Passes "AddPersonFragment" fragment to the "replaceFragment" method.
//    public void goTo_AddPersonFragment(Person person) {
//        Fragment_AddPerson fragment = Fragment_AddPerson.newInstance(person);
//        replaceFragment(fragment, "Fragment_AddPerson");
//    }

    // Receives fragment and starts transaction to replace fragments
    private void replaceFragment(android.support.v4.app.Fragment fragment, String tag) {
        try {
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            // Adds animation to the transaction and adds fragment to back stack
            // if (Objects.equals(tag, "Fragment_Property_Edit_2")) {
            if (tag == "Fragment_Property_Edit_2" || tag == "Fragment_Property_Edit_3") {
                fragmentTransaction.setCustomAnimations(R.anim.enter_left, R.anim.exit_right, R.anim.exit_left, R.anim.enter_right);
                fragmentTransaction.addToBackStack(null);
            }

            fragmentTransaction.replace(R.id.content_create_property, fragment, tag);
            fragmentTransaction.commit();

        } catch (Exception e) {
            Log.d(tag, e.toString());
        }
    }


}
