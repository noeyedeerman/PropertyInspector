package sit374_team17.propertyinspector.Main;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.amazonaws.regions.Regions;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.Calendar;

import sit374_team17.propertyinspector.Activity_Settings;
import sit374_team17.propertyinspector.Note.Activity_Note_Edit;
import sit374_team17.propertyinspector.Note.Fragment_Note_List;
import sit374_team17.propertyinspector.Note.Note;
import sit374_team17.propertyinspector.Property.Activity_Property_Edit;
import sit374_team17.propertyinspector.Property.Fragment_Property;
import sit374_team17.propertyinspector.Property.Property;
import sit374_team17.propertyinspector.R;
import sit374_team17.propertyinspector.SavedPreference;
import sit374_team17.propertyinspector.User.Activity_Login;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Listener {

    Property mProperty;
    //Calendar required variables
    protected CognitoUser cognitoUser;
    protected CognitoUserPool userPool;
    int mYear, mMonth, mDay;
    //FloatingActionButton mFabProperty, mFab_AddNote, mFab_TextNote, mFab_PhotoNote;
    SearchView mSearchView;
    Listener mListener;
    Context context;
    EditText editText_note;
    private Fragment mSelectedFragment;
    private FragmentTransaction fragmentTransaction;

    FloatingActionMenu mFabMenu_Note, mFabMenu_CreateProperty;
    FloatingActionButton mFab_TextNote, mFab_PhotoNote;
    FloatingActionButton mFab_CreateProperty;


    Animation FabOpen, FabClose, FabRClockwise, FabRAntiClockwise;
    boolean isOpen = false;
    private int viewPagerCount;
    private boolean popBackStack;
    private FragmentManager fm;
    private MenuItem mNotesItem, mPropertyItem;
    private Note mNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        // mFabProperty = (FloatingActionButton) findViewById(R.id.fab_addProperty);
       // mFabMenu_CreateProperty = (FloatingActionMenu) findViewById(R.id.property_fab_menu);
        viewPagerCount = -1;

//        mFabMenu_CreateProperty.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                //TODO something when floating action menu first item clicked
//                Intent intent = new Intent(MainActivity.this, Activity_Property_Edit.class);
//                startActivity(intent);
//
//            }
//        });

        mFab_CreateProperty = (FloatingActionButton) findViewById(R.id.fab_create_property);

        mFab_CreateProperty.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goTo_EditPropertyActivity();
            }
        });
//


        mFabMenu_Note = (FloatingActionMenu) findViewById(R.id.fab_menu_note);
        mFab_TextNote = (FloatingActionButton) findViewById(R.id.fab_text_note);
        mFab_PhotoNote = (FloatingActionButton) findViewById(R.id.fab_photo_note);


        mFabMenu_Note.hideMenu(false);
        mNote=new Note();
        mFab_TextNote.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mNote.setCommentType("text");
                goTo_NoteEditActivity(mNote, "don't");
            }
        });
        mFab_PhotoNote.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mNote.setCommentType("photo");
                goTo_NoteEditActivity(mNote, "do");

            }
        });
      //  mFabMenu_CreateProperty.setClosedOnTouchOutside(true);
        mFabMenu_Note.setClosedOnTouchOutside(true);


///////////////////////////////////////////////////////


//        mFab_AddNote = (FloatingActionButton) findViewById(R.id.fab_addNote);
//        mFab_TextNote = (FloatingActionButton) findViewById(R.id.fab_textNote);
//        mFab_PhotoNote = (FloatingActionButton) findViewById(R.id.fab_photoNote);
//
//        final TextView mTextView_textNote = (TextView) findViewById(R.id.textView_textNote);
//        final TextView mTextView_photoNote = (TextView) findViewById(R.id.textView_photoNote);
//
//        final LinearLayout mLayout_textNote = (LinearLayout) findViewById(R.id.layout_textNote);
//        final LinearLayout mLayout_photoNote = (LinearLayout) findViewById(R.id.layout_photoNote);

        FabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        FabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        FabRClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
        FabRAntiClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);

//        mLayout_textNote.setVisibility(View.GONE);
//        mLayout_photoNote.setVisibility(View.GONE);
//
//
//        mFab_AddNote.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mLayout_textNote.getVisibility() == View.VISIBLE && mLayout_photoNote.getVisibility() == View.VISIBLE) {
//                    mLayout_textNote.setVisibility(View.GONE);
//                    mLayout_photoNote.setVisibility(View.GONE);
//
//                    mFab_TextNote.startAnimation(FabClose);
//                 //   mFab_TextNote.setClickable(false);
//
//                    mFab_PhotoNote.startAnimation(FabClose);
//                   // mFab_PhotoNote.setClickable(false);
//
//                    //mTextView_textNote.startAnimation(FabClose);
////                    mTextView_textNote.setClickable(false);
////
////
//                  // mTextView_photoNote.startAnimation(FabClose);
////                    mTextView_photoNote.setClickable(false);
//
//                } else {
//                    mLayout_textNote.setVisibility(View.VISIBLE);
//                    mLayout_photoNote.setVisibility(View.VISIBLE);
//
//                    mFab_TextNote.startAnimation(FabOpen);
//                 //   mFab_TextNote.setClickable(true);
//
//                    mFab_PhotoNote.startAnimation(FabOpen);
//                   // mFab_PhotoNote.setClickable(true);
//
//                //    mTextView_textNote.startAnimation(FabOpen);
////                    mTextView_textNote.setClickable(true);
////
//                 //   mTextView_photoNote.startAnimation(FabOpen);
////                    mTextView_photoNote.setClickable(true);
//                }
//            }
//        });
//
//        mFab_TextNote.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Text Note!", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        mFab_PhotoNote.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Photo Note!", Toast.LENGTH_SHORT).show();
//            }
//        });


//
//        mFab_AddNote.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(isOpen){
//                    mFab_TextNote.startAnimation(FabClose);
//                    mFab_TextNote.setClickable(false);
//
//                    mFab_PhotoNote.startAnimation(FabClose);
//                    mFab_PhotoNote.setClickable(false);
//
//                    mFab_AddNote.startAnimation(FabRAntiClockwise);
//
//                    isOpen = false;
//                } else {
//                    mFab_TextNote.startAnimation(FabOpen);
//                    mFab_TextNote.setClickable(true);
//
//                    mFab_PhotoNote.startAnimation(FabOpen);
//                    mFab_PhotoNote.setClickable(true);
//
//                    mFab_AddNote.startAnimation(FabRClockwise);
//
//                    isOpen = true;
//                }
//              //  ScrollView myScroller = (ScrollView) findViewById(R.id.scrollView_property);
//             //   int[] scrollLocation = {0,0};
//                //  ObjectAnimator.ofInt(myScroller, "scrollY",  myScroller.getChildAt(0).getBottom()).setDuration(600).start();
//              //  ObjectAnimator.ofInt(myScroller, "scrollY",  myScroller.findViewById(R.id.commentView).getBottom()+1600).setDuration(600).start();
//                //ObjectAnimator.ofInt(myScroller, "scrollY",  200).setDuration(600).start();
//            }
//        });
        // mFab_AddNote.hide();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        goTo_HomeFragment(getCurrentFocus());
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        // Create a CognitoUserPool object to refer to your user pool
        userPool = new CognitoUserPool(this, Activity_Login.userPoolId, Activity_Login.clientId, Activity_Login.clientSecret, clientConfiguration, Regions.AP_SOUTHEAST_2);
        // Create a CognitoUserPool object to refer to your user pool
        cognitoUser = userPool.getCurrentUser();
    }


    public void goTo_HomeFragment(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() < 1) {
            Fragment_Home fragment = Fragment_Home.newInstance("param1", "param2");
            replaceFragment(fragment, "Fragment_Home");
        }
    }

    public void goTo_EditPropertyActivity() {
        Intent intent = new Intent(MainActivity.this, Activity_Property_Edit.class);
        intent.putExtra("tokens",getIntent().getStringExtra("tokens"));
        startActivity(intent);
      //  mFabMenu_CreateProperty.close(true);
    }

    public void goTo_PropertyFragment(Property property) {
        Fragment_Property fragment = Fragment_Property.newInstance(property);
        replaceFragment(fragment, "Fragment_Property");
        mSelectedFragment = fragment;
        viewPagerCount = 0;
        mFab_CreateProperty.hide(false);
        mFabMenu_Note.showMenu(false);
    }

    public void goTo_NoteEditActivity(Note note, String openCamera) {
        Intent intent = new Intent(MainActivity.this, Activity_Note_Edit.class);
        intent.putExtra("property", mProperty);
        intent.putExtra("note", note);
        intent.putExtra("methodName", openCamera);
        startActivityForResult(intent, 1);
        mFabMenu_Note.close(true);
    }

    private void replaceFragment(Fragment fragment, String tag) {
        try {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            if (tag == "Fragment_Property") {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                FragmentManager manager = getSupportFragmentManager();
                Fragment_Note_List fragment_note_list = (Fragment_Note_List) manager.findFragmentById(R.id.content_main);
                fragment_note_list.refreshNotes();
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.

                // Do something with the contact here (bigger example below)
            }
        }

    }

    //    public void goTo_NoteEditActivity() {
//
//        Intent intent = new Intent(MainActivity.this, Activity_Note_Edit.class);
//        intent.putExtra("property", mProperty);
//
//        startActivity(intent);
//        mFabMenu_Note.close(true);
////        Fragment_Note_Edit fragment = Fragment_Note_Edit.newInstance(property);
////        replaceFragment(fragment, "Fragment_Note_Edit");
////        mFabMenu_Note.hideMenu(false);
//
//
//    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        fm = getSupportFragmentManager();

        if (fm.getBackStackEntryCount() != 0) {
            Fragment_Property fragment_property = (Fragment_Property) fm.findFragmentById(R.id.content_main);
            fragment_property.onBackPressed();
        } else if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    
    


    @Override
    public void addToBackStack() {
        fragmentTransaction.addToBackStack(null);
    }

    @Override
    public void viewPagerCount(int count) {
        viewPagerCount = count;
    }

    @Override
    public void popBackStack(boolean bool) {
        //  popBackStack = bool;
        fm.popBackStack();
        mFabMenu_Note.hideMenu(false);
     //   mFabMenu_CreateProperty.showMenu(false);
   //     mFab_CreateProperty.show();
        mFab_CreateProperty.show(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
      //  mNotesItem = menu.findItem(R.id.action_notes);
     //   mNotesItem.setVisible(false);
      //  mPropertyItem = menu.findItem(R.id.action_property);
        //mNotesItem.setVisible(false);
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
                            Toast.makeText(getApplicationContext(), year + "/" + (monthOfYear + 1) + "/" + dayOfMonth, Toast.LENGTH_LONG).show();
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        } else if (id == R.id.nav_privacySettings) {

        } else if (id == R.id.nav_alert) {

        } else if (id == R.id.nav_password) {
            changePassword();
        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(MainActivity.this, Activity_Settings.class);
            startActivity(intent);
        } else if (id == R.id.nav_help) {

        } else if (id == R.id.nav_logout) {
            SavedPreference savedPreference=new SavedPreference(this);
            savedPreference.deleteCredentials();
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
     //   FragmentManager manager = getSupportFragmentManager();
      //  Fragment toggleFragment = (ToggleFragment) manager.findFragmentById(R.id.content_main);
     //   toggleFragment.deleteToggle(id);
    }

    @Override
    public void refreshNotes() {

    }

    @Override
    public void onHomeInteraction() {

    }

    @Override
    public void onPropertyClicked(Property property) {
        mProperty = property;
        goTo_PropertyFragment(property);
    }

    @Override
    public void onNoteClicked(Note note) {
        goTo_NoteEditActivity(note, "don't");
    }

    AlertDialog alert;
    Button bt_done;
    EditText edt_new, edt_confirm, edt_current;

    void changePassword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_password, null);
        builder.setView(dialogView);
        bt_done = (Button) dialogView.findViewById(R.id.bt_change);
        edt_new = (EditText) dialogView.findViewById(R.id.edt_new);
        edt_current = (EditText) dialogView.findViewById(R.id.edt_current);
        edt_confirm = (EditText) dialogView.findViewById(R.id.edt_confirm);
        bt_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_new.getText().toString().equals("") || edt_new.getText().toString().equals("") || edt_current.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(), getString(R.string.error_old_password), Toast.LENGTH_SHORT).show();
                else if (!edt_current.getText().toString().equals(getIntent().getStringExtra("password")))
                    Toast.makeText(getApplicationContext(), getString(R.string.error_new_password), Toast.LENGTH_SHORT).show();
                else if (!edt_new.getText().toString().equals(edt_confirm.getText().toString()))
                    Toast.makeText(getApplicationContext(), getString(R.string.error_new_password), Toast.LENGTH_SHORT).show();
                else if (edt_new.getText().toString().length() < 6)
                    Toast.makeText(getApplicationContext(), getString(R.string.error_length_password), Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(getApplicationContext(), "Password changing..Please wait", Toast.LENGTH_SHORT).show();
                    new Thread() {
                        @Override
                        public void run() {
                            GenericHandler handler = new GenericHandler() {

                                @Override
                                public void onSuccess() {
                                    // Password change was successful!
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(), "Password change was successful!", Toast.LENGTH_SHORT).show();
                                            alert.dismiss();
                                        }
                                    });

                                }

                                @Override
                                public void onFailure(Exception exception) {
                                    // Password change failed, probe exception for details
                                    Log.e(getClass().getName(), exception.toString());
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(), "Password change failed", Toast.LENGTH_SHORT).show();
                                            alert.dismiss();
                                        }
                                    });
                                }
                            };
                            cognitoUser.changePassword(edt_current.getText().toString(), edt_new.getText().toString(), handler);
                        }
                    }.start();
                }
            }
        });
        alert = builder.create();
        alert.show();
    }


    public SearchView getSearchView() {
        return mSearchView;
    }

}